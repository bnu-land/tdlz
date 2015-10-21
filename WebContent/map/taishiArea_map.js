require([
    "esri/map",
    "esri/graphic",
    "esri/config",
    "esri/Color",
    "esri/urlUtils",
    "esri/geometry/Point",
    "esri/geometry/Extent",
    "esri/SpatialReference",
    "esri/layers/FeatureLayer",
    "esri/InfoTemplate",
    "esri/symbols/SimpleFillSymbol",
    "esri/symbols/SimpleMarkerSymbol",
    "esri/symbols/SimpleLineSymbol",

    "esri/renderers/ClassBreaksRenderer",
    "esri/renderers/SimpleRenderer",
    "esri/renderers/DotDensityRenderer",
    "esri/renderers/ScaleDependentRenderer",

    "esri/tasks/query",
    "esri/tasks/find",
    "esri/TimeExtent",

    "esri/dijit/InfoWindowLite",  //弹出对话框
    "esri/dijit/Popup",           //弹出对话框
    "esri/dijit/PopupTemplate",   //弹出对话框模板
    "esri/dijit/HomeButton",  //Home按钮
    "esri/dijit/Scalebar",    //比例尺
    "esri/dijit/LayerSwipe",  //图层卷帘效果
    "esri/dijit/TimeSlider",        //时间轴
    "esri/dijit/Legend",        //图例

    "dojo/_base/array",
    "dojo/parser",
    "dojo/dom",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/dom-construct",

    "dojo/on",
    "dojo/number",
    "dojo/string",

    "dojo/domReady!"
], function (Map, Graphic, esriConfig, Color, urlUtils, Point, Extent, SpatialReference, FeatureLayer, InfoTemplate, SimpleFillSymbol, SimpleMarkerSymbol, SimpleLineSymbol, ClassBreakRenderer, SimpleRenderer, DotDensityRenderer, ScaleDependentRenderer, Query, TimeExtent, InfoWindowLite, Popup, PopupTemplate, HomeButton, Scalebar, LayerSwipe, TimeSlider, Legend, arrayUtils, parser, dom, domClass, domStyle, domConstruct, on, number, String) {

    var map;
    var mapServiceLayer;
    var navigation; //导航
    var popup;
    var template;
    var renderer;   //设置的渲染样式
    var mapSr;   // 当前地图的空间参考系
    var showLayerGroupIndex;     //显示的图层组的index
    var featureLayer;   //采样点的FeatureLayer
    var findTask, findParams;   //查询要素
    var store;      //图层渲染使用的数据store
    var params = [];     //查询的参数，1、统计类型；2、搜索关键词；3、开始时间；4、结束时间
    var renderByName = "";    //统计分析的方式，比如交易量volume，交易次数number，交易价格price
    var renderType = 'symbol';         //专题图渲染类型
    var renderArea = "";     //统计分析的区域
    var renderDateValues = ["", ""];   //统计分析开始和结束的时间
    var graphicLayer;  //map的GraphicLayer层
    var infoTemplate;   //属性框
    var legend;         //地图的图例
    var mapExtent;     //默认显示的地图范围
    var spatialReference;   //坐标系
    var minDataValue = 0;    //渲染最小值
    var maxDataValue = 0;   //渲染最大值
    var isFireSliderChange = false;
    var timeSlider;
    var minValueSlider;     //时间轴最小值
    var maxValueSlider;     //时间轴最大值
    var mapURL;

    esriConfig.defaults.io.corsDetection = false;
    esriConfig.defaults.io.proxyUrl = "map/proxy/proxy.jsp";
    //esriConfig.defaults.io.alwaysUseProxy = false;

    dojo.addOnLoad(initMap);

    //-------------------------------------地图初始化-------------------------------------------------------------------
    function initMap() {
        //清空地图内容，再次添加
        if (map) {
            map.destroy();
        }
        map = new Map("mapDiv_taishiArea", {
            logo: false
        });
        mapURL = MAPURLS["csqzjxzq"];   //获取地图url
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapURL);
        map.addLayer(mapServiceLayer);

        mapServiceLayer.on("load", function () {
            mapExtent = mapServiceLayer.fullExtent;
        });

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            console.log("Map onLoad event.");
            //initSlider();       //初始化timeSlider
            graphicLayer = map.graphics;    //设置graphicLayer层

            Ext.getCmp('taishi_minDate').setValue('2014-01-01');

            //设置专题图
            Ext.getCmp('radioGroup_taishiArea').setValue({
                'taishiArea': 'volume'
            });
        });

        //绑定事件
        Ext.getCmp('radioGroup_taishiArea').on('change', AreaMapRadioChange);           //radioGroup_taishiArea事件绑定
        Ext.getCmp('radioGroup_taishiRender').on('change', RenderTypeRadioChange);      //taishiRender事件绑定
        Ext.getCmp('taishi_maxDate').on('change', DateFilterChange);      //taishiRender事件绑定
    }

    //---------------界面函数事件------------------------------------------
    function AreaMapRadioChange(field, newValue, oldValue, eOpts) {
        renderByName = newValue.taishiArea;	//设置统计渲染的方式，比如统计量，价格等。由radioGroup的change事件触发
        InitStatisticAndRenderMap();		//渲染地图
        //timeSlider.setValues['2014-01-01','2014-06-31'];
    }

    function RenderTypeRadioChange(field, newValue, oldValue, eOpts) {
        renderType = newValue.renderType;	//设置专题图类型
        InitStatisticAndRenderMap();		//渲染地图
    }

    function DateFilterChange(dateCmp, newValue, oldValue, eOpts) {
        renderDateValues[0] = Ext.getCmp('taishi_minDate').getValue();
        renderDateValues[1] = newValue;
        InitStatisticAndRenderMap();
    }

    //函数入口------------------------------------------------------
    InitStatisticAndRenderMap = function () {
        //console.log("渲染方式：", renderByName);
        //console.log("renderDate:", renderDateValues);
        //获取并设置参数
        if (!renderByName || renderByName == "") {
            return; //如果渲染方式没有确定，则返回
        }
        //判断是否得到起始时间
//        if(isNaN(renderDateValues[0]) || !isNaN(renderDateValues[1])){
//            Ext.MessageBox.alert("提示", "请设置统计事件。");
//            return;
//        }
        //渲染字段的最大值初始化
        maxDataValue = 0;
        //清除渲染
        graphicLayer.setRenderer(null);
        //设置渲染区域
        renderArea = Ext.getCmp("taishiArea_renderArea").getValue();
        //设置数据store
        store = SetStore();
        store.load({
            params: {
                searchKeyword: renderArea,  //设置地区范围
                searchDate1: renderDateValues[0],    //不设置起始时间，需要查询该地区所有事件范围
                searchDate2: renderDateValues[1]     //同上
            }
        });
        //console.log("store:", store);
        //渲染地图
        SetLayerRenderAttr();
    }


    //设置本次渲染的数据store
    SetStore = function () {
        var mystore;
        switch (renderByName) {
            case "volume":
                mystore = Ext.StoreMgr.get('TaishiVolume_Pie'); //获得store对象
                break;
            case "number":
                mystore = Ext.StoreMgr.get('TaishiNumber_Pie'); //获得store对象
                break;
            case "price":
                mystore = Ext.StoreMgr.get('TaishiPrice'); //获得store对象
                break;
        }
        return mystore;
    }

    //获取并设置图层渲染字段的值
    SetLayerRenderAttr = function () {
        //create find task with url to map service
        findTask = new esri.tasks.FindTask(mapURL);

        //create find parameters and define known values
        findParams = new esri.tasks.FindParameters();
        findParams.returnGeometry = true;
        findParams.layerIds = [0];
        findParams.searchFields = ["xzqmc"];

        findParams.searchText = "长寿区";
        findTask.execute(findParams, showResults);

        function showResults(results) {
            map.graphics.clear();       //情况已有的graphic
            //Build an array of attribute information and add each found graphic to the map
            dojo.forEach(results, function (result) {
                var graphic = result.feature;
                var featureAttributes = result.feature.attributes;

                var xzqField = featureAttributes["xzqzj"];  //取得要素的行政区属性
                var record = store.findRecord("jyqy", xzqField, 0, true);   //在store中查询，字段为jyqy，值为行政区名称，0为起始index，true为任意匹配

                var fieldName = '';     //存储渲染数据的字段
                var fieldValue = 0;      //该渲染字段的值
                if (record) {
                    switch (renderByName) {
                        case "volume":
                            fieldName = 'jylsum';
                            break;
                        case "number":
                            fieldName = 'taicount';
                            break;
                        case "price":
                            fieldName = 'djavg';
                            break;
                    }
                    fieldValue = record.get(fieldName); //获取渲染字段的值
                    dateValue = record.get('jysj'); //获取交易时间
                    featureAttributes["value"] = fieldValue;    //设置渲染字段的值
                    if (fieldValue > maxDataValue) {
                        maxDataValue = fieldValue;      //取得渲染字段的最大值
                    }
//                    var dateMax = new Date(maxValueSlider);
//                    var dateMin = new Date(minValueSlider);
//                    var date = new Date(dateValue);
                }
                var infoContent;
                switch (renderByName) {
                    case "volume":
                        infoContent = "<div style='font: 18px Segoe UI'>该区域的交易量为 <b>${value:NumberFormat(places:2)}公顷</b>.</div>";
                        break;
                    case "number":
                        infoContent = "<div style='font: 18px Segoe UI'>该区域的交易次数为 <b>${value:NumberFormat(places:0)}次</b>.</div>";
                        break;
                    case "price":
                        infoContent = "<div style='font: 18px Segoe UI'>该区域的交易价格为 <b>${value:NumberFormat(places:2)}万元/公顷</b>.</div>";
                        break;
                }
                infoTemplate = new InfoTemplate("${xzqzj}", infoContent);
//              graphic.setAttributes(featureAttributes);    //设置属性
                graphic.setInfoTemplate(infoTemplate);  //设置属性框
                graphicLayer.add(graphic);
            });


            //设置渲染方式
            renderer = SetRenderType();
            if (!renderer) {
                return;
            }
            graphicLayer.setRenderer(renderer);

            if (mapExtent) {
                map.setExtent(mapExtent);
                //legend.refresh();
            }
            //设置鼠标滑过显示查询框
            dojo.connect(map.graphics, "onMouseOver", function (evt) {
                var g = evt.graphic;
                map.infoWindow.setContent(g.getContent());
                map.infoWindow.setTitle(g.getTitle());
                map.infoWindow.show(evt.screenPoint, map.getInfoWindowAnchor(evt.screenPoint));
            });
            dojo.connect(map.graphics, "onMouseOut", function () {
                map.infoWindow.hide();
            });

//            //图例
//            if (!legend) {
//                legend = new Legend({
//                    map: map,
//                    layerInfos: [
//                        { title: "图例", layer: graphicLayer }
//                    ]
//                }, "legend_taishi");
//            }
//            legend.startup();
        }
    }

    //设置渲染方式
    function SetRenderType() {
        var renderer;

        switch (renderType) {
            case "color":
                var colorFrom;
                var colorTo;
                switch (renderByName) {
                    case "volume":
                        colorFrom = new Color([0, 220, 100, 0.5]);
                        colorTo = new Color([220, 120, 0, 0.5]);
                        break;
                    case "number":
                        colorFrom = new Color([125, 188, 252, 0.5]);
                        colorTo = new Color([220, 120,0, 0.5]);
                        break;
                    case "price":
                        colorFrom = new Color([70, 220, 248, 0.5]);
                        colorTo = new Color([220, 120, 0, 0.5]);
                        break;
                    default :
                        colorFrom = new Color([0, 220, 0, 0.5]);
                        colorTo = new Color([220, 0, 0, 0.5]);
                        break;
                }
                renderer = new SimpleRenderer(new SimpleFillSymbol().setOutline(new SimpleLineSymbol().setWidth(0.5)));
                renderer.setColorInfo({
                    field: "value",
                    minDataValue: minDataValue,
                    maxDataValue: maxDataValue,
                    colors: [
                        colorFrom,
                        colorTo
                    ]
                });


                break;
            case "symbol":
                var colorSymbol;
                switch (renderByName) {
                    case "volume":
                        colorSymbol = new Color([0, 220, 30, 0.5]);
                        break;
                    case "number":
                        colorSymbol = new Color([220, 0, 30, 0.5]);
                        break;
                    case "price":
                        colorSymbol = new Color([0, 30, 220, 0.5]);
                        break;
                    default :
                        colorSymbol = new Color([0, 179, 30, 0.5]);
                        break;
                }
                var markerSym = new SimpleMarkerSymbol();
                markerSym.setColor(colorSymbol);
                markerSym.setOutline(markerSym.outline.setColor(new Color([133, 197, 133, 0.75])));

                var renderer1 = new SimpleRenderer(markerSym);
                var proportionalSymbolInfo = {
                    field: "value",
                    minSize: 10,
                    maxSize: 80,
                    minDataValue: minDataValue,
                    maxDataValue: maxDataValue
                };
                renderer1.setProportionalSymbolInfo(proportionalSymbolInfo);

                var params = {rendererInfos: [
                    {
                        "renderer": renderer1,
                        "minScale": 650000,
                        "maxScale": 100000
                    }
                ]};

                renderer = new ScaleDependentRenderer(params);

                break;
            case "dot":
                renderer = new ScaleDependentRenderer({
                    rendererInfos: [
                        {
                            renderer: new DotDensityRenderer({
                                fields: [
                                    {
                                        name: "value",
                                        color: new Color("#FF0000")
                                    }
                                ],
                                dotValue: 1,
                                dotSize: 2
                            }),
                            maxScale: 100000,
                            minScale: 650000
                        }
                    ]
                });
                break;
        }
        return renderer;
    }

    function GetMaxValueOfStore(myStore, field) {
        var maxId = 0;
        if (myStore.getCount() > 0) {
            maxId = myStore.getAt(0).get(field); // initialise to the first record's id value.
            myStore.each(function (rec) // go through all the records
            {
                maxId = Math.max(maxId, rec.get(field));
            });
        }
        return maxId;
    }

//清除map上的graphics
    ClearGraphics = function () {
        map.graphics.clear();
    }

//初始化时间轴
    function initSlider() {
        timeSlider = null;
        //console.log("加载timeSlider");
        timeSlider = Ext.create('Ext.slider.Multi', {
//            width: 600,
            //values: [25, 50, 75],
            //increment: 5,
//            minValue: 0,
//            maxValue: 100,
            useTips: true,
            values: ['2014-01-01', '2014-06-30'],
            style: {
                width: '100%'
            },
            dateFormat: 'Y-m-d',
            config: {
                dateFormat: 'Y-m-d',
                dateIncrement: Ext.Date.DAY,
                minDate: '2014-01-01',
                maxDate: '2014-06-30',
                values: ['2014-01-01', '2014-06-30']
            },

            convertToUnix: function (date) {
                var pd = Ext.Date.parse(date, this.dateFormat);
                return Number(Ext.Date.format(new Date(pd), 'U'));
            },

            convertFromUnix: function (dt) {
                var pd = Ext.Date.parse(dt, 'U');
                return Ext.Date.format(new Date(pd), this.dateFormat);
            },

            constructor: function (config) {
                //console.log("config before foreach:", config);
                var format = (config.dateFormat || this.config.dateFormat);
                this.dateFormat = format;
                config.dateFormat = (config.dateFormat || this.config.dateFormat);
                this.values = [];
                this.minValue = this.convertToUnix(config.minDate);
                this.maxValue = this.convertToUnix(config.maxDate);
                var me = this;
                if (config.dateFields) {
                    config.values = [];
                    config.dateFields.forEach(function (item) {
                        var tf = Ext.Date.format(item.getValue(), me.dateFormat);
                        me.values.push(me.convertToUnix(tf));
                    });
                } else {
                    config.dateFields = [];
                    config.values = (config.values || []);
                    config.values.forEach(function (item) {
                        me.values.push(me.convertToUnix(item));
                        config.dateFields.push(Ext.create('Ext.form.field.Date', {
                            value: new Date(item)
                        }));
                    });
                    //console.log("config after foreach:", config);
                }
                config.values = me.values.sort(function (a, b) {
                    return a - b
                });

                //this.callParent(arguments);
                this.initConfig(config);
                //console.log("config last :",config);
                this.values = config.values;
                return this;
            },

            tipText: function (thumb) {
                var pd = Ext.Date.parse(thumb.value, 'U');
                var tip = Ext.Date.format(new Date(pd), 'Y-m-d');
                return tip;
            },

            listeners: {
                change: function (sld, val, tb) {
                    //tip设置
//                    var pd = Ext.Date.parse(val,'U');
//                    this.tipText = Ext.Date.format(new Date(pd),this.dateFormat);
                    //设置滑块的值
                    //sld.dateFields[tb.index].setValue(val);
                },
                changecomplete: function (slider, newValue, thumb, eOpts) {
                    var values = slider.getValues();
                    if (!isNaN(values[0])) {
                        renderDateValues[0] = this.convertFromUnix(values[0]);
                    } else {
                        renderDateValues[0] = values[0];
                    }
                    if (!isNaN(values[1])) {
                        renderDateValues[1] = this.convertFromUnix(values[1]);
                    } else {
                        renderDateValues[1] = values[1];
                    }
                    //设置时间轴上的时间
                    document.getElementById("taishi_daterange").innerHTML = renderDateValues[0] + "&nbsp;到&nbsp;" + renderDateValues[1];

                    InitStatisticAndRenderMap();
                }
            },

            applyDateIncrement: function (inc) {
                var minc = Ext.Date.format(Ext.Date.add(new Date(), inc, 1), 'U') - Ext.Date.format(new Date(), 'U');
                this.increment = minc;
                //this.increment = 1;
            },

            // this defaults to true, setting to false allows the thumbs to pass each other
            //constrainThumbs: false,
            renderTo: "taishi_timeSliderDiv"
        });
        timeSlider.applyDateIncrement(timeSlider.config.dateIncrement);
        timeSlider.constructor(timeSlider.config);

//        var timeSlider = new TimeSlider({
//            style: "width: 100%;"
//        }, document.getElementById("taishi_timeSliderDiv"));
//        console.log("slider",timeSlider);
        //map.setTimeSlider(timeSlider);


//        var timeExtent = new TimeExtent();
//        timeExtent.startTime = new Date("1/1/2013 UTC");
//        timeExtent.endTime = new Date("12/31/2014 UTC"); //"12/31/2009 UTC"
//        timeSlider.setThumbCount(2);
//        timeSlider.createTimeStopsByTimeInterval(timeExtent, 2, "UNIT_MONTHS");
//        timeSlider.setThumbIndexes([0,1]);
        //timeSlider.setThumbMovingRate(2000);
//        timeSlider.startup();
//
//        //add labels for every other time stop
//        var labels = arrayUtils.map(timeSlider.timeStops, function(timeStop, i) {
//            if ( i % 2 === 0 ) {
//                return timeStop.getUTCFullYear() +"."+ (timeStop.getMonth()+1); //返回年月，如2010.10
//            } else {
//                return "";
//            }
//        });
//
//        timeSlider.setLabels(labels);
//
//        timeSlider.on("time-extent-change", function(evt) {
//            var startValString = evt.startTime.getUTCFullYear()+"."+(evt.startTime.getMonth()+1);   //返回年月
//            var endValString = evt.endTime.getUTCFullYear()+"."+(evt.endTime.getMonth()+1);
//            dom.byId("taishi_daterange").innerHTML = "<i>" + startValString + " 到 " + endValString  + "<\/i>";
//        });
    }
})
;
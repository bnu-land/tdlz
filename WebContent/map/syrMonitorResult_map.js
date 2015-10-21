require([
    "esri/map",
    "esri/graphic",
    "esri/geometry/Point",
    "esri/geometry/Polyline",
    "esri/geometry/Polygon",
    "esri/geometry/Extent",

    "esri/SpatialReference",
    "esri/layers/FeatureLayer",
    "esri/InfoTemplate",
    "esri/symbols/SimpleFillSymbol",
    "esri/symbols/SimpleLineSymbol",
    "esri/renderers/ClassBreaksRenderer",
    "esri/tasks/query",

    "esri/dijit/InfoWindowLite",  //弹出对话框
    "esri/dijit/Popup",           //弹出对话框
    "esri/dijit/PopupTemplate",   //弹出对话框模板
    "esri/dijit/HomeButton",  //Home按钮
    "esri/dijit/Scalebar",    //比例尺
    "esri/dijit/LayerSwipe",  //图层卷帘效果

    "esri/Color",
    "dojo/dom",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/dom-construct",

    "dojo/on",
    "dojo/number",
    "dojo/string",
    "dojo/domReady!"
], function (Map, Graphic, Point, Polyline, Polygon, Extent, SpatialReference, FeatureLayer, InfoTemplate, SimpleFillSymbol, SimpleLineSymbol, ClassBreakRenderer, Query, InfoWindowLite, Popup, PopupTemplate, HomeButton, Scalebar, LayerSwipe, Color, dom, domClass, domStyle, domConstruct, on, number, String) {

    var map;
    var mapServiceLayer;
    var navigation; //导航
    var homeButton; //home键
    var currentevtid;   //当前的模块id

    var infoTemplate;
    var infoWindow; //弹出的对话框
    var popup;
    var template;
    var renderer;   //设置的渲染样式
    var swipeWidget;    //卷帘控件
    var swipeServiceLayer;     //用于卷帘的地图服务图层
//var swipeLayer;     //卷帘的图层

    var showLayerGroupIndex;     //显示的图层组的index
    var featureLayer;   //查询要素图层
    var radioValue;     //radioGroup的值
    var timeSlider;     //时间轴控件
    var timeSliderTips = [];
    var isFireSliderChange = false;
    var searchProjectId;    //当前选中的项目编号
    //数据
    var storeYq;    //yqprojectmonitorInfo的Store
    var recordCurrent;  //当前显示数据的record
    //地图
    var mapURL_fukenjindu;
    var mapURL_fukenxiangmu;
    var mapURL_RSImage;

    esriConfig.defaults.io.corsDetection = false;
    //esriConfig.defaults.io.proxyUrl = "map/proxy/proxy.jsp";
    //esriConfig.defaults.io.alwaysUseProxy = false;

    dojo.addOnLoad(initMap);

    function initMap() {
        //清空地图内容，再次添加
        if (map) {
            map.destroy();
        }
        map = new Map("mapdiv_syrMonitorResult", {
            logo: false
        });
        mapURL_fukenjindu = MAPURLS["fukenjindu"];
        mapURL_fukenxiangmu = MAPURLS["fukenxiangmu"];
        mapURL_RSImage = MAPURLS["fukenjindursimage"];
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapURL_fukenjindu);
        map.addLayer(mapServiceLayer);

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            console.log("Map onLoad event.");
            graphicLayer = map.graphics;    //设置graphicLayer层

            //获取项目编号
            searchProjectId = Ext.getCmp('prjid_result').text;
            //获取数据
            storeYq = Ext.StoreMgr.get('yqprjmonitorInfoStore'); //获得store对象
            CreateTimeSlider();

            //工程进度默认不显示
            Ext.getCmp('checkBox_monitor_jindu').setValue(false);

            //设置专题图
            Ext.getCmp('radioGroup_monitor').setValue({
                'monitor': '规划图'
            });
        });
        //绑定事件
        Ext.getCmp('radioGroup_monitor').on('change', RadioGroup_monitor_Change);           //radioGroup_taishiArea事件绑定
        Ext.getCmp('checkBox_monitor_RSImage').on('change', MonitorRSImageCheckedChange);   //遥感影像对比分析
        Ext.getCmp('checkBox_monitor_jindu').on('change', MonitorJinduCheckedChange);       //复垦进度查看按钮
        Ext.get('btn_monitor_scopeAbnormal').on('click', DrawMonitorPolygon);           //范围异常按钮
    }

    //---------------界面函数事件------------------------------------------
    function RadioGroup_monitor_Change(field, newValue, oldValue, eOpts) {
        radioValue = newValue.monitor;	//设置统计渲染的方式，比如统计量，价格等。由radioGroup的change事件触发
        ShowSpecifiedMap(radioValue)
    }

    //遥感影像对比
    function MonitorRSImageCheckedChange(field, newValue, oldValue, eOpts) {
        if (newValue) {
            SwipeLayerToContrast(); //显示卷帘
        } else {
            SwipeDestroy();         //隐藏卷帘
        }
    }

    //查看工程进度
    function MonitorJinduCheckedChange(field, newValue, oldValue, eOpts) {
        if (newValue) {
            document.getElementById("info_monitor").style.display = "";     //显示进度
            document.getElementById("bottomPanel_monitor").style.display = "";     //显示时间轴
            map.graphics.clear();   //清楚现有graphicLayer
            isFireSliderChange = true;
            timeSlider.setValue(1); //设置timeSlider的值，出发其事件，查看项目进度
        } else {
            document.getElementById("info_monitor").style.display = "none"; //隐藏进度
            document.getElementById("bottomPanel_monitor").style.display = "none";     //隐藏时间轴
        }
    }

    //查看规划图，现状图，竣工图
    function ShowSpecifiedMap(LayerNameToShow) {
        var findTask, findParams;   //查询要素
        findTask = new esri.tasks.FindTask(mapURL_fukenxiangmu);
        //create find parameters and define known values
        findParams = new esri.tasks.FindParameters();
        findParams.returnGeometry = true;
        //设置样式
        var polygonSymbol;
        //设置搜索的图层
        switch (LayerNameToShow) {
            case "规划图":
                findParams.layerIds = [1];
                polygonSymbol = new SimpleFillSymbol()
                    .setColor(new Color([255, 0, 255, 0.5]))
                    .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 0, 255]), 1));
                break;
            case "现状图":
                findParams.layerIds = [2];
                polygonSymbol = new SimpleFillSymbol()
                    .setColor(new Color([0, 255, 0, 0.5]))
                    .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0]), 1));
                break;
            case "竣工图":
                findParams.layerIds = [3];
                polygonSymbol = new SimpleFillSymbol()
                    .setColor(new Color([0, 0, 255, 0.5]))
                    .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0]), 1));
                break;
        }
        //console.log("layerName2",layerNameShow);

        findParams.searchFields = ["项目编号"];

        findParams.searchText = searchProjectId;    //项目编号
        findTask.execute(findParams, showResults);

        function showResults(results) {
            //symbology for graphics
            var markerSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0]), 1), new Color([0, 255, 0, 0.5]));
            var lineSymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASH, new Color([255, 0, 0]), 1);
            //var polygonSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new Color([255, 0, 0]), 2), new Color([255, 0, 0, 0.5]));

            //find results return an array of findResult.
            map.graphics.clear();
            var data = [];   //存储查询结果数据
            //Build an array of attribute information and add each found graphic to the map
            dojo.forEach(results, function (result) {
                var graphic = result.feature;
                var attr = result.feature.attributes;
                //设置属性框
                var infoTemplate = new InfoTemplate();
                if (LayerNameToShow == '规划图') {
                    infoTemplate.setTitle("项目规划图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块编号: ${片块编号} <br/>"
                    );
                } else if (LayerNameToShow == '现状图') {
                    infoTemplate.setTitle("项目现状图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块名称: ${片块名称} <br/>"
                    );
                }
                else if (LayerNameToShow == '竣工图') {
                    infoTemplate.setTitle("项目竣工图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块编号: ${片块编号} <br/>"
                    );
                }
                graphic.setInfoTemplate(infoTemplate);

                //设置样式
                switch (graphic.geometry.type) {
                    case "point":
                        graphic.setSymbol(markerSymbol);
                        break;
                    case "polyline":
                        graphic.setSymbol(lineSymbol);
                        break;
                    case "polygon":
                        graphic.setSymbol(polygonSymbol);
                        break;
                }
                //console.log("graphic:",graphic);
                map.graphics.add(graphic);
                data.push([attr["片块编号"], attr["st_area(shape)"], graphic]);
            });

            //显示查询结果
            var store = new Ext.data.ArrayStore({
                data: data,         //data取上面的mydata二维数组
                fields: [
                    { name: 'landparcelid'},
                    { name: 'area'},
                    {name: 'graphic'}
                ]
            });
            //显示查询结果窗口
            var findGridGrid = Ext.getCmp('monitorResult_graphicsGrid');
            findGridGrid.reconfigure(store);

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

            //缩放到graphics
            var extent = esri.graphicsExtent(map.graphics.graphics);
            map.setExtent(extent, true);

        }
    }

    //创建时间轴-------------------------------------------------------------------------------
    function CreateTimeSlider() {
        timeSliderTips = [];
        //初始化tips的内容
        if (!storeYq) {
            return;     //如果没有数据，则返回
        }
        storeYq.each(function (record) {
            timeSliderTips.push(record.get('monitorTime'));
        });
        var count = storeYq.getCount(); //取得记录条数

        if (!timeSlider) {
            timeSlider = new Ext.slider.SingleSlider({
                renderTo: "timeSliderDiv_monitor",
//                width: 100%,
                increment: 1,
                minValue: 0,
                maxValue: count - 1,
                value: 0,
                style: "width: 100%;",
                useTips: true,
                tipText: function (thumb) {
                    return timeSliderTips[thumb.value];
                },
                listeners: {
                    change: function (sld, val, tb) {
                        if (isFireSliderChange) {
                            this.fireEvent('changecomplete', this, val);
                            isFireSliderChange = false;
                        }
                    },
                    changecomplete: function (slider, newValue, thumb, eOpts) {
                        recordCurrent = storeYq.getAt(newValue);
                        document.getElementById("taishi_daterange").innerHTML = timeSliderTips[newValue];
                        SetJinduInfo(recordCurrent);
                    }
                }//,
//                setValue : function(v, animate, changeComplete){
//                    v = this.normalizeValue(v);
//                    if(v !== this.value && this.fireEvent('beforechange', this, v, this.value) !== false){
//                        this.value = v;
//                        this.moveThumb(v, animate !== false);
//                        this.fireEvent('change', this, v);
//                        if(changeComplete){
//                            this.fireEvent('changecomplete', this, v);
//                        }
//                    }
//                }
            });
        }
    }

    //设置复垦项目进度信息div
    function SetJinduInfo(record) {
        document.getElementById("projectNameDiv").innerHTML = record.get('projectName');
        document.getElementById("fukenjinduInfoDiv").innerHTML =
            "<br>土地平整工程：<b>" + record.get('ppercentPz') + "%</b><br>"
            + "田间道路工程：<b>" + record.get('ppercentDl') + "%</b><br>"
            + "农田水利工程：<b>" + record.get('ppercentSl') + "%</b><br>";
    }

    //绘制多边形
    function DrawMonitorPolygon() {
        console.log("开始绘制多边形。");
        //显示时间轴和进度信息
        Ext.getCmp('checkBox_monitor_jindu').setValue(true);
        //获取渲染数据
        if (!storeYq) {
            return;
        }
        var index = timeSlider.getValue();  //获取当前数据的index
        var record = storeYq.getAt(index);   //获取当前的record
        var rwId = record.get('rwId');       //获取任务id
        var date = record.get('monitorTime');   //获取监测时间
        var syrMonitorDataStore = Ext.StoreMgr.get('syrMonitorData'); //获得store对象
        syrMonitorDataStore.load({
            params: {
                projectName: record.get('projectName'),
                monitorstarttime: date,
                monitorendtime: date,
                accuracy: '0'
            }
        });
        if (syrMonitorDataStore.getCount() === 0) {
            return;
        }
        map.graphics.clear();
        var polygonSymbol = new SimpleFillSymbol()
            .setColor(new Color([255, 0, 255, 0.5]))
            .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 0, 255]), 1));
        syrMonitorDataStore.each(function (xyrecord) {
            //var xyrecord = syrMonitorDataStore.getAt(0);
            var xyListStr = xyrecord.get('coordinateXy');   //获取坐标串
            var xyList = eval(xyListStr);       //将js的string转换为object
            if (xyList.length >= 3) {
                var polygon = new Polygon({
                    "rings": [
                        xyList
                    ],
                    "spatialReference": map.spatialReference
                });
                var attr = {
                    "项目名称": xyrecord.get('projectName'),
                    "任务编号": xyrecord.get('rwId'),
                    "地块编号": xyrecord.get('dkId'),
                    "监测人": xyrecord.get('head'),
                    "监测内容": xyrecord.get('monitorContent'),
                    "监测时间": xyrecord.get('monitorTime').pattern("yyyy-MM-dd"),
                    "采集人员": xyrecord.get('recorder'),
                    "土地平整工程进度": xyrecord.get('tdpzPercent') + "%",
                    "田间道路工程进度": xyrecord.get('tjdlPercent') + "%",
                    "农田水利工程进度": xyrecord.get('ntslPercent') + "%"
                };
                var infoTemplate = new esri.InfoTemplate("地块属性", "${*}");
                var graphic = new Graphic(polygon, polygonSymbol, attr, infoTemplate);
                map.graphics.add(graphic);
            }
        });

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

        //缩放到graphics
        var extent = esri.graphicsExtent(map.graphics.graphics);
        map.setExtent(extent, true);

    }

    //创建卷帘对比------------------------------------------------------------------------------
    function SwipeLayerToContrast(evtid) {
        if (!swipeServiceLayer) {
            swipeServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapURL_RSImage, {
                id: "image"
            });
        }
        map.addLayer(swipeServiceLayer, 0);
        //console.log("swipeServiceLayer:", swipeServiceLayer);

        var swipeLayer = map.getLayer(mapServiceLayer.id);
        if (swipeLayer) {
            swipeLayer.opacity = 0.5;
            swipeWidget = new esri.dijit.LayerSwipe({
                type: "vertical", //Try switching to "scope" or "horizontal"vertical
                map: map,
                layers: [ swipeLayer ]
            }, "swipeDiv");
            swipeWidget.startup();
        }
    }

    function SwipeDestroy() {
        swipeWidget.destroy(); //销毁卷帘控件
        //init(); //初始化地图
    }

    //定位到graphic---------------
    locateByGraphic = function (graphic) {
        if (graphic) {
            //var extent = graphic.geometry.getExtent().expand(5.0);
            var geo = graphic.geometry;
            if (geo.type == 'point') {
                var point = new esri.geometry.Point(geo.x, geo.y, geo.spatialReference); //这个点处于地图的中间
                map.centerAt(point);
            } else {
                var ext = geo._extent;
                var extent = new Extent(ext.xmin, ext.ymin, ext.xmax, ext.ymax, geo.spatialReference);
                map.setExtent(extent.expand(5.0), false);
                //console.log('extent',extent);
            }
        }
    }

    //时间格式化输出
    Date.prototype.pattern = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        var week = {
            "0": "/u65e5",
            "1": "/u4e00",
            "2": "/u4e8c",
            "3": "/u4e09",
            "4": "/u56db",
            "5": "/u4e94",
            "6": "/u516d"
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        if (/(E+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }
});
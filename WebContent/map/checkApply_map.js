require([
    "esri/map",
    "esri/geometry/Point",
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
], function (Map, Point, Extent, SpatialReference, FeatureLayer, InfoTemplate, SimpleFillSymbol,SimpleLineSymbol, ClassBreakRenderer, Query, InfoWindowLite, Popup, PopupTemplate, HomeButton, Scalebar, LayerSwipe, Color, dom, domClass, domStyle, domConstruct, on, number, String) {
    var map;
    var mapServiceLayer;
    var navigation; //导航
    var infoTemplate;
    var popup;
    var template;
    var renderer;   //设置的渲染样式
    var spatialReference;   // 当前地图的空间参考系
    var showLayerGroupIndex;     //显示的图层组的index
    var featureLayer_ght;   //规划图的FeatureLayer
    var featureLayer_xzt;   //现状图的FeatureLayer
    var featureLayer_jgt;   //竣工图的FeatureLayer
    var searchProjectId;          //查询关键词
    var layerNameShow;              //要显示的图层名称
    var mapURL;


    esriConfig.defaults.io.corsDetection = false;
//    esriConfig.defaults.io.proxyUrl = "map/proxy/proxy.jsp";
    //esriConfig.defaults.io.alwaysUseProxy = false;

    dojo.addOnLoad(initMap);

    //-------------------------------------地图初始化-------------------------------------------------------------------
    function initMap() {
        //清空地图内容，再次添加
        if (map) {
            map.destroy();
        }
        map = new Map("mapdiv_checkApply", {
            logo: false
        });
        map.removeAllLayers();
        mapURL = MAPURLS["fukenxiangmu"];   //获取地图url
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapURL);
        map.addLayer(mapServiceLayer);

        infoTemplate = new esri.InfoTemplate("${项目名称}", "${*}");

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            console.log("Map onLoad event.");

            //处理传递过来的参数
            var findTextCmp = Ext.getCmp('applyprojectId');
            //console.log("findTextCmp:",findTextCmp);
            if (findTextCmp) {
                searchProjectId = findTextCmp.getValue();
                //console.log("searchProjectId:", searchProjectId);
                if (!searchProjectId) {
                    return;
                }
                Ext.getCmp('radioGroup_checkApply').setValue({
                    'checkApply': '规划图'
                });     //默认选中规划图
            }
        });
        dojo.connect(map, "onExtentChange", ResizeMap);

        //绑定事件
        Ext.getCmp('radioGroup_checkApply').on('change', RadioGroupValueChange);   //RadioGroup事件绑定
    }

    //查询要素
    FindFeatureByKey = function () {
        //console.log("查询关键词：",searchText);
        //create find task with url to map service
        var findTask, findParams;   //查询要素
        findTask = new esri.tasks.FindTask(mapURL);

        //create find parameters and define known values
        findParams = new esri.tasks.FindParameters();
        findParams.returnGeometry = true;
        //设置样式
        var polygonSymbol;
        //设置搜索的图层
        switch (layerNameShow) {
            case "规划图":
                findParams.layerIds = [1];
                polygonSymbol = new SimpleFillSymbol()
                    .setColor(new Color([255, 0, 255, 0.5]))
                    .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 0, 255]), 1));
                break;
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
            var markerSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 1), new dojo.Color([0, 255, 0, 0.5]));
            var lineSymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASH, new dojo.Color([255, 0, 0]), 1);
            //var polygonSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255, 0, 0]), 2), new dojo.Color([255, 0, 0, 0.5]));

            //find results return an array of findResult.
            map.graphics.clear();
            //Build an array of attribute information and add each found graphic to the map
            dojo.forEach(results, function (result) {
                var graphic = result.feature;
                //设置属性框
                var infoTemplate = new InfoTemplate();
                if (layerNameShow == '规划图') {
                    infoTemplate.setTitle("项目规划图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块编号: ${片块编号} <br/>"
                    );
                } else if (layerNameShow == '现状图') {
                    infoTemplate.setTitle("项目现状图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块名称: ${片块名称} <br/>"
                    );
                }
                else if (layerNameShow == '竣工图') {
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
    }

    //---------------界面函数事件------------------------------------------
    function RadioGroupValueChange(field, newValue, oldValue, eOpts) {
        layerNameShow = newValue.checkApply;	//获取RadioGroup的值
        //console.log("layerName:",layerNameShow);
        FindFeatureByKey();
    }

    //地图功能--------------------------------------------------------------------------
    //当界面发生变化时，重整地图的大小
    ResizeMap = function () {
        map.resize();
        //map.reposition();
    }

});
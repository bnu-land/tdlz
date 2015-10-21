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
    var renderer;   //设置的渲染样式

    var featureLayer;
    var findTask, findParams;   //查询要素
    var layerNameShow;  //需要显示的图层名称
    var searchProjectId;    //搜索的项目编号
    var mapURL_jiaoyifengxian;
    var mapURL_fukenxiangmu;

    esriConfig.defaults.io.corsDetection = false;
    //esriConfig.defaults.io.proxyUrl = "map/proxy/proxy.jsp";
    //esriConfig.defaults.io.alwaysUseProxy = false;

    dojo.addOnLoad(initMap);

    //-------------------------------------地图初始化-------------------------------------------------------------------
    function initMap() {
        //清空地图内容，再次添加
        if (map) {
            map.destroy();
        }
        map = new Map("mapdiv_abnmRisk", {
            logo: false
        });
        map.removeAllLayers();
        mapURL_jiaoyifengxian = MAPURLS["jiaoyifengxian"];
        mapURL_fukenxiangmu = MAPURLS["fukenxiangmu"];
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapURL_jiaoyifengxian);
        map.addLayer(mapServiceLayer);
        //ZoomToLayer(mapServiceLayer);
        searchProjectId = "FK20145112032";

        // dojo框架事件
        mapServiceLayer.on('load',function () {
            showRiskMap();
        });


        dojo.connect(map, "onExtentChange", ResizeMap);
    }


    //查询要素
    function showRiskMap() {
        //console.log("查询关键词：",searchText);
        //create find task with url to map service
        var findTask, findParams;   //查询要素
        findTask = new esri.tasks.FindTask(mapURL_fukenxiangmu);

        //create find parameters and define known values
        findParams = new esri.tasks.FindParameters();
        findParams.returnGeometry = true;
        findParams.layerIds = [1];
        findParams.searchFields = ["项目编号"];
        findParams.searchText = searchProjectId;    //项目编号
        findTask.execute(findParams, showResults);

        function showResults(results) {
            //设置样式
            var markerSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 1), new dojo.Color([0, 255, 0, 0.5]));
            var lineSymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASH, new dojo.Color([255, 0, 0]), 1);
            //var polygonSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255, 0, 0]), 2), new dojo.Color([255, 0, 0, 0.5]));
            var polygonSymbol;
            polygonSymbol = new SimpleFillSymbol()
                .setColor(new Color([255, 0, 255, 0.5]))
                .setOutline(new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 0, 255]), 1));

            //设置属性框
            var infoTemplate = new InfoTemplate();
            infoTemplate.setTitle("地块属性");
            infoTemplate.setContent(
                    "项目编号: ${项目编号} <br/>"
                    + "项目名称: ${项目名称} <br/>"
                    + "片块编号: ${片块编号} <br/>"
            );
            //find results return an array of findResult.
            map.graphics.clear();
            //Build an array of attribute information and add each found graphic to the map
            dojo.forEach(results, function (result) {
                var graphic = result.feature;
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

    //缩放到图层
    function ZoomToLayer(map, layer) {
        if (!layer) {
            return;
        }
        var query = new esri.tasks.Query();
        query.where = "0=1";
        layer.selectFeatures(query, esri.layers.FeatureLayer.SELECTION_NEW, function () {
            map.setExtent(layer.fullExtent);
        });
    }


//地图功能--------------------------------------------------------------------------
//当界面发生变化时，重整地图的大小
    ResizeMap = function () {
        map.resize();
        //map.reposition();
    }
});








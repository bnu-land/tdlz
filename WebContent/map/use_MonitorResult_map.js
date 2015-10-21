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
    var visibleLayer = [];
    var mapServiceLayer;
    var mapURL;


    esriConfig.defaults.io.corsDetection = false;
    //esriConfig.defaults.io.proxyUrl = "map/proxy/proxy.jsp";
    //esriConfig.defaults.io.alwaysUseProxy = false;

    dojo.addOnLoad(initMap);

    function initMap() {
        //清空地图内容，再次添加
        if (map) {
            map.destroy();
        }
        map = new Map("mapdiv_MonitorResult", {
            logo: false
        });
        mapURL = MAPURLS["chouyang"];
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapURL);
        map.addLayer(mapServiceLayer);

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            console.log("Map onLoad event.");
            //graphicLayer = map.graphics;    //设置graphicLayer层
            visibleLayer = [];
            visibleLayer.push(4);
            mapServiceLayer.setVisibleLayers(visibleLayer);
            showSaplingPoint();
        });
    }

    //查询要素
    function showSaplingPoint() {

        var findTask, findParams;   //查询要素
        findTask = new esri.tasks.FindTask(mapURL);
        findParams = new esri.tasks.FindParameters();
        findParams.returnGeometry = true;
        findParams.layerIds = [0];
        findParams.searchFields = ["qsdwmc"];
        findParams.searchText = "双龙镇";    //示例
        findTask.execute(findParams, showResults);

        function showResults(results) {
            //symbology for graphics
            var markerSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 1), new dojo.Color([55, 255, 55, 0.5]));

            //find results return an array of findResult.
            map.graphics.clear();
            var data = [];   //存储查询结果数据
            dojo.forEach(results, function (result) {
                var graphic = result.feature;
                var attr = result.feature.attributes;
                //设置属性框
                var infoTemplate = new InfoTemplate();
                infoTemplate.setTitle("抽样点属性");
                infoTemplate.setContent(
                        "权属单位名称: ${qsdwmc} <br/>"
                        + "图斑编号: ${tbbh} <br/>"
                        + "地类名称: ${dlmc} <br/>"
                        + "地类编码: ${dlbm} <br/>"
                        + "标识码: ${bsm} <br/>"
                        + "要素代码: ${ysdm} <br/>"
                );
                graphic.setInfoTemplate(infoTemplate);

                //设置样式
                if (graphic.geometry.type == "point") {
                    graphic.setSymbol(markerSymbol);
                }
                map.graphics.add(graphic);
                data.push([attr["qsdwmc"], attr["tbbh"], attr["dlmc"],attr["dlbm"],attr["bsm"],attr["ysdm"],graphic]);
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

            //显示查询结果
            var store = new Ext.data.ArrayStore({
                data: data,         //data取上面的mydata二维数组
                fields: [
                    { name: 'qsdwmc'},
                    { name: 'tbbh'},
                    { name: 'dlmc'},
                    { name: 'dlbm'},
                    { name: 'bsm'},
                    { name: 'ysdm'},
                    {name: 'graphic'}
                ]
            });
            //显示查询结果窗口
            var findGridForm = Ext.getCmp('grid_useMonitorResult_SamplesAttr');
            findGridForm.reconfigure(store);
        }
    }


})
;
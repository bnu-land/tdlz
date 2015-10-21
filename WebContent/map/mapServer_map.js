require([
    "esri/map",
    "esri/graphic",
    "esri/config",
    "esri/urlUtils",
    "esri/geometry/Point",
    "esri/geometry/Extent",
    "esri/SpatialReference",
    "esri/layers/FeatureLayer",
    "esri/InfoTemplate",

    "esri/symbols/SimpleFillSymbol",
    "esri/symbols/SimpleLineSymbol",
    "esri/symbols/SimpleMarkerSymbol",
    "esri/renderers/ClassBreaksRenderer",
    "esri/tasks/query",

    "esri/dijit/InfoWindowLite",  //弹出对话框
    "esri/dijit/Popup",           //弹出对话框
    "esri/dijit/PopupTemplate",   //弹出对话框模板
    "esri/dijit/HomeButton",  //Home按钮
    "esri/dijit/Scalebar",    //比例尺
    "esri/dijit/LayerSwipe",  //图层卷帘效果

    "esri/Color",
    "dojo/parser",
    "dojo/dom",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/dom-construct",

    "dojo/on",
    "dojo/number",
    "dojo/string",

    "dojo/domReady!"
], function (Map, Graphic, esriConfig, urlUtils, Point, Extent, SpatialReference, FeatureLayer, InfoTemplate, SimpleFillSymbol, SimpleLineSymbol, SimpleMarkerSymbol, ClassBreakRenderer, Query, InfoWindowLite, Popup, PopupTemplate, HomeButton, Scalebar, LayerSwipe, Color, parser, dom, domClass, domStyle, domConstruct, on, number, String) {

    var map;
    var mapServiceLayer;
    var navigation; //导航
    var infoTemplate;
    var renderer;   //设置的渲染样式

    var featureLayer_ght;   //规划图的FeatureLayer
    var featureLayer_xzt;   //现状图的FeatureLayer
    var featureLayer_jgt;   //竣工图的FeatureLayer
    var findTask, findParams;   //查询要素
    var layerNameShow;  //需要显示的图层名称
    var searchProjectId;    //搜索的项目编号
    var url;

    //parser.parse();
//    urlUtils.addProxyRule({
//        urlPrefix: "http://172.16.190.35",
//        proxyUrl: "/map/proxy/"
//    });
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
        map = new Map("mapdiv_MapServer", {
            logo: false
        });
        map.removeAllLayers();
        url = MAPURLS["fukenxiangmu"];
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(url);
        map.addLayer(mapServiceLayer);

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            console.log("Map onLoad event.");

            //处理传递过来的参数
            var findTextCmp = Ext.getCmp('mapserver_projectId');
            //console.log("findTextCmp:",findTextCmp);
            if (findTextCmp) {
                searchProjectId = findTextCmp.getValue();
            }
            Ext.getCmp('radioGroup_mapserver').setValue({
                'mapserver': '规划图'
            });     //默认查看规划图，执行渲染操作
        });
        dojo.connect(map, "onExtentChange", ResizeMap);
        //绑定事件
        Ext.getCmp('radioGroup_mapserver').on('change', RadioGroupValueChange);   //RadioGroup事件绑定
    }

    //查询要素
    FindFeatureByText = function () {
        //console.log("查询关键词：",searchText);
        //create find task with url to map service
        findTask = new esri.tasks.FindTask(url);

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
        findParams.searchFields = ["项目编号"];
        findParams.searchText = searchProjectId;    //查询关键词
        findTask.execute(findParams, showResults);

        function showResults(results) {

            //设置属性框
            var infoTemplate = new InfoTemplate();
            infoTemplate.setTitle("片块属性");
            infoTemplate.setContent(
                    "项目编号: ${项目编号} <br/>"
                    + "项目名称: ${项目名称} <br/>"
                    + "片块编号: ${片块编号} <br/>"
            );
            map.graphics.clear();
            var data = [];   //存储查询结果数据
            //Build an array of attribute information and add each found graphic to the map
            dojo.forEach(results, function (result) {
                var graphic = result.feature;
                var attr = result.feature.attributes;
                graphic.setInfoTemplate(infoTemplate);  //属性框
                //设置样式
                if (graphic.geometry.type = "polygon") {
                    graphic.setSymbol(polygonSymbol);
                }
                map.graphics.add(graphic);
                //if()
                data.push([attr["片块编号"], attr["st_area(shape)"], graphic]);
            });
            //缩放到graphics
            var extent = esri.graphicsExtent(map.graphics.graphics);
            map.setExtent(extent, true);
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
            var findFeaturePanel = Ext.getCmp('mapserver_searchResult');
            findFeaturePanel.expand();
            var findGridForm = Ext.getCmp('mapserver_graphicGrid');
            findGridForm.reconfigure(store);
            //修改panel标题
            Ext.getCmp("mapserver_searchResult").setTitle(layerNameShow + "查询结果");

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
        }
    }

//清除map上的graphics
    ClearGraphics = function () {
        map.graphics.clear();

    }

//定位到graphic
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
            //map.graphics.clear();   //清除graphics图层对象
            //mapSr = geo.spatialReference;
            //console.log('mapSr:',mapSr);
//            console.log("locateGraphic,graphic:", graphic);
        }
    }

//---------------界面函数事件------------------------------------------
    function RadioGroupValueChange(field, newValue, oldValue, eOpts) {
        layerNameShow = newValue.mapserver;	//获取RadioGroup的值
        //console.log("layerName:", layerNameShow);
        FindFeatureByText();
    }

//地图功能--------------------------------------------------------------------------
//当界面发生变化时，重整地图的大小
    ResizeMap = function () {
        map.resize();
        //map.reposition();
    }
})
;
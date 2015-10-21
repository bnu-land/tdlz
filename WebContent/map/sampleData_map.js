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
    "esri/tasks/find",

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
    var popup;
    var template;
    var renderer;   //设置的渲染样式
    var mapSr;   // 当前地图的空间参考系
    var showLayerGroupIndex;     //显示的图层组的index
    var featureLayer_cyd;   //采样点的FeatureLayer
    var featureLayer_ght;   //规划图的FeatureLayer
    var featureLayer_xzt;   //现状图的FeatureLayer
    var featureLayer_jgt;   //竣工图的FeatureLayer
    var spatialReference;   //地图的坐标系
    var findTask, findParams;   //查询要素
    var store;  //extjs的store
    var featuresToAdd = [];      //准备添加到
    var mapURL;

    //parser.parse();
//    urlUtils.addProxyRule({
//        urlPrefix: "http://localhost",
//        proxyUrl: "/map/proxy/"
//    });
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
        map = new Map("mapdiv_SampleData", {
            logo: false
        });
        map.removeAllLayers();
        mapURL = MAPURLS["fukenxiangmu"];
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapURL);

        map.addLayer(mapServiceLayer);

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            //添加采样点图层
            featureLayer_cyd = new esri.layers.FeatureLayer(mapURL+"/0");
            previewSampleDataOnMap();
        });

        dojo.connect(map, "onExtentChange", ResizeMap);

        //infoTemplate = new esri.InfoTemplate("${项目名称}", "${*}");

//        map.addLayer(featureLayer_cyd);

        //GetMapSpatialReference();   //获取坐标系

        //previewSampleDataOnMap();
    }


    //采样点预览
    function previewSampleDataOnMap() {
        //取得数据
        store = Ext.StoreMgr.get('SampleStore'); //获得store对象
        var count = store.getCount();   //取得采样点数量
        //console.log("数据点量：", count);
        if (map.graphics) {
            map.graphics.clear();   //清除已有的graphics
        }
        //console.log("开始遍历store");
        //遍历store
        for (var index = 0; index < count; index++) {
            var featuresToAdd = [];

            var record = store.getAt(index);
            var xloc = record.get('longitude');
            var yloc = record.get('latitude');
            //console.log("x,y:", xloc + "-" + yloc);
            if (!(xloc && yloc)) {
                //console.log('xloc && yloc is false');
                return;
            }
            //console.log("坐标系map:", map.spatialReference);

            var point = new Point(xloc, yloc, map.spatialReference);
            var markerSymbol = new esri.symbol.SimpleMarkerSymbol(
                esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10,
                new esri.symbol.SimpleLineSymbol(
                    esri.symbol.SimpleLineSymbol.STYLE_SOLID,
                    new dojo.Color([255, 0, 0]), 1), new dojo.Color([0, 255, 0, 0.25]));
            //var sms = new SimpleMarkerSymbol().setStyle(SimpleMarkerSymbol.STYLE_SQUARE).setColor(new Color([255, 0, 0, 0.5]));
            var attr = {
                "编号": record.get('sampleId'),
                "项目编号": record.get('projectId'),
                "项目名称": record.get('projectName'),
                "采集地点": record.get('sampleAddresss'),
                "采集时间": record.get('sampletime').pattern("yyyy-MM-dd"),
                "采集人员": record.get('recorder'),
                "pH值": record.get('phvalue'),
                "x": record.get('longitude'),
                "y": record.get('latitude'),
                "有机质含量": record.get('organicmatter') + "g/kg",
                "土壤质地": record.get('soiltexture'),
                "土层厚度": record.get('soilThickness') + "cm",
                "土壤含水量": record.get('soilmoisture')*100 + "%",
                "全氮": record.get('totalnitrogen') + "g/kg",
                "全磷": record.get('totalphosphorus') + "g/kg",
                "全钾": record.get('totalpotassium') + "g/kg"
            };
//            var infoTemplate = new InfoTemplate(
//                "采样点属性",
//                    "项目编号: ${项目编号} <br/>"
//                    + "项目名称: ${项目名称} <br/>"
//                    + "采集地点: ${采集地点} <br/>"
//                    + "pH值: ${ph} <br/>"
//                    + "采样点编号: ${编号}"
//            );
            var infoTemplate = new esri.InfoTemplate("采样点属性", "${*}");
            var graphic = new Graphic(point, markerSymbol, attr, infoTemplate);
            //console.log(graphic);
            //var graphic = new Graphic(point, null, attr, null);
            //record.set("graphic", graphic);
            map.graphics.add(graphic);
            featuresToAdd.push(graphic);

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


        //缩放到graphics
        //var extent = esri.graphicsExtent(map.graphics.graphics);
        //map.setExtent(extent.expand(5.0), true);
//        console.log('store:',store);
    }

    SaveGraphicsToLayer = function () {
        //保存到数据库
        if (featureLayer_cyd) {
            featureLayer_cyd.applyEdits(featuresToAdd, null, null, function test() {
                //console.log("保存成功。");
            }, function (error) {
                console.log("保存失败：", error);
            });
        } else {
            //console.log("图层为空");
        }
    }

    //取得地图真实坐标系
    function GetMapSpatialReference() {
        findTask = new esri.tasks.FindTask(mapURL);

        //create find parameters and define known values
        findParams = new esri.tasks.FindParameters();
        findParams.returnGeometry = true;
        findParams.layerIds = [0];
        findParams.searchFields = ["项目编号"];

        findParams.searchText = "FK20145112032";    //！！先保证图层里面有fk001的数据
        findTask.execute(findParams, showResults);

        function showResults(results) {
            var graphic = result[0].feature;
            spatialReference = graphic.geometry.spatialReference;
            //console.log("从背后图层获取坐标系：", spatialReference);
        }
    }

    //缩放到图层
    function ZoomToLayer(layerName) {
        var featLayerToZoom;
        switch (layerName) {
            case "采样点":
                featLayerToZoom = featureLayer_cyd;
                break;
            case "规划图":
                featLayerToZoom = featureLayer_ght;
                break;
            case "现状图":
                featLayerToZoom = featureLayer_xzt;
                break;
            case "竣工图":
                featLayerToZoom = featureLayer_jgt;
                break;
        }
        if (!featLayerToZoom) {
            return;
        }
        var query = new esri.tasks.Query();
        query.where = "0=1";
        featLayerToZoom.selectFeatures(query, esri.layers.FeatureLayer.SELECTION_NEW, function () {
            map.setExtent(featLayerToZoom.fullExtent);
        });
    }

    //清除map上的graphics
    function ClearGraphics() {
        map.graphics.clear();
    }

    //定位到graphic
    function locateByGraphic(graphic) {
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

    //地图功能--------------------------------------------------------------------------
    //当界面发生变化时，重整地图的大小
    function ResizeMap() {
        map.resize();
        //map.reposition();
    }

    function onMapZoom() {
        var mapLevel = map.getLevel();
        //console.log("getmaplevel:", mapLevel);
    }

});
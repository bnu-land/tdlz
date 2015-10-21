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
var findTask, findParams;   //查询要素
var mapUrl;

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

    "dojo/_base/Color",
    "dojo/parser",
    "dojo/dom",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/dom-construct",

    "dojo/on",
    "dojo/number",
    "dojo/string",

    "dojo/domReady!"
], function (Map, Graphic, esriConfig,urlUtils, Point, Extent, SpatialReference, FeatureLayer, InfoTemplate, SimpleFillSymbol, SimpleMarkerSymbol, ClassBreakRenderer, Query, InfoWindowLite, Popup, PopupTemplate, HomeButton, Scalebar, LayerSwipe, Color, parser, dom, domClass, domStyle, domConstruct, on, number, String) {

    //parser.parse();
//    urlUtils.addProxyRule({
//        urlPrefix: "http://172.16.190.35",
//        proxyUrl: "/map/proxy/"
//    });
    esriConfig.defaults.io.corsDetection=false;
    esriConfig.defaults.io.proxyUrl = "map/proxy/proxy.jsp";
    //esriConfig.defaults.io.alwaysUseProxy = false;

    dojo.addOnLoad(initMap);

    //-------------------------------------地图初始化-------------------------------------------------------------------
    function initMap() {
        //清空地图内容，再次添加
        if (map) {
            map.destroy();
        }
        map = new Map("mapdiv_MapServer",{
            logo:false
        });
        map.removeAllLayers();
        mapUrl = MAPURLS["fukenxiangmu"];   //获取地图url
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(mapUrl);
        map.addLayer(mapServiceLayer);

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            console.log("Map onLoad event.");
        });
        dojo.connect(map, "onExtentChange", ResizeMap);

        //添加FeatureLayer
        ShowSpecificLayerByName("全部");

        //处理传递过来的参数
        var findTextCmp = Ext.getCmp('map_fkSampleSearchText');
        //console.log("findTextCmp:",findTextCmp);
        if (findTextCmp) {
            var findText = findTextCmp.getValue();
            //console.log("findText:",findText);
            FindFeatureByText(findText);
        }
    }

    //指定要显示的图层(现状图，规划图，竣工图）
    ShowSpecificLayerByName = function (layerName) {
        //先移除已有的图层
        RemoveFeatureLayer("全部");
        // 信息查询框模板
        infoTemplate = new esri.InfoTemplate("${项目名称}", "${*}");

        switch (layerName) {
            case "采样点":
                featureLayer_cyd = new esri.layers.FeatureLayer(mapUrl+"/0", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });
                map.addLayer(featureLayer_cyd);
                break;
            case "规划图":
                featureLayer_ght = new esri.layers.FeatureLayer(mapUrl+"/1", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });
                map.addLayer(featureLayer_ght);
                break;
            case "现状图":
                featureLayer_xzt = new esri.layers.FeatureLayer(mapUrl+"/2", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });
                map.addLayer(featureLayer_xzt);
                break;
            case "竣工图":
                featureLayer_jgt = new esri.layers.FeatureLayer(mapUrl+"/3", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });
                map.addLayer(featureLayer_jgt);
                break;
            case "全部":
                featureLayer_cyd = new esri.layers.FeatureLayer(mapUrl+"/0", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });
                featureLayer_ght = new esri.layers.FeatureLayer(mapUrl+"/1", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });
                featureLayer_xzt = new esri.layers.FeatureLayer(mapUrl+"/2", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });
                featureLayer_jgt = new esri.layers.FeatureLayer(mapUrl+"/3", {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: [ "*" ],
                    infoTemplate: infoTemplate
                });

                map.addLayer(featureLayer_cyd);
                map.addLayer(featureLayer_ght);
                map.addLayer(featureLayer_xzt);
                map.addLayer(featureLayer_jgt);
                break;
        }

        ZoomToLayer(layerName);//缩放到图层
    }

    //移除FeatureLayer
    RemoveFeatureLayer = function (layerName) {
        switch (layerName) {
            case "采样点":
                if (featureLayer_cyd) {
                    map.removeLayer(featureLayer_cyd);
                }
                break;
            case "规划图":
                if (featureLayer_ght) {
                    map.removeLayer(featureLayer_ght);
                }
                break;
            case "现状图":
                if (featureLayer_xzt) {
                    map.removeLayer(featureLayer_xzt);
                }
                break;
            case "竣工图":
                if (featureLayer_jgt) {
                    map.removeLayer(featureLayer_jgt);
                }
                break;
            case "全部":
                if (featureLayer_cyd) {
                    map.removeLayer(featureLayer_cyd);
                }
                if (featureLayer_ght) {
                    map.removeLayer(featureLayer_ght);
                }
                if (featureLayer_xzt) {
                    map.removeLayer(featureLayer_xzt);
                }
                if (featureLayer_jgt) {
                    map.removeLayer(featureLayer_jgt);
                }
                break;
        }
    }

    //缩放到图层
    ZoomToLayer = function (layerName) {
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

    //查询要素
    FindFeatureByText = function (searchText) {
        //console.log("查询关键词：",searchText);
        //create find task with url to map service
        findTask = new esri.tasks.FindTask(mapUrl);

        //create find parameters and define known values
        findParams = new esri.tasks.FindParameters();
        findParams.returnGeometry = true;
        findParams.layerIds = [0, 1, 2];
        findParams.searchFields = ["项目编号", "项目名称", "片块编号", "编号", "采集地点", "xzqmc"];

        findParams.searchText = searchText;
        findTask.execute(findParams, showResults);

        function showResults(results) {
            //symbology for graphics
            var markerSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 1), new dojo.Color([0, 255, 0, 0.25]));
            var lineSymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASH, new dojo.Color([255, 0, 0]), 1);
            var polygonSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NONE, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255, 0, 0]), 2), new dojo.Color([255, 255, 0, 0.25]));

            //find results return an array of findResult.
            map.graphics.clear();
            var data = [];   //存储查询结果数据
            //Build an array of attribute information and add each found graphic to the map
            dojo.forEach(results, function (result) {
                var graphic = result.feature;
                //设置属性框
                var layerName = result.layerName;
                //console.log('layername:',layerName);
                var infoTemplate = new InfoTemplate();
                if (layerName.indexOf('采样点')>=0) {
                    infoTemplate.setTitle("采样点属性");
                    infoTemplate.setContent("项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "采样点编号: ${编号} <br/>"
                            + "采集地点: ${采集地点} <br/>"
                            + "pH值: ${ph} <br/>"
                            + "经度: ${经度} <br/>"
                            + "纬度: ${纬} <br/>"
                    );

                } else if (layerName.indexOf('规划图')>=0) {
                    infoTemplate.setTitle("项目规划图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块编号: ${片块编号} <br/>"
                            + "图层: ${layer} <br/>"
                    );
                }else if (layerName.indexOf('现状图')>=0) {
                    infoTemplate.setTitle("项目现状图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块名称: ${片块名称} <br/>"
                    );
                }
                else if (layerName.indexOf('竣工图')>=0) {
                    infoTemplate.setTitle("项目竣工图属性");
                    infoTemplate.setContent(
                            "项目编号: ${项目编号} <br/>"
                            + "项目名称: ${项目名称} <br/>"
                            + "片块名称: ${片块名称} <br/>"
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
                map.graphics.add(graphic);
                data.push([result.layerName, result.foundFieldName, result.value, graphic]);
            });

            //缩放到graphics
            var extent = esri.graphicsExtent(map.graphics.graphics);
            map.setExtent(extent, true);
            //显示查询结果
            var store = new Ext.data.ArrayStore({
                data: data,         //data取上面的mydata二维数组
                fields: [
                    { name: 'layername'},
                    { name: 'fieldname'},
                    { name: 'value'},
                    {name: 'graphic'}
                ]
            });
//           console.log("findFeature,store:",store);
            //显示查询结果窗口
            var findFeaturePanel = Ext.getCmp('mapServerTabPanel');
            findFeaturePanel.expand();
            var findGridForm = Ext.getCmp('map_ShowFindResultGrid');
            findGridForm.reconfigure(store);

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

    //采样点预览
    previewSampleDataOnMap = function () {
        //取得数据
        var store = Ext.StoreMgr.get('map_fkSampleManagementStore'); //获得store对象
        //var store = Ext.StoreManager.lookup('map_fkSampleManagementStore');
        var count = store.getCount();
//        console.log('store:', store);
//        console.log('count:', count);
//        //取得坐标系
//        var graphic = map.graphics.graphics[0];
//        var geo = graphic.geometry;
//        var sr = geo.spatialReference; //坐标系

        //console.log(sr);
        map.graphics.clear();   //清除已有的graphics
        //遍历store
        for (var index = 0; index < count; index++) {
            var features = [];

            var record = store.getAt(index);
            var xloc = record.get('longitude');
            var yloc = record.get('latitude');
            if (!(xloc && yloc)) {
                console.log('xloc && yloc is false');
                return;
            }

            var point = new Point(xloc, yloc, map.spatialReference)
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
                "ph": record.get('phvalue'),
                "经度": record.get('longitude'),
                "纬": record.get('latitude'),
                "海波": 0
            };
            var infoTemplate = new InfoTemplate(
                "采样点属性",
                    "项目编号: ${项目编号} <br/>"
                    + "项目名称: ${项目名称} <br/>"
                    + "采集地点: ${采集地点} <br/>"
                    + "pH值: ${ph} <br/>"
                    + "采样点编号: ${编号}"
            );
            //var graphic = new Graphic(point, markerSymbol, attr, infoTemplate);
            var graphic = new Graphic(point, null, attr, null);
            record.set("graphic", graphic);
            map.graphics.add(graphic);
            features.push(graphic);
            //console.log('record：',record);
            //console.log('index',index);
        }

        //featureLayer_cyd.applyEdits(features, null, null);
        featureLayer_cyd.applyEdits(features, null, null,function test1(){alert("good to go")}, function(error){
            console.log(error);
        } );

        //缩放到graphics
        //locateByGraphic(graphic);
//        var extent = esri.graphicsExtent(map.graphics.graphics);
//        map.setExtent(extent, true);
//        console.log('store:',store);
    }


    //定位到项目
    function LocateProject() {
        var queryUrl = mapServiceLayer.url + "/" + (showLayerGroupIndex + 20);
        console.log("QueryUrl:", queryUrl);
        var queryTask = new esri.tasks.QueryTask(queryUrl);
        var query = new esri.tasks.Query();
        query.returnGeometry = true;
        query.outFields = [ 'objectid' ];
        console.log("queryTask:", queryTask);
        console.log("query:", query);

        dojo.on(dojo.dom.byId("execute"), "click", execute);
        function execute() {
            queryTask.execute(query, getResults);
        }
    }

    function getResults(results) {
        var resultItems = [];
        var resultCount = results.features.length;
        console.log("feature.length:", resultCount);
        for (var i = 0; i < resultCount; i++) {
            var featureAttributes = results.features[i].attributes;
            for (var attr in featureAttributes) {
                resultItems.push("<b>" + attr + ":</b>  "
                    + featureAttributes[attr] + "<br>");
            }
            resultItems.push("<br>");
        }
        dojo.dom.byId("info").innerHTML = resultItems.join("");
    }

    //地图功能--------------------------------------------------------------------------
    //当界面发生变化时，重整地图的大小
    ResizeMap = function () {
        map.resize();
        //map.reposition();
    }


    function onMapZoom() {
        var mapLevel = map.getLevel();
        console.log("getmaplevel:", mapLevel);
    }

    function showExtent(extent) {
        var s = "";
        s = "xmin: " + extent.xmin.toFixed(2) + "," + "ymin: "
            + extent.ymin.toFixed(2) + "," + "xmax: "
            + extent.xmax.toFixed(2) + "," + "ymax: "
            + extent.ymax.toFixed(2) + "," + "CenterX:"
            + ((extent.xmax + extent.xmin) / 2.0).toFixed(2) + ","
            + "CenterY:"
            + ((extent.ymax + extent.ymin) / 2.0).toFixed(2);
        // dojo.byId("extentInfoDiv").innerHTML = s;
        console.log("MapExtent:", s);
//        var findTextCmp = Ext.getCmp('map_fkSampleSearchText');
//        if (findTextCmp) {
//            var xy = ((extent.xmax + extent.xmin) / 2.0).toFixed(2) + ","
//                + ((extent.ymax + extent.ymin) / 2.0).toFixed(2);
//            findTextCmp.setValue(xy);
//        }

    }


});
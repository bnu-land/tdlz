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


    esriConfig.defaults.io.corsDetection = false;
    //esriConfig.defaults.io.proxyUrl = "map/proxy/proxy.jsp";
    //esriConfig.defaults.io.alwaysUseProxy = false;

    dojo.addOnLoad(initMap);

    function initMap() {
        //清空地图内容，再次添加
        if (map) {
            map.destroy();
        }
        map = new Map("mapdiv_MonitoringSampling", {
            logo: false
        });
        //var urlChouyangMap = GetMapURLByName("chouyang");
        var urlChouyangMap = MAPURLS["chouyang"];   //获取地图url
        mapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(urlChouyangMap);
        map.addLayer(mapServiceLayer);

        // dojo框架事件
        dojo.connect(map, "onLoad", function () {
            console.log("Map onLoad event.");
            //graphicLayer = map.graphics;    //设置graphicLayer层

        });
        //绑定事件
        Ext.getCmp('text_useSampling_ditu').on('change', Text_LayerURL_Change);           //绑定textField 的Change事件
        Ext.getCmp('text_useSampling_baogao').on('change', Text_LayerURL_Change);           //绑定textField 的Change事件
        Ext.getCmp('text_useSampling_yangben').on('change', Text_LayerURL_Change);            //绑定textField 的Change事件
        Ext.getCmp('text_useSampling_fanwei').on('change', Text_LayerURL_Change);           //绑定textField 的Change事件
        Ext.getCmp('text_useSampling_zhishi').on('change', Text_LayerURL_Change);          //绑定textField 的Change事件

        Ext.getCmp('check_useSampling_ditu').on('change', Check_LayerVisible_Change);           //绑定checkBox 的Change事件
        Ext.getCmp('check_useSampling_fanwei').on('change', Check_LayerVisible_Change);           //绑定checkBox 的Change事件
        Ext.getCmp('check_useSampling_zhishi').on('change', Check_LayerVisible_Change);           //绑定checkBox 的Change事件
        Ext.getCmp('check_useSampling_baogao').on('change', Check_LayerVisible_Change);           //绑定checkBox 的Change事件
        Ext.getCmp('check_useSampling_yangben').on('change', Check_LayerVisible_Change);           //绑定checkBox 的Change事件
    }

    //---------------界面函数事件------------------------------------------
    function Text_LayerURL_Change(field, newValue, oldValue, eOpts) {
        switch (field.id) {
            case "text_useSampling_ditu":
                Ext.getCmp('check_useSampling_ditu').setValue('true');
                break;
            case "text_useSampling_fanwei":
                Ext.getCmp('check_useSampling_fanwei').setValue('true');
                break;
            case "text_useSampling_zhishi":
                Ext.getCmp('check_useSampling_zhishi').setValue('true');
                break;
            case "text_useSampling_baogao":
                Ext.getCmp('check_useSampling_baogao').setValue('true');
                break;
            case "text_useSampling_yangben":
                Ext.getCmp('check_useSampling_yangben').setValue('true');
                break;
        }
    }

    function Check_LayerVisible_Change(checkBox, newValue, oldValue, eOpts) {
        visibleLayer = [];
        if (Ext.getCmp('check_useSampling_ditu').getValue()) {
            if (Ext.getCmp('text_useSampling_ditu').getValue() != '') {
                visibleLayer.push(5);
            }
        }
        if (Ext.getCmp('check_useSampling_baogao').getValue()) {
            if (Ext.getCmp('text_useSampling_baogao').getValue() != '') {
                visibleLayer.push(4);
            }
        }
        if (Ext.getCmp('check_useSampling_yangben').getValue()) {
            if (Ext.getCmp('text_useSampling_yangben').getValue() != '') {
                visibleLayer.push(2);
            }
        }
        if (Ext.getCmp('check_useSampling_fanwei').getValue()) {
            if (Ext.getCmp('text_useSampling_fanwei').getValue() != '') {
                visibleLayer.push(1);
            }
        }
        if (Ext.getCmp('check_useSampling_zhishi').getValue()) {
            if (Ext.getCmp('text_useSampling_zhishi').getValue() != '') {
                visibleLayer.push(3);
            }
        }
        mapServiceLayer.setVisibleLayers(visibleLayer);

        switch (checkBox.id) {
            case "check_useSampling_zhishi":
                if (Ext.getCmp('check_useSampling_zhishi').getValue()) {
                    Ext.getCmp('text_useSampling_zhishiCount').setValue('5');
                    Ext.getCmp('text_useSampling_zhishiTotal').setValue('417');
                } else {
                    Ext.getCmp('text_useSampling_zhishiCount').setValue('');
                    Ext.getCmp('text_useSampling_zhishiTotal').setValue('');
                }
                break;
        }
    }

    //设置图层是否可视的方法
    function setLayerVisibility() {
        //用dojo.query获取css为listCss的元素数组
        var inputs = dojo.query(".listCss");
        visible = [];
        //对checkbox数组进行变量把选中的id添加到visible
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].checked) {
                visible.push(inputs[i].id);
            }
        }
        //设置可视图层
        dynamicMapServiceLayer.setVisibleLayers(visible);
    }


});
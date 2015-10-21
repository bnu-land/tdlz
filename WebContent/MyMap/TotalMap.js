//规划功能地图js
var jstNavToolbar, jstMap, jstSymbol, jstInfoTemplate, jstDynamicMapServiceLayer, jstQueryTask, jstQuery;
var jsPortalUrl = "http://www.arcgis.com";

require([
         "esri/map",
         
         "esri/geometry/Point",
         "esri/geometry/Extent",
         "esri/SpatialReference",
         "esri/layers/FeatureLayer",
        
         "esri/InfoTemplate",
         "esri/symbols/SimpleFillSymbol",
         "esri/renderers/ClassBreaksRenderer",
         "esri/tasks/query",
         "esri/tasks/QueryTask",
         
         "esri/toolbars/navigation",
         "esri/dijit/OverviewMap",
         "esri/layers/graphics",
         "dijit/layout/BorderContainer",
         "dijit/layout/ContentPane",
         "esri/config",
         "esri/request",
         "esri/geometry/scaleUtils",
         "esri/renderers/SimpleRenderer",
         "esri/symbols/PictureMarkerSymbol",
         "esri/symbols/SimpleFillSymbol",
         "esri/symbols/SimpleLineSymbol",
         "dojo/json",
         "dojo/parser",
         "dojo/sniff",
         "dojo/_base/array",
         "esri/Color",
         "dojo/_base/lang",
         
         "esri/dijit/InfoWindowLite",  //弹出对话框
         "esri/dijit/Popup",           //弹出对话框
         "esri/dijit/PopupTemplate",   //弹出对话框模板
         "esri/dijit/HomeButton",  //Home按钮
         "esri/dijit/Scalebar",    //比例尺
         "esri/dijit/LayerSwipe",  //图层卷帘效果

         "dojo/_base/Color",
         "dojo/dom",
         "dojo/dom-class",
         "dojo/dom-style",
         "dojo/dom-construct",

         "dojo/on",
         "dojo/number",
         "dojo/string",
         "dojo/domReady!"
     ], function (Map, Point, Extent, SpatialReference, 
    		 FeatureLayer, InfoTemplate, SimpleFillSymbol, ClassBreakRenderer, 
    		 Query, QueryTask, InfoWindowLite, Popup, PopupTemplate, HomeButton, Scalebar, 
    		 LayerSwipe, Color, dom, domClass, domStyle, domConstruct, on, number, 
    		 String) {

    jsInit();
   
	//加载地图
	function jsInit() {
		
	   

//     require(["esri/map", "dojo/domReady!"], function(Map) { 
//             jstMap = new esri.Map("guihuaMap", {
//             basemap: "satellite",
//             center: [102.953, 38.472],
//             zoom: 3,
//     		  basemap: "satellite",
//             maxZoom:17         
//             }); 
//       });
	    
//    	jstMap = new esri.Map("guihuaMap");  

	    esriConfig.defaults.geometryService = 
	    	new esri.tasks.GeometryService("http://tasks.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer");

	    jstMap = new esri.Map("totalProjectionMap", { 
	    	sliderOrientation: "horizontal"
	    });        
	    var imageParameters = new esri.layers.ImageParameters();
	    imageParameters.format = "jpeg";  //set the image type to PNG24, note default is PNG8.

	    jstDynamicMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer", {     
	    	"imageParameters":imageParameters
	    });
	    
	    jstMap.addLayer(jstDynamicMapServiceLayer);    
	    jstNavToolbar = new esri.toolbars.Navigation(jstMap);  
	    dojo.connect(jstMap, "onClick", jsExecuteQueryTask);
	    jstQueryTask = new esri.tasks.QueryTask("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer/7");
	
	    dojo.connect(jstQueryTask, "onComplete", jsShowTask);
	    jstQuery = new esri.tasks.Query();
	    jstQuery.returnGeometry = true;
	    jstQuery.outFields = ["*"];
	    
	    var content = "<b>权属单位</b>: ${QSDWMC}" +
	    "<br><b>户主身份证</b>: ${DYNHSFZ}" +
	    "<br><b>房屋结构</b>: ${FWJG}" +
	    "<br><b>房屋使用时间</b>: ${FWSYSJ}" +
	    "<br><img src= 'http://localhost:8888/llOA/housePhotos/${FWZP}'/>";
	    
	  
	    jstInfoTemplate = new esri.InfoTemplate("${DYNHXM}的房子", content);
	}
	
	function jsExecuteQueryTask(evt) {
        //onClick event returns the evt point where the user clicked on the map.
        //This is contains the mapPoint (esri.geometry.point) and the screenPoint (pixel xy where the user clicked).
        //set query geometry = to evt.mapPoint Geometry
        jstQuery.geometry = evt.mapPoint;

        //Execute task and call showResults on completion
        jstQueryTask.execute(jstQuery, jsShowTask);
    }
	
	function jsShowTask(featureSet) {
        //remove all graphics on the maps graphics layer
        jstMap.graphics.clear();
        var symbol = new esri.symbol.SimpleFillSymbol
        (esri.symbol.SimpleFillSymbol.STYLE_SOLID, 
        		new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, 
        				new dojo.Color([102,204,255]), 2), new dojo.Color([255,255,0,0.5]));
        //QueryTask returns a featureSet.  Loop through features in the featureSet and add them to the map.

        dojo.forEach(featureSet.features,function(feature){
	      var graphic = feature;
	      graphic.setSymbol(symbol);
	
	      //Set the infoTemplate.
	      graphic.setInfoTemplate(jstInfoTemplate);
	
	      //Add graphic to the map graphics layer.
	      jstMap.graphics.add(graphic);
        
        });
    }
	
	///////////////
	//图层控制
	window.jstLoadLayerList = function(LayerPanel){

	    var html="<table border='1'cellpadding='0' cellspacing='0' bordercolor='red' style='width: 100%;'>";
	    var LayerDIV=LayerPanel.getEl();//获取DIV
	    var ids = jstMap.layerIds;
	    var infos=jstMap.getLayer(ids[0]).layerInfos;//获取图层的信息库
	    for(var i=0;i<infos.length;i++){//逐条浏览各个图层的信息	    	
	        var info=infos[i]; 
	        html=html+"<tr><td><input id='"+info.id+"' name='layerList' class='listCss' type='checkbox' value='checkbox' onclick='jstSetVisibility()' "+(info.defaultVisibility ? "checked":"")+" />"+info.name+"</td></tr>"
	    }
        html=html+"</table>";

        LayerDIV.setHTML(html);
	}
		
	window.jstSetVisibility = function(){
		
	       //用dojo.query获取css为listCss的元素数组
	    var inputs = dojo.query(".listCss");
	    visible = [];
	       //对checkbox数组进行变量把选中的id添加到visible
	    for(var i=0;i<inputs.length;i++){
	    	
	        if(inputs[i].checked){
	           visible.push(inputs[i].id);
	        }
	    }
	    //设置可视图层
	    try{
	    	jstDynamicMapServiceLayer.setVisibleLayers(visible);
		}catch(e){
			alert(e.message);
		}
	}
	
	
	
	
	
	//直接读shp文件添加图层。
	window.jstAddFeatureLayer = function(featureCollection){
	   	//define a popup template
		var infoTemplateLyr = new esri.InfoTemplate("Details", "${*}");
   
   	
        //create a feature layer based on the feature collection
        jsFeatureLayer = new esri.layers.FeatureLayer(featureCollection, {
          id: 'flickrLayer',
          infoTemplate: infoTemplateLyr
        });

		require([
		  "esri/SpatialReference"
		], function(SpatialReference) {
			var graphics = jsFeatureLayer.graphics;
			for (var i = 0; i < graphics.length; i++)
			{
				var graphic = graphics[i];
				var spatialReference2 = new SpatialReference({"wkid": 2360});
			
				graphic.geometry.setSpatialReference(spatialReference2);
			     
			}
		});
        //associate the features with the popup on click
        jsFeatureLayer.on("click", function(evt) {
          jstMap.infoWindow.setFeatures([evt.graphic]);
        });
//      Ext.Msg.alert("1",JSON.stringify(jsFeatureLayer.toJson()));
			jstMap.addLayers([jsFeatureLayer]);
			
	}
	
    //添加动态图层，需设置工作空间，现暂时不用。
	window.jstAddDynamicLayer1 = function() { 
		
		require([		
		    "esri/layers/LayerDataSource",
			"esri/InfoTemplate",
		    "esri/layers/TableDataSource",
		    ],function (LayerDataSource,  TableDataSource){

	        var content = "<b>Name</b>: ${NAME}" + "<br /><b>Area</b>: ${Area}";
            var TableSource = new esri.layers.TableDataSource(); 
            TableSource.workspaceId = "lichong"; 
            TableSource.dataSourceName = "zxt1111"; 
            var layerSource = new esri.layers.LayerDataSource(); 
            layerSource.dataSource = TableSource; 
              
            var infoTemplate = new esri.InfoTemplate("Dynamic Layer", content);
            var  featureLayer  =  new 
			esri.layers.FeatureLayer("http://localhost:6080/arcgis/rest/services/maptest2/MapServer/dynamicLayer", {                                 
                mode: esri.layers.FeatureLayer.MODE_SNAPSHOT, 
                infoTemplate: infoTemplate,
                source: layerSource 
            }); 
            featureLayer.on("load", function(evt){
          
                var gs = esriConfig.defaults.geometryService;
  				var extent = evt.layer.fullExtent;
  					
  				if ( extent.spatialReference.wkid === jstMap.spatialReference.wkid ) {					
			        jstMap.setExtent(extent);     
			        } else {
			        	gs.project([extent], jstMap.spatialReference).then(function (results) {	
			                jstMap.setExtent(results[0]);
			            });
			        }
  				var Infos = jstDynamicMapServiceLayer.layerInfos;
	
                visible = [];
                
                for (var i = 0; i < Infos.length; i++)
                {
                	visible.push(-1);
                }
                jstDynamicMapServiceLayer.setVisibleLayers(visible);
                    
                var simplePolySymbol = new esri.symbol.SimpleFillSymbol();
                simplePolySymbol.setOutline(new esri.symbol.SimpleLineSymbol(
					  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
					  new dojo.Color([0,0,0,0.5]), 1));
                simplePolySymbol.setColor(new dojo.Color([0,255,0,0.7]));
		
                var renderer = new esri.renderer.SimpleRenderer(simplePolySymbol); 
                featureLayer.setRenderer(renderer); 

                Ext.Msg.alert("1", JSON.stringify(layerSource.toJson()));
                jsLayer1 = featureLayer;
                jsLayer2 = featureLayer;
             //       jstMap.addLayer(jsLayer1); 
            });
		});                       
	};
	
})
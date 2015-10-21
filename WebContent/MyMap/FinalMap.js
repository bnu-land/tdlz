//js
var jsfNavToolbar, jsfMap, jsfInfoTemplate, jsfDynamicMapServiceLayer, jsfLayer1, jsfLayer2, jsfGP;
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
		
	    esri.config.defaults.io.proxyUrl = "/proxy";
	    dojo.connect(dojo.byId("inFile1"), "onchange", function (evt) {
	    		    	
	    	var fileName = evt.target.value.toLowerCase();
	    	if (dojo.isIE) { //filename is full path in IE so extract the file name
	    		var arr = fileName.split("\\");
	    		fileName = arr[arr.length - 1];
	    	}
	    	
	    	if (fileName.indexOf(".zip") !== -1) {//is file a zip - if not notify user 
		    		jsGenerateFeatureCollection(fileName, 1);
		    	}else{
  //        <!-- dojo.byId('upload-status').innerHTML = '<p style="color:red">娣诲姞shp file</p>';	 -->	   
	    	}
	    });
      
	    dojo.connect(dojo.byId("inFile2"), "onchange", function (evt) {
	    	var fileName = evt.target.value.toLowerCase();
	    	if (dojo.isIE) { //filename is full path in IE so extract the file name
	    		var arr = fileName.split("\\");
	    		fileName = arr[arr.length - 1];
	    	}
	    	
	    	if (fileName.indexOf(".zip") !== -1) {//is file a zip - if not notify user 
	    		jsGenerateFeatureCollection(fileName, 2);
	    	}else{
//             <!-- dojo.byId('upload-status').innerHTML = '<p style="color:red">娣诲姞shp file</p>';	 -->	   
	    	}
	    });

//     require(["esri/map", "dojo/domReady!"], function(Map) { 
//             jsfMap = new esri.Map("guihuaMap", {
//             basemap: "satellite",
//             center: [102.953, 38.472],
//             zoom: 3,
//     		  basemap: "satellite",
//             maxZoom:17         
//             }); 
//       });
	    
//    	jsfMap = new esri.Map("guihuaMap");  

	    esriConfig.defaults.geometryService = 
	    	new esri.tasks.GeometryService("http://tasks.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer");

	    jsfMap = new esri.Map("finalMap", { 
	    	sliderOrientation: "horizontal"
	    });        
	    var imageParameters = new esri.layers.ImageParameters();
	    imageParameters.format = "jpeg";  //set the image type to PNG24, note default is PNG8.

	    jsfDynamicMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer", {     
	    	"imageParameters":imageParameters
	    });
	    
	    jsfMap.addLayer(jsfDynamicMapServiceLayer);    
	    jsfNavToolbar = new esri.toolbars.Navigation(jsfMap);
	    
	}
	
	var status;
	window.jsGenerateFeatureCollection = function(fileName,number) {
		
        var name = fileName.split(".");
        //Chrome and IE add c:\fakepath to the value - we need to remove it
        //See this link for more info: http://davidwalsh.name/fakepath
        name = name[0].replace("c:\\fakepath\\","");        
        status = 'upload-status' + number.toString();
        dojo.byId(status).innerHTML = '<b>Loading... </b>' + name;         
        //Define the input params for generate see the rest doc for details
        //http://www.arcgis.com/apidocs/rest/index.html?generate.html
        var params = {	        		
          'name': name,
          'targetSR': jsfMap.spatialReference,
          'maxRecordCount': 1000,
          'enforceInputFileSizeLimit': true,
          'enforceOutputJsonSizeLimit': true
        };

        //generalize features for display Here we generalize at 1:40,000 which is approx 10 meters 
        //This should work well when using web mercator.  
        var extent = esri.geometry.getExtentForScale(jsfMap,40000); 
        var resolution = extent.getWidth() / jsfMap.width;
        params.generalize = true;
        params.maxAllowableOffset = resolution;
        params.reducePrecision = false;
        params.numberOfDigitsAfterDecimal = 0;
        
        var myContent = {
          'filetype': 'shapefile',
          'publishParameters': dojo.toJson(params),
          'f': 'json',
          'callback.html': 'textarea'
        };
		var form = 'uploadForm' + number.toString();
		alert(form);
        //use the rest generate operation to generate a feature collection from the zipped shapefile 
        esri.request({
          url: jsPortalUrl + '/sharing/rest/content/features/generate',
          content: myContent,
          form: dojo.byId(form),
          handleAs: 'json',
          load: dojo.hitch(this, function (response) {
        	  
            if (response.error) {
              errorHandler(response.error);
              return;
            }
            dojo.byId(status).innerHTML = '<b>Loaded: </b>' + response.featureCollection.layers[0].layerDefinition.name;
            jsAddShapefileToMap(response.featureCollection,number);
          }),
          error: dojo.hitch(this, jsErrorHandler)
        });
	}

    function jsErrorHandler(error) {
    	
        alert(error.message);
        dojo.byId(status).innerHTML = "<p style='color:red'>" + error.message + "</p>";
    }
	  
    function jsAddShapefileToMap(featureCollection,number) {
        //add the shapefile to the map and zoom to the feature collection extent
        //If you want to persist the feature collection when you reload browser you could store the collection in 
        //local storage by serializing the layer using featureLayer.toJson()  see the 'Feature Collection in Local Storage' sample
        //for an example of how to work with local storage. 
        var fullExtent;
        var layers = [];
		
        dojo.forEach(featureCollection.layers, function (layer) {
          var infoTemplateLyr = new esri.InfoTemplate("Details", "${*}");
		  if (number == 1)
		  {
			jsfLayer1 = new esri.layers.FeatureLayer(layer, {
            jsfInfoTemplate: infoTemplateLyr
			});

			
			dojo.connect(jsfLayer1,'onClick',function(evt){
		
			var features = jsfLayer1.graphics;

			 
			if(Ext.getCmp("serviceWindowID1")!=undefined)
			{
				Ext.getCmp("serviceWindowID1").close();
			}

            jsfMap.infoWindow.setFeatures([features]);
			  });        
			  fullExtent = fullExtent ? fullExtent.union(jsfLayer1.fullExtent) : jsfLayer1.fullExtent;
		
			  layers.push(jsfLayer1);
		  }else if(number == 2)
		  {
			jsfLayer2 = new esri.layers.FeatureLayer(layer, {
            jsfInfoTemplate: infoTemplateLyr
			});
			dojo.connect(jsfLayer2,'onClick',function(evt){
            jsfMap.infoWindow.setFeatures([evt.graphic]);
			  });        
			  fullExtent = fullExtent ? fullExtent.union(jsfLayer2.fullExtent) : jsfLayer2.fullExtent;
			  layers.push(jsfLayer2);
		  }
		  
          
          //associate the feature with the popup on click to enable highlight and zoomto
          
        });
        jsfMap.addLayers(layers);
        jsfMap.setExtent(fullExtent.expand(1.25), true);        
        dojo.byId(status).innerHTML = "";
    }
    
	
	
	//直接读shp文件添加图层。
	window.jsAddFeatureLayer = function(featureCollection){
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
          jsfMap.infoWindow.setFeatures([evt.graphic]);
        });
//      Ext.Msg.alert("1",JSON.stringify(jsFeatureLayer.toJson()));
			jsfMap.addLayers([jsFeatureLayer]);
			
	}
	
    //添加动态图层，需设置工作空间，现暂时不用。
	window.jsAddDynamicLayer1 = function() { 
		
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
  					
  				if ( extent.spatialReference.wkid === jsfMap.spatialReference.wkid ) {					
			        jsfMap.setExtent(extent);     
			        } else {
			        	gs.project([extent], jsfMap.spatialReference).then(function (results) {	
			                jsfMap.setExtent(results[0]);
			            });
			        }
  				var Infos = jsfDynamicMapServiceLayer.layerInfos;
	
                visible = [];
                
                for (var i = 0; i < Infos.length; i++)
                {
                	visible.push(-1);
                }
                jsfDynamicMapServiceLayer.setVisibleLayers(visible);
                    
                var simplePolySymbol = new esri.symbol.SimpleFillSymbol();
                simplePolySymbol.setOutline(new esri.symbol.SimpleLineSymbol(
					  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
					  new dojo.Color([0,0,0,0.5]), 1));
                simplePolySymbol.setColor(new dojo.Color([0,255,0,0.7]));
		
                var renderer = new esri.renderer.SimpleRenderer(simplePolySymbol); 
                featureLayer.setRenderer(renderer); 

                Ext.Msg.alert("1", JSON.stringify(layerSource.toJson()));
                jsfLayer1 = featureLayer;
                jsfLayer2 = featureLayer;
             //       jsfMap.addLayer(jsfLayer1); 
            });
		});                       
	};
	
})
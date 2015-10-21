//规划功能地图js
var jsNavToolbar, jsMap, jsSymbol, jsInfoTemplate, jsDynamicMapServiceLayer, jsLayer1, jsLayer2, jsQuery, jsGP, jsSelect;
var jsFeatureLayer,jsFeatureSet,jsExtent,jsDrawingInfo,jsFields, jsFJMD;
var jsPortalUrl = "http://www.arcgis.com";
var jsFlag = "";
var resultItems = [];
var resultItems1 = [];
var resultItems2 = [];
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
//             jsMap = new esri.Map("guihuaMap", {
//             basemap: "satellite",
//             center: [102.953, 38.472],
//             zoom: 3,
//     		  basemap: "satellite",
//             maxZoom:17         
//             }); 
//       });
	    
//    	jsMap = new esri.Map("guihuaMap");  

	    esriConfig.defaults.geometryService = 
	    	new esri.tasks.GeometryService("http://tasks.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer");

	    jsMap = new esri.Map("guihuaMap", { 
	    	sliderOrientation: "horizontal"
	    });        
	    var imageParameters = new esri.layers.ImageParameters();
	    imageParameters.format = "jpeg";  //set the image type to PNG24, note default is PNG8.

	    jsDynamicMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer", {     
	    	"imageParameters":imageParameters
	    });
	    
	    jsMap.addLayer(jsDynamicMapServiceLayer);    
	    jsNavToolbar = new esri.toolbars.Navigation(jsMap);
	    
	    var ftLayer = 
	    { mode: esri.layers.FeatureLayer.MODE_ONDEMAND, infoTemplate: InfoTemplate, outFields: ["*"] } 
	    FeatureLayer1 = 
	    	new esri.layers.FeatureLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer/10", ftLayer); 
	    FeatureLayer2 = 
	    	new esri.layers.FeatureLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer/7", ftLayer); 
	    
	    
	  
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
          'targetSR': jsMap.spatialReference,
          'maxRecordCount': 1000,
          'enforceInputFileSizeLimit': true,
          'enforceOutputJsonSizeLimit': true
        };

        //generalize features for display Here we generalize at 1:40,000 which is approx 10 meters 
        //This should work well when using web mercator.  
        var extent = esri.geometry.getExtentForScale(jsMap,40000); 
        var resolution = extent.getWidth() / jsMap.width;
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
			jsLayer1 = new esri.layers.FeatureLayer(layer, {
            jsInfoTemplate: infoTemplateLyr
			});
			Ext.Msg.alert("2", jsLayer1);
			
			dojo.connect(jsLayer1,'onClick',function(evt){
		
			var features = jsLayer1.graphics;

			var dataForGrid = [];
			for (var i = 0; i < features.length; i++)
			{
				var feature = features[i];
				var attribute = feature.attributes;
				dataForGrid.push([attribute.NAME, attribute.SHAPE_AREA]);  
	
			}
			var store = Ext.create('Ext.data.ArrayStore', {  
	        fields: [  
	            {name: 'Name'},  
	            {name: 'Area'},  
	  
	        ],  
	        data: dataForGrid//对应上面代码  
	    	});  
	    
		    var grid = Ext.create('Ext.grid.Panel', {  
	        layout:"fit",  
	        store: store,  
	        // stateful: true,  
	        // stateId: 'stateGrid',  
	        frame:true,  
	        columns: [  
	          
	            {  
	                text     : '名字',  
	                width    : 75,  
	                sortable : false,  
	  
	                dataIndex: 'Name'  
	            },  
	            {  
	                header     : '面积',  
	                width    : 75,  
	                sortable : false,  
	  
	                dataIndex: 'Area'  
            	}  
	        ],  
	        height: 400,  
	        width: 400,  
	   
	        columnLines:true,//列分割线  
	        // title: 'Array Grid',  
	        viewConfig: {  
	            stripeRows: true,//分割线  
	            forceFit: true// 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度  
	  
	        }  
	  
	    	});  
			if(Ext.getCmp("serviceWindowID1")!=undefined)
			{
				Ext.getCmp("serviceWindowID1").close();
			}
	    	var findResultWindow= Ext.create('widget.window', {  
		        id: 'serviceWindowID1',  
		        collapsible:true,  
		         autoScroll:true,//处理gridpanel溢出问题  
		        title: '选中层属性',  
		        height: 400,  
		        width: 410,  
		        x:900,  
		        y:150,  
		        items: [grid]  
		    });  
		  
		    findResultWindow.show();  
            jsMap.infoWindow.setFeatures([features]);
			  });        
			  fullExtent = fullExtent ? fullExtent.union(jsLayer1.fullExtent) : jsLayer1.fullExtent;
			  Ext.Msg.alert("1", JSON.stringify(jsLayer1.toJson()));
			  layers.push(jsLayer1);
		  }else if(number == 2)
		  {
			jsLayer2 = new esri.layers.FeatureLayer(layer, {
            jsInfoTemplate: infoTemplateLyr
			});
			dojo.connect(jsLayer2,'onClick',function(evt){
            jsMap.infoWindow.setFeatures([evt.graphic]);
			  });        
			  fullExtent = fullExtent ? fullExtent.union(jsLayer2.fullExtent) : jsLayer2.fullExtent;
			  layers.push(jsLayer2);
		  }
		  
          
          //associate the feature with the popup on click to enable highlight and zoomto
          
        });
        jsMap.addLayers(layers);
        jsMap.setExtent(fullExtent.expand(1.25), true);        
        dojo.byId(status).innerHTML = "";
    }
    
    //筛选居民点
    window.jsSelectJMD = function(){
    	
		var jsQueryTask = 
			new QueryTask("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer/7");
		var jsQuery = new Query();
		
		jsQuery.returnGeometry = false;
		jsQuery.outFields = [
	        "*"
	    ];
		jsQuery.where = "id > 0";
		
	    jsQueryTask.execute(jsQuery, jsShowResults);
	    
	};
    function jsShowResults(results){		    	
    	
        var resultCount = results.features.length;
        resultItems = [];

        
        for (var i = 0; i < resultCount; i++) {
        	
            var featureAttributes = results.features[i].attributes;   
            resultItems.push([featureAttributes.id, featureAttributes.qsdwdm,
                              featureAttributes.qsdwmc, featureAttributes.dynhxm,
                              featureAttributes.dynhsfz, featureAttributes.zmj,
                              featureAttributes.fwjg, featureAttributes.fwsysj]);
          							                  
        }		
        msgTip.hide();
        
    }
    
    //Select
    window.jsSelect = function(sql){

    	var gpSelect = 'http://localhost:6080/arcgis/rest/services/GP/SelectTest/GPServer/Select';
		jsSelect = new esri.tasks.Geoprocessor(gpSelect);
		
		jsSelect.outSpatialReference=jsMap.spatialReference;
		dojo.connect(jsSelect, "onJobComplete",jsOnSelectComplete);
		alert(sql);
		var parms = {
		SelectCondition  : sql
		};

		jsSelect.submitJob(parms);
    }
    function jsOnSelectComplete(jobInfo) {
    	alert("1");
		dojo.connect(jsSelect, "onGetResultDataComplete",
				jsOnSelectResultComplete);
	
		jsSelect.getResultData(jobInfo.jobId, 					
      				  "AfterSelected");
    }
  //select服务成功后。
	function jsOnSelectResultComplete(paramResult) {		
	   //retrieve the value of the parameter from the paramresult
	   
	 
	    var featureSet = paramResult.value;
	    for (var i = 0; i < featureSet.fields.length; i++)
	    {
			var field = featureSet.fields[i];
	    }
	    
	    // create a featureLayer
		var featureCollection = {
				 "layerDefinition": null,
		         "featureSet": {"features": [],
		             "geometryType": "esriGeometryPolygon"}
		};
		var draw = {
			 "renderer":{
			 "type":"simple",
			 "symbol":{
			 "type":"esriSFS",
			 "style":"esriSFSSolid",
			 "color":[76,129,205,191],
			 "outline":{"type":"esriSLS","style":"esriSLSSolid","color":[0,0,0,255],"width":0.75}},
			 "label":"","description":""},
			 "transparency":0,"labelingInfo":null};
		featureCollection.layerDefinition = {
          "geometryType": "esriGeometryPolygon",
          
          "drawingInfo":draw,
		  "fields": featureSet.fields
        };         
	
		jsFJMD = new FeatureLayer(featureCollection);
		for(var f = 0, fl = featureSet.features.length; f < fl; f++) {

			var feature = featureSet.features[f];		
			jsFJMD.add(feature);
			
		}

		jsMap.addLayer(jsFJMD);
		msgSel.hide();
	}
    //叠加
    window.jsOverlay = function(overData){

		var gpUrl = 'http://localhost:6080/arcgis/rest/services/GP/InterTest/GPServer/Intersect';
		jsGP = new esri.tasks.Geoprocessor(gpUrl);
			
		
		jsGP.outSpatialReference=jsMap.spatialReference;
	//	dojo.connect(gp, "onExecuteComplete",onTaskSuccess);
		dojo.connect(jsGP, "onJobComplete",jsOnTaskComplete);
		
		var parms = {
		OrginLayer : jsFJMD,
        IntersectLayer : overData
		};
	
		jsGP.submitJob(parms);

    }

    function jsOnTaskComplete(jobInfo) {
		/*get the value of an output parameter Buffer_polygons
		  using getResultData. The name of the output
		  may vary in your gpTask    */

		dojo.connect(jsGP, "onGetResultDataComplete",
						  jsOnTaskResultComplete);

		jsGP.getResultData(jobInfo.jobId, 					
                          "Results");
		
		   //add graphicslayer to webmap 
		   //myMap is assumed to be an instance of map container esri.map.
    }
    var unFindLayer;
    //叠加gp服务成功后。
	function jsOnTaskResultComplete(paramResult) {
		
	   //retrieve the value of the parameter from the paramresult
	   
	 //  jsMap.removeLayer(jsLayer1);
	    jsMap.removeLayer(jsFJMD);
	    
	    var featureSet = paramResult.value;

	 // create a featureLayer
		var featureCollection = {
				 "layerDefinition": null,
		         "featureSet": {"features": [],
		             "geometryType": "esriGeometryPolygon"}
		};
		var draw = {
			 "renderer":{
			 "type":"simple",
			 "symbol":{
			 "type":"esriSFS",
			 "style":"esriSFSSolid",
			 "color":[76,200,205,191],
			 "outline":{"type":"esriSLS","style":"esriSLSSolid","color":[0,0,0,255],"width":0.75}},
			 "label":"","description":""},
			 "transparency":0,"labelingInfo":null};
		featureCollection.layerDefinition = {
          "geometryType": "esriGeometryPolygon",
          
          "drawingInfo":draw,
		  "fields": featureSet.fields
        };         
	
		jsFJMD = new FeatureLayer(featureCollection,
				{id: 'overOneLayer',
				mode: FeatureLayer.MODE_SNAPSHOT,
		        outFields: ["*"]});
		for(var f = 0, fl = featureSet.features.length; f < fl; f++) {

			var feature = featureSet.features[f];	

			jsFJMD.add(feature);
			
		}
		var fillSymbol = new esri.symbol.SimpleFillSymbol();
		fillSymbol.setOutline(new esri.symbol.SimpleLineSymbol(
				  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
				  new dojo.Color([0,0,0,0.5]), 1));
		fillSymbol.setColor(new dojo.Color([255,0,0,0.7]));
		var render = new esri.renderer.UniqueValueRenderer(fillSymbol, "DLMC");
		
		render.addValue("村庄", 
				new esri.symbol.SimpleFillSymbol().setColor(new dojo.Color([255,255,0,0.7])));
		jsFJMD.setRenderer(render);
		
		jsMap.addLayer(jsFJMD);
	    //create a graphics layer with features
		
	    var taskResultLayer= new esri.layers.GraphicsLayer
				  ({id:"MyGPSubmitJobResultLayer"});
	    
	    unFindLayer = new esri.layers.GraphicsLayer
		  ({id:"MyUnFindLayer"});
	    //Create a symbol based on the geometry. 
	    //The geometry is assumed to be polygons in the code below
	    /*
	    var simplePolySymbol = new esri.symbol.SimpleFillSymbol();
	    simplePolySymbol.setOutline(new esri.symbol.SimpleLineSymbol(
					  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
					  new dojo.Color([0,0,0,0.5]), 1));
	    simplePolySymbol.setColor(new dojo.Color([255,255,0,0.7]));
	    
	    var unFindPolySymbol = new esri.symbol.SimpleFillSymbol();
	    unFindPolySymbol.setOutline(new esri.symbol.SimpleLineSymbol(
					  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
					  new dojo.Color([0,0,0,0.5]), 1));
	    unFindPolySymbol.setColor(new dojo.Color([255,0,0,0.7]));
	    */
	    
	   
	    var attributes;
		
	    if (jsFlag == "GHSJ") {

	    	for(var f = 0, fl = featureSet.features.length; f < fl; f++) {

				var feature = featureSet.features[f];
				attributes = feature.attributes;
				if (attributes.GHDLDM != "212") {
					var leftDM = attributes.GHDLDM.substring(0, 2);
					if (leftDM != "11" && leftDM != "12" &&
							leftDM != "13" && leftDM != "14" ) {
			
						resultItems2.push([attributes.Id, attributes.QSDWDM,
						                  attributes.QSDWMC, attributes.DYNHXM,
						                  attributes.DYNHSFZ, attributes.Shape_Area,
			                              attributes.GHDLMC, attributes.FID]);
					}
					
				//	feature.setSymbol(unFindPolySymbol);
				//	unFindLayer.add(feature);
					
				}else{
				//	feature.setSymbol(simplePolySymbol);
				//	taskResultLayer.add(feature);

				}
				
			}
		}else{
			
			for(var f = 0, fl = featureSet.features.length; f < fl; f++) {

				var feature = featureSet.features[f];
				attributes = feature.attributes;
				if (attributes.DLMC != "村庄") {

					resultItems1.push([attributes.Id, attributes.QSDWDM,
					                  attributes.QSDWMC, attributes.DYNHXM,
					                  attributes.DYNHSFZ, attributes.Shape_Area,
		                              attributes.DLMC, attributes.FID]);
				//	feature.setSymbol(unFindPolySymbol);
				//	unFindLayer.add(feature);
					
				}else{
				//	feature.setSymbol(simplePolySymbol);
				//	taskResultLayer.add(feature);
				}
				
			}
		}		 
		//jsMap.addLayer(taskResultLayer);
		//jsMap.addLayer(unFindLayer);
		
		msgOver.hide();
	}
	
	//编辑feature数据
	window.jsEditFeature = function(updateAttributes){
		require([
		         "esri/graphic",
		       ], function(Graphic) {
			jsFJMD.setEditable(true);
			
			for ( var i = 0; i < jsFJMD.graphics.length; i++) {
				var g = jsFJMD.graphics[i];
				
				if (g.attributes.Id == updateAttributes.attributes.Id) {
					
					g.attributes.DLMC = updateAttributes.attributes.DLMC;
					g.attributes.SFDYXZSJ = updateAttributes.attributes.SFDYXZSJ;
				}	
			}
			jsMap.removeLayer(jsFJMD);
			
			/*
			var fillSymbol = new esri.symbol.SimpleFillSymbol();
			fillSymbol.setOutline(new esri.symbol.SimpleLineSymbol(
					  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
					  new dojo.Color([0,0,0,0.5]), 1));
			fillSymbol.setColor(new dojo.Color([255,0,0,0.7]));
			var render = new esri.renderer.UniqueValueRenderer(fillSymbol, "DLMC");
			
			render.addValue("村庄", 
					new esri.symbol.SimpleFillSymbol().setColor(new dojo.Color([255,255,0,0.7])));
			jsFJMD.setRenderer(render);
			*/
			var sql = "\"DLMC\" = '村庄'";
			
			//jsMap.addLayer(jsFJMD);
			jsFlag = "edit";
			jsSelectCommon(sql);
		});
	}
	
	var jsSelectCom;
	//常规select服务
	var jsProjectID;
	window.jsSelectCommon = function(sql, projectID){
		
		var gpSelect = 'http://localhost:6080/arcgis/rest/services/GP/SelectCom/GPServer/SelectCom';
		jsSelectCom = new esri.tasks.Geoprocessor(gpSelect);
		
		jsSelectCom.outSpatialReference=jsMap.spatialReference;
		dojo.connect(jsSelectCom, "onJobComplete", jsOnSelComComplete);
		jsProjectID = projectID;
		var parms = {
		BeforSelect :jsFJMD,
		SQL : sql
		};

		jsSelectCom.submitJob(parms);
    }
	
    function jsOnSelComComplete(jobInfo) {

		dojo.connect(jsSelectCom, "onGetResultDataComplete",
				jsOnSelComResultComplete);
	
		jsSelectCom.getResultData(jobInfo.jobId, 					
      				  "AfterSelect");
    }
    //select服务成功后。
	function jsOnSelComResultComplete(paramResult) {	
		var featureSet = paramResult.value;
	    for (var i = 0; i < featureSet.fields.length; i++)
	    {
			var field = featureSet.fields[i];
	    }
	    
	    // create a featureLayer
		var featureCollection = {
				 "layerDefinition": null,
		         "featureSet": {"features": [],
		             "geometryType": "esriGeometryPolygon"}
		};
		var draw = {
			 "renderer":{
			 "type":"simple",
			 "symbol":{
			 "type":"esriSFS",
			 "style":"esriSFSSolid",
			 "color":[255,255,0,0.7],
			 "outline":{"type":"esriSLS","style":"esriSLSSolid","color":[0,0,0,255],"width":0.75}},
			 "label":"","description":""},
			 "transparency":0,"labelingInfo":null};
		featureCollection.layerDefinition = {
          "geometryType": "esriGeometryPolygon",
          
          "drawingInfo":draw,
		  "fields": featureSet.fields
        };         
	
		jsFJMD = new FeatureLayer(featureCollection);
		for(var f = 0, fl = featureSet.features.length; f < fl; f++) {

			var feature = featureSet.features[f];
			feature.attributes.projectID = jsProjectID;
			jsFJMD.add(feature);
			
		}
		var fillSymbol = new esri.symbol.SimpleFillSymbol();
		fillSymbol.setOutline(new esri.symbol.SimpleLineSymbol(
				  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
				  new dojo.Color([0,0,0,0.5]), 1));
		fillSymbol.setColor(new dojo.Color([255,0,0,0.7]));
		var render = new esri.renderer.UniqueValueRenderer(fillSymbol, "DLMC");
		
		render.addValue("村庄", 
				new esri.symbol.SimpleFillSymbol().setColor(new dojo.Color([255,255,0,0.7])));
		jsFJMD.setRenderer(render);

		jsMap.addLayer(jsFJMD);
	
		
		msgEdit.hide();
		
	}
	
	window.jsEditOnline = function(projectID, ids){

		var featureServer = new FeatureLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/FeatureServer/7");
		var voteRecord = [];
		var id = ids.split(",");
		for ( var int = 0; int < id.length; int++) {
			Ext.Msg.alert("1", id[int]);
			var record = {
					attributes: {
						objectid: parseInt(id[int]),
						projectid: projectID
					}
				}
				voteRecord.push(record);
		}
		
		featureServer.applyEdits(null, voteRecord, null,  function(results) {alert(results)},  function(results) {alert(JSON.stringify(results))});
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
          jsMap.infoWindow.setFeatures([evt.graphic]);
        });
//      Ext.Msg.alert("1",JSON.stringify(jsFeatureLayer.toJson()));
			jsMap.addLayers([jsFeatureLayer]);
			
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
  					
  				if ( extent.spatialReference.wkid === jsMap.spatialReference.wkid ) {					
			        jsMap.setExtent(extent);     
			        } else {
			        	gs.project([extent], jsMap.spatialReference).then(function (results) {	
			                jsMap.setExtent(results[0]);
			            });
			        }
  				var Infos = jsDynamicMapServiceLayer.layerInfos;
	
                visible = [];
                
                for (var i = 0; i < Infos.length; i++)
                {
                	visible.push(-1);
                }
                jsDynamicMapServiceLayer.setVisibleLayers(visible);
                    
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
             //       jsMap.addLayer(jsLayer1); 
            });
		});                       
	};
	
})
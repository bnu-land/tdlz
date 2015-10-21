//规划功能地图js
var jswNavToolbar, jswMap, jswInfoTemplate, jswDynamicMapServiceLayer, jswFJMD;
var jswPortalUrl = "http://www.arcgis.com";
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
	if (jswFJMD != undefined) {
		jswMap.removeLayer(jswFJMD);
		jswRenderLayer();
		jswMap.addLayer(jswFJMD);
	}else{
		jswInit();
	}

	window.jswFindLogs = function(id) {
		
		jwsLogsStore = Ext.create('Ext.data.Store', {
    	    proxy: {
    	        // load using HTTP
    	        type: 'ajax',
    	        url: 'showLogs.action',  
    	        // the return will be XML, so lets set up a reader
    	        reader:{
        			type:"json",
        			root:"results",
        			totalProperty :'totalCount'		
        		},
        		
        		actionMethods: {  
                    read: 'POST'  
                },
        		writer:{
        			type:"json"
        		},
        		
    	    },
    	    fields: [
				{
				    name: 'projectID',
				    type: 'string',
				},
				{
				    name: 'cp.DYNHXM',
				    type: 'string',
				},
				{
				    name: 'cp.DYNHSFZ',
				    type: 'string',
				},  
                {
                    name: 'cp.houseNumber',
                    type: 'string',
                },
                {
                    name: 'cp.demolitionFlag',
                    type: 'string',
                },
                {
                    name: 'date',
                    type: 'auto',
             
                },
                {
                    name: 'content',
                    type: 'string',
                },
                {
                    name: 'person',
                    type: 'string',
                },
                {
                    name: 'photoNumber',
                    type: 'auto',
                },
                {
                	name: 'photoPath',
                	type: 'string',
                },
                {
                	name: 'photoExt',
                	type: 'string',
                }
    	    ]
		});
		jwsLogsStore.load({params: {
			numberSFZ :"" + id + ""
		},
    		callback: function(records, options, success){
    			var jwsLogsPanel = Ext.create('Ext.grid.Panel', { 								    	  
    		    	  region: 'center',
    		    	  allowDeselect: true,
    		    	  columnLines: true,
    		    	  store: jwsLogsStore,
    		    	  columns: [
							 {
								 xtype: 'gridcolumn',
							
								 width: 127,
								 dataIndex: 'projectID',
								 text: '项目编号'
							 },   
    						 {
    							 xtype: 'gridcolumn',

    							 width: 127,
    							 dataIndex: 'cp.houseNumber',
    							 text: '房屋编号'
    						 },      
    						 {
    							 xtype: 'gridcolumn',

    							 width: 127,
    							 dataIndex: 'cp.DYNHXM',
    							 text: '拆迁户主姓名'
    						 },
    	                     {
    	                         xtype: 'gridcolumn',

    	                         width: 127,
    	                         dataIndex: 'cp.DYNHSFZ',
    	                         text: '拆迁户主身份证'
    	                     },
    	                     {
    	                         xtype: 'datecolumn',
    	  
    	                         width: 151,
    	                         dataIndex: 'date',
    	                         text: '创建时间'
    	                     },
    	                     {
    	                         xtype: 'gridcolumn',
    	                         text: '查看内容',
    	                         columns: [
    	                                {
    	                                    xtype: 'actioncolumn',
    	                                    height: 0,
    	                                    items: [
    	                                        {
    	                                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
    	                                            	
    	                                            	var logDetailForm = Ext.create('Ext.form.Panel', {
    	                                            		height: 231,
    	                                                    autoScroll: true,
    	                                                    bodyPadding: 10,
    	                                                    title: '日志详细内容',
    	                                                    items: [
    	                                						{
    	                                						    xtype: 'textfield',
    	                                						    anchor: '100%',
    	                                						    fieldLabel: '拆迁状态'
    	                                						},
    	                                                        {
    	                                                            xtype: 'textfield',
    	                                                            anchor: '100%',
    	                                                            fieldLabel: '创建时间'
    	                                                        },
    	                                                        {
    	                                                            xtype: 'textfield',
    	                                                            anchor: '100%',
    	                                                            fieldLabel: '拆迁户主身份证'
    	                                                        },
    	                                                        {
    	                                                            xtype: 'textfield',
    	                                                            anchor: '100%',
    	                                                            fieldLabel: '拆迁户主姓名'
    	                                                        },
    	                                                        {
    	                                                            xtype: 'textfield',
    	                                                            anchor: '100%',
    	                                                            fieldLabel: '参与人员'
    	                                                        },
    	                                                        {
    	                                                            xtype: 'textareafield',
    	                                                            anchor: '100%',
    	                                                            fieldLabel: '日志内容'
    	                                                        },
    	                                                        {
    	                                                            xtype: 'fieldset',
    	                                                            height: 205,
    	                                                            autoScroll: true,
    	                                                            items: [
    	                                                                    
    	                                                            ]
    	                                                        },
    	                                                        {
    	                                                            xtype: 'button',
    	                                                            text: '返回',
    	                                                            handler: function(){
    	                          									    logDetialWindow.destroy();
    	                          								    },
    	                                                        }
    	                                                     ]
    	                                            	});
    	                                            	logDetailForm.getComponent(6).removeAll();
    	                                            	var jswContent = record.get("content");
    	                                            	var jswCount = record.get("photoNumber");
    	                                            	var jswPath = record.get("photoPath");
    	                                            	var jswExt = record.get("photoExt");
    	                                            	var jswPerson = record.get("person");
    	                                            	var jswDate = record.get("date");
    	                                            	var jswCardStatus = record.get('cp.demolitionFlag');
    	                                            	var jswSFZ = record.get('cp.DYNHSFZ');
    	                                            	var jswName = record.get('cp.DYNHXM');
    	                                            	var jswCount = record.get("photoNumber");
    	                                            	logDetailForm.getComponent(1).setValue(jswDate);
    	                                            	logDetailForm.getComponent(2).setValue(jswSFZ);
    	                                            	logDetailForm.getComponent(0).setValue(jswCardStatus);
    	                                            	logDetailForm.getComponent(3).setValue(jswName);
    	                                            	logDetailForm.getComponent(4).setValue(jswPerson);
    	                                            	logDetailForm.getComponent(5).setValue(jswContent);
    	                                  
    	                                            	//动态添加图片框
    	                                            	var ext= new Array();
    	                                            	ext =  jswExt.split(",");
    	                                            	for ( var i = 1; i <= jswCount; i++) {
    	                                            		var imgBox = new Ext.Img({
    	                 		                            	height: 201,
    	                 	                                    width: 201,
    	                 		                            });
    	                 		                            
    	                 		                            var path = "uploadPhoto/" + jswSFZ + jswDate + i.toString() + ext[i - 1];
    	                 		                    
    	                 		                            imgBox.setSrc(path);		                            
    	                 		                      
    	                 		                            logDetailForm.getComponent(6).add(imgBox);
    	                 		               
    													}
    	                                            	
    	                                            	var logDetialWindow = Ext.create('Ext.window.Window',{
    	                                    	    		title: '日志内容窗口',
    	                                    	    		width: 800,
    	                                    	    		constrain: true,
    	                                    	    		height: 600,
    	                                    	    		layout: {
    	                                    	    			type: 'fit',
    	                                    	    		},
    	                                 
    	                                    	    		items: [
    	                                    	    		                                        						 
    	                                    	    		]
    	                                    	    		 
    	                                            	});
    	                                            	logDetialWindow.add(logDetailForm);
    	                                            	logDetialWindow.show();
    	                                            }
    	                                        }
    	                                    ]
    	                                }
    	                            ]
    	                        }                                         					 
    				   ],
    				   
    		      });
    		      var jswLogsWindow = Ext.create('Ext.window.Window',{
    		    		title: '地块图层属性',
    		    		width: 600,
    		    		constrain: true,
    		    		height: 600,
    		    		layout: {
    		    			type: 'border',
    		    		},
    		    		items: [
    		    		     
    		    		     {
    							 xtype: 'panel',														 
    							 region: 'south',
    							 items: [
    								  {
    									  
    									  xtype: 'button',
    									  text: '确定',
    									  handler: function(){
    										  jswLogsWindow.destroy();
    									  },
    									  width: 70,
    									  
    								  },
    							 ]
    						 }
    							 
    		    		]
    		    		 
    		      });
    		      jswLogsWindow.add(jwsLogsPanel);
    		      jswLogsWindow.show();

    		}
		});
		
		
		
	}
	
	//加载地图
	function jswInit() {

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
//             jswMap = new esri.Map("guihuaMap", {
//             basemap: "satellite",
//             center: [102.953, 38.472],
//             zoom: 3,
//     		  basemap: "satellite",
//             maxZoom:17         
//             }); 
//       });
	    
//    	jswMap = new esri.Map("guihuaMap");  

	    esriConfig.defaults.geometryService = 
	    	new esri.tasks.GeometryService("http://tasks.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer");

	    jswMap = new esri.Map("warmMap", { 
	    	sliderOrientation: "horizontal"
	    });        
	    var imageParameters = new esri.layers.ImageParameters();
	    imageParameters.format = "jpeg";  //set the image type to PNG24, note default is PNG8.

	    jswDynamicMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/testData/MapServer", {     
	    	"imageParameters":imageParameters
	    });

	    jswMap.addLayer(jswDynamicMapServiceLayer);  
	    

		if (mapStore.isLoading()) {
			msgTip = Ext.MessageBox.show({
    	    	title:'提示',
    	    	width : 250,
    	    	msg:'加载中...',
    	    	progress:true,  
    	    	closable:false, 			
		  }); 
		}
		
	    var content = "<b>权属单位</b>: ${QSDWMC}" +
	    "<br><b>项目编号</b>: ${projectID}" +
	    "<br><b>户主身份证</b>: ${DYNHSFZ}" +
	    "<br><b>房屋结构</b>: ${FWJG}" +
	    "<br><b>房屋使用时间</b>: ${FWSYSJ}" +
	    "<br><img src='http://localhost:8888/llOA/housePhotos/${FWZP}'/>" +
	    "<br><input type='button' onClick='jswFindLogs(${Id})' value='拆迁日志'/>";
	  
	    var infoTemplate = new InfoTemplate("${DYNHXM}的房子", content);
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
        }; 
		
	    mapStore.each(function(record){
	    	
	    	var data = record.get("data");
	    	featureCollection.layerDefinition.fields = data[0].layerDefinition.fields;
	  
	    	for (var int = 0; int < data.length; int++){
	    		var featureSet = data[int].featureSet;

	    		for (var i = 0; i < featureSet.features.length; i++){

		    		featureCollection.featureSet.features.push(featureSet.features[i]);
		    	}
	    	}
    	
	    });
	    
    	jswFJMD = new FeatureLayer(featureCollection,{
            infoTemplate: infoTemplate
        });
    	
    	jswFJMD.on("click", function(evt) {

    		jswMap.infoWindow.setFeatures([evt.graphic]);
        });
		jswRenderLayer();
		jswMap.addLayer(jswFJMD);
	    
	    jswNavToolbar = new esri.toolbars.Navigation(jswMap);

	  
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
          'targetSR': jswMap.spatialReference,
          'maxRecordCount': 1000,
          'enforceInputFileSizeLimit': true,
          'enforceOutputJsonSizeLimit': true
        };

        //generalize features for display Here we generalize at 1:40,000 which is approx 10 meters 
        //This should work well when using web mercator.  
        var extent = esri.geometry.getExtentForScale(jswMap,40000); 
        var resolution = extent.getWidth() / jswMap.width;
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
          url: jswPortalUrl + '/sharing/rest/content/features/generate',
          content: myContent,
          form: dojo.byId(form),
          handleAs: 'json',
          load: dojo.hitch(this, function (response) {
        	  
            if (response.error) {
              errorHandler(response.error);
              return;
            }
            dojo.byId(status).innerHTML = '<b>Loaded: </b>' + response.featureCollection.layers[0].layerDefinition.name;
            jswAddShapefileToMap(response.featureCollection,number);
          }),
          error: dojo.hitch(this, jswErrorHandler)
        });
	}

    function jswErrorHandler(error) {
    	
        alert(error.message);
        dojo.byId(status).innerHTML = "<p style='color:red'>" + error.message + "</p>";
    }
	  
    function jswAddShapefileToMap(featureCollection,number) {
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
            jswInfoTemplate: infoTemplateLyr
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
            jswMap.infoWindow.setFeatures([features]);
			  });        
			  fullExtent = fullExtent ? fullExtent.union(jsLayer1.fullExtent) : jsLayer1.fullExtent;
			  Ext.Msg.alert("1", JSON.stringify(jsLayer1.toJson()));
			  layers.push(jsLayer1);
		  }else if(number == 2)
		  {
			jsLayer2 = new esri.layers.FeatureLayer(layer, {
            jswInfoTemplate: infoTemplateLyr
			});
			dojo.connect(jsLayer2,'onClick',function(evt){
            jswMap.infoWindow.setFeatures([evt.graphic]);
			  });        
			  fullExtent = fullExtent ? fullExtent.union(jsLayer2.fullExtent) : jsLayer2.fullExtent;
			  layers.push(jsLayer2);
		  }
		  
          
          //associate the feature with the popup on click to enable highlight and zoomto
          
        });
        jswMap.addLayers(layers);
        jswMap.setExtent(fullExtent.expand(1.25), true);        
        dojo.byId(status).innerHTML = "";
    }
    
  
	//编辑feature数据
	window.jswEditFeature = function(updateAttributes){
		require([
		         "esri/graphic",
		       ], function(Graphic) {

			jswFJMD.setEditable(true);
			
			for ( var i = 0; i < jswFJMD.graphics.length; i++) {
				var g = jswFJMD.graphics[i];
				
				if (g.attributes.Id == updateAttributes.attributes.Id) {
					
					g.attributes.DLMC = updateAttributes.attributes.DLMC;
					g.attributes.SFDYXZSJ = updateAttributes.attributes.SFDYXZSJ;
				}	
			}
			jswMap.removeLayer(jswFJMD);
			
			/*
			var fillSymbol = new esri.symbol.SimpleFillSymbol();
			fillSymbol.setOutline(new esri.symbol.SimpleLineSymbol(
					  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
					  new dojo.Color([0,0,0,0.5]), 1));
			fillSymbol.setColor(new dojo.Color([255,0,0,0.7]));
			var render = new esri.renderer.UniqueValueRenderer(fillSymbol, "DLMC");
			
			render.addValue("村庄", 
					new esri.symbol.SimpleFillSymbol().setColor(new dojo.Color([255,255,0,0.7])));
			jswFJMD.setRenderer(render);
			*/
			var sql = "\"DLMC\" = '村庄'";
			
			//jswMap.addLayer(jswFJMD);

		});
	}

	function jswRenderLayer(){
		jswFJMD.setEditable(true);
		
		jswCardStore.each(function(record){
			var cardHouseID = record.get('houseNumber');
			
			var cardFlag = record.get('demolitionFlag');
			for ( var i = 0; i < jswFJMD.graphics.length; i++) {
				var g = jswFJMD.graphics[i];
				
				if (g.attributes.Id == cardHouseID) {
					
					if (cardFlag == '正在拆迁') {
						g.attributes.ZMJ = 1;
						continue;
					}
					if (cardFlag == '拆迁完成') {
						g.attributes.ZMJ = 2;
						continue;
					}
					if (cardFlag == '未拆迁') {
						g.attributes.ZMJ = 0;
						continue;
					}
				}	
			}
		});

		var fillSymbol = new esri.symbol.SimpleFillSymbol();
		fillSymbol.setOutline(new esri.symbol.SimpleLineSymbol(
				  esri.symbol.SimpleLineSymbol.STYLE_SOLID,
				  new dojo.Color([0,0,0,0.5]), 1));
		fillSymbol.setColor(new dojo.Color([255,255,0,0.7]));
		var render = new esri.renderer.UniqueValueRenderer(fillSymbol, "ZMJ");
		
		render.addValue(1, 
				new esri.symbol.SimpleFillSymbol().setColor(new dojo.Color([255,0,0,0.7])));
		render.addValue(2, 
				new esri.symbol.SimpleFillSymbol().setColor(new dojo.Color([0,255,0,0.7])));
		jswFJMD.setRenderer(render);
	}
	
})
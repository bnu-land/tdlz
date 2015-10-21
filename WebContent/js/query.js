//引入命名空间
dojo.require("esri.map");
dojo.require("esri.tasks.query");
dojo.require("esri.tasks.find");

var map, queryTask, query;

var symbol, infoTemplate, infoTemplateMohu,infoTemplateKxc;

var searchkeyword,findTask, findParams;

//用来记录显示图层的id用
var visible = [];
//ArcGISDynamicMapServiceLayer
var dynamicMapServiceLayer=[];

//地图初始化方法
function init() 
{

   map = new esri.Map("map", {
       logo: false
   });
   dynamicMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tdlzmap/tdlz6/MapServer");
   //添加图层载入后监听方法loadLayerList
   dojo.connect(dynamicMapServiceLayer,"onLoad",loadLayerList);
   //添加到地图控件进行显示
   map.addLayer(dynamicMapServiceLayer);
   
   //loadLayerList(dynamicMapServiceLayer);
     
   //默认查询所有地块
   queryDk();
}

//地块点击查询---begin
function queryDk(){
        //Listen for click event on the map, when the user clicks on the map call executeQueryTask function.
        dojo.connect(map, "onClick", executeQueryTask);

        //build query task
        //queryTask = new esri.tasks.QueryTask("http://localhost:8399/arcgis/rest/services/fangsjjxxg_qy/MapServer/9");
		queryTask = new esri.tasks.QueryTask("http://localhost:6080/arcgis/rest/services/tdlzmap/tdlz6/MapServer/3");
        
        //Can listen for onComplete event to process results or can use the callback option in the queryTask.execute method.
        //dojo.connect(queryTask, "onComplete", showResults);

 		//build query filter
        query = new esri.tasks.Query();
        query.returnGeometry = true;
        //query.where = "MJ > 120";
        query.outFields = ["TDLX","DKWZ","ZLDJ","QSXZ","QSDWMC","CZR","MJ","XCZP"];
       
        //create the infoTemplate to be used in the infoWindow.
        //All ${attributeName} will be substituted with the attribute value for current feature.
        //infoTemplate = new esri.InfoTemplate("${名称}", "乡镇名称: ${名称}<br />ID: ${ID}");
		infoTemplate = new esri.InfoTemplate("地块详细信息", "土地类型: ${TDLX}<br/>地块位置: ${DKWZ}<br/>质量等级: ${ZLDJ}<br/>权属性质: ${QSXZ}<br/>所属村: ${QSDWMC}<br/>采集员: ${CZR}<br/>面积: ${MJ} 平方米<br/>现场照片：<img src='images/${XCZP}.JPG'/>");
		
        symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([0,255,255]), 3), new dojo.Color([255,255,0,0.6]));

}

function executeQueryTask(evt) {
        //onClick event returns the evt point where the user clicked on the map.
        //This is contains the mapPoint (esri.geometry.point) and the screenPoint (pixel xy where the user clicked).
        //set query geometry = to evt.mapPoint Geometry
        query.geometry = evt.mapPoint;

        //Execute task and call showResults on completion
        queryTask.execute(query, showResults);
      }

function showResults(featureSet) {
        //remove all graphics on the maps graphics layer
        clearSel();
        
        var features = featureSet.features;
        
        //QueryTask returns a featureSet.  Loop through features in the featureSet and add them to the map.
        for (var i=0, il=features.length; i<il; i++) {
          //Get the current feature from the featureSet.
          //Feature is a graphic
          var graphic = features[0];
          graphic.setSymbol(symbol);

          //Set the infoTemplate.
          graphic.setInfoTemplate(infoTemplate);

          //Add graphic to the map graphics layer.
          map.graphics.add(graphic);
        }

        dojo.connect(map.graphics, "onMouseOver", function(evt) {
          var g = evt.graphic;
          map.infoWindow.setContent(g.getContent());
          map.infoWindow.setTitle(g.getTitle());
          map.infoWindow.show(evt.screenPoint,map.getInfoWindowAnchor(evt.screenPoint));
        });
        dojo.connect(map.graphics, "onMouseOut", function() {map.infoWindow.hide();} );

      }
//地块点击查询---end


//模糊查询---begin
function findmultilayerbyattr(){
   //实例化FindTask
   findTask = new esri.tasks.FindTask("http://localhost:6080/arcgis/rest/services/tdlzmap/tdlz6/MapServer");
   //FindTask的参数
   findParams = new esri.tasks.FindParameters();
   //返回Geometry
   findParams.returnGeometry = true;
   //查询的图层id
   findParams.layerIds = [0,1,2];
   //查询字段
   findParams.searchFields = ["TDLX","DKWZ","ZLDJ","QSXZ","QSDWMC","CZR","MJ","XCZP"];
   //详细信息
   infoTemplateMohu = new esri.InfoTemplate("地块详细信息", "土地类型: ${TDLX}<br/>地块位置: ${DKWZ}<br/>质量等级: ${ZLDJ}<br/>权属性质: ${QSXZ}<br/>所属村: ${QSDWMC}<br/>采集员: ${CZR}<br/>面积: ${MJ} 平方米<br/>现场照片：<img src='images/${XCZP}.JPG'/>");
} 

//根据输入的关键字进行findTask操作
function findbyattr(searchText)
{
   //alert("searchText"+searchText+"---");
   if(searchText=='') 
   {
   	   alert("请输入关键字");
   	   return;
   }
   else{    
   	   searchkeyword = searchText;
   	   //模糊查询
   	   findmultilayerbyattr();
	   findParams.searchText = searchText;
	   findTask.execute(findParams,showFindResults);
	   //清空上次查询结果
	   clearSel();
   }
}

//显示findTask的结果
function showFindResults(results)
{
  //清楚上一次的高亮显示
   map.graphics.clear();
   //var dataForGrid = [];
   var rExtent = new esri.geometry.Extent();
   for(var i=0;i<results.length;i++)
   {
      var curFeature=results[i];
      var graphic = curFeature.feature;
      //把查询到的对象的字段信息等插入到dataForGrid中
      //dataForGrid.push(graphic.attributes);
      //根据类型设置显示样式
      switch (graphic.geometry.type)
      {
         case "point":
              var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255,0,0]), 1), new dojo.Color([0,255,0,0.25]));
              break;
         case "polyline":
              var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASH, new dojo.Color([255,0,0]), 1);
              break;
         case "polygon":
              var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([0,255,255]), 3), new dojo.Color([255,255,0,0.4]));
                  //symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255,0,0]), 2), new dojo.Color([255,255,0,0.5]));
              break;
       }
       //设置显示样式
       graphic.setSymbol(symbol);
       //添加到graphics进行高亮显示
       map.graphics.add(graphic);
       
       //设置地图视图范围
       if(i==0)
       {
          rExtent=graphic.geometry.getExtent();
       }
       else
       {
          rExtent=rExtent.union(graphic.geometry.getExtent());
       }
       //设置地图视图范围
   	   map.setExtent(rExtent);
   	   
   	   //Set the infoTemplate.
       graphic.setInfoTemplate(infoTemplateMohu);
   	   
   	   dojo.connect(map.graphics, "onMouseOver", function(evt) {
          var g = evt.graphic;
          map.infoWindow.setContent(g.getContent());
          map.infoWindow.setTitle(g.getTitle());
          map.infoWindow.show(evt.screenPoint,map.getInfoWindowAnchor(evt.screenPoint));
        });
        dojo.connect(map.graphics, "onMouseOut", function() {map.infoWindow.hide();} );
   }
   
   //在右上方显示查询模糊查询结果数目
   var resultdivhtml = "根据关键字'<b>"+searchkeyword+"</b>',查询到"+results.length+"个地块";
   //alert(resultdivhtml);
   dojo.byId("resultdiv").innerHTML=resultdivhtml;
   
}
//模糊查询---end


//清空所选，取消高亮显示
function clearSel()
{
  //清楚上一次的高亮显示
   map.graphics.clear();
   dojo.byId("resultdiv").innerHTML="";
}

//图层显示-----begin
//载入地图名称到右边的列表中
function loadLayerList(layers)
{
   
   //alert("loadlayerlist");
   var html="<div class='tclb'>图层列表</div><br>";
   //获取图层信息
   var infos=layers.layerInfos;
   // alert(infos.length);
   for(var i=0;i<infos.length;i++)
   {
     
      var info = infos[i];
      //图层默认显示的话就把图层id添加到visible
      if(info.defaultVisibility)
      {
         visible.push(info.id);
      }
      //输出图层列表的html
      html=html+"<div style='line-height:24px;'><input id='"+info.id+"' name='layerList' class='listCss' type='checkbox' value='checkbox' onclick='setLayerVisibility()' "+(info.defaultVisibility ? "checked":"")+" />"+info.name+"</div>"
   }
   html=html+"<br><div><img src='images/tuli01.png'/></div>";
   //设置可视图层
   dynamicMapServiceLayer.setVisibleLayers(visible);
   //在右边显示图层名列表
   dojo.byId("toc").innerHTML=html;
}
//设置图层是否可视的方法
function setLayerVisibility()
{
   //用dojo.query获取css为listCss的元素数组
   var inputs = dojo.query(".listCss");
   visible = [];
   //对checkbox数组进行变量把选中的id添加到visible
   for(var i=0;i<inputs.length;i++)
   {
      if(inputs[i].checked)
      {
         visible.push(inputs[i].id);
      } 
   }
   //设置可视图层
   dynamicMapServiceLayer.setVisibleLayers(visible);
}
//图层显示-----end

dojo.addOnLoad(init);
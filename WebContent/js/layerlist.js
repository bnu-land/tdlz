//图层显示-----begin
//载入地图名称到右边的列表中
function loadLayerList(layers)
{
   alert("loadLayerList");
   var html="<div class='tclb'>图层列表</div><br>";
   //获取图层信息
   var infos=layers.layerInfos;
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
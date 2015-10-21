<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
    <%
    String contextPath = request.getContextPath();
    %>
        <title>应用管理</title>     
        <style type="text/css"> .textfield-red { color: #FF0000; } </style> <!-- 字体红色的样式-->
        <STYLE TYPE="text/css">
            #content {
                width: 100%;
                height: 100%;
            }
                                
            .add {
                background-image: url(images/icon/add.gif) !important;
            }
                                
            .update {
                background-image: url(images/icon/update.gif) !important;
            }
                                
            .delete {
                background-image: url(images/icon/delete.gif) !important;
            }
            .assign {
                background-image: url(images/icon/list-items.gif) !important;
            }
            .ux-combo-selectall{
                padding:3px;
            }
            .ux-combo-selectall-icon-checked{
                 transparent  url(ext-3.1.0/resources/images/default/menu/checked.gif);
            }
            .ux-combo-selectall-icon-unchecked {  
                 transparent  url(ext-3.1.0/resources/images/default/menu/unchecked.gif);  
            }
            .ux-combo-selectall-icon {
                text-indent:1.8em;
                background-position: 3px 2px ! important;  
                background-repeat:no-repeat ! important;  
                height:22px;
                line-height:20px;
                font-size:12px;
                font-weight:bold;
                -moz-user-select:none;
            }
                                
            .ux-lovcombo-icon {
            width:16px;
            height:16px;
            float:left;
            background-position: -1px -1px ! important;
            background-repeat:no-repeat ! important;
            }
            .ux-lovcombo-icon-checked {
             transparent  url(ext-3.1.0/resources/images/default/menu/checked.gif);
            }
            .ux-lovcombo-icon-unchecked {
             transparent  url(ext-3.1.0/resources/images/default/menu/unchecked.gif);
            }
            /* IE patch */
            .ext-ie .ux-lovcombo-item-text {
            position:absolute;
            left:16px;
            top:3px;
            }
            .ext-ie .ux-lovcombo-icon {
            float:none;
            }
            .ext-ie .x-combo-list-item {
            position:relative;
            } 
        </STYLE>
         <link href="ext-3.1.0/ux/css/Spinner.css" type="text/css" rel="stylesheet" />
        <script type="text/javascript" src="ext-3.1.0/ux/DateTime.js"></script>
        <script type="text/javascript" src="ext-3.1.0/ux/SpinnerField.js"></script>
        <script type="text/javascript" src="ext-3.1.0/ux/Spinner.js"></script>
        <script type="text/javascript" src="js/Ext.ux.form.LovCombo.js"></script>
        <script type="text/javascript" src="ext-3.1.0/ux/fileuploadfield/FileUploadField.js"></script>
                            
        <script type="text/javascript">
            Ext.onReady(function() {  
                Ext.QuickTips.init(); 
                var excelUpload = new Ext.form.TextField({   
                    id:'upload', 
                    anchor:'90%',   
                    height:30,
                    width:350,  
                    name:'userfile',
                    inputType:'file',
                    fieldLabel:'文件选择'
                });
                var formPanel = new Ext.form.FormPanel({
                    labelWidth:80,
                    bodyStyle:'padding:5 5 5 5',
                    height:515,
                    width:200,
                    frame:true,
                    fileUpload:true,
                    items:[excelUpload]
                }); 
                                    
                // 定义按钮
                var upLoadFile = new Ext.Toolbar.Button({
                    text:'上传'
                });
                // 上传数据功能
                var up = function(bt) {
                    var filepath = Ext.getCmp('upload').getRawValue();// 上传文件名称的路径
                    var suffix = filepath.substring(filepath.lastIndexOf('.') + 1, filepath.length);
                    if (filepath == ""){
                        Ext.Msg.show({title:'提示',msg:'请选择文件!',buttons:Ext.Msg.OK,icon:Ext.MessageBox.INFOR});
                        return;
                    }  else {
                        var array = new Array();
                        array = filepath.split("\\");
                        var length = array.length;
                        var fileName = "";
                        var index = 0;
                        for (index = 0; index < length; index++) {
                            if (fileName == "") {
                                fileName = array[index];
                            } else {
                                fileName = fileName + "/" + array[index];
                            }
                        }
                        formPanel.getForm().submit({
                            url:'file.sp?method=upload',
                            method:'POST',
                            waitMsg:'数据上传中, 请稍等...',
                            success:function(form, action) {
                                if(action.result.success == true){
                                    Ext.MessageBox.alert("提示信息","文件上传成功！！！");
                                }
                            },
                            failure : function(form, action) {
                                Ext.MessageBox.alert("提示信息","请求失败,文件上传失败");
                            }
                        });
                    }
                }
                                    
                // 添加按钮的响应事件
                upLoadFile.addListener('click', up, false);
                                          
                var window = new Ext.Window({
                    title:'上传文件',
                    width:500,
                    height:200,
                    minWidth:500,
                    minHeight:200,
                    layout:'fit',
                    plain:true,
                    modal:true,
                    //closeAction:'hide',
                    bodyStyle:'padding:5px;',
                    buttonAlign:'center',
                    items:formPanel,
                    buttons:[upLoadFile]
                });
                window.show();
                                    
            }); 
        </script>
  </head>
<body>
    <div id="Layer1" style="position:absolute; width:100%; height:100%; z-index:-1">
    <img src="images/file.jpg" height="100%" width="100%"/>
    <div id="form"></div>
    </div>
</body>
</html>
/*
 * File: app/view/abnmWholeSupervisionTab.js
 *
 * This file was generated by Sencha Architect version 3.1.0.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('MyApp.view.abnmWholeSupervisionTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.abnmWholeSupervisionTab',

    requires: [
        'Ext.grid.Panel',
        'Ext.grid.RowNumberer',
        'Ext.grid.column.Date',
        'Ext.grid.View',
        'Ext.selection.CheckboxModel',
        'Ext.form.field.Date',
        'Ext.button.Button',
        'Ext.toolbar.Paging'
    ],

    height: 750,
    id: 'abnmWholeSupervisionTab',
    layout: 'fit',
    title: '监管>>全过程异常监管信息表',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    id: 'abnmWholeSupervisionGrid',
                    collapsible: false,
                    title: '',
                    forceFit: true,
                    store: 'abnmWholeSupervisionStore',
                    columns: [
                        {
                            xtype: 'rownumberer',
                            width: 47,
                            text: '记录号'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'projectId',
                            text: '项目编号',
                            tooltip: ''
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'projectName',
                            text: '项目名称',
                            tooltip: ''
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'abwsTitle',
                            text: '标题',
                            tooltip: ''
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'abwsClass',
                            text: '异常来源'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'abwsContent',
                            text: '异常情况描述'
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'abwsDate',
                            text: '发布时间',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'abwsIsTaskAssigned',
                            text: '任务下达'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'rwId',
                            text: '任务编号'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'abwsXunchacontent',
                            text: '巡查情况描述'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'abwsOpinion',
                            text: '处理意见'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'abwsCollector',
                            text: '意见填写人'
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'abwsCollecttime',
                            text: '意见填写时间',
                            format: 'Y-m-d'
                        }
                    ],
                    selModel: Ext.create('Ext.selection.CheckboxModel', {

                    }),
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            frame: false,
                            enableOverflow: true,
                            items: [
                                {
                                    xtype: 'datefield',
                                    id: 'searchDateabws',
                                    width: 204,
                                    fieldLabel: '起始时间',
                                    labelWidth: 70,
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'searchKeywordabws',
                                    width: 211,
                                    fieldLabel: '',
                                    labelWidth: 50,
                                    emptyText: '请输入搜索关键字'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var getDate = Ext.util.Format.date(Ext.getCmp('searchDateabws').getValue(),'Y-m-d');
                                        var getKeyword = Ext.getCmp('searchKeywordabws').getValue();
                                        var mystore=Ext.StoreMgr.get('abnmWholeSupervisionStore'); //获得store对象
                                        //在load事件中添加参数
                                        //mystore.load(
                                        //{params:{
                                        //    start:"0",
                                        //    limit:"25",
                                        //    searchKeyword:getKeyword,
                                        //    searchDate:getDate
                                        //}
                                        //}
                                        //);



                                        mystore.on('beforeload',function(store,options){

                                            var new_params = {
                                                searchKeyword:getKeyword,
                                                searchDate:getDate
                                            };

                                            Ext.apply(store.proxy.extraParams,new_params);
                                        });

                                        mystore.load(
                                        {
                                            params:{
                                                start:"0",
                                                limit:"25"
                                            }
                                        }
                                        );
                                    },
                                    icon: 'images/file_view0.png',
                                    text: '搜索'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var grid = Ext.getCmp('abnmWholeSupervisionGrid');
                                        //var gird = this.down('grid');
                                        var record = grid.getSelectionModel().getSelection();

                                        if(record.length === 0){
                                            Ext.Msg.alert('提示','请先选择您要操作的行！');
                                            return;

                                        }else{

                                            var  ids =new Array(record.length);
                                            for(var i = 0;i<record.length;i++){
                                                ids[i] = record[i].get("abwsId");

                                            }
                                            //Ext.Msg.alert('提示',ids);



                                            Ext.Ajax.request({
                                                url:'delete_abwsByIds',
                                                params:{ids:ids},
                                                success:function(response){
                                                    Ext.Msg.alert('成功','删除成功');
                                                    var mystore = Ext.StoreMgr.get('abnmWholeSupervisionStore');
                                                    mystore.load();
                                                },
                                                failure:function(response){
                                                    Ext.Msg.alert('失败','删除失败');
                                                }

                                            });


                                        }
                                    },
                                    text: '删除'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {


                                        var grid = Ext.getCmp('abnmWholeSupervisionGrid');

                                        var record = grid.getSelectionModel().getSelection();

                                        if(record.length != 1 || record[0].get("abwsIsTaskAssigned")!= '否'){
                                            Ext.Msg.alert('提示','请先选择一条未下达任务的信息！');
                                            return;

                                        }else{


                                            var win = Ext.widget('abnmWSRWAddTab');
                                            win.show();

                                            Ext.getCmp('rwClassAddrw').setValue('异常监管');
                                            Ext.getCmp('projectIdAddrw').setValue(record[0].get("projectId"));
                                            Ext.getCmp('projectNameAddrw').setValue(record[0].get("projectName"));
                                            Ext.getCmp('abwsIdGet').setValue(record[0].get("abwsId"));
                                            Ext.getCmp('addRwContent').setValue(record[0].get("abwsContent"));





                                            Date.prototype.format = function(format){
                                                var o = {
                                                    "M+" : this.getMonth()+1, //month
                                                    "d+" : this.getDate(), //day
                                                    "h+" : this.getHours(), //hour
                                                    "m+" : this.getMinutes(), //minute
                                                    "s+" : this.getSeconds(), //second
                                                    "q+" : Math.floor((this.getMonth()+3)/3), //quarter
                                                    "S" : this.getMilliseconds() //millisecond
                                                };
                                                if(/(y+)/.test(format)) {
                                                    format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
                                                }
                                                for(var k in o) {
                                                    if(new RegExp("("+ k +")").test(format)) {
                                                        format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
                                                    }
                                                }
                                                return format;
                                            };


                                            var newDate = new Date();//日历实例化

                                            var newRwId = "YCJG"+newDate.format("yyyyMMddhhmmss");//当前日期格式化
                                            Ext.getCmp('getRwId').setValue(newRwId);

                                        }
                                    },
                                    text: '任务下达'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var tabs = this.up('tabpanel');
                                        tabs.removeAll(true);
                                        tabs.add(Ext.widget('abnmWholeSupRWTab'));

                                    },
                                    text: '任务管理'
                                }
                            ]
                        },
                        {
                            xtype: 'pagingtoolbar',
                            dock: 'bottom',
                            height: 36,
                            width: 360,
                            displayInfo: true,
                            store: 'abnmWholeSupervisionStore'
                        }
                    ],
                    listeners: {
                        cellclick: {
                            fn: me.onAbnmWholeSupervisionGridCellClick,
                            scope: me
                        }
                    }
                }
            ]
        });

        me.callParent(arguments);
    },

    onAbnmWholeSupervisionGridCellClick: function(tableview, td, cellIndex, record, tr, rowIndex, e, eOpts) {
        //if(cellIndex == 14)
        //{

        //    var abnmWSRWwin = Ext.widget('abnmWSRWAddTab');
        //abnmWSRWwin.show();
        //}
        //else{
        if(cellIndex >1 && cellIndex <13){
            var win = Ext.widget('abnmWSUpdateTab');
            win.show();
            var getForm = Ext.getCmp('abnmWSUpdateForm').getForm();	//获取form
            getForm.loadRecord(record);






        }
    }

});
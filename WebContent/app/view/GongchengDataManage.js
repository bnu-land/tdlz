/*
 * File: app/view/GongchengDataManage.js
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

Ext.define('MyApp.view.GongchengDataManage', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.GongchengDataManage',

    requires: [
        'Ext.form.Panel',
        'Ext.grid.Panel',
        'Ext.grid.RowNumberer',
        'Ext.grid.View',
        'Ext.form.field.Text',
        'Ext.button.Button',
        'Ext.toolbar.Paging',
        'Ext.selection.CheckboxModel'
    ],

    layout: 'fit',
    title: '复垦工程质量信息',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 140,
                    id: 'GongchengDataManageForm',
                    layout: 'fit',
                    bodyPadding: 10,
                    title: '',
                    items: [
                        {
                            xtype: 'gridpanel',
                            id: 'GongchengDataGrid',
                            title: '',
                            store: 'FkYanshouGongchengStore',
                            columns: [
                                {
                                    xtype: 'rownumberer'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'projectId',
                                    text: '项目编号'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'projectName',
                                    text: '项目名称'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'area',
                                    text: '项目实际规模'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 200,
                                    dataIndex: 'chgc',
                                    text: '拆除工程完成质量描述'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 200,
                                    dataIndex: 'tdpz',
                                    text: '土地平整工程完成质量描述'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 200,
                                    dataIndex: 'ntsl',
                                    text: '农田水利工程完成质量描述'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 200,
                                    dataIndex: 'tjdl',
                                    text: '田间道路工程完成质量描述'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'recorder',
                                    text: '采样人'
                                }
                            ],
                            dockedItems: [
                                {
                                    xtype: 'toolbar',
                                    dock: 'top',
                                    items: [
                                        {
                                            xtype: 'textfield',
                                            id: 'gongchengSearch',
                                            fieldLabel: '项目编号',
                                            name: 'sampleSearch'
                                        },
                                        {
                                            xtype: 'button',
                                            handler: function(button, event) {
                                                var mystore=Ext.StoreMgr.get('FkYanshouGongchengStore');//获得store对象
                                                //在load事件中添加参数
                                                mystore.load(
                                                {params:{
                                                    start:"0",
                                                    limit:"25",
                                                    searchKeyword:Ext.getCmp('gongchengSearch').getValue()
                                                }
                                            }
                                            );
                                            },
                                            text: '查询'
                                        },
                                        {
                                            xtype: 'button',
                                            handler: function(button, event) {
                                                var tabs = this.up('tabpanel');
                                                tabs.removeAll(true);
                                                //用tabs.add()新增加一个tab页面,通过Ext.widget(xtype)得到文章回收站的页面
                                                var newtab = tabs.add(Ext.widget('gongchengdataAdd'));
                                            },
                                            icon: 'images/table_add.png',
                                            text: '添加'
                                        },
                                        {
                                            xtype: 'button',
                                            handler: function(button, event) {
                                                var grid = Ext.getCmp('GongchengDataGrid');
                                                var record = grid.getSelectionModel().getSelection();
                                                var projectSelectedId = record[0].get("projectId");
                                                if (record.length === 0) {
                                                    Ext.Msg.alert('提示', '请先选择您要操作的行！');
                                                    return;

                                                } else if (record.length > 1) {
                                                    Ext.Msg.alert('提示', '只能选择一行数据');
                                                    return;
                                                } else {
                                                    Ext.Msg.alert('提示', projectSelectedId);
                                                    var tabs = this.up('tabpanel');
                                                    tabs.removeAll(true);
                                                    //用tabs.add()新增加一个tab页面,通过Ext.widget(xtype)得到文章回收站的页面
                                                    var newtab = tabs.add(Ext.widget('UpdateGongchengdata'));


                                                    //先通过id获得文章发表的form表单，然后加载数据，传过去的参数为articleSelectedId，url为get_infoArticleDetail
                                                    var UpdateGongchengdataFrom = newtab.getComponent('UpdateGongchengdataFrom');
                                                    UpdateGongchengdataFrom.load({
                                                        params: {
                                                            projectSelectedId: projectSelectedId
                                                        },
                                                        url:'get_gongchengDetail',
                                                        method:'GET',
                                                        success:function(form,action){

                                                        },
                                                        failure:function(form,action){
                                                            Ext.Msg.alert('提示','数据加载失败');
                                                        }
                                                    });



                                                }
                                            },
                                            icon: 'images/table_edit.png',
                                            text: '编辑'
                                        },
                                        {
                                            xtype: 'button',
                                            handler: function(button, event) {
                                                var grid = Ext.getCmp('GongchengDataGrid');
                                                var record = grid.getSelectionModel().getSelection();
                                                if(record.length === 0){
                                                    Ext.Msg.alert('提示','请先选择您要操作的行！');
                                                    return;

                                                }else{

                                                    var  sampleIds =new Array(record.length);
                                                    for(var i = 0;i<record.length;i++){
                                                        sampleIds[i] = record[i].get("projectId");

                                                    }
                                                    //Ext.Msg.alert('提示',projectIds);

                                                    Ext.Ajax.request({
                                                        url:'del_gongchengByIds',
                                                        params:{sampleIds:sampleIds},

                                                        success:function(response){
                                                            Ext.Msg.alert('success','项目工程质量信息已删除');
                                                            var mystore = Ext.StoreMgr.get('FkYanshouGongchengStore');
                                                            mystore.load();

                                                        }

                                                    });

                                                }
                                            },
                                            icon: 'images/table_delete.png',
                                            text: '删除'
                                        },
                                        {
                                            xtype: 'button',
                                            handler: function(button, event) {
                                                var tabs = this.up('tabpanel');
                                                tabs.removeAll(true);
                                                var newtab = tabs.add(Ext.widget('EvaluateSelectProject'));
                                            },
                                            icon: 'images/return.png',
                                            text: '返回'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'pagingtoolbar',
                                    dock: 'bottom',
                                    width: 360,
                                    displayInfo: true,
                                    store: 'FkYanshouGongchengStore'
                                }
                            ],
                            selModel: Ext.create('Ext.selection.CheckboxModel', {

                            })
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});
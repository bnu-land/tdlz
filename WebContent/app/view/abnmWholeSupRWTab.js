/*
 * File: app/view/abnmWholeSupRWTab.js
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

Ext.define('MyApp.view.abnmWholeSupRWTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.abnmWholeSupRWTab',

    requires: [
        'Ext.grid.Panel',
        'Ext.grid.RowNumberer',
        'Ext.grid.column.Date',
        'Ext.grid.View',
        'Ext.toolbar.Paging',
        'Ext.button.Button',
        'Ext.selection.CheckboxModel'
    ],

    id: 'abnmWholeSupRWTab',
    layout: 'fit',
    title: '监管>>全过程异常监管任务表',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    id: 'abnmWholeSupRWGrid',
                    title: '',
                    store: 'abnmWSRWstore',
                    columns: [
                        {
                            xtype: 'rownumberer'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'rwId',
                            text: '任务编号'
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
                            dataIndex: 'rwContent',
                            text: '任务描述'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'rwClass',
                            text: '任务类别'
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'rwGeneratetime',
                            text: '任务生成时间',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'rwStarttime',
                            text: '任务执行时间',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'rwEndtime',
                            text: '任务截止时间',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datecolumn',
                            dataIndex: 'rwSubmittime',
                            text: '任务提交时间',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'rwIsfinished',
                            text: '任务是否完成'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'rwResponsiblePerson',
                            text: '下达任务人'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'pagingtoolbar',
                            dock: 'bottom',
                            width: 360,
                            displayInfo: true,
                            store: 'abnmWSRWstore'
                        },
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var tabs = this.up('tabpanel');
                                        tabs.removeAll(true);
                                        tabs.add(Ext.widget('abnmWholeSupervisionTab'));
                                    },
                                    text: '返回'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var grid = Ext.getCmp('abnmWholeSupRWGrid');
                                        //var gird = this.down('grid');
                                        var record = grid.getSelectionModel().getSelection();

                                        if(record.length === 0){
                                            Ext.Msg.alert('提示','请先选择您要操作的行！');
                                            return;

                                        }else{

                                            var  ids =new Array(record.length);
                                            for(var i = 0;i<record.length;i++){
                                                ids[i] = record[i].get("rwId");

                                            }
                                            //Ext.Msg.alert('提示',ids);



                                            Ext.Ajax.request({
                                                url:'delete_abwsRWByIds',
                                                params:{ids:ids},
                                                success:function(response){
                                                    Ext.Msg.alert('成功','删除成功');
                                                    var mystore = Ext.StoreMgr.get('abnmWSRWstore');
                                                    mystore.load();
                                                },
                                                failure:function(response){
                                                    Ext.Msg.alert('失败','删除失败');
                                                }

                                            });


                                        }
                                    },
                                    icon: 'images/table_delete.png',
                                    text: '删除'
                                }
                            ]
                        }
                    ],
                    listeners: {
                        cellclick: {
                            fn: me.onTasktableGridCellClick,
                            scope: me
                        }
                    },
                    selModel: Ext.create('Ext.selection.CheckboxModel', {

                    })
                }
            ]
        });

        me.callParent(arguments);
    },

    onTasktableGridCellClick: function(tableview, td, cellIndex, record, tr, rowIndex, e, eOpts) {
        if(cellIndex >1 && cellIndex <12){
            var win = Ext.widget('abnmWSRWUpdateTab');
            win.show();
            var getForm = Ext.getCmp('abnmWSRWUpdateForm').getForm();	//获取form
            getForm.loadRecord(record);
        }
    }

});
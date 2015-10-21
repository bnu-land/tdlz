/*
 * File: app/view/db_SystemLogTab.js
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

Ext.define('MyApp.view.db_SystemLogTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.db_SystemLogTab',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.form.field.Text',
        'Ext.button.Button',
        'Ext.grid.Panel',
        'Ext.grid.RowNumberer',
        'Ext.grid.column.Number',
        'Ext.grid.column.Date',
        'Ext.grid.View',
        'Ext.selection.CheckboxModel'
    ],

    id: 'db_SystemLogTab',
    layout: 'fit',
    title: '系统运行日志',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    height: 214,
                    layout: 'fit',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'textfield'
                                },
                                {
                                    xtype: 'button',
                                    text: '查询'
                                },
                                {
                                    xtype: 'button',
                                    text: '删除'
                                }
                            ]
                        }
                    ],
                    items: [
                        {
                            xtype: 'gridpanel',
                            store: 'dbBackupRecoveryStore',
                            columns: [
                                {
                                    xtype: 'rownumberer',
                                    width: 40,
                                    text: '序号'
                                },
                                {
                                    xtype: 'numbercolumn',
                                    dataIndex: 'version',
                                    text: '用户名'
                                },
                                {
                                    xtype: 'datecolumn',
                                    dataIndex: 'date',
                                    text: '执行时间'
                                },
                                {
                                    xtype: 'numbercolumn',
                                    dataIndex: 'size',
                                    text: '登录ip'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 200,
                                    defaultWidth: 200,
                                    dataIndex: 'comment',
                                    text: '进行的操作'
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
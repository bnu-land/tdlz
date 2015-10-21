/*
 * File: app/view/abnmWSUpdateTab.js
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

Ext.define('MyApp.view.abnmWSUpdateTab', {
    extend: 'Ext.window.Window',
    alias: 'widget.abnmWSUpdateTab',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.Display',
        'Ext.form.field.TextArea',
        'Ext.button.Button'
    ],

    height: 550,
    id: 'abnmWSUpdateTab',
    width: 820,
    layout: 'fit',
    title: '全过程异常监管意见填写',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 600,
                    id: 'abnmWSUpdateForm',
                    layout: 'absolute',
                    bodyPadding: 10,
                    jsonSubmit: true,
                    items: [
                        {
                            xtype: 'displayfield',
                            x: 0,
                            y: 0,
                            hidden: true,
                            width: 300,
                            fieldLabel: 'abwsId',
                            labelWidth: 80,
                            name: 'abwsId'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 10,
                            width: 300,
                            fieldLabel: '项目编号',
                            labelWidth: 80,
                            name: 'projectId'
                        },
                        {
                            xtype: 'displayfield',
                            x: 410,
                            y: 210,
                            width: 300,
                            fieldLabel: '任务编号',
                            labelWidth: 80,
                            name: 'rwId'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 40,
                            width: 780,
                            fieldLabel: '标题',
                            labelWidth: 80,
                            name: 'abwsTitle'
                        },
                        {
                            xtype: 'textfield',
                            x: 10,
                            y: 20,
                            hidden: true,
                            width: 300,
                            fieldLabel: '来源recordid',
                            labelWidth: 80,
                            name: 'sourceRecordid'
                        },
                        {
                            xtype: 'displayfield',
                            x: 410,
                            y: 10,
                            width: 300,
                            fieldLabel: '项目名称',
                            labelWidth: 80,
                            name: 'projectName'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 210,
                            width: 300,
                            fieldLabel: '任务是否下达',
                            labelWidth: 80,
                            name: 'abwsIsTaskAssigned'
                        },
                        {
                            xtype: 'displayfield',
                            x: 410,
                            y: 70,
                            width: 300,
                            fieldLabel: '生成时间',
                            labelWidth: 80,
                            name: 'abwsDate'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 70,
                            width: 300,
                            fieldLabel: '异常来源',
                            labelWidth: 80,
                            name: 'abwsClass'
                        },
                        {
                            xtype: 'textfield',
                            x: 10,
                            y: 240,
                            width: 780,
                            fieldLabel: '意见填写人',
                            labelWidth: 80,
                            name: 'abwsCollector'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 430,
                            width: 780,
                            fieldLabel: '意见填写时间',
                            labelWidth: 80,
                            name: 'abwsCollecttime'
                        },
                        {
                            xtype: 'textareafield',
                            x: 10,
                            y: 270,
                            height: 150,
                            width: 780,
                            fieldLabel: '意见填写',
                            labelWidth: 80,
                            name: 'abwsOpinion'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 100,
                            height: 50,
                            width: 780,
                            fieldLabel: '异常情况描述',
                            labelWidth: 80,
                            name: 'abwsContent'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 160,
                            height: 50,
                            width: 780,
                            fieldLabel: '巡查情况描述',
                            labelWidth: 80,
                            name: 'abwsXunchacontent'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                var form = this.up('form').getForm();
                                if (form.isValid()) { // make sure the form contains valid data before submitting
                                    form.submit({
                                        url:'update_abws',

                                        success: function(form, action) {

                                            Ext.Msg.alert('成功', '成功回复');
                                            var mystore = Ext.StoreMgr.get('abnmWholeSupervisionStore'); //获得store对象
                                            mystore.load();
                                            var win = Ext.getCmp('abnmWSUpdateTab');
                                            win.close();
                                        },
                                        failure: function(form, action) {
                                            Ext.Msg.alert('失败', '回复失败');
                                        }
                                    });
                                } else { // display error alert if the data is invalid
                                Ext.Msg.alert('Invalid Data', 'Please correct form errors.');}
                            },
                            x: 200,
                            y: 460,
                            width: 100,
                            text: '确定'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                var win = Ext.getCmp('abnmWSUpdateTab');
                                win.close();
                            },
                            x: 490,
                            y: 460,
                            width: 90,
                            text: '取消'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});
/*
 * File: app/view/sampleDataAdd.js
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

Ext.define('MyApp.view.sampleDataAdd', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.sampleDataAdd',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button'
    ],

    layout: 'fit',
    title: '增加样点数据',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 110,
                    id: 'sampledataAddForm',
                    autoScroll: true,
                    layout: 'absolute',
                    bodyPadding: 10,
                    title: '',
                    jsonSubmit: true,
                    standardSubmit: false,
                    url: 'add_sample',
                    items: [
                        {
                            xtype: 'textfield',
                            x: 700,
                            y: 110,
                            hidden: true,
                            fieldLabel: 'recordId',
                            name: 'recordId'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 90,
                            fieldLabel: '样品编号',
                            name: 'sampleId',
                            emptyText: 'FK20145112032010'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 90,
                            width: 300,
                            fieldLabel: '采集地点',
                            name: 'sampleAddresss',
                            emptyText: '新合村'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 40,
                            fieldLabel: '项目编号',
                            name: 'projectId',
                            emptyText: 'FK20145112032'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 40,
                            width: 300,
                            fieldLabel: '项目名称',
                            name: 'projectName',
                            emptyText: '长寿区新市镇新合村等(2)个村农村集体建设用地复垦项目'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 140,
                            fieldLabel: '坐标X值',
                            name: 'longitude',
                            emptyText: '112.1234'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 140,
                            width: 300,
                            fieldLabel: '坐标Y值',
                            name: 'latitude',
                            emptyText: '28.3456'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 290,
                            fieldLabel: '有机质(g.kg-1)',
                            name: 'organicmatter',
                            emptyText: '15.61'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 340,
                            fieldLabel: '土壤质地',
                            name: 'soiltexture',
                            emptyText: '0.05'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 390,
                            fieldLabel: '记录人',
                            name: 'recorder',
                            emptyText: '马全胜'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 340,
                            width: 300,
                            fieldLabel: 'pH值',
                            name: 'phvalue',
                            emptyText: '5.36'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 190,
                            fieldLabel: '土层厚度(CM)',
                            name: 'soilThickness',
                            emptyText: '45'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 240,
                            fieldLabel: '土壤含水量(mm)',
                            name: 'soilmoisture',
                            emptyText: '0.67'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 190,
                            width: 300,
                            fieldLabel: '全氮(mg.kg-1)',
                            name: 'totalnitrogen',
                            emptyText: '0.765'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 240,
                            width: 300,
                            fieldLabel: '全磷(mg.kg-1)',
                            name: 'totalphosphorus',
                            emptyText: '0.376'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 290,
                            width: 300,
                            fieldLabel: '全钾(mg.kg-1)',
                            name: 'totalpotassium',
                            emptyText: '13.88'
                        },
                        {
                            xtype: 'datefield',
                            x: 420,
                            y: 390,
                            width: 300,
                            fieldLabel: '采样时间',
                            name: 'sampletime',
                            format: 'Y-m-d'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            x: 73,
                            y: 9,
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var form = this.up('form').getForm(); // get the basic form
                                        if (form.isValid()) { // make sure the form contains valid data before submitting
                                            form.submit({
                                                success: function(form, action) {
                                                    Ext.Msg.alert('Success', action.result.msg);
                                                },
                                                failure: function(form, action) {
                                                    Ext.Msg.alert('Failed', action.result.msg);
                                                }
                                            });
                                        } else { // display error alert if the data is invalid
                                            Ext.Msg.alert('Invalid Data', 'Please correct form errors.');
                                        }
                                    },
                                    icon: 'images/table_save.png ',
                                    text: '保存'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var tabs = this.up('tabpanel');
                                        tabs.removeAll(true);
                                        //用tabs.add()新增加一个tab页面,通过Ext.widget(xtype)得到文章回收站的页面
                                        var newtab = tabs.add(Ext.widget('sampleDataManage'));
                                    },
                                    icon: 'images/return.png',
                                    text: '返回'
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});
/*
 * File: app/view/addproject.js
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

Ext.define('MyApp.view.addproject', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.addproject',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.Date',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button'
    ],

    frame: true,
    id: 'addproject',
    layout: 'fit',
    title: '项目概况',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 110,
                    id: 'ProjectForm',
                    autoScroll: true,
                    layout: 'absolute',
                    bodyPadding: 10,
                    title: '',
                    jsonSubmit: true,
                    method: 'POST',
                    standardSubmit: false,
                    url: 'add_project',
                    items: [
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 40,
                            id: 'projectNewID',
                            fieldLabel: '复垦项目编号',
                            name: 'projectId',
                            readOnly: true
                        },
                        {
                            xtype: 'textfield',
                            x: 700,
                            y: 80,
                            hidden: true,
                            fieldLabel: '记录号',
                            name: 'recordId'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 90,
                            fieldLabel: '复垦项目名称',
                            name: 'projectname',
                            emptyText: '长寿区新市镇新合村等(2)个村农村集体建设用地复垦项目'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 140,
                            fieldLabel: '行政区位',
                            name: 'rlocation',
                            emptyText: '新市镇新合村、新同村'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 190,
                            fieldLabel: '区位编码',
                            name: 'areaCode',
                            emptyText: '500115001'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 240,
                            fieldLabel: '项目规模(公顷)',
                            name: 'rarea',
                            emptyText: '3.1116'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 290,
                            hidden: true,
                            fieldLabel: '是否公示',
                            name: 'showYesno',
                            value: false
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 290,
                            hidden: true,
                            fieldLabel: '是否完成',
                            name: 'isfinished',
                            value: false
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 240,
                            fieldLabel: '图幅编号',
                            name: 'rmapnumber',
                            emptyText: 'H48G049082、H48G049083'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 140,
                            fieldLabel: '资金花费（万元）',
                            name: 'rmoney',
                            emptyText: '61.66'
                        },
                        {
                            xtype: 'textfield',
                            x: 420,
                            y: 190,
                            fieldLabel: '施工单位',
                            name: 'conUnit',
                            emptyText: '重庆大正土地规划整治有限公司'
                        },
                        {
                            xtype: 'datefield',
                            x: 420,
                            y: 40,
                            fieldLabel: '施工时间',
                            name: 'starttime',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datefield',
                            x: 420,
                            y: 90,
                            fieldLabel: '竣工时间',
                            name: 'completiontime',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                Date.prototype.format = function(format){
                                    var o = {
                                        "M+" : this.getMonth()+1, //month
                                        "d+" : this.getDate(), //day
                                        "h+" : this.getHours(), //hour
                                        "m+" : this.getMinutes() //minute
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

                                var newFKId = "FK"+newDate.format("yyyyMMddhhmm");//当前日期格式化
                                Ext.getCmp('projectNewID').setValue(newFKId);

                            },
                            x: 290,
                            y: 40,
                            text: '生成编号'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            x: 38,
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
                                    icon: 'images/table_save.png',
                                    iconCls: 'imgs/table/table_save.png',
                                    text: '保存项目',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var tabs = this.up('tabpanel');
                                        tabs.removeAll(true);
                                        //用tabs.add()新增加一个tab页面,通过Ext.widget(xtype)得到文章回收站的页面
                                        var newtab = tabs.add(Ext.widget('manageProject'));
                                        var mystore = Ext.StoreMgr.get('publicProjectStore');
                                        mystore.load();
                                    },
                                    icon: 'images/return.png',
                                    iconCls: 'imgs/form/return.png',
                                    text: '返回'
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {

    }

});
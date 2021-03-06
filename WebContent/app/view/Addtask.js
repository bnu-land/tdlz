/*
 * File: app/view/Addtask.js
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

Ext.define('MyApp.view.Addtask', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.Addtask',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.Date',
        'Ext.form.field.TextArea',
        'Ext.form.field.ComboBox',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button'
    ],

    frame: true,
    id: 'addproject1',
    layout: 'fit',
    title: '下达数据采集任务',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 110,
                    id: 'AddtaskForm',
                    autoScroll: true,
                    layout: 'absolute',
                    bodyPadding: 10,
                    title: '',
                    jsonSubmit: true,
                    standardSubmit: false,
                    url: 'add_task',
                    items: [
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 90,
                            id: 'rwId',
                            width: 340,
                            fieldLabel: '任务编号',
                            name: 'rwId',
                            readOnly: true
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 40,
                            id: 'projecttaskId',
                            width: 340,
                            fieldLabel: '复垦项目编号',
                            name: 'projectId',
                            readOnly: true
                        },
                        {
                            xtype: 'textfield',
                            x: 510,
                            y: 40,
                            id: 'projecttaskName',
                            width: 360,
                            fieldLabel: '复垦项目名称',
                            name: 'projectName',
                            readOnly: true
                        },
                        {
                            xtype: 'textfield',
                            x: 510,
                            y: 190,
                            width: 360,
                            fieldLabel: '下达任务人',
                            name: 'rwResponsiblePerson',
                            emptyText: '长寿区国土资源局'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 290,
                            hidden: true,
                            fieldLabel: '任务是否完成',
                            name: 'rwIsfinished',
                            value: false
                        },
                        {
                            xtype: 'datefield',
                            x: 90,
                            y: 420,
                            hidden: true,
                            width: 360,
                            fieldLabel: '任务提交时间',
                            name: 'rwSubmittime',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datefield',
                            x: 40,
                            y: 140,
                            id: 'rwGeneratetime',
                            width: 340,
                            fieldLabel: '任务生成时间',
                            name: 'rwGeneratetime',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datefield',
                            x: 510,
                            y: 140,
                            width: 360,
                            fieldLabel: '任务开始时间',
                            name: 'rwStarttime',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'datefield',
                            x: 40,
                            y: 190,
                            width: 340,
                            fieldLabel: '任务截止时间',
                            name: 'rwEndtime',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'textareafield',
                            x: 40,
                            y: 230,
                            height: 162,
                            width: 830,
                            fieldLabel: '任务内容',
                            name: 'rwContent',
                            emptyText: '为了对编号20140118，项目名称为长寿区海棠等（2个）镇古林等（11个）村农村建设用地复垦项目的项目进行验收。需要进行土壤样品采集分析录入。具体采样点点数与空间分布请采样者具体根据实际情况而定。'
                        },
                        {
                            xtype: 'combobox',
                            x: 510,
                            y: 90,
                            width: 360,
                            fieldLabel: '任务类别',
                            name: 'rwClass',
                            emptyText: '请选择任务类别',
                            queryMode: 'local',
                            store: 'rwClassStore'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                Date.prototype.format = function(format){
                                    var o = {
                                        "M+" : this.getMonth()+1, //month
                                        "d+" : this.getDate(), //day
                                        "h+" : this.getHours(), //hour
                                        "m+" : this.getMinutes(), //minute
                                        "s+" : this.getSeconds(), //cond
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

                                var newRwId = "FKYS"+newDate.format("yyyyMMddhhmmss");//当前日期格式化
                                Ext.getCmp('rwId').setValue(newRwId);

                                var tm=new Date();
                                var stateDate = new Date(tm.getFullYear(),tm.getMonth(),tm.getDate());
                                Ext.getCmp('rwGeneratetime').setValue(stateDate);

                            },
                            x: 390,
                            y: 90,
                            text: '生成任务编号'
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
                                        var f=Ext.getCmp('AddtaskForm');
                                        f.getForm().submit(
                                        {
                                            url:'add_task',
                                            method:'post',
                                            success:function(form, action){
                                                Ext.MessageBox.alert('信息',action.result.msg);
                                            },
                                            failure:function(){

                                                Ext.MessageBox.alert('错误','操作失败！');
                                            }
                                        }
                                        );
                                    },
                                    icon: 'images/table_save.png',
                                    iconCls: 'imgs/table/table_save.png',
                                    text: '保存任务'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var tabs = this.up('tabpanel');
                                        tabs.removeAll(true);
                                        //用tabs.add()新增加一个tab页面,通过Ext.widget(xtype)得到文章回收站的页面
                                        var newtab = tabs.add(Ext.widget('EvaluateSelectProject'));
                                    },
                                    icon: 'images/return.png',
                                    iconCls: 'imgs/form/return.png',
                                    text: '返回'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var tabs = this.up('tabpanel');
                                        tabs.removeAll(true);
                                        //用tabs.add()新增加一个tab页面,通过Ext.widget(xtype)得到文章回收站的页面
                                        var newtab = tabs.add(Ext.widget('taskTable'));
                                    },
                                    icon: 'images/file_view0.png',
                                    iconCls: 'imgs/form/return.png',
                                    text: '任务管理表'
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
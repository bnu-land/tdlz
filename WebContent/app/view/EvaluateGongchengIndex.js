/*
 * File: app/view/EvaluateGongchengIndex.js
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

Ext.define('MyApp.view.EvaluateGongchengIndex', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.EvaluateGongchengIndex',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.button.Button',
        'Ext.form.field.TextArea',
        'Ext.form.field.Date'
    ],

    layout: 'fit',
    title: '工程质量评价',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 110,
                    id: 'EvaluateGongchengIndexForm',
                    autoScroll: true,
                    layout: 'absolute',
                    bodyPadding: 10,
                    title: '',
                    jsonSubmit: true,
                    standardSubmit: false,
                    url: 'add_gongchengxinxi',
                    items: [
                        {
                            xtype: 'combobox',
                            x: 40,
                            y: 320,
                            id: 'GongchengOpinion',
                            width: 400,
                            fieldLabel: '工程质量评价意见',
                            labelWidth: 120,
                            emptyText: '请选择',
                            queryMode: 'local',
                            store: 'GongchengOpinionStore'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                var tabs = this.up('tabpanel');
                                tabs.removeAll(true);
                                //用tabs.add()新增加一个tab页面,通过Ext.widget(xtype)得到文章回收站的页面
                                var newtab = tabs.add(Ext.widget('EvaluateSelectProject'));
                            },
                            x: 580,
                            y: 390,
                            icon: 'images/return.png',
                            scale: 'medium',
                            text: '返回'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 90,
                            width: 400,
                            fieldLabel: '项目实际规模',
                            name: 'area'
                        },
                        {
                            xtype: 'textfield',
                            x: 530,
                            y: 90,
                            width: 380,
                            fieldLabel: '评价人',
                            name: 'recorder'
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 40,
                            id: 'PROJECTID',
                            width: 400,
                            fieldLabel: '项目编号',
                            name: 'projectId',
                            size: 26
                        },
                        {
                            xtype: 'textfield',
                            x: 40,
                            y: 350,
                            hidden: true,
                            width: 310,
                            fieldLabel: '现场图片',
                            name: 'fkPicture',
                            value: 'Upload/before/zjd.jpg'
                        },
                        {
                            xtype: 'textfield',
                            x: 530,
                            y: 40,
                            id: 'projectnameG',
                            width: 380,
                            fieldLabel: '项目名称',
                            name: 'projectName'
                        },
                        {
                            xtype: 'textareafield',
                            x: 40,
                            y: 140,
                            height: 62,
                            id: 'chgcid',
                            width: 400,
                            fieldLabel: '拆迁工程完成质量',
                            name: 'chgc'
                        },
                        {
                            xtype: 'textareafield',
                            x: 40,
                            y: 230,
                            height: 62,
                            id: 'tdpzid',
                            width: 400,
                            fieldLabel: '土地平整工程完成质量',
                            name: 'tdpz'
                        },
                        {
                            xtype: 'textareafield',
                            x: 530,
                            y: 230,
                            height: 62,
                            id: 'tjdlid',
                            width: 380,
                            fieldLabel: '田间道路工程完成质量',
                            name: 'tjdl'
                        },
                        {
                            xtype: 'textareafield',
                            x: 530,
                            y: 140,
                            height: 62,
                            id: 'ntslid',
                            width: 380,
                            fieldLabel: '农田水利工程完成质量',
                            name: 'ntsl'
                        },
                        {
                            xtype: 'datefield',
                            x: 530,
                            y: 320,
                            width: 380,
                            fieldLabel: '评价日期',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                var PROJECTID=Ext.getCmp('PROJECTID').getValue();
                                var projectnameG=Ext.getCmp('projectnameG').getValue();
                                var GongchengOpinion=Ext.getCmp('GongchengOpinion').getValue();
                                var chgcid=Ext.getCmp('chgcid').getValue();
                                var tdpzid=Ext.getCmp('tdpzid').getValue();
                                var ntslid=Ext.getCmp('ntslid').getValue();
                                var tjdlid=Ext.getCmp('tjdlid').getValue();

                                Ext.Ajax.request({
                                    url:'GongchengOpinionResult',
                                    method: 'POST',
                                    params:{PROJECTID:PROJECTID,
                                        projectnameG:projectnameG,
                                        GongchengOpinion:GongchengOpinion,
                                        chgcid:chgcid,
                                        tdpzid:tdpzid,
                                        ntslid:ntslid,
                                        tjdlid:tjdlid
                                    },

                                    success:function(options, eOpts){
                                    Ext.Msg.alert('提示', '保存成功！'); },
                                    failure:function(options, eOpts){
                                    Ext.Msg.alert('提示', '保存失败！'); }

                                });
                            },
                            x: 280,
                            y: 390,
                            width: 50,
                            icon: '',
                            scale: 'medium',
                            text: '保存'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});
/*
 * File: app/view/checkApply.js
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

Ext.define('MyApp.view.checkApply', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.checkApply',

    requires: [
        'Ext.panel.Panel',
        'Ext.form.Label',
        'Ext.form.field.ComboBox',
        'Ext.button.Button',
        'Ext.toolbar.Toolbar',
        'Ext.form.RadioGroup',
        'Ext.form.field.Radio'
    ],

    height: 596,
    width: 875,
    autoScroll: true,
    layout: 'border',
    title: '审核申请',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    region: 'west',
                    width: 200,
                    layout: 'absolute',
                    bodyStyle: 10,
                    collapsed: false,
                    collapsible: true,
                    title: '',
                    items: [
                        {
                            xtype: 'textfield',
                            x: 20,
                            y: 40,
                            id: 'checkApplyId',
                            width: 150,
                            fieldLabel: '',
                            readOnly: true
                        },
                        {
                            xtype: 'label',
                            x: 20,
                            y: 20,
                            style: 13,
                            text: '验收申请编号：'
                        },
                        {
                            xtype: 'label',
                            x: 20,
                            y: 80,
                            style: 13,
                            text: '项目编号：'
                        },
                        {
                            xtype: 'label',
                            x: 20,
                            y: 140,
                            style: 13,
                            text: '项目名称：'
                        },
                        {
                            xtype: 'label',
                            x: 20,
                            y: 200,
                            style: 13,
                            text: '审核意见：'
                        },
                        {
                            xtype: 'textfield',
                            x: 20,
                            y: 100,
                            id: 'applyprojectId',
                            width: 150,
                            fieldLabel: '',
                            readOnly: true
                        },
                        {
                            xtype: 'textfield',
                            x: 20,
                            y: 160,
                            id: 'applyProjectname',
                            width: 150,
                            fieldLabel: '',
                            readOnly: true
                        },
                        {
                            xtype: 'combobox',
                            x: 20,
                            y: 220,
                            id: 'ComboboBoxApply',
                            width: 150,
                            fieldLabel: '',
                            emptyText: '请选择',
                            repeatTriggerClick: true,
                            forceSelection: true,
                            queryMode: 'local',
                            store: 'comboStore'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                //var checkApplyId=Ext.getCmp('checkApplyId').getValue();
                                //var applyResult=Ext.getCmp('shenheyijian').getValue();
                                Ext.Ajax.request({
                                    url : "applyCheckResult",
                                    method: 'POST',
                                    params:{
                                        checkApplyId:Ext.getCmp('checkApplyId').getValue(),
                                        applyResult:Ext.getCmp('ComboboBoxApply').getValue()
                                    },
                                    success:function(options, eOpts){
                                    Ext.Msg.alert('提示', '保存成功！'); },
                                    failure:function(options, eOpts){
                                    Ext.Msg.alert('提示', '保存失败！');  }
                                }) ;
                            },
                            x: 20,
                            y: 310,
                            icon: '',
                            text: '提交'
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                var tabs = this.up('tabpanel');
                                tabs.removeAll(true);
                                var newtab = tabs.add(Ext.widget('ManagAcceptApply'));
                            },
                            x: 100,
                            y: 310,
                            icon: 'images/return.png',
                            text: '返回'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    region: 'center',
                    html: '<div id="mapdiv_checkApply" class="MapClass"> </div>',
                    layout: 'fit',
                    collapsible: false,
                    title: '核查项目资料',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            autoScroll: true,
                            items: [
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        //window.open('/MyTDJG/jsp/OpenWord.jsp');
                                        Ext.Ajax.request({
                                            url : "seeApplyword",
                                            scope : this,
                                            success:function(options, eOpts){
                                            },
                                            failure:function(options, eOpts){
                                            Ext.Msg.alert('提示', '查看申请表失败！');  }
                                        }) ;
                                        window.open('/tdjg/jsp/EvaluateReport/04.htm');
                                    },
                                    icon: 'images/file_view0.png',
                                    text: '申请表'
                                },
                                {
                                    xtype: 'radiogroup',
                                    id: 'radioGroup_checkApply',
                                    width: 270,
                                    fieldLabel: '查看地图',
                                    labelWidth: 70,
                                    items: [
                                        {
                                            xtype: 'radiofield',
                                            name: 'checkApply',
                                            boxLabel: '规划图',
                                            inputValue: '规划图'
                                        },
                                        {
                                            xtype: 'radiofield',
                                            name: 'checkApply',
                                            boxLabel: '现状图',
                                            inputValue: '现状图'
                                        },
                                        {
                                            xtype: 'radiofield',
                                            name: 'checkApply',
                                            boxLabel: '竣工图',
                                            inputValue: '竣工图'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        //window.open('/MyTDJG/jsp/Picture.jsp');
                                        Ext.Ajax.request({
                                            url : "seePictureword",
                                            scope : this,
                                            success:function(options, eOpts){
                                                //Ext.Msg.alert('提示', '查看申请表成功！');
                                            },
                                            failure:function(options, eOpts){
                                            Ext.Msg.alert('提示', '查看失败！');  }
                                        }) ;
                                        window.open('/tdjg/jsp/Picture/2011009.htm');
                                    },
                                    icon: 'images/file_view0.png',
                                    text: '施工前后对比图'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        Ext.Ajax.request({
                                            url : "seeBiddingword",
                                            scope : this,
                                            success:function(options, eOpts){
                                                //Ext.Msg.alert('提示', '查看申请表成功！');
                                            },
                                            failure:function(options, eOpts){
                                            Ext.Msg.alert('提示', '查看失败！');  }
                                        }) ;
                                        window.open('/tdjg/Upload/fkapplyBidding/fkapplyBidding20140305152609491.htm');
                                    },
                                    icon: 'images/file_view0.png',
                                    text: '财务资料'
                                }
                            ]
                        }
                    ],
                    listeners: {
                        afterrender: {
                            fn: me.onPanelAfterRender,
                            scope: me
                        }
                    }
                }
            ]
        });

        me.callParent(arguments);
    },

    onPanelAfterRender: function(component, eOpts) {
        var head = document.getElementsByTagName('head')[0];
        var script= document.createElement("script");
        script.type = "text/javascript";
        script.src="map/checkApply_map.js";
        head.appendChild(script);
    }

});
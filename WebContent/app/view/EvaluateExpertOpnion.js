/*
 * File: app/view/EvaluateExpertOpnion.js
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

Ext.define('MyApp.view.EvaluateExpertOpnion', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.EvaluateExpertOpnion',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.TextArea',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Label'
    ],

    height: 386,
    layout: 'fit',
    title: '专家意见',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    id: 'EvaluateExpertOpnionForm',
                    autoScroll: true,
                    layout: 'absolute',
                    bodyPadding: 10,
                    title: '',
                    items: [
                        {
                            xtype: 'textareafield',
                            x: 100,
                            y: 100,
                            height: 72,
                            id: 'microreliefDescription',
                            width: 390,
                            fieldLabel: '微地貌描述',
                            name: 'microreliefDescription',
                            emptyText: '海拔278米，地面坡度5°～10°，岗顶平齐，岗体多呈馒头状，和缓起伏，微向平原倾斜'
                        },
                        {
                            xtype: 'textfield',
                            x: 560,
                            y: 100,
                            id: 'landProportion',
                            width: 430,
                            fieldLabel: '项目区田土比重',
                            name: 'landProportion',
                            emptyText: '田地比重过大'
                        },
                        {
                            xtype: 'textfield',
                            x: 560,
                            y: 50,
                            id: 'projectnameE',
                            width: 430,
                            fieldLabel: '项目名称',
                            name: 'landProportion',
                            readOnly: true
                        },
                        {
                            xtype: 'textfield',
                            x: 560,
                            y: 150,
                            id: 'waterStorageCapacity',
                            width: 430,
                            fieldLabel: '项目区蓄水能力',
                            name: 'waterStorageCapacity',
                            emptyText: '水利工程总蓄水能力达11万立方米'
                        },
                        {
                            xtype: 'textfield',
                            x: 100,
                            y: 190,
                            id: 'trafficConditions',
                            width: 390,
                            fieldLabel: '交通条件',
                            name: 'trafficConditions',
                            emptyText: '距通往主干道20分钟车程'
                        },
                        {
                            xtype: 'textfield',
                            x: 100,
                            y: 50,
                            id: 'projectidE',
                            width: 390,
                            fieldLabel: '项目编号',
                            name: 'trafficConditions',
                            readOnly: true
                        },
                        {
                            xtype: 'textfield',
                            x: 560,
                            y: 190,
                            id: 'fertilityEvaluation',
                            width: 430,
                            fieldLabel: '肥力评价',
                            name: 'fertilityEvaluation',
                            emptyText: '肥力质量中等'
                        },
                        {
                            xtype: 'textfield',
                            x: 100,
                            y: 240,
                            id: 'goukanQuality',
                            width: 390,
                            fieldLabel: '沟粪函坎质量评价',
                            name: 'goukanQuality',
                            emptyText: '沟粪函坎设计与质量达标'
                        },
                        {
                            xtype: 'textfield',
                            x: 560,
                            y: 240,
                            id: 'formingClodsDegree',
                            width: 430,
                            fieldLabel: '主体田土块成型度（打圈）',
                            name: 'formingClodsDegree',
                            emptyText: '地块台面宽高3-5'
                        },
                        {
                            xtype: 'textareafield',
                            x: 100,
                            y: 300,
                            height: 122,
                            id: 'evaluationResults',
                            width: 890,
                            fieldLabel: '综合评价结果',
                            name: 'evaluationResults',
                            emptyText: '新增耕地土质肥沃满足新增耕地质量要求，工程数量质量都达到了实施方案设计标准。'
                        },
                        {
                            xtype: 'textareafield',
                            x: 100,
                            y: 440,
                            height: 82,
                            id: 'evaluationExpert',
                            width: 890,
                            fieldLabel: '评价单位/评价人/职称',
                            name: 'evaluationExpert',
                            emptyText: '白天云 研究员 重庆农业局'
                        },
                        {
                            xtype: 'label',
                            x: 440,
                            y: 10,
                            text: '复垦项目专家意见'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            x: 70,
                            y: 10,
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var projectidE=Ext.getCmp('projectidE').getValue();
                                        var projectnameE=Ext.getCmp('projectnameE').getValue();
                                        var microreliefDescription=Ext.getCmp('microreliefDescription').getValue();
                                        var landProportion=Ext.getCmp('landProportion').getValue();
                                        var waterStorageCapacity=Ext.getCmp('waterStorageCapacity').getValue();
                                        var trafficConditions=Ext.getCmp('trafficConditions').getValue();
                                        var fertilityEvaluation=Ext.getCmp('fertilityEvaluation').getValue();
                                        var goukanQuality=Ext.getCmp('goukanQuality').getValue();
                                        var formingClodsDegree=Ext.getCmp('formingClodsDegree').getValue();
                                        var evaluationResults=Ext.getCmp('evaluationResults').getValue();
                                        var evaluationExpert=Ext.getCmp('evaluationExpert').getValue();

                                        Ext.Ajax.request({
                                            url:'ExpertOpinionResult',
                                            method: 'POST',
                                            params:{PROJECTID:projectidE,
                                                projectnameE:projectnameE,
                                                microreliefDescription:microreliefDescription,
                                                landProportion:landProportion,
                                                waterStorageCapacity:waterStorageCapacity,
                                                trafficConditions:trafficConditions,
                                                goukanQuality:goukanQuality,
                                                formingClodsDegree:formingClodsDegree,
                                                evaluationResults:evaluationResults,
                                                evaluationExpert:evaluationExpert,
                                                fertilityEvaluation:fertilityEvaluation
                                            },

                                            success:function(options, eOpts){
                                            Ext.Msg.alert('提示', '专家意见提交到数据库成功！'); },
                                            failure:function(options, eOpts){
                                            Ext.Msg.alert('提示', '专家意见提交到数据库失败！'); }

                                        });
                                    },
                                    icon: 'images/table_go.png',
                                    text: '保存'
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
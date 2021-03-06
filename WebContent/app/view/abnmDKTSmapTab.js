/*
 * File: app/view/abnmDKTSmapTab.js
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

Ext.define('MyApp.view.abnmDKTSmapTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.abnmDKTSmapTab',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.panel.Panel',
        'Ext.form.CheckboxGroup',
        'Ext.form.field.Checkbox',
        'Ext.Img'
    ],

    id: 'abnmDKTSmapTab',
    layout: 'border',
    title: '交易>>交易风险预警地图',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                var tabs = this.up('tabpanel');
                                tabs.removeAll(true);
                                tabs.add(Ext.widget('abnmDkTransSupervisionTab'));
                            },
                            text: '返回'
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'panel',
                    region: 'center',
                    html: '<div id="mapdiv_abnmRisk" class="MapClass"></div>',
                    title: ''
                },
                {
                    xtype: 'panel',
                    region: 'east',
                    width: 150,
                    title: '',
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            flex: 2,
                            title: '图层列表',
                            items: [
                                {
                                    xtype: 'checkboxgroup',
                                    width: 300,
                                    fieldLabel: '',
                                    vertical: true,
                                    layout: {
                                        type: 'vbox',
                                        align: 'stretch'
                                    },
                                    items: [
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: '高风险区域',
                                            checked: true
                                        },
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: '中风险区域',
                                            checked: true
                                        },
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: '低风险区域',
                                            checked: true
                                        },
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: '无风险区域',
                                            checked: true
                                        },
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: '行政区划图',
                                            checked: true
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            flex: 1,
                            title: '图例',
                            items: [
                                {
                                    xtype: 'image',
                                    height: 104,
                                    width: 155,
                                    src: 'images/jiaoyifengxian.png'
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
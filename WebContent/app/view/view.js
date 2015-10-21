/*
 * File: app/view/view.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
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

Ext.define('MyApp.view.view', {
    extend: 'Ext.container.Viewport',

    requires: [
        'MyApp.view.main',
        'MyApp.view.ctnUser',
        'MyApp.view.proj',
        'MyApp.view.application',
        'MyApp.view.reclProc',
        'MyApp.view.notice',
        'MyApp.view.MontCtrl',
        'MyApp.view.preWarm',
        'MyApp.view.recFile',
        'MyApp.view.efctEvlu',
        'MyApp.view.dh'
    ],

    border: 2,
    autoScroll: true,
    layout: {
        type: 'fit'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'container',
                    height: 636,
                    width: 960,
                    fixed: true,
                    autoScroll: true,
                    layout: {
                        type: 'border'
                    },
                    items: [
                        {
                            xtype: 'toolbar',
                            region: 'south',
                            frame: true,
                            height: 25,
                            id: 'down',
                            items: [
                                {
                                    xtype: 'timefield',
                                    height: 15,
                                    fieldLabel: 'Label',
                                    anyMatch: true,
                                    autoSelect: false,
                                    caseSensitive: true
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            region: 'north',
                            height: 120,
                            id: 'logo_up',
                            margin: '0 0 5 0',
                            shadowOffset: 20,
                            layout: {
                                regionWeights: {
                                    north: 100,
                                    south: 0,
                                    center: 0,
                                    west: 0,
                                    east: 0
                                },
                                type: 'border'
                            },
                            items: [
                                {
                                    xtype: 'image',
                                    region: 'center',
                                    split: false,
                                    autoShow: true,
                                    src: 'img/2.jpg'
                                }
                            ]
                        },
                        {
                            xtype: 'container',
                            region: 'center',
                            id: 'body',
                            margin: '5 0 5 5',
                            layout: {
                                type: 'card'
                            },
                            items: [
                                {
                                    xtype: 'main1',
                                    autoShow: true
                                },
                                {
                                    xtype: 'ctnuser11'
                                },
                                {
                                    xtype: 'mycontainer9'
                                },
                                {
                                    xtype: 'mycontainer3'
                                },
                                {
                                    xtype: 'mycontainer8',
                                    layout: {
                                        type: 'card'
                                    }
                                },
                                {
                                    xtype: 'mycontainer12'
                                },
                                {
                                    xtype: 'mycontainer2',
                                    layout: {
                                        type: 'card'
                                    }
                                },
                                {
                                    xtype: 'prewarm',
                                    id: 'PreWarm'
                                },
                                {
                                    xtype: 'mycontainer10'
                                },
                                {
                                    xtype: 'mycontainer15'
                                }
                            ]
                        },
                        {
                            xtype: 'mynewmenu',
                            margin: '5 5 5 0',
                            width: 160,
                            region: 'west'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});
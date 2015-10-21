/*
 * File: app/store/storePermission.js
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

Ext.define('MyApp.store.storePermission', {
    extend: 'Ext.data.Store',

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            autoSync: true,
            storeId: 'storePermission',
            data: [
                {
                    id: 1,
                    permission: '农户'
                },
                {
                    id: 2,
                    permission: '业主单位'
                },
                {
                    id: 3,
                    permission: '行政管理单位'
                }
            ],
            fields: [
                {
                    name: 'id'
                },
                {
                    name: 'permission'
                }
            ]
        }, cfg)]);
    }
});
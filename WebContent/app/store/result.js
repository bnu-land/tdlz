/*
 * File: app/store/result.js
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

Ext.define('MyApp.store.result', {
    extend: 'Ext.data.Store',

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'warmLogs',
            proxy:{
        		type:"ajax",
        		url:"showWarmLogs.action",
        		
        		reader:{
        			type:"json",
        			root:"results",
        			totalProperty :'totalCount'		
        		},
        		actionMethods: {  
                    read: 'POST'  
                },
        		writer:{
        			type:"json"
        		}
        	},
        	pageSize: 2,
            fields: [
				{
				    name: 'projectID',
				    type: 'string'
				},	    
             	{
                    name: 'solveWay',
                    type: 'string'
                },
            	{
                    name: 'solveTime',
                    type: 'date',
 	  	        	mapping: 'solveTime.time',  
 	  	        	dateFormat : 'time'
                },
                {
                    name: 'warmCategory',
                    type: 'string'
                },
                {
                    name: 'logsFlag',
                    type: 'string'
                },
                {
                    name: 'rule_id',
                    type: 'string'
                },
                {
                    name: 'warmTime',
                    type: 'date',
 	  	        	mapping: 'warmTime.time',  
 	  	        	dateFormat : 'time'
                },
                {
                    name: 'warmContent',
                    type: 'string'
                },
                {
                	name: 'warmTarget',
                	type: 'string'
                },
                {
                	name: 'id',
                	type: 'auto'
                }
            ]
        }, cfg)]);
        this.load({params:{start:0,limit:2, categoryAZ : "安置监测"}});
    }
});
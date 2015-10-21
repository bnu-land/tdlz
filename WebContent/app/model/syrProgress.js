/*
 * File: app/model/syrProgress.js
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

Ext.define('MyApp.model.syrProgress', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.Field'
    ],

    fields: [
        {
            dateFormat: 'Y-m-d',
            dateWriteFormat: 'Y-m-d',
            name: 'time',
            type: 'date'
        },
        {
            mapping: 'projectName',
            name: 'projectName',
            type: 'string'
        },
        {
            name: 'ppercentPz',
            type: 'float'
        },
        {
            name: 'ppercentSl',
            type: 'float'
        },
        {
            name: 'ppercentDl',
            type: 'float'
        },
        {
            name: 'report',
            type: 'string'
        }
    ]
});
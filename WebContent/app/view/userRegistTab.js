/*
 * File: app/view/userRegistTab.js
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

Ext.define('MyApp.view.userRegistTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.userRegistTab',

    title: '用户注册',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    title: '用户注册',
                    jsonSubmit: true,
                    url: 'add_user',
                    items: [
                        {
                            xtype: 'textfield',
                            fieldLabel: '用户名',
                            name: 'username'
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: '电话',
                            name: 'phone'
                        },
                        {
                            xtype: 'textfield',
                            fieldLabel: 'Email',
                            name: 'email'
                        },
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
                            text: '提交'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});
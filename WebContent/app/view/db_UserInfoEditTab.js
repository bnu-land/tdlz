/*
 * File: app/view/db_UserInfoEditTab.js
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

Ext.define('MyApp.view.db_UserInfoEditTab', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.db_UserInfoEditTab',

    requires: [
        'Ext.form.Panel',
        'Ext.form.FieldSet',
        'Ext.form.field.Text',
        'Ext.form.field.Checkbox',
        'Ext.form.field.HtmlEditor',
        'Ext.button.Button'
    ],

    id: 'db_UserInfoEditTab',
    layout: 'fit',
    title: '用户信息',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    id: 'db_UserInfoEditForm',
                    layout: 'auto',
                    bodyPadding: 10,
                    jsonSubmit: true,
                    items: [
                        {
                            xtype: 'fieldset',
                            title: '帐户信息',
                            items: [
                                {
                                    xtype: 'textfield',
                                    id: 'users_userid',
                                    width: 400,
                                    fieldLabel: '用户编号',
                                    name: 'userId',
                                    readOnly: true
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'users_username',
                                    width: 400,
                                    fieldLabel: '用户编号',
                                    name: 'userName',
                                    readOnly: true
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'users_registerdate',
                                    width: 400,
                                    fieldLabel: '注册日期',
                                    name: 'registerdate',
                                    readOnly: true
                                }
                            ]
                        },
                        {
                            xtype: 'fieldset',
                            title: '密码修改',
                            items: [
                                {
                                    xtype: 'textfield',
                                    id: 'users_password',
                                    width: 400,
                                    fieldLabel: '新密码',
                                    name: 'userPwd',
                                    inputType: 'password'
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'users_confirmpassword',
                                    width: 400,
                                    fieldLabel: '确认密码',
                                    inputType: 'password'
                                },
                                {
                                    xtype: 'checkboxfield',
                                    anchor: '100%',
                                    id: 'users_enabled',
                                    fieldLabel: '是否启用',
                                    name: 'enabled',
                                    boxLabel: '',
                                    checked: true,
                                    inputValue: '1',
                                    uncheckedValue: '0'
                                }
                            ]
                        },
                        {
                            xtype: 'fieldset',
                            title: '用户信息',
                            items: [
                                {
                                    xtype: 'textfield',
                                    id: 'users_truename',
                                    width: 400,
                                    fieldLabel: '真实姓名',
                                    name: 'trueName'
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'users_mobilenum',
                                    width: 400,
                                    fieldLabel: '手机号码',
                                    name: 'mobileNum'
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'users_phonenum',
                                    width: 400,
                                    fieldLabel: '固定电话',
                                    name: 'phoneNum'
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'users_email',
                                    width: 400,
                                    fieldLabel: '邮箱',
                                    name: 'email',
                                    inputType: 'email'
                                },
                                {
                                    xtype: 'htmleditor',
                                    anchor: '100%',
                                    height: 120,
                                    id: 'user_description',
                                    fieldLabel: '用户介绍',
                                    name: 'description'
                                }
                            ]
                        },
                        {
                            xtype: 'button',
                            text: '确认修改',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            text: '取消',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick1,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        var myform = Ext.getCmp('db_UserInfoEditForm').getForm();

        //验证表单
        //验证密码
        var pwd = Ext.getCmp('users_password').getValue();
        var repwd = Ext.getCmp('users_confirmpassword').getValue();
        if(pwd != repwd){
            Ext.Msg.alert('提示', '两次输入的密码不同，请检查。');
            return;
        }

        if (myform.isValid())
        { // make sure the form contains valid data before submitting
            //使不提交的表单禁用
            Ext.getCmp('users_confirmpassword').disable();

            myform.submit({
                url : 'update_UserInfo',
                success : function (form, action)
                {
                    Ext.Msg.alert('成功', '部门信息更新成功');
                    var mystore = Ext.StoreMgr.get('uUserInfoStore'); //获得store对象
                    mystore.reload();
                    Ext.getCmp('users_confirmpassword').enable();
                },
                failure: function(form, action){
                    Ext.Msg.alert('失败', '部门信息更新失败');
                }
            });
        }
        else{
            Ext.Msg.alert('注意', '填写的信息有误，请检查！');
        }

        return;
    },

    onButtonClick1: function(button, e, eOpts) {

    }

});
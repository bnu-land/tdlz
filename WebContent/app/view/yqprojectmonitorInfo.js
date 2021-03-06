/*
 * File: app/view/yqprojectmonitorInfo.js
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

Ext.define('MyApp.view.yqprojectmonitorInfo', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.yqprojectmonitorInfo',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.form.field.Number',
        'Ext.button.Button',
        'Ext.toolbar.Paging',
        'Ext.grid.Panel',
        'Ext.grid.View',
        'Ext.grid.column.Date',
        'Ext.grid.column.Number',
        'Ext.selection.CheckboxModel',
        'Ext.grid.column.Boolean'
    ],

    frame: true,
    layout: 'border',
    title: '监测任务计划',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    region: 'north',
                    height: 100,
                    id: 'yqprojectmonitorInfo',
                    autoScroll: true,
                    autoDestroy: false,
                    layout: 'absolute',
                    bodyPadding: 10,
                    title: '',
                    jsonSubmit: true,
                    method: 'POST',
                    standardSubmit: false,
                    url: 'add_yqprojectmonitorInfo ',
                    items: [
                        {
                            xtype: 'combobox',
                            x: -5,
                            y: 10,
                            id: 'projectnames',
                            width: 260,
                            fieldLabel: '项目名称',
                            labelAlign: 'right',
                            labelWidth: 70,
                            name: 'projectName',
                            allowBlank: false,
                            displayField: 'projectname',
                            store: 'syrSchemeShow3',
                            valueField: 'projectname',
                            listeners: {
                                select: {
                                    fn: me.onProjectnamesSelect,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'textfield',
                            x: 270,
                            y: 10,
                            id: 'prjid',
                            width: 180,
                            fieldLabel: '项目编号',
                            labelAlign: 'right',
                            labelWidth: 60,
                            name: 'projectId',
                            readOnly: true,
                            allowBlank: false
                        },
                        {
                            xtype: 'datefield',
                            x: 470,
                            y: 10,
                            id: 'monitort1',
                            width: 180,
                            fieldLabel: '监测时间',
                            labelAlign: 'right',
                            labelWidth: 60,
                            name: 'monitorTime',
                            allowBlank: false,
                            altFormats: 'Y-m-d',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'numberfield',
                            x: 680,
                            y: 10,
                            id: 'finishlimit',
                            width: 210,
                            fieldLabel: '任务完成期限（天）',
                            labelAlign: 'right',
                            labelWidth: 120,
                            name: 'finishdateLimit',
                            allowBlank: false,
                            minValue: 0
                        },
                        {
                            xtype: 'numberfield',
                            x: 80,
                            y: 60,
                            id: 'pz1',
                            width: 170,
                            fieldLabel: '土地平整进度(%)',
                            labelAlign: 'right',
                            name: 'ypercentPz',
                            allowBlank: false,
                            maxValue: 100,
                            minValue: 0
                        },
                        {
                            xtype: 'numberfield',
                            x: 480,
                            y: 60,
                            id: 'dl1',
                            width: 170,
                            fieldLabel: '田间道路进度(%)',
                            labelAlign: 'right',
                            name: 'ypercentDl',
                            allowBlank: false,
                            maxValue: 100,
                            minValue: 0
                        },
                        {
                            xtype: 'numberfield',
                            x: 280,
                            y: 60,
                            id: 'sl1',
                            width: 170,
                            fieldLabel: '农田水利进度(%)',
                            labelAlign: 'right',
                            name: 'ypercentSl',
                            allowBlank: false,
                            maxValue: 100,
                            minValue: 0
                        },
                        {
                            xtype: 'button',
                            handler: function(button, event) {
                                var myform = this.up('form').getForm(); // get the basic form
                                if (myform.isValid()) { // make sure the form contains valid data before submitting
                                    myform.submit({
                                        success: function(form, action) {
                                            var mystore = Ext.StoreMgr.get('yqprjmonitorInfoStore'); //获得store对象
                                            mystore.load(
                                            {
                                                params: {
                                                    start: "0",
                                                    limit: "15",
                                                    projectId: Ext.getCmp('prjid').getValue()
                                                }
                                            }
                                            );
                                            Ext.Msg.alert('success', '添加数据成功');

                                        },
                                        failure: function(form, action) {
                                            Ext.Msg.alert('failure', '添加失败');
                                        }
                                    });
                                } else {
                                    Ext.Msg.alert('注意', '填写信息不能为空，请检查！');
                                }


                                return;
                            },
                            x: 800,
                            y: 50,
                            height: 30,
                            id: 'addbutton',
                            width: 90,
                            text: '添加一条记录'
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    region: 'center',
                    id: 'yqgrid',
                    fixed: true,
                    autoScroll: true,
                    title: '',
                    forceFit: true,
                    store: 'yqprjmonitorInfoStore',
                    viewConfig: {
                        height: 100
                    },
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            width: 150,
                            dataIndex: 'projectId',
                            text: '项目编号'
                        },
                        {
                            xtype: 'gridcolumn',
                            width: 500,
                            dataIndex: 'projectName',
                            text: '项目名称'
                        },
                        {
                            xtype: 'gridcolumn',
                            hidden: true,
                            dataIndex: 'recordId',
                            text: '记录号'
                        },
                        {
                            xtype: 'datecolumn',
                            width: 150,
                            align: 'center',
                            dataIndex: 'monitorTime',
                            text: '监测时间',
                            format: 'Y-m-d'
                        },
                        {
                            xtype: 'numbercolumn',
                            hidden: true,
                            dataIndex: 'recordId',
                            text: 'record_id'
                        },
                        {
                            xtype: 'gridcolumn',
                            text: '工程预期进度（%）',
                            columns: [
                                {
                                    xtype: 'numbercolumn',
                                    width: 60,
                                    align: 'center',
                                    dataIndex: 'ypercentPz',
                                    text: '土地平整',
                                    format: '00'
                                },
                                {
                                    xtype: 'numbercolumn',
                                    width: 60,
                                    align: 'center',
                                    dataIndex: 'ypercentDl',
                                    text: '田间道路',
                                    format: '00'
                                },
                                {
                                    xtype: 'numbercolumn',
                                    width: 60,
                                    align: 'center',
                                    dataIndex: 'ypercentSl',
                                    text: '农田水利',
                                    format: '00'
                                }
                            ]
                        },
                        {
                            xtype: 'booleancolumn',
                            width: 80,
                            dataIndex: 'isSended',
                            text: '已下达',
                            falseText: '否',
                            trueText: '是'
                        },
                        {
                            xtype: 'booleancolumn',
                            width: 80,
                            dataIndex: 'isFinished',
                            text: '已完成',
                            falseText: '否',
                            trueText: '是'
                        },
                        {
                            xtype: 'datecolumn',
                            hidden: true,
                            dataIndex: 'finishTime',
                            text: '完成时间'
                        },
                        {
                            xtype: 'gridcolumn',
                            text: '工程实际进度（%）',
                            columns: [
                                {
                                    xtype: 'numbercolumn',
                                    width: 60,
                                    align: 'center',
                                    dataIndex: 'ppercentPz',
                                    text: '土地平整',
                                    format: '00'
                                },
                                {
                                    xtype: 'numbercolumn',
                                    width: 60,
                                    align: 'center',
                                    dataIndex: 'ppercentDl',
                                    text: '田间道路',
                                    format: '00'
                                },
                                {
                                    xtype: 'numbercolumn',
                                    width: 60,
                                    align: 'center',
                                    dataIndex: 'ppercentSl',
                                    text: '农田水利',
                                    format: '00'
                                }
                            ]
                        }
                    ],
                    selModel: Ext.create('Ext.selection.CheckboxModel', {

                    }),
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            width: 950,
                            items: [
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var tabs = Ext.getCmp('myTabPanel');
                                        tabs.removeAll(true);
                                        var newtab = tabs.add(Ext.widget('syrDataManagement'));

                                        var mystore = Ext.StoreMgr.get('syrSchemeShow3'); //获得store对象
                                        mystore.load(
                                        {
                                            params: {
                                                start: '0',
                                                limit: '15',
                                                projectName: '',
                                                province: '',
                                                city: '',
                                                county: '',
                                                town: '',
                                                contructstarttime: '',
                                                contructendtime: ''
                                            }
                                        });
                                    },
                                    height: 22,
                                    width: 94,
                                    icon: 'images/return.png',
                                    text: '返回项目列表'
                                },
                                {
                                    xtype: 'textfield',
                                    id: 'projectids',
                                    width: 281,
                                    fieldLabel: '项目编号',
                                    labelWidth: 50
                                },
                                {
                                    xtype: 'datefield',
                                    id: 'monitorstart8',
                                    width: 186,
                                    fieldLabel: '预期监测时间',
                                    labelAlign: 'right',
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'datefield',
                                    id: 'monitorend8',
                                    width: 103,
                                    fieldLabel: '——',
                                    labelAlign: 'right',
                                    labelSeparator: '—',
                                    labelWidth: 20
                                },
                                {
                                    xtype: 'textfield',
                                    hidden: true,
                                    id: 'projectname9',
                                    fieldLabel: 'Label'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var  mystore=Ext.StoreMgr.get('yqprjmonitorInfoStore');//获取store对象

                                        /*在load时间中添加参数
                                        mystore.load(
                                        { params:{
                                        start:"0",
                                        limit:"15",
                                        projectId:Ext.getCmp('projectids').getValue(),
                                        monitorstarttime:Ext.util.Format.date(Ext.getCmp('monitorstart8').getValue(),'Y-m-d'),
                                        monitorendtime:Ext.util.Format.date(Ext.getCmp('monitorend8').getValue(),'Y-m-d'),
                                        accuracy:"0"}
                                        }
                                        );
                                        */

                                        mystore.on('beforeload', function (store, options) {
                                            var new_params = {projectId:Ext.getCmp('projectids').getValue(),
                                                    monitorstarttime:Ext.util.Format.date(Ext.getCmp('monitorstart8').getValue(),'Y-m-d'),
                                                    monitorendtime:Ext.util.Format.date(Ext.getCmp('monitorend8').getValue(),'Y-m-d'),
                                                accuracy:"0"};
                                            Ext.apply(store.proxy.extraParams, new_params);
                                        });
                                        mystore.load({ params: { start: 0, limit: 15} });
                                    },
                                    width: 55,
                                    icon: 'images/file_view0.png',
                                    text: '搜索'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var grid = Ext.getCmp('yqgrid');
                                        var record =grid.getSelectionModel().getSelection();//获取到表上的那一列
                                        if(record.length > 1){
                                            Ext.MessageBox.show({
                                                title:"提示",
                                                msg:"只能选择一行数据",
                                                icon: Ext.MessageBox.INFO,
                                                buttons: Ext.Msg.OK
                                            });
                                            return;
                                        }

                                        else if (record.length===0) {
                                            Ext.Msg.alert('提示', '请先选择您要编辑的数据!');
                                            return;
                                        } else {
                                            var win = new Ext.Window({
                                                title: '编辑监测任务计划',
                                                height: 306,
                                                width: 523,
                                                items: [
                                                {
                                                    xtype: 'form',
                                                    height: 278,
                                                    id: 'yqprojectmonitorInfo12',
                                                    autoScroll: true,
                                                    autoDestroy: false,
                                                    layout: {
                                                        type: 'absolute'
                                                    },
                                                    bodyPadding: 10,
                                                    jsonSubmit: true,
                                                    items: [
                                                    {
                                                        xtype: 'combobox',
                                                        x: 0,
                                                        y: 10,
                                                        id: 'projectnames12',
                                                        width: 260,
                                                        fieldLabel: '项目名称',
                                                        labelAlign: 'right',
                                                        labelWidth: 70,
                                                        name: 'projectName',
                                                        readOnly: true

                                                    },
                                                    {
                                                        xtype: 'combobox',
                                                        x: 200,
                                                        y: 10,
                                                        id: 'recordId12',
                                                        width: 260,
                                                        fieldLabel: '记录号',
                                                        labelAlign: 'right',
                                                        labelWidth: 70,
                                                        name: 'recordId',
                                                        readOnly: true,
                                                        hidden: true

                                                    },
                                                    {
                                                        xtype: 'textfield',
                                                        x: 270,
                                                        y: 10,
                                                        id: 'prjid12',
                                                        width: 210,
                                                        fieldLabel: '项目编号',
                                                        labelAlign: 'right',
                                                        labelWidth: 60,
                                                        name: 'projectId',
                                                        readOnly: true
                                                    },
                                                    {
                                                        xtype: 'datefield',
                                                        x: 10,
                                                        y: 60,
                                                        id: 'monitort12',
                                                        width: 240,
                                                        fieldLabel: '监测时间',
                                                        labelAlign: 'right',
                                                        labelWidth: 60,
                                                        name: 'monitorTime',
                                                        allowBlank: false,
                                                        altFormats: 'Y-m-d',
                                                        format: 'Y-m-d'
                                                    },
                                                    {
                                                        xtype: 'numberfield',
                                                        x: 270,
                                                        y: 60,
                                                        id: 'finishlimit12',
                                                        width: 210,
                                                        fieldLabel: '任务完成期限（天）',
                                                        labelAlign: 'right',
                                                        labelWidth: 120,
                                                        name: 'finishdateLimit',
                                                        allowBlank: false,
                                                        minValue: 0
                                                    },
                                                    {
                                                        xtype: 'numberfield',
                                                        x: 20,
                                                        y: 120,
                                                        id: 'pz12',
                                                        width: 230,
                                                        fieldLabel: '土地平整进度(%)',
                                                        labelAlign: 'right',
                                                        name: 'ypercentPz',
                                                        allowBlank: false,
                                                        maxValue: 100,
                                                        minValue: 0
                                                    },
                                                    {
                                                        xtype: 'numberfield',
                                                        x: 280,
                                                        y: 120,
                                                        id: 'dl12',
                                                        width: 200,
                                                        fieldLabel: '田间道路进度(%)',
                                                        labelAlign: 'right',
                                                        name: 'ypercentDl',
                                                        allowBlank: false,
                                                        maxValue: 100,
                                                        minValue: 0
                                                    },
                                                    {
                                                        xtype: 'numberfield',
                                                        x: 20,
                                                        y: 170,
                                                        id: 'sl12',
                                                        width: 230,
                                                        fieldLabel: '农田水利进度(%)',
                                                        labelAlign: 'right',
                                                        name: 'ypercentSl',
                                                        allowBlank: false,
                                                        maxValue: 100,
                                                        minValue: 0
                                                    },
                                                    {
                                                        xtype: 'button',
                                                        handler: function(button, event) {
                                                            var myform = Ext.getCmp('yqprojectmonitorInfo12').getForm();
                                                            if (myform.isValid()) { // make sure the form contains valid data before submitting
                                                                myform.submit({
                                                                    url:'update_yqtable',
                                                                    success: function(form, action){
                                                                        Ext.Msg.alert('success', '修改内容成功');
                                                                        var mystore = Ext.StoreMgr.get('yqprjmonitorInfoStore'); //获得store对象
                                                                        mystore.load(
                                                                        {
                                                                            params: {
                                                                                start: "0",
                                                                                limit: "25",
                                                                                projectId: Ext.getCmp('prjid12').getValue()
                                                                            }
                                                                        }
                                                                        );
                                                                        win.close();
                                                                    },
                                                                    failure: function(form, action) {
                                                                        Ext.Msg.alert('failure', '修改失败');
                                                                        win.close();

                                                                    }
                                                                });
                                                            }   else { Ext.Msg.alert('注意', '填写信息不能为空，请检查！');
                                                            }
                                                            return;
                                                        },
                                                        x: 130,
                                                        y: 230,
                                                        height: 30,
                                                        id: 'update12',
                                                        width: 90,
                                                        text: '保存修改'
                                                    },
                                                    {
                                                        xtype: 'button',
                                                        handler: function(button, event) {
                                                            win.close();
                                                        },
                                                        x: 280,
                                                        y: 230,
                                                        height: 30,
                                                        id: 'return12',
                                                        width: 90,
                                                        text: '取消修改'
                                                    }
                                                    ]
                                                } ]
                                            });

                                            Ext.getCmp('projectnames12').setValue(record[0].get("projectName"));
                                            Ext.getCmp('recordId12').setValue(record[0].get("recordId"));
                                            Ext.getCmp('prjid12').setValue(record[0].get("projectId"));
                                            Ext.getCmp('monitort12').setValue(record[0].get("monitorTime"));
                                            Ext.getCmp('finishlimit12').setValue(record[0].get("finishdateLimit"));
                                            Ext.getCmp('pz12').setValue(record[0].get("ypercentPz"));
                                            Ext.getCmp('dl12').setValue(record[0].get("ypercentDl"));
                                            Ext.getCmp('sl12').setValue(record[0].get("ypercentSl"));
                                            win.show();
                                        }
                                    },
                                    icon: 'images/table_edit.png',
                                    text: '编辑'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        var grid = Ext.getCmp('yqgrid');
                                        var record =grid.getSelectionModel().getSelection();//获取到表上的那一列

                                        if(record.length > 1){
                                            Ext.MessageBox.show({
                                                title:"提示",
                                                msg:"只能选择一行数据",
                                                icon: Ext.MessageBox.INFO,
                                                buttons: Ext.Msg.OK
                                            });
                                            return;
                                        }

                                        else if (record.length===0) {
                                            Ext.Msg.alert('提示', '请先选择您要删除的数据!');
                                            return;
                                        } else {

                                            var  recordIds =new Array(record.length);
                                            for(var i = 0;i<record.length;i++){
                                                recordIds[i] = record[i].get("recordId");

                                            }



                                            Ext.Ajax.request({
                                                url:'del_yqmonitorInfodataByIds',
                                                params:{recordIds:recordIds},
                                                success:function(response){
                                                    var mystore = Ext.StoreMgr.get('yqprjmonitorInfoStore'); //获得store对象
                                                    mystore.load(
                                                    {
                                                        params: {
                                                            start: "0",
                                                            limit: "15",
                                                            projectId: record[0].get("projectId")
                                                        }
                                                    });


                                                    Ext.Msg.alert('success','删除数据成功!');


                                                }

                                            });

                                        }
                                    },
                                    icon: 'images/table_delete.png',
                                    text: '删除'
                                },
                                {
                                    xtype: 'button',
                                    handler: function(button, event) {
                                        Ext.getCmp('projectids').setValue('');

                                        var mystore = Ext.StoreMgr.get('yqprjmonitorInfoStore'); //获得store对象
                                        mystore.load(
                                        {
                                            params: {
                                                start: "0",
                                                limit: "15",
                                                projectId: '',
                                                monitorstarttime:'',
                                                monitorendtime:'',
                                                accuracy:'0'
                                            }
                                        });
                                    },
                                    text: '显示全部'
                                }
                            ]
                        }
                    ]
                }
            ],
            dockedItems: [
                {
                    xtype: 'pagingtoolbar',
                    dock: 'bottom',
                    width: 360,
                    displayInfo: true,
                    displayMsg: '显示{0} - {1}条，共计 {2}条',
                    emptyMsg: '没有数据',
                    store: 'yqprjmonitorInfoStore'
                }
            ]
        });

        me.callParent(arguments);
    },

    onProjectnamesSelect: function(combo, records, eOpts) {
        var projectName=records[0].get('projectname');
        Ext.Ajax.request({
            url:'get_projectIDList',
            params:{projectName:projectName},
            success:function(response){
                var u =Ext.JSON.decode(response.responseText);
                Ext.getCmp("prjid").setValue(u.projectId);
            }
        });

    }

});
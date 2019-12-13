<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="policyChangeList" filterBtn="true" fit="true" fitColumns="true"
                    title="" sortName="createDate" actionUrl="policyChangeController.do?datagrid" idField="id"
                    queryMode="group" extendParams="checkOnSelect:false,onSelect:function(index,row){datagridSelect(index,row);}"
                    pageSize="5">
            <t:dgCol title="操作" frozenColumn="true" field="opt" width="60"></t:dgCol>
            <t:dgFunOpt title="回复" exp="status#eq#1" funname="policyChangeConfirm(id)" urlclass="ace_button" urlStyle="background-color:#6fb3e0;"/>

            <t:dgCol title="主键"  field="id" hidden="true" queryMode="single"></t:dgCol>

            <t:dgCol title="变更说明" field="description" queryMode="single" sortable="false" width="100"></t:dgCol>
            <t:dgCol title="变更日期"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" sortable="false"  width="180"></t:dgCol>
            <t:dgCol title="变更状态"  field="status" sortable="false" queryMode="single" dictionary="pliCgSta" width="100"></t:dgCol>
            <t:dgCol title="保单号" field="policyEntity.policyNo" queryMode="single" sortable="false" width="165"></t:dgCol>
            <t:dgCol title="车牌号" field="policyEntity.plateNo" queryMode="single" sortable="false" width="80"></t:dgCol>
            <t:dgCol title="车架号"   field="policyEntity.frameNo" queryMode="single" sortable="false" width="160"></t:dgCol>
            <t:dgCol title="投保人"  field="policyEntity.holderCompName" sortable="false" queryMode="single" width="220"></t:dgCol>
            <t:dgCol title="投保人证件号"  field="policyEntity.holderOrgCode" sortable="false" queryMode="single" width="160"></t:dgCol>
            <t:dgCol title="被保人"  field="policyEntity.insuredCompName" sortable="false" queryMode="single" width="220"></t:dgCol>
            <t:dgCol title="被保人证件号"  field="policyEntity.insuredOrgCode" sortable="false" queryMode="single" width="160"></t:dgCol>
            <t:dgCol title="保险公司"	field="policyEntity.insurCompName" sortable="false" queryMode="single" dictionary="ins_comp" width="100"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/mutitables/mutitables.urd.js"></script>
<script type="text/javascript" src="plug-in/mutitables/mutitables.curdInIframe.js"></script>
<script type="text/javascript">
    $(function(){
        curd = $.curdInIframe({
            name:"jformOrderMain2",
            isMain:true,
            describe:"订单信息",
            form:{width:'100%',height:'100%'},
        });
        gridname = curd.getGridname();
    });

    /**
     * 双击事件开始编辑
     */
    function datagridDbclick(index,field,value){
        $("#policyChangeList").datagrid('beginEdit', index);
    }
    /**
     * 选中事件加载子表数据
     */
    function datagridSelect(index,row){
        $('#policyChangeList').datagrid('unselectAll');
        parent.PolicyChangeManager.onMainTabSelected(row.id);

    }
    /**
     * 主页面重置调用方法
     */
    function queryResetit(){
        searchReset('policyChangeList');
        policyChangeListsearch();
    }

    function policyChangeConfirm(id) {
        createwindow("回复", "policyChangeController.do?addConfirm&policyChangeId="+id);
    }

    function queryPolicyChangeList(policyNo) {
        $("#policyChangeList").datagrid('load', {
            policyNo: policyNo,
            "policyEntity.policyNo": policyNo
        });
    }
</script>
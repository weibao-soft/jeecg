<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>

<style type="text/css">
*{font-size:14px;}
div.datagrid-cell{font-size:14px;}
</style>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">

  <t:datagrid name="policySubordList" checkbox="false" pagination="true" fitColumns="false" title="保单列表" pageSize="20"
  	  actionUrl="policySubordController.do?datagrid&parentIds=${parentIds}" idField="id" 
  	  fit="true" queryMode="group" filter="true" sortName="payTime" sortOrder="desc" >

   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="50"></t:dgCol>
   <t:dgCol title="支付时间"  field="payTimeFilter" formatter="yyyy-MM-dd" hidden="true" query="true" queryMode="group" sortable="false"  width="150"></t:dgCol>
   <t:dgCol title="下级机构"  field="departId" hidden="true" query="true" queryMode="single" sortable="false" dictionary="t_s_depart,id,departname" dictCondition="where parentdepartid='${parentId }'" width="120"></t:dgCol>   
   <t:dgCol title="出单机构"  field="departName" frozenColumn="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
   <t:dgCol title="出单员"  field="userNo" queryMode="group" sortable="false" width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="userName" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="保单号"  field="policyNo" query="false" queryMode="single" sortable="false" width="165"></t:dgCol>   
   <t:dgCol title="车牌号"  field="plateNo" query="true" queryMode="single" sortable="false" width="80"></t:dgCol>
   <t:dgCol title="车架号"  field="frameNo" query="true" queryMode="single" sortable="false" width="160"></t:dgCol>
   <t:dgCol title="投保人"  field="holderCompName" sortable="false" width="220"></t:dgCol>
   <t:dgCol title="保费"  field="premium" queryMode="single" sortable="false" width="60"></t:dgCol>
   <t:dgCol title="保单状态"  field="status" query="true" queryMode="single" sortable="false" dictionary="qpolStatus" width="80"></t:dgCol>
   <t:dgCol title="支付状态"  field="payStatus" queryMode="single" sortable="false" defaultVal='N' dictionary="payStatus" width="80"></t:dgCol>
   <t:dgCol title="核保时间"  field="lastUpdateTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single" width="160"></t:dgCol>
   <t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single" width="160"></t:dgCol>
   <t:dgCol title="产品方案"  field="prodPlan" queryMode="single" sortable="false" width="310"></t:dgCol>
   <t:dgCol title="产品编码"  field="planCode" hidden="true" queryMode="single" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="保险公司"  field="insurCompName" queryMode="single" sortable="false" defaultVal='N' dictionary="ins_comp" width="100"></t:dgCol>
    
  </t:datagrid>
  
  </div>
</div>

<script type="text/javascript">
//layer.msg("${parentIds}");
</script>
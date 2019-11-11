<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>

<style type="text/css">
*{font-size:14px;}
</style>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">

  <t:datagrid name="policySubordList" checkbox="false" sortName="frameNo" pagination="true" fitColumns="false" title="保单列表" 
  	  superQuery="true" actionUrl="policySubordController.do?datagrid&parentIds=${parentIds}" idField="id" fit="true" queryMode="group" filter="true">

   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="50"></t:dgCol>
   
   <t:dgCol title="投保人"  frozenColumn="true" field="holderCompName" query="true" queryMode="single" width="220"></t:dgCol>
   <t:dgCol title="车牌号/被保人"  frozenColumn="true" field="plateNo" query="true" queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="车架号"  field="frameNo" query="true" queryMode="single" width="200"></t:dgCol>
   <t:dgCol title="部门"  field="departId" hidden="true" query="true" queryMode="single" dictionary="t_s_depart,id,departname" dictCondition="where parentdepartid='${parentId }'" width="120"></t:dgCol>
   <t:dgCol title="出单机构"  field="departName" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="保单号"  field="policyNo" query="false" queryMode="single" width="200"></t:dgCol>
   <t:dgCol title="保费"  field="price" queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="保单状态"  field="status" query="true" queryMode="single" dictionary="poliStatus" width="100"></t:dgCol>
   <t:dgCol title="支付状态"  field="payStatus" queryMode="single" defaultVal='N' dictionary="payStatus" width="100"></t:dgCol>
   <t:dgCol title="创建日期"  field="createTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single" width="160"></t:dgCol>
   <t:dgCol title="投保时间"  field="lastUpdateTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single" width="160"></t:dgCol>
   <t:dgCol title="出单员"  field="userNo" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="userName" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="保险产品"  field="prodName" query="true" queryMode="single" width="150"></t:dgCol>
   <t:dgCol title="产品代码"  field="prodCode" hidden="true" queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="保险公司"  field="insurCompName" queryMode="single" defaultVal='N' dictionary="ins_comp" width="100"></t:dgCol>
    
  </t:datagrid>
  
  </div>
</div>

<script type="text/javascript">
//layer.msg("${parentIds}");
</script>
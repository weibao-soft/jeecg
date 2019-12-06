<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="withdrawDetailList" checkbox="false" fitColumns="true" title="提现记录" actionUrl="companyAcctController.do?withdrawDetailDatagrid" 
  	idField="id" fit="true" queryMode="group" collapsible="true" pageSize="20" sortName="payTime" sortOrder="desc">
   <t:dgCol title="主键" hidden="true" field="id"  width="0"></t:dgCol>
   
   <t:dgCol title="保单号"  field="policyNo"  width="150" sortable="false"></t:dgCol>
   <t:dgCol title="车牌号"  field="plateNo" sortable="false" width="80"></t:dgCol>
   <t:dgCol title="签单日期"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
   <t:dgCol title="产品名称"  field="prodName" sortable="false" width="320"></t:dgCol>
   <t:dgCol title="保费"  field="premium"  sortable="false"  width="80"></t:dgCol>
   <t:dgCol title="出单机构"  field="departName" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="出单员"  field="userName"  sortable="false" width="100"></t:dgCol>
   <t:dgCol title="分润金额"  field="amount" sortable="false" width="80"></t:dgCol>
   <t:dgCol title="分润时间"  field="divideTime" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
   <t:dgCol title="结算状态"  field="rewardStatus" dictionary="rwdstatus" sortable="false" width="80"></t:dgCol>
	
  </t:datagrid>
  </div>
</div>
<script type="text/javascript" src="webpage/com/jeecg/demo/orderOne2Many/rowedit.js" ></script>
<script type="text/javascript">

function getOrderList(id){
	$('#withdrawDetailList').datagrid('load',{
		orderId : id
	});
}
</script>
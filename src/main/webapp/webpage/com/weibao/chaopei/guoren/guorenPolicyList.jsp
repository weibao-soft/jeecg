<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>

<style type="text/css">
*{font-size:14px;}
div.datagrid-cell{font-size:14px;}
</style>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">

  <t:datagrid name="guorenPolicyList" checkbox="false" sortName="payTime" sortOrder="desc" pagination="true" fitColumns="false" title="保单列表" pageSize="100"
  	  actionUrl="guorenPolicyController.do?datagrid" idField="id" fit="true" queryMode="group" filter="true">	   	
  	
  	<t:dgCol title="操作"  frozenColumn="true" field="opt" width="200"></t:dgCol>
	<t:dgFunOpt title="快递"  funname="addTab(id)" urlclass="ace_button"  urlfont="fa-check" urlStyle="background-color:#1a7bb9;"/>
   	<t:dgFunOpt title="退保"  funname="addTab(id)" urlclass="ace_button"  urlfont="fa-check" urlStyle="background-color:#1a7bb9;"/>
   	<t:dgFunOpt title="导出Word" funname="exportWordDoc(id)" urlclass="ace_button" urlfont="fa-cog" urlStyle="background-color:#18a689;"/>

	<t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="50"></t:dgCol>
	<t:dgCol title="支付时间"  field="payTimeFilter" hidden="true" formatter="yyyy-MM-dd" query="true" queryMode="group" sortable="false"  width="150"></t:dgCol>
	<t:dgCol title="创建日期"  field="createTimeFilter" hidden="true" formatter="yyyy-MM-dd" query="true" queryMode="group" sortable="false"  width="150"></t:dgCol>
   	<t:dgCol title="保单链接"  field="policyUrl" hidden="true" width="100"></t:dgCol>
   	
   	<t:dgCol title="保单号"  frozenColumn="true" formatterjs="policyHref" field="policyNo" query="true" queryMode="single" sortable="false" width="160"></t:dgCol>
   	<t:dgCol title="车牌号"  frozenColumn="true" field="plateNo" query="true" queryMode="single" sortable="false" width="80"></t:dgCol>
   	<t:dgCol title="车架号"   field="frameNo" query="true" queryMode="single" sortable="false" width="160"></t:dgCol>   	
   	<t:dgCol title="保单状态"  field="status" sortable="false" query="true" queryMode="single" dictionary="poliStatus" width="70"></t:dgCol>
   	<t:dgCol title="支付状态"  field="payStatus" sortable="false" query="true" queryMode="single" dictionary="payStatus" width="70"></t:dgCol>   	   	
   	<t:dgCol title="保费"  field="premium" sortable="false" width="60"></t:dgCol>
   	<t:dgCol title="创建日期"  field="createTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" sortable="false"  width="150"></t:dgCol>   	
   	<t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss" sortable="false"  width="150"></t:dgCol>   	
   	<t:dgCol title="投保人"  field="holderCompName" sortable="false"  width="220"></t:dgCol>
   	<t:dgCol title="投保人证件号"  field="holderOrgCode" sortable="false"  width="160"></t:dgCol>   	
   	<t:dgCol title="被保人"  field="insuredCompName" sortable="false"  width="220"></t:dgCol>
   	<t:dgCol title="被保人证件号"  field="insuredOrgCode" sortable="false"  width="160"></t:dgCol>   	
   	<t:dgCol title="发票类型" field="invoiceType" query="true" showMode="checkbox" dictionary="taxiType"  sortable="false"  width="80"></t:dgCol>
   	<t:dgCol title="发票号码" field="invoiceNumb" sortable="false"  width="90"></t:dgCol>
   	<t:dgCol title="发票接收手机" field="receiverMobile" sortable="false"  width="90"></t:dgCol>
   	<t:dgCol title="纳税号" field="taxpayerNo" sortable="false"  width="160"></t:dgCol>
   	<t:dgCol title="公司地址" field="compAddress" sortable="false"  width="220"></t:dgCol>
   	<t:dgCol title="公司电话" field="compPhone" sortable="false"  width="120"></t:dgCol>
   	<t:dgCol title="开户行" field="depositBank" sortable="false"  width="180"></t:dgCol>
   	<t:dgCol title="账号" field="bankAccount" sortable="false"  width="160"></t:dgCol>
   	<t:dgCol title="是否纸质保单"  field="isPaperPolicy" sortable="false" queryMode="single" dictionary="isPaper" width="80"></t:dgCol>
   	<t:dgCol title="是否纸质发票"  field="isPaperInvoice" sortable="false" queryMode="single" dictionary="isPaper" width="80"></t:dgCol>
   	<t:dgCol title="邮寄地址" field="taxiAddr" sortable="false"  width="360"></t:dgCol>
   	
   	<t:dgCol title="保单归属"  field="holderCompName24" sortable="false"  width="120"></t:dgCol>   	
   	<t:dgCol title="保单归属"  field="departId" hidden="true" query="true" queryMode="single" sortable="false" dictionary="t_s_depart,id,departname" dictCondition="where parentdepartid='${parentId }'" width="120"></t:dgCol>   	
   	<t:dgCol title="出单机构"  field="departName" sortable="false" width="120"></t:dgCol>   	   	
   	<t:dgCol title="出单员"  field="userName" sortable="false"  width="100"></t:dgCol>   	
   	<t:dgCol title="产品编码"  field="planCode" sortable="false"  width="100"></t:dgCol>
   	<t:dgCol title="产品方案"  field="prodPlan" sortable="false" width="260"></t:dgCol>
   	<t:dgToolBar title="导出数据" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   	<t:dgToolBar title="导出发票信息" icon="icon-putout" funname="ExportTaxiXls"></t:dgToolBar>
  </t:datagrid>
  
  </div>
</div>

<script type="text/javascript">
	function policyHref(value, row, index){	
		if (value != null && value != ''){		
			return '<a href="'+row.policyUrl+'" style="color:red" target="_blank" >'+value+'</a>';
		} 
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("guorenPolicyController.do?exportXls","guorenPolicyList");
	}
	
	//导出发票信息
	function ExportTaxiXls() {
		JeecgExcelExport("guorenPolicyController.do?ExportTaxiXls","guorenPolicyList");
	}
	
	//导出Word文档
	function exportWordDoc(id) {
		JeecgExcelExport("guorenPolicyController.do?exportWordDoc&policyId="+id,"guorenPolicyList");
	}
	
	//导出Word文档
	function exportWordDocs(title, url, gname) {
		var rowsData = $('#'+gname).datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip($.i18n.prop('read.selectItem'));
			return;
		}
		if (rowsData.length > 1) {
			tip($.i18n.prop('read.selectOneItem'));
			return;
		}
		var id = rowsData[0].id
		JeecgExcelExport("guorenPolicyController.do?exportWordDoc&policyId="+id,"guorenPolicyList");
	}

</script>
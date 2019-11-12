<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" style="width:100%;">
   <div style="padding:0px;border:0px;overflow-x:hidden;width:100%;">
   <iframe id="mainList" src="${webRoot}/companyAcctController.do?base" frameborder="0" height="40%" width="100%"></iframe>
   <div id="accDiv" class="easyui-accordion" style="padding-right:15px;overflow-x:hidden;box-sizing: border-box;">
		<div title="余额详情" data-options="iconCls:'icon-ok'" style="overflow:auto;box-sizing: border-box;">
			<iframe id="companyAcctBalanceDetail" height="400" src="${webRoot}/companyAcctController.do?acctReceiveBalanceDetail" frameborder="0" width="100%" ></iframe>
		</div>		
  </div>
  </div>
</div>
<script type="text/javascript">
	function getReAcctBalanceList(id){
		var frameObj=document.getElementById("companyAcctBalanceDetail");
		frameObj.src="${webRoot}/companyAcctController.do?acctReceiveBalanceDetail";
		//$("#companyAcctBalanceDetail")[0].contentWindow.getAcctBalanceList(id);
	}
	
	function getUnreAcctBalanceList(id){
		var frameObj=document.getElementById("companyAcctBalanceDetail");
		frameObj.src="${webRoot}/companyAcctController.do?acctUnreceiveBalanceDetail";
		//$("#companyAcctBalanceDetail")[0].contentWindow.getAcctBalanceList(id);
	}

	$(function(){		
		var abc = parseInt(document.body.clientWidth)-17;
		$("#accDiv").css("width", abc);
	});

	
</script>
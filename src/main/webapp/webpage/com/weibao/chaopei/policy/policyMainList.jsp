<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
   <div region="center" style="padding:0px;border:0px;overflow-x:hidden;">
   <iframe id="mainList" src="${webRoot}/policyMainController.do?mainlist" frameborder="0" height="100%" width="100%"></iframe>
  </div>
</div>
<script type="text/javascript">
	function getCustomerList(id){		
		//调用iframe：policyDetailList 里的getCustomerList用于刷新产品方案列表
		$("#policyDetailList")[0].contentWindow.getCustomerList(id);
	}

	$(function(){		
		var abc = parseInt(document.body.clientWidth)-17;
		$("#accDiv").css("width", abc);
	});

	
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
   <div region="center" style="padding:0px;border:0px;overflow-x:hidden;">
   <iframe id="mainList" src="${webRoot}/productMainController.do?mainlist" frameborder="0" height="45%" width="100%"></iframe>
   <div id="accDiv" class="easyui-accordion" style="padding-right:15px;overflow-x:hidden;box-sizing: border-box;" >
		<div title="产品详情" data-options="iconCls:'icon-ok'" style="overflow:auto;box-sizing: border-box; width: 100%">
			<iframe id="productDetailList" height="50%" src="${webRoot}/productMainController.do?productDetailListBase" frameborder="0" width="100%" ></iframe>
		</div>		
  </div>
  </div>
</div>
<script type="text/javascript">
	function getCustomerList(id){		
		//调用iframe：productDetailList 里的getCustomerList用于刷新产品方案列表
		$("#productDetailList")[0].contentWindow.getCustomerList(id);
	}

	$(function(){		
		var abc = parseInt(document.body.clientWidth)-17;
		$("#accDiv").css("width", abc);
	});

	
</script>
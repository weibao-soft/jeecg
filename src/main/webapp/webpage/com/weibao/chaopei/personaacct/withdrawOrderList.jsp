<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
   <div region="center" style="padding:0px;border:0px;overflow-x:hidden;">
   <iframe id="mainList" src="${webRoot}/personalAcctController.do?withdrawOrderListBase&accountId=${accountId}" frameborder="0" height="45%" width="100%"></iframe>
   <div id="accDiv" class="easyui-accordion" style="padding-right:15px;overflow-x:hidden;box-sizing: border-box;" >
		<div title="取现详情" data-options="iconCls:'icon-ok'" style="overflow:auto;box-sizing: border-box; width: 100%">
			<iframe id="withdrawOrderList" height="50%" src="${webRoot}/personalAcctController.do?withdrawOrderDetails" frameborder="0" width="100%" ></iframe>
		</div>		
  </div>
  </div>
</div>
<script type="text/javascript">
function getOrderList(id){
	//调用iframe：withdrawOrderList 里的getOrderList用于刷新产品方案列表
	$("#withdrawOrderList")[0].contentWindow.getOrderList(id);
}

$(function(){
	var abc = parseInt(document.body.clientWidth)-17;
	$("#accDiv").css("width", abc);
});

</script>
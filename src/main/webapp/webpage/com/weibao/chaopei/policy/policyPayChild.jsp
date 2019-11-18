<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>保单支付</title>
<t:base type="jquery,easyui,tools,autocomplete,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/winEVMsgBox.js"></script>
<SCRIPT type="text/javascript">
window.onbeforeunload = onbeforeunload_handler;
window.onunload = onunload_handler;
//window.onunload = function () { alert("谢谢光临");EV_closeAlert(); }//窗口关闭时去掉遮罩效果
function onbeforeunload_handler() {
    if(console) console.log("关闭事件1......");
	debugger;
    var warning="确认退出?";
    return warning;
}
function onunload_handler() {
    if(console) console.log("关闭事件2......");
	EV_closeAlert();
    var warning="谢谢光临";
    alert(warning);
	debugger;
    return warning;
}

$(document).ready(function() {
	console.log("子页面，===== ${payUrl}");
});
function closeDialog() {
}
</SCRIPT>
</head>
<body>
<div style="padding:0px;border:0px;overflow:auto;">
<iframe src="${payUrl}" frameborder="0" height="600" width="1200"></iframe>
</div>
</body>
</html>
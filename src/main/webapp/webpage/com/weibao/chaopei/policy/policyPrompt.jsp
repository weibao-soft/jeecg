<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>开具专用发票</title>
<t:base type="jquery,easyui,tools,autocomplete,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/policyMain.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.editable-select.min.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/jquery/jquery.editable-select.min.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/custom.css"/>

<SCRIPT type="text/javascript">
//var compName2 = parent.$("#compName2p").val();
//layer.msg(compName2);
$(document).ready(function() {
    window.setTimeout(customFunc, 300);
});

function customFunc() {
	//$("#recipients").val("${policyMainPage.recipients }");
}
function closeDialog() {
}
</SCRIPT>
</head>
<body>
<t:formvalid formid="dialog_formobj" dialog="true" layout="table" tiptype="1" action="" beforeSubmit="" callback="">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<span style="margin-left: 5px; text-align: right;"></span>
			</td>
		</tr>
        <tr>
            <td style="text-align:center;">
<div id="toast_info" class="alert alert-info" style="opacity: 1; min-width: 200px; top: 326px; left: 267.5px;">
  <button type="button" class="close" data-dismiss="alert">
    <i class="ace-icon fa fa-times"></i>
  </button>
  <strong style="margin-right:10px">
    <i class="ace-icon fa fa-info-circle" style="margin-right:5px"></i>
  </strong>
  <div style="display:inline-block;padding-right:30px;">1、国任保险公司服务电话：4008667788
    <br>2、公司最近季度的 偿付能力信息详见国任保险公司官网。
    <br>3、本保单所指的车辆类型为各类车型的营运车辆 （包括特种车辆、危险货物运输车辆），以具体约定的车牌号/车架号对应的车辆为准。
    <br>4、本保单仅承担被保险人在本保单项下指定的机动车在使用过程中由于发生交通事故而造 成第三者 的财产损失和人身伤亡，依法应由被保险人承担的赔偿责任,本条所指第三者不包 含本车车上人员。
    <br>5、本保单约定对 于超过该机动车综合商业险第三者责任保险赔偿金额 （100万元）和该车辆机动车交通事故责任强制保险赔偿限额之和 以上的部分，对于本保 单约定的赔偿限额内负责赔偿。
    <br>6、本保单不负责赔偿任何现金、支票（有价证券）、古 董字 画及其他无法估值的物品。
    <br>7、移动车辆（车头、挂车视为一体，以车头名义投保） 。
    <br>8、空载情况下发生的第三者保 险责任也属于保险责任范围内。
    <br>9、本保单为指定 车辆物流安全责任险，指定车辆认定以车牌号、车辆识别代码为准 ，如果保险期间内发生车 牌号变更，出险时以车辆识别代码为准。
    <br>10、出险时无需提供安全生产证明资料。
    <br></div></div>
            <span style="margin-left: 5px; text-align: right;">(提示，任何信息缺失，均导致开票失败，请仔细填写！)</span>
            </td>
        </tr>
	</table>
	<div style="margin:3px auto"><button type="button" class="subBtnmy">确认</button></div>
</t:formvalid>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>开具专用发票</title>
<t:base type="jquery,easyui,tools,autocomplete,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/policyMain.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/policyCommon.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.editable-select.min.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/jquery/jquery.editable-select.min.css"/>
<SCRIPT type="text/javascript">
//var compName2 = parent.$("#compName2p").val();
//layer.msg(compName2);
$(document).ready(function() {
    $("#recipients").css("width", "140px");
	getReceivers();
    window.setTimeout(customFunc, 300);
});

function customFunc() {
	editableInvoice();

	$("#recipients").val("${policyMainPage.recipients }");
    //var num=$('#recipients option:first').attr("data-num");
	//$("#recipientsTel").val(num);
}
function closeDialog() {
}
</SCRIPT>
</head>
<body>
<t:formvalid formid="dialog_formobj" dialog="true" layout="table" tiptype="1" action="" beforeSubmit="" callback="">
	<input id="id" name="id" type="hidden" value="${policyMainPage.id }"/>
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">公司名称:</label>
			</td>
			<td class="value">
		     	<input id="compName" name="compName" type="text" style="width: 150px" class="inputxt" datatype="*" ignore="checked" value="${policyMainPage.compName }" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">公司名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">纳税人识别号:</label>
			</td>
			<td class="value">
				<input id="taxpayerNo" name="taxpayerNo" type="text" maxlength="18" style="width: 150px"  class="inputxt" datatype="*" ignore="checked" value="${policyMainPage.taxpayerNo }" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">纳税人识别号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">公司地址:</label>
			</td>
			<td class="value">
		     	<input id="compAddress" name="compAddress" type="text" style="width: 150px" class="inputxt" datatype="*" ignore="checked" value="${policyMainPage.compAddress }" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">公司地址</label>
			</td>
			<td align="right">
				<label class="Validform_label">公司电话:</label>
			</td>
			<td class="value">
				<input id="compPhone" name="compPhone" type="text" style="width: 150px"  class="inputxt" datatype="*" ignore="checked" value="${policyMainPage.compPhone }" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">公司电话</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">开户行:</label>
			</td>
			<td class="value">
		     	<input id="depositBank" name="depositBank" type="text" style="width: 150px" class="inputxt"  datatype="*"  ignore="checked" value="${policyMainPage.depositBank }" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户行</label>
			</td>
			<td align="right">
				<label class="Validform_label">银行账号:</label>
			</td>
			<td class="value">
				<input id="bankAccount" name="bankAccount" type="text" style="width: 150px"  class="inputxt" datatype="*" ignore="checked" value="${policyMainPage.bankAccount }" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">银行账号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">收件人:</label>
			</td>
			<td class="value"><select name="recipients" id="recipients" class="editable-select" style="width:160px;" autocomplete="off" datatype="*" ignore="checked" value="${policyMainPage.recipients }">
				<option value=""></option></select><span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">收件人</label>
			</td>
			<td align="right">
				<label class="Validform_label">收件人电话:</label>
			</td>
			<td class="value">
				<input id="recipientsTel" name="recipientsTel" type="text" style="width: 150px"  class="inputxt" datatype="*" ignore="checked" value="${policyMainPage.recipientsTel }" />
				<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">收件人电话</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">发票收件地址:</label>
			</td>
			<td class="value" colspan="3">
		     	<input id="reciAddress" name="reciAddress" type="text" style="width: 400px" class="inputxt"  datatype="*"  ignore="checked" value="${policyMainPage.reciAddress }" />
				<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">发票收件地址</label>
			</td>
		</tr>
        <tr>
            <td colspan="4" style="text-align:center;height: 10px;"><span style="color: red; margin-left: 5px; text-align: right;">(提示，任何信息缺失，均导致开票失败，请仔细填写！)</span></td>
        </tr>
	</table>
</t:formvalid>
</body>
</html>
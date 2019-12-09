<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>修改保单</title>
<t:base type="jquery,easyui,tools,autocomplete,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/winEVMsgBox.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/policyMain.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/policyCommon.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/driveValid.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/utils.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/common.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/toast.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.editable-select.min.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/jquery/jquery.editable-select.min.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/custom.css"/>

<style type="text/css">
*{font-size:14px;}
input[type="text"]{font-size:14px;}
select{height:46px;}
dict_select{width:200px;}
.radio-one {cursor: pointer;}
.info-table table{border-right:1px solid #E3E3E3;border-bottom:1px solid #E3E3E3} 
.info-table table td{border-left:1px solid #E3E3E3;border-top:1px solid #E3E3E3}
</style>
<SCRIPT type="text/javascript">
$(document).ready(function(){
	window.Custom.dialogLoading(true);
	var params = {};
	params.paramId = "${policyMainPage.prodId }";
	var url = "policyMainController.do?getProductPlan";
	getHolders();
	getCommonSelect("planId", url, params);

    $("#holderCompNature").css("width", "200px");
    $("#industryType").css("width", "200px");
    $("#holderNature").css("width", "200px");
    window.setTimeout(customFunc, 500);

	var abc = $("#formobj").width()+17;
	$("#formobj").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
});

function customFunc() {
	editablePolicy();
	
	var invoiceType = "${policyMainPage.invoiceType}";
	$("#planId").val("${policyMainPage.planId}");
	$("#holderCompName").val("${policyMainPage.holderCompName}");
	if(invoiceType == "3") {
		$("#invoiceType").val(invoiceType);
		$("#taxpayerNo2").val("");
	} else {
		$("#invoiceType").val(invoiceType).trigger('change');
		$("#taxpayerNop").val("");
	}
	window.Custom.dialogLoading(false);
}

//Ajax方式提交表单数据
function submitForm() {
	$("#save").attr("disabled", true);
	$("#insur").attr("disabled", true);
    window.Utils.showLoading(imgName);

	var url = "policyDraftController.do?insuranceUpdate";
	var params = getSubmitParam();
	params = getUpdateParam(params);
	//tip(JSON.stringify(params));
	ajaxSubmitForm(url, params, false, null);
}
function confirmSubmit() {
	submitForm();
	$("#promptDiv").hide();
}
//存草稿
function saveDraft() {
	$("#status").val("1");
	submitData();
}
//提交核保
function insurance() {
	$("#status").val("1");
	if(!validData()) {
		return false;
	}

	var invoice=$("#invoiceType").val();
	if(invoice=='2') {
		var taxpayerNo = $("#taxpayerNo2").val();
        $("#taxpayerNop").val(taxpayerNo);
	}

	getMainContent();
	$("#readedPrompt").attr("checked", false);
	$('#promptDiv').show();
}
//支付
function doPay() {
    var mainTabId = "tab_402880ea6e26628b016e26665a0f0001";
	submitPay("${policyMainPage.id}", mainTabId);
}
//刷新保单列表
function reloadPolicyLists() {
	var mydiv = window.parent.$("#tab_402880ea6e26628b016e26665a0f0001");
	var myframe = $(mydiv).children("iframe")[0];
	var myWindow = myframe.contentWindow;
    myWindow.reload();
    closeCurrent('tab_${policyMainPage.id}');
}
function test() {
	debugger;
	var pobj = window.parent.document;
	var pobj2 = window.parent.frames;
	var len = window.parent.frames.length;
	//window.parent.frames[1].reload();

	var mydiv = window.parent.$("#tab_402880ea6e26628b016e26665a0f0001");
	var myframe = $(mydiv).children("iframe")[0];
	var myWindow = myframe.contentWindow;
	var myDocument = myframe.contentDocument;
    if(console) console.log(mydiv.html());
    var myEle = myWindow.document.getElementById("lywidth_demo");
    myWindow.reload();
}
</SCRIPT>
</head>
<body class="panel-noscroll">

<t:formvalid formid="formobj" dialog="false" layout="table" tiptype="1" action="policyDraftController.do?doUpdate" callback="jeecgFormFileCallBack@Override">
<fieldset class="step" style="width:99%;padding-bottom: 20px;">
 <legend>国任投保</legend>
 <table cellpadding="0" cellspacing="0" class="formtable" style="table-layout: fixed;" width="1200">
	<input id="id" name="id" type="hidden" value="${policyMainPage.id }"/>
	<input id="prodId" name="prodId" type="hidden" value="${policyMainPage.prodId }"/>
	<input id="draftId" name="draftId" type="hidden" value="${policyMainPage.draftId }"/>
	<input id="premium" name="premium" type="hidden" value="${policyMainPage.premium }"/>
 
 <tr><td style="width:60px;border-top:1px solid #E3E3E3;border-left:1px solid #E3E3E3;"></td><td style="width:1140px;">
	 <table cellpadding="0" cellspacing="0" class="formtable" style="border:1px solid #E3E3E3;border-bottom:0px solid #FFF;" width="100%">
	 <tr><td style="width:150px;">方案保障</td>
	 <td style="width:auto;">
			<select name="planId" id="planId" style="width:500px;" value="${policyMainPage.planId}">
			</select>
			<span class="Validform_checktip"></span></td></tr>
	 </table>
 </td></tr>
 
 <tr><td style="width:60px;border:1px solid #E3E3E3;border-right:0px solid #FFF;">投保内容</td><td style="width:1140px;">
	 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
	 
	 <tr><td>
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr><td style="width:150px;border-right:1px solid #E3E3E3;">车辆信息：</td>
		 <td style="width:auto;">
			<table name="policy_tabel" id="policy_tabel">
			<tbody id="add_policy_tabel">
			<c:if test="${fn:length(policyMainPage.vehicles) <= 0 }">
				<tr name='policytr'>
				<input name="vehicles[0].id" type="hidden"/>
				<td><div style="text-align:right;width:140px;">车牌号：<BR/>（新车填写：未上牌）</div></td>
				<td><input type="text" name="vehicles[0].plateNo" class="policy" title="plateNo" maxlength="8" style="width:100px;" value="未上牌"></td>
				<td><span style="color: red;">*</span>车架号 </td>
				<td><input type="text" name="vehicles[0].frameNo" class="policy" title="frameNo" maxlength="17"></td>
				<td><span style="color: red;">*</span>发动机号 </td>
				<td><input type="text" name="vehicles[0].engineNo" class="policy" title="engineNo" maxlength="40" style="width:120px;"></td>
				</tr>
			</c:if>
			<c:if test="${fn:length(policyMainPage.vehicles) > 0 }">
				<c:forEach items="${policyMainPage.vehicles}" var="poVal" varStatus="stat">
					<tr name='policytr'>
					<input name="vehicles[${stat.index }].id" type="hidden" value="${poVal.id }"/>
					<td><div style="text-align:right;width:140px;">车牌号：<BR/>（新车填写：未上牌）</div></td>
					<td><input type="text" name="vehicles[${stat.index }].plateNo" class="policy" title="plateNo" maxlength="8" style="width:100px;" value="${poVal.plateNo}"></td>
					<td><span style="color: red;">*</span>车架号 </td>
					<td><input type="text" name="vehicles[${stat.index }].frameNo" class="policy" title="frameNo" maxlength="17" value="${poVal.frameNo}"></td>
					<td><span style="color: red;">*</span>发动机号 </td>
					<td><input type="text" name="vehicles[${stat.index }].engineNo" class="policy" title="engineNo" maxlength="40" style="width:120px;" value="${poVal.engineNo}"></td>
		   			</tr>
				</c:forEach>
			</c:if>
			</tbody>
			</table>
		 </td></tr>
	 </table>
	 </td></tr>
	 
	 <tr><td>
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr><td style="width:150px;border-right:1px solid #E3E3E3;">保险期间：</td>
		 <td style="width:auto;">
		 自 <input type="text" name="startDate" id="start" value="${start}" class="Wdate" style="width:100px;" onblur="calculateYear();" 
		 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${start}',maxDate:'${max}'})"/> <input type="text" 
		 name="shour" id="shour" style="width:20px;" value="00" disabled/> 起 至 <input type="text" name="endDate" id="end" value="${end}" class="Wdate"
		 style="width:100px;" disabled/> <input type="text" name="ehour" id="ehour" style="width:20px;" value="24" disabled/> 止，连续 <input type="text" 
		 name="year" id="year" style="width:60px;" value="${year}" onblur="calculateMonths(this);">年 共<label id="month">12</label>月 </td></tr>
	 </table>
	 </td></tr>
	 
	 <tr><td style="width:100%"><label class="Validform_label"> 投保人： </label></td></tr>
	 
	 <tr><td class="info-table">
	 <table cellpadding="0" cellspacing="0" style="border-bottom:0px solid #FFF;" class="formtable" width="100%">
		 <tr><td style="width:150px;">投保人性质</td>
		 <td style="width:300px;"><t:dictSelect field="holderNature" id="holderNature" type="list" divClass="dict_select" title=""
						typeGroupCode="holdNature" defaultVal="${policyMainPage.holderNature}" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span></td>
		 <td style="width:150px;"></td><td style="width:auto;"></td></tr>
		 <tr><td><span style="color: red;">*</span>单位名称</td>
		 <td><select name="holderCompName" id="holderCompName" style="width:180px;" autocomplete="off" value="${policyMainPage.holderCompName}">
				<option value=""></option>
				</select></td>
		 <td><span style="color: red;">*</span>组织机构代码<BR/>(统一社会信用代码) </td>
		 <td><input type="text" name="holderOrgCode" id="holderOrgCode" maxlength="18" style="width:200px;" value="${policyMainPage.holderOrgCode}"/></td></tr>
		 <tr><td><span style="color: red;">*</span>单位性质</td>
		 <td><t:dictSelect field="holderCompNature" id="holderCompNature" type="list" divClass="dict_select" title=""
						typeGroupCode="compNature" defaultVal="${policyMainPage.holderCompNature}" hasLabel="false" ></t:dictSelect></td>
		 <td><span style="color: red;">*</span>行业类别</td>
		 <td><t:dictSelect field="industryType" id="industryType" type="list" divClass="dict_select" title=""
						typeGroupCode="industType" defaultVal="${policyMainPage.industryType}" hasLabel="false" ></t:dictSelect></td></tr>
		 <tr><td><span style="color: red;">*</span>联系人姓名</td>
		 <td><input type="text" name="contactName" id="contactName" style="width:200px;" value="${policyMainPage.contactName}"/></td>
		 <td><span style="color: red;">*</span>手机</td>
		 <td><input type="text" name="policyMobile" id="policyMobile" maxlength="11" style="width:200px;" value="${policyMainPage.policyMobile}"/></td></tr>
		 <tr><td style="color: red">发票类型</td><td>
		 <select name="invoiceType" id="invoiceType" style="width:200px;">
				<option value="1">不开发票</option>
				<option value="2">增值税普通发票</option>
				<option value="3">增值税专用发票</option>
				</select><span class="Validform_checktip"></span>
		 </td><td></td><td></td></tr>
		 <tr id="invoiceTr" style="display: none;">
		 <td>纳税人识别号</td>
		 <td><input type="text" name="taxpayerNo2" id="taxpayerNo2" maxlength="18" style="width:200px;" value="${policyMainPage.taxpayerNo}"/></td>
		 <td>手机号</td>
		 <td><input type="text" name="receiverMobile" id="receiverMobile" maxlength="11" style="width:200px;" value="${policyMainPage.receiverMobile}"/></td></tr>
	 </table>
	 </td></tr>
	 
	 <tr>
	 <td style="width:100%"><label class="Validform_label">被保人：</label><input id="check1" type="checkbox" />同投保人</td>
	 </tr>
	 
	 <tr><td class="info-table">
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr>
		 <td style="width:150px;"><span style="color: red;">*</span>单位名称 </td>
		 <td style="width:300px;"><input type="text" name="insuredCompName" id="insuredCompName" style="width:200px;" value="${policyMainPage.insuredCompName}" autocomplete="off"/></td>
		 <td style="width:150px;"><span style="color: red;">*</span>组织机构代码<BR/>(统一社会信用代码)</td>
		 <td style="width:auto;"><input type="text" name="insuredOrgCode" id="insuredOrgCode" maxlength="18" style="width:200px;" value="${policyMainPage.insuredOrgCode}"/>
		 <span class="Validform_checktip"></span></td>
		 </tr>
	 </table>
	 </td></tr>
	 
	 </table>
 </td></tr>
 </table>
 
<div style="text-align:center;width:98%;padding-top:10px;">
<input id="insur" class="subBtnmy btn-size" type="button" value="提交核保" onclick="insurance();"/>
<input id="pay" class="subBtnmy btn-size" type="button" value="在线支付" onclick="doPay();" disabled/>
<input id="back" class="btnmy btn-size" type="button" value="关闭" onclick="closeCurrent('tab_${policyMainPage.id}');"/>
</div>
</fieldset>

<input id="status" name="status" type="hidden" value="${policyMainPage.status}"/>
<input id="userId" name="userId" type="hidden" value="${policyMainPage.userId}" />
<input id="payStatus" name="payStatus" type="hidden" value="${policyMainPage.payStatus }"/>
<input id="rewardStatus" name="rewardStatus" type="hidden" value="${policyMainPage.rewardStatus }"/>
<input id="createTime" name="createTime" type="hidden" value="${policyMainPage.createTime }"/>
<input id="endDate" name="endDate" type="hidden" value="${end}" />
<input id="invoiceObj" name="invoiceObj" type="hidden" />
<input id="compNamep" name="compName" type="hidden" value="${policyMainPage.compName}"/>
<input id="taxpayerNop" name="taxpayerNo" type="hidden" value="${policyMainPage.taxpayerNo}"/>
<input id="compAddressp" name="compAddress" type="hidden" value="${policyMainPage.compAddress}"/>
<input id="compPhonep" name="compPhone" type="hidden" value="${policyMainPage.compPhone}" />
<input id="depositBankp" name="depositBank" type="hidden" value="${policyMainPage.depositBank}" />
<input id="bankAccountp" name="bankAccount" type="hidden" value="${policyMainPage.bankAccount}" />
<input id="recipientsp" name="recipients" type="hidden" value="${policyMainPage.recipients}" />
<input id="recipientsTelp" name="recipientsTel" type="hidden" value="${policyMainPage.recipientsTel}" />
<input id="reciAddressp" name="reciAddress" type="hidden" value="${policyMainPage.reciAddress}" />
<input id="insuranceObj" name="insuranceObj" type="hidden" />
<input id="payUrl" name="payUrl" type="hidden" />
<input id="payResult" name="payResult" type="hidden" />
<input id="insResult" name="insResult" type="hidden" value="1"/>
<input id="isDraft" name="isDraft" type="hidden" value="false"/>
</t:formvalid>

<%@include file="policyPromptDiv.jsp"%>
<%@include file="/webpage/com/weibao/chaopei/policy/policyPayiFrame.jsp"%>
</body>
</html>
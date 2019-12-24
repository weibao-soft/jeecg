<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增保单</title>
<t:base type="jquery,easyui,tools,autocomplete,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/weibao/goodtrans/freightPolicy.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/policyCommon.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/toast.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/utils.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/common.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/driveValid.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.editable-select.min.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/ace/assets/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/jquery/jquery.editable-select.min.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/custom.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/main.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/popdiv.css"/>

<style type="text/css">
*{font-size:14px;}
a {
    color: #428bca;
}
a:hover {
    text-decoration: underline;
}
input[type="text"]{font-size:14px;}
select{height:46px;}
td .border-right{border-right:1px solid #E3E3E3;}
.nobr {white-space: nowrap;}
.my-style {
    border-radius: 7px;
    border: 2px solid #BABABA;
    padding-left: 3px;
    padding-right: 3px;
    font-size: 12px;
    color: #BABABA;
}
</style>
<SCRIPT type="text/javascript">
$(document).ready(function(){
	window.Custom.dialogLoading(true);
	var params = {};
	params.paramId = "${prodId}";
	params.paramId = "4028dc816ead101e016ead2eafcb0002";
	var url = "policyMainController.do?getProductPlan";
	getCommonSelect("planId", url, params);
	getHolders("holderName");
	getHolders("insuredName");

    window.setTimeout(customFunc, 500);
});

function customFunc() {
	editablePolicy();

    var code=$('#planId option:first').attr("data-code");
	$("#premium").val(code);
	window.Custom.dialogLoading(false);
}

//核保失败回调函数
function failureCallback(result) {
    //if(console) console.log("returnObj == ", result);
    debugger;
	var freightId = result.id;
	var createTime = result.createTime;
	var payStatus = result.payStatus;
	document.getElementById("id").value = freightId;
	document.getElementById("createTime").value = createTime;
	document.getElementById("payStatus").value = payStatus;
}
//Ajax方式提交表单数据，并打开支付页面
function submitForm(tabId) {
	var url = "freightPolicyController.do?insuranceAdd";
	var params = getSubmitParam();
	ajaxPay(url, params, tabId);
}
function submitFormUpd(tabId) {
	var url = "freightPolicyController.do?insuranceUpdate";
	var params = getSubmitParam();
	params = getUpdateParam(params);
	ajaxPay(url, params, tabId);
}
function confirmSubmit() {
	var tabId = "tab_4028e5846eb182ce016eb1d551d20001";
	if($("#payResult").val() == "1") {
		submitForm(tabId);
	} else {
		submitFormUpd(tabId);
	}
    window.Utils.showLoading(imgName);
    $("#pay").attr("disabled", true);
	$("#promptDiv").hide();
}

//支付
function doPay() {
	$("#status").val("1");
	if(!validData()) {
		return false;
	}

	getMainContent();
	$("#readedPrompt").attr("checked", false);
	$('#promptDiv').show();

	//submitPay("${prodId}");
}

</SCRIPT>
</head>
<body style="overflow-x: hidden;overflow-y: auto;">

<t:formvalid formid="formobj" dialog="false" layout="table" tiptype="1" action="freightPolicyController.do?doAdd" callback="jeecgFormFileCallBack@Override">
<fieldset class="step" style="width:100%;padding-bottom: 20px;">
<legend>东瑞安心运</legend>
<table cellpadding="0" cellspacing="1" class="formtable" style="table-layout: fixed;" width="1200">
	<input id="id" name="id" type="hidden" value="${freightPolicyPage.id }"/>
	<input id="prodId" name="prodId" type="hidden" value="${prodId}"/>
	<input id="premium" name="premium" type="hidden"/>
	<input name="allInsuredAmount" id="allInsuredAmount" type="hidden"/>
	<input name="allPremium" id="allPremium" type="hidden"/>
 
 <tr>
 <td style="width:15px;text-align:center;border-top:1px solid #E3E3E3;border-left:1px solid #E3E3E3;"></td><td style="width:1185px;">
	 <table cellpadding="0" cellspacing="0" class="formtable" style="border:1px solid #E3E3E3;border-bottom:0px solid #FFF;" width="100%">
	 <tr><td style="width:150px;">方案保障</td>
	 <td style="width:auto;">
		<select name="planId" id="planId" style="width:500px;">
		</select><span class="Validform_checktip"></span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="nobr" onclick="openRemindDiv();">禁止承保类</a></td>
	 </tr>
	 </table></td>
 </tr>
 
 <tr>
 <td style="width:15px;text-align:center;">投保内容</td><td style="width:1185px;">
	<table cellpadding="0" cellspacing="1" class="formtable" width="100%">
	 
	<tr><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr><td style="width:150px;" class="border-right">车辆信息：</td>
		 <td style="width:auto;">
			<table name="vehicle_tabel" id="vehicle_tabel">
			<tbody id="add_vehicle_tabel">
				<tr name='vehicletr'>
				<input name="id" type="hidden"/>
				<td><div style="text-align:right;width:auto;"><span style="color: red;">*</span>车牌号</div></td>
				<td><input type="text" name="vehiclePlateNo" id="vehiclePlateNo" class="policy" maxlength="8" style="width:100px;" value="未上牌"></td>
				<td><span style="color: red;">*</span>挂车车牌号 </td>
				<td><input type="text" name="trailerPlateNo" id="trailerPlateNo" class="policy" maxlength="8" style="width:100px;"></td>
				<td><span style="color: red;">*</span>车架号 </td>
				<td><input type="text" name="vehicleFrameNo" id="vehicleFrameNo" class="policy" maxlength="20"></td>
				</tr>
			</tbody>
			</table></td>
		</tr>
	</table></td>
	</tr>
	 
	<tr><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr><td style="width:150px;" class="border-right">保险期间：</td>
		 <td style="width:auto;">
			 <span>自 <input type="text" name="startDate" id="start" value="${start}" class="Wdate" style="width:100px;" onblur="calculateYear();"
			 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${start}',maxDate:'${max}'})"/> <input type="text"
			 name="shour" id="shour" style="width:20px;" value="00" disabled/> 起 至 <input type="text" name="endDate" id="end" value="${end}" class="Wdate"
			 style="width:100px;" disabled/> <input type="text" name="ehour" id="ehour" style="width:20px;" value="24" disabled/> 止 </span> 
			 <div style="display: none;"><span style="color: red;">*</span>起运地 <input type="text" name="cargoStartSite" 
			 id="cargoStartSite"><span style="color: red;">*</span>目的地 <input type="text" name="cargoTargetSite" id="cargoTargetSite"></div>
		 </td>
		</tr>
	</table></td>
	</tr>
	 
	<tr><td style="width:100%"><label class="Validform_label"> 投保人： </label></td></tr>

	<tr><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr style="height:46px;"><td style="width:150px;" class="border-right">投保人类型</td>
		 <td style="width:300px;" class="border-right"><t:dictSelect field="holderPartType" id="holderPartType" type="radio" divClass="dict_select" title=""
						typeGroupCode="partType" defaultVal="QY" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span></td>
		 <td style="width:150px;" class="border-right"></td><td style="width:auto;"></td>
		</tr>
		 
		<tr>
		 <td class="border-right"><div name="holderQYProp"><span style="color: red;">*</span>企业名称</div><div name="holderGRProp" 
		 style="display: none;"><span style="color: red;">*</span>投保人姓名</div></td>
		 <td class="border-right"><select name="holderName" id="holderName" class="editable-select" style="width:180px;" autocomplete="off">
				<option value=""></option>
				</select></td>
		 <td class="border-right"><div name="holderQYProp"><span style="color: red;">*</span>证件号码<BR/>(统一社会信用编码)</div><div name="holderGRProp" 
		 style="display: none;"><span style="color: red;">*</span>证件号码</div></td>
		 <td><input type="text" name="holderCertNo" id="holderCertNo" maxlength="18" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>证件类型</td>
		 <td class="border-right"><t:dictSelect field="holderCertType" id="holderCertType" type="list" divClass="dict_select" title=""
						typeGroupCode="cardType" defaultVal="1" hasLabel="false" ></t:dictSelect></td>
		 <td class="border-right"><span style="color: red;">*</span>证件有效期</td>
		 <td><input type="text" name="holderValidity" id="holderValidity" maxlength="12" style="width:200px;" /></td>
		</tr>
		<tr name="holderGRTr" style="display: none;">
		 <td class="border-right"><span style="color: red;">*</span>性别</td>
		 <td class="border-right"><t:dictSelect field="holderSex" id="holderSex" type="list" divClass="dict_select" title=""
						typeGroupCode="sex" defaultVal="F" hasLabel="false" ></t:dictSelect></td>
		 <td class="border-right"><span style="color: red;">*</span>职业</td>
		 <td><input type="text" name="holderProfession" id="holderProfession" maxlength="20" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>邮编</td>
		 <td class="border-right"><input type="text" name="holderPostal" id="holderPostal" maxlength="8" style="width:200px;" /></td>
		 <td class="border-right"><div name="holderGRProp" style="display: none;"><span style="color: red;">*</span>国籍</div></td>
		 <td><input type="text" name="holderNation" id="holderNation" maxlength="20" style="width:200px;display: none;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>通讯地址</td>
		 <td class="border-right" colspan="3"><input type="text" name="holderAddress" id="holderAddress" style="width:650px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>联系人姓名</td>
		 <td class="border-right"><input type="text" name="holderCtatName" id="holderCtatName" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>Email</td>
		 <td><input type="text" name="holderCtatEmail" id="holderCtatEmail" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>办公电话</td>
		 <td class="border-right"><input type="text" name="holderCtatPhone" id="holderCtatPhone" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>手机号码</td>
		 <td><input type="text" name="holderCtatMobile" id="holderCtatMobile" maxlength="11" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>传真号码</td>
		 <td class="border-right"><input type="text" name="holderCtatFax" id="holderCtatFax" style="width:200px;" /></td>
		 <td class="border-right"></td><td></td>
		</tr>
		 
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>法人/负责人姓名</td>
		 <td class="border-right"><input type="text" name="corporate" id="corporate" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>法人证件号码</td>
		 <td><input type="text" name="corporCertNo" id="corporCertNo" maxlength="18" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>法人证件名称</td>
		 <td class="border-right"><input type="text" name="corporCertName" id="corporCertName" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>法人证件有效期</td>
		 <td><input type="text" name="corporValidity" id="corporValidity" maxlength="12" style="width:200px;" /></td>
		</tr>
	</table></td>
	</tr>
 
	<tr>
	 <td style="width:100%"><label class="Validform_label">被保人：</label><input id="check1" type="checkbox" checked/>同投保人</td>
	</tr>
	 
	<tr id="insuredTr" style="display: none;"><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<div id="insuredDiv" style="display: none;">
		<tr style="height:46px;"><td style="width:150px;" class="border-right">被保人类型</td>
		 <td style="width:300px;" class="border-right"><t:dictSelect field="insuredPartType" id="insuredPartType" type="radio" divClass="dict_select" title=""
						typeGroupCode="partType" defaultVal="QY" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span></td>
		 <td style="width:150px;" class="border-right"></td><td style="width:auto;"></td>
		</tr>
		 
		<tr>
		 <td class="border-right"><div name="insuredQYProp"><span style="color: red;">*</span>企业名称</div><div name="insuredGRProp" 
		 style="display: none;"><span style="color: red;">*</span>被保人姓名</div></td>
		 <td class="border-right"><select name="insuredName" id="insuredName" class="editable-select" style="width:180px;" autocomplete="off">
				<option value=""></option>
				</select></td>
		 <td class="border-right"><div name="insuredQYProp"><span style="color: red;">*</span>证件号码<BR/>(统一社会信用编码)</div><div name="insuredGRProp" 
		 style="display: none;"><span style="color: red;">*</span>证件号码</div></td>
		 <td><input type="text" name="insuredCertNo" id="insuredCertNo" maxlength="18" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>证件类型</td>
		 <td class="border-right"><t:dictSelect field="insuredCertType" id="insuredCertType" type="list" divClass="dict_select" title=""
						typeGroupCode="cardType" defaultVal="1" hasLabel="false" ></t:dictSelect></td>
		 <td class="border-right"><span style="color: red;">*</span>有效期至</td>
		 <td><input type="text" name="insuredValidity" id="insuredValidity" maxlength="12" style="width:200px;" /></td>
		</tr>
		<tr name="insuredGRTr" style="display: none;">
		 <td class="border-right"><span style="color: red;">*</span>性别</td>
		 <td class="border-right"><t:dictSelect field="insuredSex" id="insuredSex" type="list" divClass="dict_select" title=""
						typeGroupCode="sex" defaultVal="F" hasLabel="false" ></t:dictSelect></td>
		 <td class="border-right"><span style="color: red;">*</span>职业</td>
		 <td><input type="text" name="insuredProfession" id="insuredProfession" maxlength="20" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>邮编</td>
		 <td class="border-right"><input type="text" name="insuredPostal" id="insuredPostal" maxlength="8" style="width:200px;" /></td>
		 <td class="border-right"><div name="insuredGRProp" style="display: none;"><span style="color: red;">*</span>国籍</div></td>
		 <td><input type="text" name="insuredNation" id="insuredNation" maxlength="20" style="width:200px;display: none;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>通讯地址</td>
		 <td class="border-right" colspan="3"><input type="text" name="insuredAddress" id="insuredAddress" style="width:650px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>联系人姓名</td>
		 <td class="border-right"><input type="text" name="insuredCtatName" id="insuredCtatName" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>Email</td>
		 <td><input type="text" name="insuredCtatEmail" id="insuredCtatEmail" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>办公电话</td>
		 <td class="border-right"><input type="text" name="insuredCtatPhone" id="insuredCtatPhone" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>手机号码</td>
		 <td><input type="text" name="insuredCtatMobile" id="insuredCtatMobile" maxlength="11" style="width:200px;" /></td>
		</tr>
		<tr>
		 <td class="border-right"><span style="color: red;">*</span>传真号码</td>
		 <td class="border-right"><input type="text" name="insuredCtatFax" id="insuredCtatFax" style="width:200px;" /></td>
		 <td class="border-right"></td><td></td>
		</tr>
		</div>
	</table></td>
	</tr>
 
	<tr>
	 <td style="width:100%"><label class="Validform_label">权益人：</label></td>
	</tr>
	 
	<tr><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr>
		 <td style="width:150px;" class="border-right"><span style="color: red;">*</span>权益人姓名</td>
		 <td style="width:300px;" class="border-right"><input type="text" name="beneficiary" id="beneficiary" style="width:200px;" /></td>
		 <td style="width:150px;" class="border-right"></td><td></td>
		</tr>
	</table></td>
	</tr>
	 
	<tr><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr><td style="width:150px;" class="border-right">货物信息：</td>
		 <td style="width:auto;">
			<table name="policy_tabel" id="policy_tabel">
			<tbody id="add_policy_tabel">
				<tr name='policytr'>
				<td><span style="color: red;">*</span>货物名称 </td>
				<td><input type="text" name="goodsName" class="policy" title="goodsName" maxlength="20"></td>
				<td><span style="color: red;">*</span>承保险别 </td>
				<td><input type="text" name="category" class="policy" title="category" style="width:100px;" maxlength="20"></td>
				<td><span style="color: red;">*</span>件数/重量 </td>
				<td><input type="text" name="countOrWeight" class="policy" title="countOrWeight" style="width:60px;" maxlength="20"></td>
				<td><span style="color: red;">*</span>保险金额 </td>
				<td><input type="text" name="insuredAmount" class="policy" title="insuredAmount" style="width:120px;"></td>
				<td><span style="color: red;">*</span>费率 </td>
				<td><input type="text" name="premiumRate" class="policy" title="premiumRate" style="width:100px;"></td>
				</tr>
			</tbody>
			</table></td>
		</tr>
	</table></td>
	</tr>
	 
	</table></td>
</tr>
</table>

<div style="text-align:center;width:99%;padding-top:10px;">
	<input id="pay" class="subBtnmy btn-size" type="button" value="立即支付" onclick="doPay();"/>
	<input id="back" class="btnmy btn-size" type="button" value="关闭" onclick="closeCurrent('tab_4028e5846eb182ce016eb1d551d20001');"/>
</div>
</fieldset>

<input id="status" name="status" type="hidden" value="1" />
<input id="userId" name="userId" type="hidden" />
<input id="payStatus" name="payStatus" type="hidden" value="${freightPolicyPage.payStatus}"/>
<input id="createTime" name="createTime" type="hidden" value="${freightPolicyPage.createTime}"/>
<input id="endDate" name="endDate" type="hidden" value="${end}" />
<input id="recipients" name="recipients" type="hidden" />
<input id="recipientsTel" name="recipientsTel" type="hidden" />
<input id="reciAddress" name="reciAddress" type="hidden" />
<input id="payObj" name="payObj" type="hidden" />
<input id="payUrl" name="payUrl" type="hidden" />
<input id="payResult" name="payResult" type="hidden" value="1"/>
<input id="insResult" name="insResult" type="hidden" value="1"/>
<input id="isDraft" name="isDraft" type="hidden" value="true"/>
</t:formvalid>

<%@include file="freightPromptDiv.jsp"%>
<%@include file="freightPromptDiv2.jsp"%>
<%@include file="/webpage/com/weibao/chaopei/policy/policyPayiFrame.jsp"%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增保单</title>
<t:base type="jquery,easyui,tools,autocomplete,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/freight/freightPolicy.js"></script>
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
td .border-right{border-right:1px solid #E3E3E3;}
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
    //window.Utils.showLoading();
	getHolders();


    $("#holderCompNature").css("width", "200px");
    $("#industryType").css("width", "200px");
    $("#holderCertType").css("width", "200px");
    $("#holderCardType").css("width", "200px");
    $("#insuredCertType").css("width", "200px");
    $("#insuredCardType").css("width", "200px");
    window.setTimeout(customFunc, 500);

	var abc = $("#formobj").width()+17;
	$("#formobj").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
});

function customFunc() {
	editablePolicy();
	
    //window.Utils.closeLoading();
	window.Custom.dialogLoading(false);
}

//核保失败回调函数
function failureCallback(result) {
    if(console) console.log("returnObj == ", result);
    debugger;
	var draftId = result.draftId;
	var createTime = result.createTime;
	var vehicles = result.vehicles;
	document.getElementById("draftId").value = draftId;
	document.getElementById("createTime").value = createTime;
	for(var j = 0; j < vehicles.length; j++) {
		var vehicleBack = vehicles[j];
		var frameNo = vehicleBack.frameNo;
		var pid = vehicleBack.id;

		var plateNos = $("[class='policy'][title='plateNo']");
		var length = plateNos.length;
		for(var i = 0; i < length; i++) {
			var vehicle = {};
			vehicle.id = document.getElementsByName("vehicles["+i+"].id")[0].value;
			vehicle.frameNo = document.getElementsByName("vehicles["+i+"].frameNo")[0].value;
			if(frameNo == vehicle.frameNo) {
				document.getElementsByName("vehicles["+i+"].id")[0].value = pid;
			}
		}
	}
}
//Ajax方式提交表单数据
function submitForm() {
	var url = "freightPolicyController.do?insuranceAdd";
	var params = getSubmitParam();
	ajaxSubmitForm(url, params, true);
}
function submitFormUpd() {
	var url = "freightPolicyController.do?insuranceUpdate";
	var params = getSubmitParam();
	params = getUpdateParam(params);
	ajaxSubmitForm(url, params, true);
}
function confirmSubmit() {
	if($("#insResult").val() == "1") {
		submitForm();
	} else {
		submitFormUpd();
	}
	$("#save").attr("disabled", true);
	$("#insur").attr("disabled", true);
    window.Utils.showLoading(imgName);
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
	
    //window.setTimeout(window.Utils.closeLoading, 2000);
}
//支付
function doPay() {
	//submitPay("${prodId}");
}
</SCRIPT>
</head>
<body class="panel-noscroll">

<t:formvalid formid="formobj" dialog="false" layout="table" tiptype="1" action="freightPolicyController.do?doAdd" callback="jeecgFormFileCallBack@Override">
<fieldset class="step" style="width:100%;padding-bottom: 20px;">
 <legend>东瑞安心运</legend>
 <table cellpadding="0" cellspacing="1" class="formtable" style="table-layout: fixed;" width="1200">
	<input id="id" name="id" type="hidden" value="${policyMainPage.id }"/>
	<input id="prodId" name="prodId" type="hidden"/>
	<input id="premium" name="premium" type="hidden"/>
 
 <tr><td style="width:60px;"></td><td style="width:1140px;">
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
	 <tr><td style="width:150px;" class="border-right">保额</td>
	 <td style="width:auto;">
		<input type="text" name="coverage" id="coverage" maxlength="10" style="width:200px;" />
		<span class="Validform_checktip"></span></td>
	 </tr>
	 </table>
 </td></tr>
 
 <tr><td style="width:60px;">投保内容</td><td style="width:1140px;">
	 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
	 
	 <tr><td style="width:100%"><label class="Validform_label"> 投保人： </label></td></tr>

	 <tr><td>
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr style="height:46px;"><td style="width:150px;" class="border-right">投保人类型</td>
		 <td style="width:300px;" class="border-right"><t:dictSelect field="holderPartType" id="holderPartType" type="radio" divClass="dict_select" title=""
						typeGroupCode="partType" defaultVal="QY" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span></td>
		 <td style="width:150px;" class="border-right"><label id="holderCertLbl"><span style="color: red;">*</span>证件类型</label></td>
		 <td style="width:auto;"><t:dictSelect field="holderCertType" id="holderCertType" type="list" divClass="dict_select" title=""
						typeGroupCode="certType" defaultVal="2" hasLabel="false" ></t:dictSelect></td>
		 </tr>
		 <tr name="holderQYTr">
		 <td class="border-right"><span style="color: red;">*</span>企业名称</td>
		 <td class="border-right"><select name="holderCompName" id="holderCompName" class="editable-select" style="width:180px;" autocomplete="off">
				<option value=""></option>
				</select></td>
		 <td class="border-right"><span style="color: red;">*</span>证件号码<BR/>(统一社会信用编码) </td>
		 <td><input type="text" name="holderOrgCode" id="holderOrgCode" maxlength="18" style="width:200px;" /></td>
		 </tr>
		 <tr name="holderQYTr">
		 <td class="border-right"><span style="color: red;">*</span>联系人姓名</td>
		 <td class="border-right"><input type="text" name="holderContName" id="holderContName" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>联系人手机</td>
		 <td><input type="text" name="holderPlcyMobile" id="holderPlcyMobile" maxlength="11" style="width:200px;" /></td>
		 </tr>
		 <tr name="holderGRTr" style="display: none;">
		 <td class="border-right"><span style="color: red;">*</span>姓名</td>
		 <td class="border-right"><input type="text" name="holderName" id="holderName" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>证件号码</td>
		 <td><input type="text" name="holderCardNumber" id="holderCardNumber" maxlength="18" style="width:200px;" /></td>
		 </tr>
		 <tr name="holderGRTr" style="display: none;">
		 <td class="border-right"><span style="color: red;">*</span>证件类型</td>
		 <td class="border-right"><t:dictSelect field="holderCardType" id="holderCardType" type="list" divClass="dict_select" title=""
						typeGroupCode="cardType" defaultVal="1" hasLabel="false" ></t:dictSelect></td>
		 <td class="border-right"><span style="color: red;">*</span>手机号码</td>
		 <td><input type="text" name="holderMobileNo" id="holderMobileNo" maxlength="11" style="width:200px;" /></td>
		 </tr>
	 </table>
	 </td></tr>
 
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
		 <td style="width:150px;" class="border-right"><label id="insuredCertLbl"><span style="color: red;">*</span>证件类型</label></td>
		 <td style="width:auto;"><t:dictSelect field="insuredCertType" id="insuredCertType" type="list" divClass="dict_select" title=""
						typeGroupCode="certType" defaultVal="2" hasLabel="false" ></t:dictSelect></td>
		 </tr>
		 <tr name="insuredQYTr">
		 <td class="border-right"><span style="color: red;">*</span>企业名称 </td>
		 <td class="border-right"><input type="text" name="insuredCompName" id="insuredCompName" style="width:200px;" autocomplete="off"/></td>
		 <td class="border-right"><span style="color: red;">*</span>证件号码<BR/>(统一社会信用编码)</td>
		 <td><input type="text" name="insuredOrgCode" id="insuredOrgCode" maxlength="18" style="width:200px;" />
		 <span class="Validform_checktip"></span></td>
		 </tr>
		 <tr name="insuredQYTr">
		 <td class="border-right"><span style="color: red;">*</span>联系人姓名</td>
		 <td class="border-right"><input type="text" name="insuredContName" id="insuredContName" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>联系人手机</td>
		 <td><input type="text" name="insuredPlcyMobile" id="insuredPlcyMobile" maxlength="11" style="width:200px;" /></td>
		 </tr>
		 <tr name="insuredGRTr" style="display: none;">
		 <td class="border-right"><span style="color: red;">*</span>姓名</td>
		 <td class="border-right"><input type="text" name="insuredName" id="insuredName" style="width:200px;" /></td>
		 <td class="border-right"><span style="color: red;">*</span>证件号码</td>
		 <td><input type="text" name="insuredCardNumber" id="insuredCardNumber" maxlength="18" style="width:200px;" /></td>
		 </tr>
		 <tr name="insuredGRTr" style="display: none;">
		 <td class="border-right"><span style="color: red;">*</span>证件类型</td>
		 <td class="border-right"><t:dictSelect field="insuredCardType" id="insuredCardType" type="list" divClass="dict_select" title=""
						typeGroupCode="cardType" defaultVal="1" hasLabel="false" ></t:dictSelect></td>
		 <td class="border-right"><span style="color: red;">*</span>手机号码</td>
		 <td><input type="text" name="insuredMobileNo" id="insuredMobileNo" maxlength="11" style="width:200px;" /></td>
		 </tr>
		 </div>
	 </table>
	 </td></tr>
	 
	 <tr><td>
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr><td style="width:150px;" class="border-right">保单信息：</td>
		 <td style="width:auto;">
			<table name="policy_tabel" id="policy_tabel">
			<tbody id="add_policy_tabel">
				<tr name='policytr'>
				<td><span style="color: red;">*</span>保险货物项目 </td>
				<td><input type="text" name="cargoItem" class="policy" title="cargoItem" style="width:100px;" maxlength="20"></td>
				<td><span style="color: red;">*</span>包装及数量 </td>
				<td><input type="text" name="cargoPackageAndCount" class="policy" title="cargoPackageAndCount" style="width:60px;" maxlength="20"></td>
				<td><span style="color: red;">*</span>起运地 </td>
				<td><input type="text" name="cargoStartSite" class="policy" title="cargoStartSite"></td>
				<td><span style="color: red;">*</span>目的地 </td>
				<td><input type="text" name="cargoTargetSite" class="policy" title="cargoTargetSite"></td>
				</tr>
			</tbody>
			</table>
		 </td></tr>
	 </table>
	 </td></tr>
	 
	 <tr><td>
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr><td style="width:150px;" class="border-right">保险期间：</td>
		 <td style="width:auto;">
		 起运时间 <input type="text" name="startDate" id="start" value="${start}" class="Wdate" style="width:150px;" 
		 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${start}',maxDate:'${max}'})"/> 结束时间 <input type="text" name="endDate" id="end" value="卸货完成时结束"
		 style="width:200px;" disabled/> </td>
		 </tr>
	 </table>
	 </td></tr>
	 
	 <tr><td>
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr><td style="width:150px;" class="border-right">车辆信息：</td>
		 <td style="width:auto;">
			<table name="vehicle_tabel" id="vehicle_tabel">
			<tbody id="add_vehicle_tabel">
				<tr name='vehicletr'>
				<input name="id" type="hidden"/>
				<td><div style="text-align:right;width:auto;"><span style="color: red;">*</span>车牌号</div></td>
				<td><input type="text" name="plateNo" class="policy" title="plateNo" maxlength="8" style="width:100px;" value=""></td>
				<td><span style="color: red;">*</span>运单号 </td>
				<td><input type="text" name="wayBillNo" class="policy" title="wayBillNo" maxlength="17"></td>
				<td><span style="color: red;">*</span>运输方式 </td>
				<td><select name="cargoTransportWay" style="width:100px;" class="policy" title="cargoTransportWay">
				<option value="1">水运</option>
				<option value="2">航空</option>
				<option value="3">公路</option>
				<option value="4">铁路</option>
				<option value="5">邮包</option>
				<option value="6">联运</option>
				</select></td>
				</tr>
			</tbody>
			</table>
		 </td></tr>
	 </table>
	 </td></tr>
	 
	 </table>
 </td></tr>
 </table>
 <br>
<div style="text-align:center;width:99%;padding-top:10px;">
<input id="readedPrompt" type="checkbox" style=""/><b><span style="font-size: 15px;">我已阅读《投保须知》和《保险条款》</span></b>
</div>

<div style="text-align:center;width:99%;padding-top:10px;">
<input id="pay" class="subBtnmy" type="button" value="立即支付" onclick="doPay();" style="height:30px;width:100px !important;border-radius:5px" disabled/>
<input id="back" class="btnmy" type="button" value="关闭" onclick="closeCurrent('tab_4028e5846eb182ce016eb1d551d20001');" style="height:30px;width:100px !important;border-radius:5px"/>
</div>
</fieldset>

<input id="status" name="status" type="hidden" value="1" />
<input id="userId" name="userId" type="hidden" />
<input id="payStatus" name="payStatus" type="hidden" value="0"/>
<input id="createTime" name="createTime" type="hidden" value="${policyMainPage.createTime }"/>
<input id="endDate" name="endDate" type="hidden" value="${end}" />
<input id="compNamep" name="compName" type="hidden" />
<input id="taxpayerNop" name="taxpayerNo" type="hidden" />
<input id="compAddressp" name="compAddress" type="hidden" />
<input id="recipientsp" name="recipients" type="hidden" />
<input id="recipientsTelp" name="recipientsTel" type="hidden" />
<input id="reciAddressp" name="reciAddress" type="hidden" />
<input id="insuranceObj" name="insuranceObj" type="hidden" />
<input id="payUrl" name="payUrl" type="hidden" />
<input id="payResult" name="payResult" type="hidden" />
<input id="insResult" name="insResult" type="hidden" value="1"/>
<input id="isDraft" name="isDraft" type="hidden" value="true"/>
</t:formvalid>

<%@include file="/webpage/com/weibao/chaopei/policy/policyPromptDiv.jsp"%>
<%@include file="/webpage/com/weibao/chaopei/policy/policyPayiFrame.jsp"%>
</body>
</html>
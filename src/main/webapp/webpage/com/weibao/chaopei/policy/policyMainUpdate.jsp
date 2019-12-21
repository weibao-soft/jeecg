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
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/PolicyMainUpdateComponent.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/policyCommon.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/driveValid.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/utils.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/DateUtils.js"></script>
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
.info-table table{border-right:1px solid #E3E3E3;border-bottom:0px solid #FFF;} 
.info-table table td{border-left:1px solid #E3E3E3;border-top:1px solid #E3E3E3;}
</style>
<SCRIPT type="text/javascript">
$(document).ready(function(){
	window.Custom.dialogLoading(true);
	var params = {};
	params.paramId = "${policyMainPage.prodId }";
	var url = "policyMainController.do?getProductPlan";
	getCommonSelect("planId", url, params);

    window.setTimeout(customFunc, 600);
    //初始化默认保险期间
    initDefaultInsurDate("${year}");
	var vehicles = ${el:toJsonString(policyMainPage.vehicles)};
	// 初始化界面事件监听
	initToggleShourModeEvent('${start}', '${max}', '${end}', '${year}', vehicles);

});

function customFunc() {
	editablePolicy();
	editableInvoice();
	
	var invoiceType = "${policyMainPage.invoiceType}";
	$("#planId").val("${policyMainPage.planId}");
	$("#holderCompName").val("${policyMainPage.holderCompName}");
	$("#recipients").val("${policyMainPage.recipients }");
	var isPaperPolicy = "${policyMainPage.isPaperPolicy}";
	var isPaperInvoice = "${policyMainPage.isPaperInvoice}";
	if(isPaperPolicy == "1") {
		$("#check2").attr("checked", true);
	}
	if(isPaperInvoice == "1") {
		$("#check3").attr("checked", true);
	}
	if(invoiceType == "3") {
		$("#invoiceType").val(invoiceType);
		$("#taxpayerNo2").val("");
	} else {
		$("#invoiceType").val(invoiceType).trigger('change');
		$("#taxpayerNop").val("");
	}
	window.Custom.dialogLoading(false);
}
function customFunc2() {
	window.Custom.dialogLoading(false);
}

//imgName = "swagger/images/throbber.gif";
imgName = "images/loading-a.gif";
//Ajax方式提交表单数据
function submitForm() {
	$("#save").attr("disabled", true);
	$("#insur").attr("disabled", true);
	//window.Custom.dialogLoading(true);
    window.Utils.showLoading(imgName);

    var mainTabId = "tab_402880ea6df0af64016df0b507840001";
	var url = "policyDraftController.do?insuranceUpdate";
	var params = getSubmitParam();
	params = getUpdateParam(params);
	ajaxSubmitForm(url, params, false, mainTabId);
	//tip(JSON.stringify(params));
    //window.setTimeout(window.Utils.closeLoading, 2000);
    //window.setTimeout(customFunc2, 2000);
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
	
	//var payUrl = "${payUrl}";
	//payUrl = encodeURIComponent(payUrl);
	//EV_modeAlert();//弹出遮罩层
    //var payUrl = "https://devyun.guorenpcic.com/paycenter/?orderId=23a2e077d1e4fd19a61&code=&payOrderNo=js02&platform=pc";
    //参数： url, 名称, 窗体样式
	//window.childWindow = window.open("policyMainController.do?goChild&payUrl="+payUrl, "支付", "height=666, width=1266, top=0, left=0, alwaysRaised=yes, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	//window.childWindow.focus();//子窗口获取焦点
}
//支付
function doPay() {
    var mainTabId = "tab_402880ea6df0af64016df0b507840001";
	submitPay("${policyMainPage.draftId}", mainTabId);
}

function getIframeTemplate(url) {
    console.log(url)
    return '<iframe name="iframe" src="' + url + '" width="100%" height="100%" frameborder="0" marginheight="0" marginwidth="0" scrolling="yes" target="_self" seamless ></iframe>';
}
//刷新保单列表
function reloadPolicyLists() {
	var mydiv = window.parent.$("#tab_402880ea6df0af64016df0b507840001");
	var myframe = $(mydiv).children("iframe")[0];
	var myWindow = myframe.contentWindow;
    myWindow.reload();
    closeCurrent('tab_${policyMainPage.id}');
}
</SCRIPT>
</head>
<body style="overflow-x: hidden;overflow-y: auto;">

<t:formvalid formid="formobj" dialog="false" layout="table" tiptype="1" action="policyDraftController.do?doUpdate" callback="jeecgFormFileCallBack@Override">
<fieldset class="step" style="width:99%;padding-bottom: 20px;">
<legend>国任投保</legend>
<table cellpadding="0" cellspacing="1" class="formtable" style="table-layout: fixed;" width="1200">
	<input id="id" name="id" type="hidden" value="${policyMainPage.id }"/>
	<input id="prodId" name="prodId" type="hidden" value="${policyMainPage.prodId }"/>
	<input id="draftId" name="draftId" type="hidden" value="${policyMainPage.draftId }"/>
	<input id="premium" name="premium" type="hidden" value="${policyMainPage.premium }"/>
 
 <tr>
 <td style="width:15px;text-align:center;"></td><td style="width:1185px;">
	 <table cellpadding="0" cellspacing="0" class="formtable" width="100%">
	 <tr><td style="width:130px;">方案保障</td>
	 <td style="width:auto;">
			<select name="planId" id="planId" style="width:500px;" value="${policyMainPage.planId}">
			</select><span class="Validform_checktip"></span></td></tr>
	 </table></td>
 </tr>
 
 <tr>
 <td style="width:15px;text-align:center;">投保内容</td><td style="width:1185px;">
	<table cellpadding="0" cellspacing="1" class="formtable" width="100%">
	 
	<tr><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr><td style="width:130px;border-right:1px solid #E3E3E3;">车辆信息：</td>
		 <td style="width:auto;">
			<table name="policy_tabel" id="policy_tabel">
			<thead>
				<tr>
					<td style="width:120px;"><span style="color: red;">*</span>车牌号<BR/>(新车填写:未上牌)</td>
					<td><span style="color: red;">*</span>车架号</td>
					<td><span style="color: red;">*</span>发动机号</td>
					<td><span style="color: red;">*</span>核定<BR/>载重质量</td>
					<td align="center">操作</td>
					<td align="center">保险期间</td>
				</tr>
			</thead>
			<tbody id="add_policy_tabel">
			<c:if test="${fn:length(policyMainPage.vehicles) <= 0 }">
				<tr name='policytr'>
				<input name="vehicles[0].id" type="hidden"/>
				<td><input type="text" name="vehicles[0].plateNo" class="policy" title="plateNo" maxlength="8" style="width:100px;" value="未上牌"></td>
				<td><input type="text" name="vehicles[0].frameNo" class="policy" title="frameNo" maxlength="17" style="width:180px;" placeholder="输入车架号"></td>
				<td><input type="text" name="vehicles[0].engineNo" class="policy" title="engineNo" maxlength="40" style="width:120px;" placeholder="输入发动机号"></td>
				<td><input type="text" name="vehicles[0].tonCount" class="policy" title="tonCount" maxlength="2" style="width:60px;" value="0" placeholder="默认填: 0" readonly></td>
				<td><input class="btn" type="button" value="新增 " onclick="addPolicy();" style="height:30px;width:100px !important;"/></td>
				<td><span data-event="toggleShourMode" class="radio-one"><input type="checkbox" name="dateMode" value="custom"/>自定义</span></td>
				</tr>
			</c:if>
			<c:if test="${fn:length(policyMainPage.vehicles) > 0 }">
				<c:forEach items="${policyMainPage.vehicles}" var="poVal" varStatus="stat">
					<tr name='policytr'>
					<input name="vehicles[${stat.index }].id" type="hidden" value="${poVal.id }"/>
					<td><input type="text" name="vehicles[${stat.index }].plateNo" class="policy" title="plateNo" maxlength="8" 
					style="width:100px;" value="${poVal.plateNo}"></td>
					<td><input type="text" name="vehicles[${stat.index }].frameNo" class="policy" title="frameNo" maxlength="17" 
					style="width:180px;" placeholder="输入车架号" value="${poVal.frameNo}"></td>
					<td><input type="text" name="vehicles[${stat.index }].engineNo" class="policy" title="engineNo" maxlength="40" 
					style="width:120px;" placeholder="输入发动机号" value="${poVal.engineNo}"></td>
					<td><input type="text" name="vehicles[${stat.index }].tonCount" class="policy" title="tonCount" maxlength="2" 
					style="width:60px;" placeholder="默认填: 0" value="${poVal.tonCount}" readonly></td>
				<c:if test="${stat.index == 0 }">
					<td><input class="btn" type="button" value="新增 " onclick="addPolicy();" 
					style="height:30px;width:100px !important;"/></td>
				</c:if>
				<c:if test="${stat.index > 0 }">
					<td><input class="btn" type="button" value="删除" onclick="removePolicy(this);" 
					style="height:30px;width:100px !important;"/></td>
				</c:if>
					<td><span data-event="toggleShourMode" class="radio-one"><input type="checkbox" checked name="dateMode" value="custom"/>自定义</span></td>
		   			</tr>
				</c:forEach>
			</c:if>
			</tbody>
			</table></td>
		</tr>
	</table></td>
	</tr>
	 
	<tr><td>
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr><td style="width:130px;border-right:1px solid #E3E3E3;">保险期间：</td>
		 <td id="defaultInsuranceDate" style="width:auto;">
<%--		 <span>自 <input type="text" name="startDate" id="start" value="${start}" class="Wdate" style="width:100px;" onblur="calculateYear();" --%>
<%--		 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${start}',maxDate:'${max}'})"/> <input type="text" --%>
<%--		 name="shour" id="shour" style="width:20px;" value="00" disabled/> 起 至 <input type="text" name="endDate" id="end" value="${end}" class="Wdate"--%>
<%--		 style="width:100px;" disabled/> <input type="text" name="ehour" id="ehour" style="width:20px;" value="24" disabled/> 止，连续 <input type="text" --%>
<%--		 name="year" id="year" style="width:60px;" value="${year}" onblur="calculateMonths(this);">年 共<label id="month">12</label>月 </span> --%>
		 </td>
		</tr>
	</table></td>
	</tr>
	 
	<tr><td style="width:100%"><label class="Validform_label"> 投保人： </label></td></tr>
	 
	<tr><td class="info-table">
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr><td style="width:130px;"><span style="color: red;">*</span>投保人性质</td>
		 <td style="width:300px;"><t:dictSelect field="holderNature" id="holderNature" type="list" divClass="dict_select" title=""
						typeGroupCode="holdNature" defaultVal="${policyMainPage.holderNature}" hasLabel="false" ></t:dictSelect>
						<span class="Validform_checktip"></span></td>
		 <td style="width:130px;"></td><td style="width:auto;"></td>
		</tr>
		<tr><td><span style="color: red;">*</span>单位名称</td>
		 <td><select name="holderCompName" id="holderCompName" style="width:180px;" autocomplete="off" value="${policyMainPage.holderCompName}">
				<option value=""></option>
				</select></td>
		 <td><span style="color: red;">*</span>组织机构代码<BR/>(统一社会信用代码) </td>
		 <td><input type="text" name="holderOrgCode" id="holderOrgCode" maxlength="18" style="width:200px;" value="${policyMainPage.holderOrgCode}"/></td>
		</tr>
		<tr><td><span style="color: red;">*</span>单位性质</td>
		 <td><t:dictSelect field="holderCompNature" id="holderCompNature" type="list" divClass="dict_select" title=""
						typeGroupCode="compNature" defaultVal="${policyMainPage.holderCompNature}" hasLabel="false" ></t:dictSelect></td>
		 <td><span style="color: red;">*</span>行业类别</td>
		 <td><t:dictSelect field="industryType" id="industryType" type="list" divClass="dict_select" title=""
						typeGroupCode="industType" defaultVal="${policyMainPage.industryType}" hasLabel="false" ></t:dictSelect></td>
		</tr>
		<tr><td><span style="color: red;">*</span>联系人姓名</td>
		 <td><input type="text" name="contactName" id="contactName" style="width:200px;" value="${policyMainPage.contactName}"/></td>
		 <td><span style="color: red;">*</span>联系人手机</td>
		 <td><input type="text" name="policyMobile" id="policyMobile" maxlength="11" style="width:200px;" value="${policyMainPage.policyMobile}"/></td>
		</tr>
		<tr><td style="color: red;font-size:15px;">发票类型</td><td>
		 <select name="invoiceType" id="invoiceType" style="width:200px;">
				<option value="1">不开发票</option>
				<option value="2">增值税普通发票</option>
				<option value="3">增值税专用发票</option>
				</select><span class="Validform_checktip"></span>
		 </td><td></td><td></td>
		</tr>
		<tr id="invoiceTr" style="display: none;">
		 <td>纳税人识别号</td>
		 <td><input type="text" name="taxpayerNo2" id="taxpayerNo2" maxlength="18" style="width:200px;" value="${policyMainPage.taxpayerNo}"/></td>
		 <td>接收人手机</td>
		 <td><input type="text" name="receiverMobile" id="receiverMobile" maxlength="11" style="width:200px;" value="${policyMainPage.receiverMobile}"/></td>
		</tr>
	</table></td>
	</tr>
	 
	<tr>
	 <td style="width:100%"><label class="Validform_label"> 纸质发票、保单收件人： </label><span style="color: red;font-size:15px;"><input id="check2" 
	     type="checkbox" />是否纸质保单&nbsp;&nbsp;<input id="check3" type="checkbox" />是否纸质发票</span></td>
	</tr>
	 
	<tr><td style="table-layout: fixed;" class="info-table">
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		<tr><td style="width:130px;">收件人</td>
			<td style="width:300px;"><select name="recipients" id="recipients" class="editable-select" style="width:180px;" autocomplete="off" value="${policyMainPage.recipients }">
				<option value=""></option></select>
				<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">收件人</label>
			</td>
			<td style="width:130px;">收件人电话</td>
			<td style="width:auto;">
				<input id="recipientsTel" name="recipientsTel" type="text" style="width: 200px" value="${policyMainPage.recipientsTel }" />
				<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">收件人电话</label>
			</td>
		</tr>
		<tr><td>收件地址</td>
			<td colspan="3">
		     	<input id="reciAddress" name="reciAddress" type="text" style="width: 630px" value="${policyMainPage.reciAddress }" />
				<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">收件地址</label>
			</td>
		</tr>
	</table></td>
	</tr>
 
	<tr>
	 <td style="width:100%"><label class="Validform_label">被保人：</label><input id="check1" type="checkbox" />同投保人</td>
	</tr>
	 
	<tr><td class="info-table">
	<table cellpadding="0" cellspacing="0" class="formtable" width="100%">
		 <tr>
		 <td style="width:130px;"><span style="color: red;">*</span>单位名称 </td>
		 <td style="width:300px;"><input type="text" name="insuredCompName" id="insuredCompName" style="width:200px;" value="${policyMainPage.insuredCompName}" autocomplete="off"/></td>
		 <td style="width:130px;"><span style="color: red;">*</span>组织机构代码<BR/>(统一社会信用代码)</td>
		 <td style="width:auto;"><input type="text" name="insuredOrgCode" id="insuredOrgCode" maxlength="18" style="width:200px;" value="${policyMainPage.insuredOrgCode}"/>
		 <span class="Validform_checktip"></span></td>
		 </tr>
	</table></td>
	</tr>
	 
	</table></td>
 </tr>
</table>
 
<div style="text-align:center;width:98%;padding-top:10px;">
	<input id="save" class="btnmy btn-size" type="button" value="存草稿" onclick="saveDraft();"/>
	<input id="insur" class="subBtnmy btn-size" type="button" value="提交核保" onclick="insurance();"/>
	<input id="pay" class="subBtnmy btn-size" type="button" value="在线支付" onclick="doPay();" disabled/>
	<c:if test="${isDraft eq true }">
	<input id="back" class="btnmy btn-size" type="button" value="关闭" onclick="closeCurrent('tab_${policyMainPage.draftId}');"/>
	</c:if>
	<c:if test="${isDraft eq false }">
	<input id="back" class="btnmy btn-size" type="button" value="关闭" onclick="closeCurrent('tab_${policyMainPage.id}');"/>
	</c:if>
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
<input id="insuranceObj" name="insuranceObj" type="hidden" />
<input id="payUrl" name="payUrl" type="hidden" />
<input id="payResult" name="payResult" type="hidden" />
<input id="insResult" name="insResult" type="hidden" value="1"/>
<input id="isPaperPolicy" name="isPaperPolicy" type="hidden" value="${policyMainPage.isPaperPolicy}"/>
<input id="isPaperInvoice" name="isPaperInvoice" type="hidden" value="${policyMainPage.isPaperInvoice}"/>
<input id="isDraft" name="isDraft" type="hidden" value="${isDraft}"/>
</t:formvalid>

<%@include file="policyPromptDiv.jsp"%>
<%@include file="policyPayiFrame.jsp"%>
</body>
</html>
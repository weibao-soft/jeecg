<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增保单</title>
<t:base type="jquery,easyui,tools,autocomplete,DatePicker"></t:base>
<SCRIPT type="text/javascript">
$(document).ready(function(){
　　　$("#invoiceType").change(function() {
　　　　　　var value=$(this).children('option:selected').val();
        if(value=='3') {
        	$("#invoiceTr").css("display", "none");
        	add("开具增值税专用发票","policyMainController.do?goAdd");
        } else if(value=='2') {
        	$("#invoiceTr").css("display", "table-row");
        } else if(value=='1') {
        	$("#invoiceTr").css("display", "none");
        }
　　　});
});

function addPolicy() {
	var trbody = "<tr name='policytr'>";
	trbody += "<td><div style='text-align:right;width:120px;'>车牌号：<BR/>（新车填写：未上牌）</div></td>";
	trbody += "<td><input type='text' name='plateNo'></td>";
	trbody += "<td><span style='color: red;'>*</span>车架号 </td>";
	trbody += "<td><input type='text' name='frameNo'></td>";
	trbody += "<td><span style='color: red;'>*</span>发动机号 </td>";
	trbody += "<td><input type='text' name='engineNo'></td>";
	trbody += "<td><input class='btn' type='button' value='删除' onclick='removePolicy(this);'";
	trbody += " style='height:30px;width:100px !important;border-radius:5px'/></td>";
	trbody += "</tr>";
	$("#policy_tabel").find("tbody").append(trbody);
	//$("#policy_tabel").find("tbody").replaceWith(trbody);
}

function removePolicy(obj) {
	$(obj).parents("tr[name='policytr']").remove();
}

function calculateMonths(obj) {
	var year = $(obj).val();
	var months = Number(year * 12);
	//isRealNum(year);
	//alert(months);
	$("#month").text(months);
}

/**
 * 添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function add(title,addurl) {
	var url = setChildUrl();
	createwindowN('开具增值税专用发票', addurl + url,'50%','50%');
}

/**
 * 创建添加或编辑窗口
 * @param title
 * @param addurl
 */
function createwindowN(title,addurl,width,height) {
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}else{
		width = isRealNum(width)?width:700;
		height = isRealNum(height)?height:400;
		width=parseInt(width);
		height=parseInt(height);
	}

	$.dialog({
		content: 'url:'+addurl,
		lock : true,
		zIndex: getzIndex(),
		width:width,
		height:height,
		title:title,
		opacity : 0.3,
		cache:false,
		/*onLoad:function(){
	    	var iframe = this.iframe.contentWindow;
	    	setChildData(iframe);
		},*/
	    ok: function(){
	    	var iframe = this.iframe.contentWindow;
	    	return setParentData(iframe);
	    },
	    okVal: '保存',/*$.i18n.prop('dialog.submit'),*/
	    cancelVal: $.i18n.prop('dialog.close'),
	    cancel: true /*为true等价于function(){}*/
	});
}

function setChildUrl() {
	var param = getParentParam();
	var url = "";
	url = url + "&compName2=" + param.compName2;
	url = url + "&taxpayerNo=" + param.taxpayerNo;
	url = url + "&compAddress=" + param.compAddress;
	url = url + "&compPhone=" + param.compPhone;
	url = url + "&depositBank=" + param.depositBank;
	url = url + "&bankAccount=" + param.bankAccount;
	url = url + "&recipients=" + param.recipients;
	url = url + "&recipientsTel=" + param.recipientsTel;
	url = url + "&reciAddress=" + param.reciAddress;
	
    if(window.console) window.console.log(url);
	return url;
}

function setChildData(iframe) {
	var param = getParentParam();
    
	$("#compName2", iframe.document).val(param.compName2);
	$("#taxpayerNo", iframe.document).val(param.taxpayerNo);
	$("#compAddress", iframe.document).val(param.compAddress);
	$("#compPhone", iframe.document).val(param.compPhone);
	$("#depositBank", iframe.document).val(param.depositBank);
	$("#bankAccount", iframe.document).val(param.bankAccount);
	$("#recipients", iframe.document).val(param.recipients);
	$("#recipientsTel", iframe.document).val(param.recipientsTel);
	$("#reciAddress", iframe.document).val(param.reciAddress);
}

function getParentParam() {
	var param = {};
	param.compName2 = $("#compName2p").val();
	param.taxpayerNo = $("#taxpayerNop").val();
	param.compAddress = $("#compAddressp").val();
	param.compPhone = $("#compPhonep").val();
	param.depositBank = $("#depositBankp").val();
	param.bankAccount = $("#bankAccountp").val();
	param.recipients = $("#recipientsp").val();
	param.recipientsTel = $("#recipientsTelp").val();
	param.reciAddress = $("#reciAddressp").val();
	
	return param;
}

function setParentData(iframe) {
	var param = {};
	param.compName2 = $("#compName2", iframe.document).val();
	param.taxpayerNo = $("#taxpayerNo", iframe.document).val();
	param.compAddress = $("#compAddress", iframe.document).val();
	param.compPhone = $("#compPhone", iframe.document).val();
	param.depositBank = $("#depositBank", iframe.document).val();
	param.bankAccount = $("#bankAccount", iframe.document).val();
	param.recipients = $("#recipients", iframe.document).val();
	param.recipientsTel = $("#recipientsTel", iframe.document).val();
	param.reciAddress = $("#reciAddress", iframe.document).val();

	if(!validParam(param, iframe)) {
		return false;
	}
	parseData(JSON.stringify(param));
	return true;
}

function parseData(info) {
    if(window.console) console.log(info);
    if (info) {
        info = JSON.parse(info);
        $("#compName2p").val(info.compName2);
        $("#taxpayerNop").val(info.taxpayerNo);
        $("#compAddressp").val(info.compAddress);
        $("#compPhonep").val(info.compPhone);
        $("#depositBankp").val(info.depositBank);
        $("#bankAccountp").val(info.bankAccount);
        $("#recipientsp").val(info.recipients);
        $("#recipientsTelp").val(info.recipientsTel);
        $("#reciAddressp").val(info.reciAddress);
    }
}

function validParam(param, iframe) {
	if(param.compName2 == null || param.compName2 == "") {
		iframe.$.messager.alert('提示','请填写公司名称!','info');
		return false;
	}
	if(param.taxpayerNo == null || param.taxpayerNo == "") {
		iframe.$.messager.alert('提示','请填写纳税人识别号!','info');
		return false;
	}
	if(param.compAddress == null || param.compAddress == "") {
		iframe.$.messager.alert('提示','请填写公司地址!','info');
		return false;
	}
	if(param.compPhone == null || param.compPhone == "") {
		iframe.$.messager.alert('提示','请填写公司电话!','info');
		return false;
	}
	if(param.depositBank == null || param.depositBank == "") {
		iframe.$.messager.alert('提示','请填写开户行!','info');
		return false;
	}
	if(param.bankAccount == null || param.bankAccount == "") {
		iframe.$.messager.alert('提示','请填写银行账号!','info');
		return false;
	}
	if(param.recipients == null || param.recipients == "") {
		iframe.$.messager.alert('提示','请填写收件人!','info');
		return false;
	}
	if(param.recipientsTel == null || param.recipientsTel == "") {
		iframe.$.messager.alert('提示','请填写收件人电话!','info');
		return false;
	}
	if(param.reciAddress == null || param.reciAddress == "") {
		iframe.$.messager.alert('提示','请填写发票收件地址!','info');
		return false;
	}
	return true;
}

function validData() {
	var frameNos = $("[name=frameNo]");
	var engineNos = $("[name=engineNo]");
	for(var i = 0; i < frameNos.length; i++) {
	    //if(window.console) console.log($(frameNos[i]).val());
		if($(frameNos[i]).val() == null || $(frameNos[i]).val() == "") {
			$.messager.alert('提示','车架号不能为空!','info');
			return false;
		}
	}
	for(var i = 0; i < engineNos.length; i++) {
	    //if(window.console) console.log($(engineNos[i]).val());
		if($(engineNos[i]).val() == null || $(engineNos[i]).val() == "") {
			$.messager.alert('提示','发动机号不能为空!','info');
			return false;
		}
	}
	var compName = $("#compName").val();
	var orgCode = $("#orgCode").val();
	var contactName = $("#contactName").val();
	var policyMobile = $("#policyMobile").val();
	var compName3 = $("#compName3").val();
	var orgCode3 = $("#orgCode3").val();
	if(compName == null || compName == "") {
		$.messager.alert('提示','“投保人”单位名称不能为空!','info');
		return false;
	}
	if(orgCode == null || orgCode == "") {
		$.messager.alert('提示','“投保人”组织机构代码不能为空!','info');
		return false;
	}
	if(contactName == null || contactName == "") {
		$.messager.alert('提示','联系人名称不能为空!','info');
		return false;
	}
	if(policyMobile == null || policyMobile == "") {
		$.messager.alert('提示','手机不能为空!','info');
		return false;
	}
	if(compName3 == null || compName3 == "") {
		$.messager.alert('提示','“被投保人”单位名称不能为空!','info');
		return false;
	}
	if(orgCode3 == null || orgCode3 == "") {
		$.messager.alert('提示','“被投保人”组织机构代码不能为空!','info');
		return false;
	}
	return true;
}

function submitData() {
	if(!validData()) {
		return false;
	}

	var invoice=$("#invoiceType").val();
	if(invoice=='2') {
		var taxpayerNo = $("#taxpayerNo2").val();
        $("#taxpayerNop").val(taxpayerNo);
	}
	
	$("#formobj").submit();
}
  </SCRIPT>
</head>
<body>

<t:formvalid formid="formobj" dialog="false" layout="table" tiptype="1" action="policyMainController.do?doAdd">
	 <fieldset class="step" style="width:100%;padding-bottom: 20px;">
	 <legend>保单投保</legend>
	 <table cellpadding="0" cellspacing="1" class="formtable" width="1200">
	 
	 <tr><td style="width:10%"></td><td style="width:90%">
		 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
		 <tr><td style="width:15%">
				<label class="Validform_label"> 方案保障  </label> 
		 </td><td style="width:85%">
				<select name="plan1" id="plan1" style="width:400px;">                                                                                                   
				<option value="1">保障：累计600万，每次限额200万 保费:1000元</option>
				<option value="2">保障：累计600万，每次限额300万 保费:1300元</option>
				<option value="3">保障：累计200万，每次限额200万 保费:900元</option>
				<option value="4">保障：累计300万，每次限额300万 保费:1200元</option>
				<option value="5">保障：累计600万，每次限额200万，计划B 保费:1200元</option>
				<option value="6">保障：累计600万，每次限额300万，计划B 保费:1500元</option>
				<option value="7">保障：累计200万，每次限额200万,计划B 保费:1100元</option>
				<option value="8">保障：累计300万，每次限额300万，计划B 保费:1400元</option>
				</select>
				<span class="Validform_checktip"></span></td></tr>
		 </table>
	 </td></tr>
	 
	 <tr><td style="width:10%">投保内容</td><td style="width:90%">
		 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
		 
		 <tr><td>
		 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
			 <tr><td style="width:15%">
					<label class="Validform_label"> 车辆信息： </label> 
			 </td><td style="width:85%">
					<table name="policy_tabel" id="policy_tabel">
					<tbody>
						<tr>
						<td><div style="text-align:right;width:120px;">车牌号：<BR/>（新车填写：未上牌）</div></td>
						<td><input type="text" name="plateNo"></td>
						<td><span style="color: red;">*</span>车架号 </td>
						<td><input type="text" name="frameNo"></td>
						<td><span style="color: red;">*</span>发动机号 </td>
						<td><input type="text" name="engineNo"></td>
						<td><input class="btn" type="button" value="新增 " onclick="addPolicy();" style="height:30px;width:100px !important;border-radius:5px"/></td>
						</tr>
					</tbody>
					</table>
					<span class="Validform_checktip"></span>
			 </td></tr>
		 </table>
		 </td></tr>
		 
		 <tr><td>
		 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
			 <tr><td style="width:15%"><label class="Validform_label"> 保险期间： </label>
			 </td><td style="width:85%">
			 自 <input type="text" name="start" id="start" style="width:100px;" /> <input type="text" name="shour" id="shour" style="width:20px;" value="00" disabled/><img onclick="WdatePicker({el:'start'})" 
			 src="plug-in/easyui/themes/black/images/datebox_arrow.png" style="opacity: 0.6;height:17px;width:17px;" align="absmiddle"> 起 至 <input type="text" name="end" id="end" 
			 style="width:100px;" /> <input type="text" name="ehour" id="ehour" style="width:20px;" value="24" disabled/><img onclick="WdatePicker({el:'end'})" 
			 src="plug-in/easyui/themes/black/images/datebox_arrow.png" style="opacity: 0.6;height:17px;width:17px;" align="absmiddle"> 止，连续 <input type="text" 
			 name="year" id="year" style="width:100px;" value="1" onblur="calculateMonths(this);">年 共<label id="month">12</label>月 </td></tr>
		 </table>
		 </td></tr>
		 
		 <tr><td>
		 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
			 <tr><td style="width:15%"><label class="Validform_label"> 投保人： </label></td>
			 <td style="width:35%"></td><td style="width:15%"></td><td style="width:35%"></td></tr>
			 <tr><td style="width:15%">投保人性质</td>
			 <td style="width:35%"><select name="holderNature" id="holderNature" style="width:200px;">
					<option value="1">团体投保</option>
					</select><span class="Validform_checktip"></span></td>
			 <td style="width:15%"></td><td style="width:35%"></td></tr>
			 <tr><td><span style="color: red;">*</span>单位名称</td>
			 <td><input type="text" name="compName" id="compName" style="width:200px;" /></td>
			 <td><span style="color: red;">*</span>组织机构代码<BR/>(统一社会信用代码) </td>
			 <td><input type="text" name="orgCode" id="orgCode" style="width:200px;" /></td></tr>
			 <tr><td><span style="color: red;">*</span>单位性质</td>
			 <td><select name="compNature" id="compNature" style="width:200px;">
					<option value="1">企业</option>
					<option value="1">政府机关</option>
					<option value="1">事业机关</option>
					<option value="1">其他</option>
					</select></td>
			 <td><span style="color: red;">*</span>行业类别</td>
			 <td><select name="industryType" id="industryType" style="width:200px;">
					<option value="1">交通运输设备制造业</option>
					<option value="1">邮政业</option>
					<option value="1">国家机构</option>
					<option value="1">其他</option>
					</select></td></tr>
			 <tr><td><span style="color: red;">*</span>联系人姓名</td>
			 <td><input type="text" name="contactName" id="contactName" style="width:200px;" /></td>
			 <td><span style="color: red;">*</span>手机</td>
			 <td><input type="text" name="policyMobile" id="policyMobile" style="width:200px;" /></td></tr>
			 <tr><td>发票类型</td><td>
			 <select name="invoiceType" id="invoiceType" style="width:200px;">
					<option value="1">不开发票</option>
					<option value="2">增值税普通发票</option>
					<option value="3">增值税专用发票</option>
					</select><span class="Validform_checktip"></span>
			 </td><td></td><td></td></tr>
			 <tr id="invoiceTr" style="display: none;">
			 <td>纳税人识别号</td>
			 <td><input type="text" name="taxpayerNo2" id="taxpayerNo2" style="width:200px;" /></td>
			 <td>手机号</td>
			 <td><input type="text" name="receiverMobile" id="receiverMobile" style="width:200px;" /></td></tr>
		 </table>
		 </td></tr>
		 
		 <tr><td>
		 <table cellpadding="0" cellspacing="1" class="formtable" width="100%">
			 <tr>
			 <td style="width:15%"><label class="Validform_label"> 被保人： </label> <input id="d12" type="checkbox" />同投保人</td>
			 <td style="width:35%"></td><td style="width:15%"></td><td style="width:35%"></td>
			 </tr>
			 <tr>
			 <td><span style="color: red;">*</span>单位名称 </td>
			 <td><input type="text" name="compName3" id="compName3" style="width:200px;" /></td>
			 <td><span style="color: red;">*</span>组织机构代码<BR/>(统一社会信用代码)</td>
			 <td><input type="text" name="orgCode3" id="orgCode3" style="width:200px;" />
			 <span class="Validform_checktip"></span></td>
			 </tr>
		 </table>
		 </td></tr>
		 
		 </table>
	 </td></tr>
	 </table>
	<div style="text-align:center;width:1200px;padding-top:10px;">
	<input class="btn" type="button" value="提交" onclick="submitData();" style="height:30px;width:100px !important;border-radius:5px">
	</div>
		
	</fieldset>

<input id="invoiceObj" name="invoiceObj" type="hidden" />
<input id="compName2p" name="compName2" type="hidden" />
<input id="taxpayerNop" name="taxpayerNo" type="hidden" />
<input id="compAddressp" name="compAddress" type="hidden" />
<input id="compPhonep" name="compPhone" type="hidden" />
<input id="depositBankp" name="depositBank" type="hidden" />
<input id="bankAccountp" name="bankAccount" type="hidden" />
<input id="recipientsp" name="recipients" type="hidden" />
<input id="recipientsTelp" name="recipientsTel" type="hidden" />
<input id="reciAddressp" name="reciAddress" type="hidden" />
</t:formvalid>

</body>
</html>
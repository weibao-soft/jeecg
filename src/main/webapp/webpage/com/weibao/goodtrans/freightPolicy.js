
$(document).ready(function() {
	$("input[name='holderPartType']").change(function() {
		//debugger;
		var value=$("input[name='holderPartType']:checked").val();
        if(value=='GR') {
        	$("[name='holderGRTr']").css("display", "table-row");
        	$("[name='holderGRProp']").css("display", "table-row");
        	$("[name='holderQYProp']").css("display", "none");
        	$("#holderNation").css("display", "table-row");
        } else if(value=='QY') {
        	$("[name='holderGRTr']").css("display", "none");
        	$("[name='holderQYProp']").css("display", "table-row");
        	$("[name='holderGRProp']").css("display", "none");
        	$("#holderNation").css("display", "none");
        }
	});
	$("input[name='insuredPartType']").change(function() {
		var value=$("input[name='insuredPartType']:checked").val();
        if(value=='GR') {
        	$("[name='insuredGRTr']").css("display", "table-row");
        	$("[name='insuredGRProp']").css("display", "table-row");
        	$("[name='insuredQYProp']").css("display", "none");
        	$("#insuredNation").css("display", "table-row");
        } else if(value=='QY') {
        	$("[name='insuredGRTr']").css("display", "none");
        	$("[name='insuredQYProp']").css("display", "table-row");
        	$("[name='insuredGRProp']").css("display", "none");
        	$("#insuredNation").css("display", "none");
        }
	});
	$("#check1").click(function() {
	    var value=$(this).is(':checked');
        if(value) {
        	$("#insuredTr").css("display", "none");
        } else {
        	$("#insuredTr").css("display", "table-row");
        }
	});
});


function editablePolicy() {
	$("#holderName").editableSelect({
        effects: 'slide',
        bg_iframe: false,
        case_sensitive: false,
        items_then_scroll: 10,
        isFilter:false,
        onSelect: function(list_item) {
            var holderId=$(list_item[0]).attr("data-id");
            //$("#holderOrgCode").val(holderId);
        	getHolderById(holderId);
        }
    });
	$("#insuredName").editableSelect({
        effects: 'slide',
        bg_iframe: false,
        case_sensitive: false,
        items_then_scroll: 10,
        isFilter:false,
        onSelect: function(list_item) {
            var holderId=$(list_item[0]).attr("data-id");
            //$("#holderOrgCode").val(holderId);
        	getHolderById(holderId);
        }
    });
}

//显示弹出层
function openDiv(payUrl) {
  var frameObj=document.getElementById("payiFrame");
  frameObj.src=payUrl;
  $("#payDiv").show();
  window.Utils.showLoading();
}
//公共支付函数：参数 params为 Json类型
function ajaxPay(url, params, tabId, mainTabId) {
$.ajax({
    url: url,
    type: "POST",
    data: params,
    dataType: "json",
    error: function () {
        layer.alert("服务器异常");
    },
    success: function (data) {
  	    var result = data.obj;
        //if(console) console.log("ajaxReturn == ", data);
        $("#payResult").val("0");
        if (data.success) {
		    //layer.msg(data.msg, {icon:6});
            var payUrl = result.data;
            //var payUrl = "https://devyun.guorenpcic.com/paycenter/?orderId=23a2e077d1e4fd19a61&amp;code=&amp;payOrderNo=js02&amp;platform=pc";
            if(console) console.log("payUrl == ", payUrl);
      	    $("#payObj").val(result);
      	    $("#payUrl").val(payUrl);
      	    $("#tabId").val(tabId);
      	    $("#mainTabId").val(mainTabId);
      	    
      		//弹出层的方式打开支付页面，打开的不是浏览器窗口，只是显示了一个层
      	    openDiv(payUrl);
        } else {
        	if(result != null && result != "") {
        		failureCallback(result);
        	}
      	    
            layer.alert(data.msg);
        }
        $("#pay").attr("disabled", false);
        window.Utils.closeLoading();
        return false;
    }
});
}
//获取表单提交数据
function getSubmitParam() {
	var param = {};
	param.planId = document.getElementById("planId").value;

	var holderPartTypes = document.getElementsByName("holderPartType");
	for(var i = 0; i < holderPartTypes.length; i++) {
		if(holderPartTypes[i].checked) {
			param.holderPartType = holderPartTypes[i].value;
		}
	}
	param.holderName = document.getElementById("holderName").value;
	param.holderCertNo = document.getElementById("holderCertNo").value;
	param.holderCertType = document.getElementById("holderCertType").value;
	param.holderValidity = document.getElementById("holderValidity").value;
	param.holderNation = document.getElementById("holderNation").value;
	param.holderAddress = document.getElementById("holderAddress").value;
	param.holderCtatName = document.getElementById("holderCtatName").value;
	param.holderCtatEmail = document.getElementById("holderCtatEmail").value;
	param.holderCtatPhone = document.getElementById("holderCtatPhone").value;
	param.holderCtatMobile = document.getElementById("holderCtatMobile").value;
	param.holderCtatFax = document.getElementById("holderCtatFax").value;
	if(param.holderPartType == 'GR') {
		param.holderSex = document.getElementById("holderSex").value;
		param.holderProfession = document.getElementById("holderProfession").value;
		param.holderPostal = document.getElementById("holderPostal").value;
	}

	var insuredPartTypes = document.getElementsByName("insuredPartType");
	for(var i = 0; i < insuredPartTypes.length; i++) {
		if(insuredPartTypes[i].checked) {
			param.insuredPartType = insuredPartTypes[i].value;
		}
	}
	param.insuredName = document.getElementById("insuredName").value;
	param.insuredCertNo = document.getElementById("insuredCertNo").value;
	param.insuredCertType = document.getElementById("insuredCertType").value;
	param.insuredValidity = document.getElementById("insuredValidity").value;
	param.insuredNation = document.getElementById("insuredNation").value;
	param.insuredAddress = document.getElementById("insuredAddress").value;
	param.insuredCtatName = document.getElementById("insuredCtatName").value;
	param.insuredCtatEmail = document.getElementById("insuredCtatEmail").value;
	param.insuredCtatPhone = document.getElementById("insuredCtatPhone").value;
	param.insuredCtatMobile = document.getElementById("insuredCtatMobile").value;
	param.insuredCtatFax = document.getElementById("insuredCtatFax").value;
	if(param.insuredPartType == 'GR') {
		param.insuredSex = document.getElementById("insuredSex").value;
		param.insuredProfession = document.getElementById("insuredProfession").value;
		param.insuredPostal = document.getElementById("insuredPostal").value;
	}

	param.corporate = document.getElementById("corporate").value;
	param.corporCertNo = document.getElementById("corporCertNo").value;
	param.corporCertName = document.getElementById("corporCertName").value;
	param.corporValidity = document.getElementById("corporValidity").value;
	param.beneficiary = document.getElementById("beneficiary").value;
	param.recipients = document.getElementById("recipients").value;
	param.recipientsTel = document.getElementById("recipientsTel").value;
	param.reciAddress = document.getElementById("reciAddress").value;
	
	param.cargoStartDate = document.getElementById("start").value;
	param.cargoEndDate = document.getElementById("end").value;
	param.vehiclePlateNo = document.getElementById("vehiclePlateNo").value;
	param.trailerPlateNo = document.getElementById("trailerPlateNo").value;
	param.vehicleFrameNo = document.getElementById("vehicleFrameNo").value;
	param.allInsuredAmount = document.getElementById("allInsuredAmount").value;
	param.allPremium = document.getElementById("allPremium").value;
	param.status = document.getElementById("status").value;
	//var goods = {};
	param.goodsName = document.getElementsByName("goodsName")[0].value;
	param.category = document.getElementsByName("category")[0].value;
	param.countOrWeight = document.getElementsByName("countOrWeight")[0].value;
	param.insuredAmount = document.getElementsByName("insuredAmount")[0].value;
	param.premiumRate = document.getElementsByName("premiumRate")[0].value;

    var flag=$("#check1").is(':checked');
	debugger;
    if(flag) {
    	getInsuredParam(param);
    }
    //if(console) console.log("param == ", param);
	return param;
}
function getUpdateParam(param) {
	param.id = document.getElementById("id").value;
	param.userId = document.getElementById("userId").value;
	param.payStatus = document.getElementById("payStatus").value;
	param.createTime = document.getElementById("createTime").value;
	param.draft = document.getElementById("isDraft").value;
	
	return param;
}
function getSelectParam(param) {
	param.planName = $("#planId").children('option:selected').text();
	
	return param;
}
function getInsuredParam(param) {
	param.insuredPartType = param.holderPartType;
	param.insuredName = param.holderName;
	param.insuredCertNo = param.holderCertNo;
	param.insuredCertType = param.holderCertType;
	param.insuredValidity = param.holderValidity;
	param.insuredNation = param.holderNation;
	param.insuredAddress = param.holderAddress;
	param.insuredCtatName = param.holderCtatName;
	param.insuredCtatEmail = param.holderCtatEmail;
	param.insuredCtatPhone = param.holderCtatPhone;
	param.insuredCtatMobile = param.holderCtatMobile;
	param.insuredCtatFax = param.holderCtatFax;
	if(param.insuredPartType == 'GR') {
		param.insuredSex = param.holderSex;
		param.insuredProfession = param.holderProfession;
		param.insuredPostal = param.holderPostal;
	}
	
	return param;
}

//校验主页面上的数据
function validData() {
    var flag=$("#check1").is(':checked');
	debugger;
	if(!validHolder()) {
		return false;
	}
	if(!flag && !validInsured()) {
		return false;
	}
	if(!validCorporate()) {
		return false;
	}
	if(!validPolicy()) {
		return false;
	}
	return true;
}
//校验投保人
function validHolder() {
	var holderPartTypes = document.getElementsByName("holderPartType");
	var holderPartType = 'QY';
	for(var i = 0; i < holderPartTypes.length; i++) {
		if(holderPartTypes[i].checked) {
			holderPartType = holderPartTypes[i].value;
		}
	}
	var holderName = document.getElementById("holderName").value;
	var holderCertNo = document.getElementById("holderCertNo").value;
	var holderCertType = document.getElementById("holderCertType").value;
	var holderValidity = document.getElementById("holderValidity").value;
	
	var holderSex = document.getElementById("holderSex").value;
	var holderProfession = document.getElementById("holderProfession").value;
	var holderNation = document.getElementById("holderNation").value;
	var holderPostal = document.getElementById("holderPostal").value;
	var holderAddress = document.getElementById("holderAddress").value;
	var holderCtatName = document.getElementById("holderCtatName").value;
	var holderCtatEmail = document.getElementById("holderCtatEmail").value;
	var holderCtatPhone = document.getElementById("holderCtatPhone").value;
	var holderCtatMobile = document.getElementById("holderCtatMobile").value;
	var holderCtatFax = document.getElementById("holderCtatFax").value;
	if(holderName == null || holderName == "") {
		$.messager.alert('提示','“投保人”名称不能为空!','info');
		return false;
	}
	if(holderCertType == null || holderCertType == "") {
		$.messager.alert('提示','“投保人”证件类型不能为空!','info');
		return false;
	}
	if(holderCertNo == null || holderCertNo == "") {
		$.messager.alert('提示','“投保人”证件号码不能为空!','info');
		return false;
	}
	if(holderValidity == null || holderValidity == "") {
		$.messager.alert('提示','“投保人”证件有效期不能为空!','info');
		return false;
	}
	if(holderPostal == null || holderPostal == "") {
		$.messager.alert('提示','“投保人”邮政编码不能为空!','info');
		return false;
	}
	if(holderAddress == null || holderAddress == "") {
		$.messager.alert('提示','“投保人”通讯地址不能为空!','info');
		return false;
	}
	if(holderCtatName == null || holderCtatName == "") {
		$.messager.alert('提示','“投保人”联系人名称不能为空!','info');
		return false;
	}
	if(holderCtatEmail == null || holderCtatEmail == "") {
		$.messager.alert('提示','“投保人”Email不能为空!','info');
		return false;
	}
	if(holderCtatPhone == null || holderCtatPhone == "") {
		$.messager.alert('提示','“投保人”办公电话不能为空!','info');
		return false;
	}
	if(holderCtatMobile == null || holderCtatMobile == "") {
		$.messager.alert('提示','“投保人”手机号码不能为空!','info');
		return false;
	}
	if(holderCtatFax == null || holderCtatFax == "") {
		$.messager.alert('提示','“投保人”传真不能为空!','info');
		return false;
	}
	if(holderPartType == 'GR') {
		if(holderSex == null || holderSex == "") {
			$.messager.alert('提示','“投保人”性别不能为空!','info');
			return false;
		}
		if(holderProfession == null || holderProfession == "") {
			$.messager.alert('提示','“投保人”职业不能为空!','info');
			return false;
		}
		if(holderNation == null || holderNation == "") {
			$.messager.alert('提示','“投保人”国籍不能为空!','info');
			return false;
		}
	}
	return true;
}
//校验被保人
function validInsured() {
	var insuredPartTypes = document.getElementsByName("insuredPartType");
	var insuredPartType = 'QY';
	for(var i = 0; i < insuredPartTypes.length; i++) {
		if(insuredPartTypes[i].checked) {
			insuredPartType = insuredPartTypes[i].value;
		}
	}
	var insuredName = document.getElementById("insuredName").value;
	var insuredCertNo = document.getElementById("insuredCertNo").value;
	var insuredCertType = document.getElementById("insuredCertType").value;
	var insuredValidity = document.getElementById("insuredValidity").value;
	
	var insuredSex = document.getElementById("insuredSex").value;
	var insuredProfession = document.getElementById("insuredProfession").value;
	var insuredNation = document.getElementById("insuredNation").value;
	var insuredPostal = document.getElementById("insuredPostal").value;
	var insuredAddress = document.getElementById("insuredAddress").value;
	var insuredCtatName = document.getElementById("insuredCtatName").value;
	var insuredCtatEmail = document.getElementById("insuredCtatEmail").value;
	var insuredCtatPhone = document.getElementById("insuredCtatPhone").value;
	var insuredCtatMobile = document.getElementById("insuredCtatMobile").value;
	var insuredCtatFax = document.getElementById("insuredCtatFax").value;
	if(insuredName == null || insuredName == "") {
		$.messager.alert('提示','“被保人”名称不能为空!','info');
		return false;
	}
	if(insuredCertType == null || insuredCertType == "") {
		$.messager.alert('提示','“被保人”证件类型不能为空!','info');
		return false;
	}
	if(insuredCertNo == null || insuredCertNo == "") {
		$.messager.alert('提示','“被保人”证件号码不能为空!','info');
		return false;
	}
	if(insuredValidity == null || insuredValidity == "") {
		$.messager.alert('提示','“被保人”证件有效期不能为空!','info');
		return false;
	}
	if(insuredPostal == null || insuredPostal == "") {
		$.messager.alert('提示','“被保人”邮政编码不能为空!','info');
		return false;
	}
	if(insuredAddress == null || insuredAddress == "") {
		$.messager.alert('提示','“被保人”通讯地址不能为空!','info');
		return false;
	}
	if(insuredCtatName == null || insuredCtatName == "") {
		$.messager.alert('提示','“被保人”联系人名称不能为空!','info');
		return false;
	}
	if(insuredCtatEmail == null || insuredCtatEmail == "") {
		$.messager.alert('提示','“被保人”Email不能为空!','info');
		return false;
	}
	if(insuredCtatPhone == null || insuredCtatPhone == "") {
		$.messager.alert('提示','“被保人”办公电话不能为空!','info');
		return false;
	}
	if(insuredCtatMobile == null || insuredCtatMobile == "") {
		$.messager.alert('提示','“被保人”手机号码不能为空!','info');
		return false;
	}
	if(insuredCtatFax == null || insuredCtatFax == "") {
		$.messager.alert('提示','“被保人”传真不能为空!','info');
		return false;
	}
	if(insuredPartType == 'GR') {
		if(insuredSex == null || insuredSex == "") {
			$.messager.alert('提示','“被保人”性别不能为空!','info');
			return false;
		}
		if(insuredProfession == null || insuredProfession == "") {
			$.messager.alert('提示','“被保人”职业不能为空!','info');
			return false;
		}
		if(insuredNation == null || insuredNation == "") {
			$.messager.alert('提示','“被保人”国籍不能为空!','info');
			return false;
		}
	}
	return true;
}
//校验法人信息
function validCorporate() {
	var corporate = document.getElementById("corporate").value;
	var corporCertNo = document.getElementById("corporCertNo").value;
	var corporCertName = document.getElementById("corporCertName").value;
	var corporValidity = document.getElementById("corporValidity").value;
	var beneficiary = document.getElementById("beneficiary").value;
	if(corporate == null || corporate == "") {
		$.messager.alert('提示','“法人”姓名不能为空!','info');
		return false;
	}
	if(corporCertName == null || corporCertName == "") {
		$.messager.alert('提示','“法人”证件类型不能为空!','info');
		return false;
	}
	if(corporCertNo == null || corporCertNo == "") {
		$.messager.alert('提示','“法人”证件号码不能为空!','info');
		return false;
	}
	if(corporValidity == null || corporValidity == "") {
		$.messager.alert('提示','“法人”证件有效期不能为空!','info');
		return false;
	}
	if(beneficiary == null || beneficiary == "") {
		$.messager.alert('提示','“权益人”姓名不能为空!','info');
		return false;
	}
	return true;
}
//校验保单信息
function validPolicy() {
	var startDate = document.getElementById("start").value;
	var endDate = document.getElementById("end").value;
	var vehiclePlateNo = document.getElementById("vehiclePlateNo").value;
	var trailerPlateNo = document.getElementById("trailerPlateNo").value;
	var vehicleFrameNo = document.getElementById("vehicleFrameNo").value;

	var goodsName = document.getElementsByName("goodsName")[0].value;
	var category = document.getElementsByName("category")[0].value;
	var countOrWeight = document.getElementsByName("countOrWeight")[0].value;
	var insuredAmount = document.getElementsByName("insuredAmount")[0].value;
	var premiumRate = document.getElementsByName("premiumRate")[0].value;

	var allInsuredAmount = document.getElementById("allInsuredAmount").value;
	var allPremium = document.getElementById("allPremium").value;
	if(startDate == null || startDate == "") {
		$.messager.alert('提示','请填写保险开始时间!','info');
		return false;
	}
	if(endDate == null || endDate == "") {
		$.messager.alert('提示','请填写保险结束时间!','info');
		return false;
	}
	if(vehiclePlateNo == null || vehiclePlateNo == "") {
		$.messager.alert('提示','请填写车牌号!','info');
		return false;
	}
	if(trailerPlateNo == null || trailerPlateNo == "") {
		$.messager.alert('提示','请填写挂车牌号!','info');
		return false;
	}
	if(vehicleFrameNo == null || vehicleFrameNo == "") {
		$.messager.alert('提示','请填写车架号!','info');
		return false;
	}

	if(goodsName == null || goodsName == "") {
		$.messager.alert('提示','请填写货物名称!','info');
		return false;
	}
	if(category == null || category == "") {
		$.messager.alert('提示','请填写承保险别!','info');
		return false;
	}
	if(countOrWeight == null || countOrWeight == "") {
		$.messager.alert('提示','请填写件数/重量!','info');
		return false;
	}
	if(insuredAmount == null || insuredAmount == "") {
		$.messager.alert('提示','请填写保险金额!','info');
		return false;
	}
	if(premiumRate == null || premiumRate == "") {
		$.messager.alert('提示','请填写费率!','info');
		return false;
	}

	if(allInsuredAmount == null || allInsuredAmount == "") {
		$.messager.alert('提示','请填总保额!','info');
		return false;
	}
	if(allPremium == null || allPremium == "") {
		$.messager.alert('提示','请填写保费!','info');
		return false;
	}
	return true;
}
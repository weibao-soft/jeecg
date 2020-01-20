$(document).ready(function() {
	//初始化页面
	initPageUI();
	//绑定事件
	bindEvents();
});
//初始化页面
function initPageUI() {
    $("#holderCertType").css("width", "200px");
    $("#insuredCertType").css("width", "200px");
}
//绑定事件
function bindEvents() {
	$("input[name='holderPartType']").change(function() {
		debugger;
		var value=$("input[name='holderPartType']:checked").val();
        if(value=='GR') {
        	$("[name='holderGRTr']").css("display", "table-row");
        	$("[name='holderGRTr']").css("display", "table-row");
        	$("[name='holderQYTr']").css("display", "none");
        	$("[name='holderQYTr']").css("display", "none");
        	$("#holderCertLbl").css("display", "none");
        	$("#holderCertType").css("display", "none");
        } else if(value=='QY') {
        	$("[name='holderQYTr']").css("display", "table-row");
        	$("[name='holderQYTr']").css("display", "table-row");
        	$("#holderCertLbl").css("display", "table-row");
        	$("#holderCertType").css("display", "table-row");
        	$("[name='holderGRTr']").css("display", "none");
        	$("[name='holderGRTr']").css("display", "none");
        }
	});
	$("input[name='insuredPartType']").change(function() {
		var value=$("input[name='insuredPartType']:checked").val();
        if(value=='GR') {
        	$("[name='insuredGRTr']").css("display", "table-row");
        	$("[name='insuredGRTr']").css("display", "table-row");
        	$("[name='insuredQYTr']").css("display", "none");
        	$("[name='insuredQYTr']").css("display", "none");
        	$("#insuredCertLbl").css("display", "none");
        	$("#insuredCertType").css("display", "none");
        } else if(value=='QY') {
        	$("[name='insuredQYTr']").css("display", "table-row");
        	$("[name='insuredQYTr']").css("display", "table-row");
        	$("#insuredCertLbl").css("display", "table-row");
        	$("#insuredCertType").css("display", "table-row");
        	$("[name='insuredGRTr']").css("display", "none");
        	$("[name='insuredGRTr']").css("display", "none");
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
}

//将下拉框修改为可编辑的下拉框
function editablePolicy() {
	$("#holderCompName").editableSelect({
      effects: 'slide',
      bg_iframe: false,
      case_sensitive: false,
      items_then_scroll: 10,
      isFilter:false,
      onSelect: function(list_item) {
          var holderCode=$(list_item[0]).attr("data-code");
          $("#holderOrgCode").val(holderCode);
      }
  });
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
	        var backobj = data.back;
	        //if(console) console.log("ajaxReturn == ", data);
	        $("#payResult").val("0");
	    	if(backobj != null && backobj != "") {
	    		failureCallback(backobj);
	    	}
	        if (data.success) {
			    //layer.msg(data.msg, {icon:6});
	            var payUrl = result.payUrl;
	            //var payUrl = "https://devyun.guorenpcic.com/paycenter/?orderId=23a2e077d1e4fd19a61&amp;code=&amp;payOrderNo=js02&amp;platform=pc";
	            if(console) console.log("payUrl == ", payUrl);
	            result = JSON.stringify(result);
	      	    $("#payUrl").val(payUrl);
	      	    $("#tabId").val(tabId);
	      	    $("#mainTabId").val(mainTabId);
	      	    
	      		//弹出层的方式打开支付页面，打开的不是浏览器窗口，只是显示了一个层
	      	    openDiv(payUrl);
	        } else {
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
	if(param.insuredPartType == 'QY') {
		param.holderCtatName = document.getElementById("holderCtatName").value;
		param.holderCtatMobile = document.getElementById("holderCtatMobile").value;
	} else if(param.holderPartType == 'GR') {
		param.holderMobileNo = document.getElementById("holderMobileNo").value;
		//param.holderSex = document.getElementById("holderSex").value;
		//param.holderBirthday = document.getElementById("holderBirthday").value;
		//param.holderEmail = document.getElementById("holderEmail").value;
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
	param.phRelToIns = document.getElementById("phRelToIns").value;
	if(param.insuredPartType == 'QY') {
		param.insuredCtatName = document.getElementById("insuredCtatName").value;
		param.insuredCtatMobile = document.getElementById("insuredCtatMobile").value;
	} else if(param.insuredPartType == 'GR') {
		//param.insuredSex = document.getElementById("insuredSex").value;
	}

	param.recipients = document.getElementById("recipients").value;
	param.recipientsTel = document.getElementById("recipientsTel").value;
	param.reciAddress = document.getElementById("reciAddress").value;
	
	param.cargoStartDate = document.getElementById("start").value;
	param.cargoEndDate = document.getElementById("end").value;
	param.vehiclePlateNo = document.getElementById("vehiclePlateNo").value;
	param.trailerPlateNo = document.getElementById("trailerPlateNo").value;
	param.wayBillNo = document.getElementById("wayBillNo").value;
	param.cargoTransWay = document.getElementById("cargoTransWay").value;
	param.status = document.getElementById("status").value;

	param.cargoItem = document.getElementsByName("cargoItem")[0].value;
	param.cargoPkgAndCount = document.getElementsByName("cargoPkgAndCount")[0].value;

	param.durationType = document.getElementById("durationType").value;
	param.duration = document.getElementById("duration").value;
	param.premiumRate = document.getElementById("premiumRate").value;
	param.allPremium = document.getElementById("premium").value;
	param.insuredAmount = document.getElementById("insuredAmount").value;
	
    var flag=$("#check1").is(':checked');
	debugger;
    if(flag) {
  	    getInsuredParam(param);
    }
    //if(console) console.log("param == ", param);
	return param;
}
function getInsuredParam(param) {
	param.insuredPartType = param.holderPartType;
	param.insuredName = param.holderName;
	param.insuredCertNo = param.holderCertNo;
	param.insuredCertType = param.holderCertType;
	
	if(param.insuredPartType == 'QY') {
		param.insuredCtatName = param.holderCtatName;
		param.insuredCtatMobile = param.holderCtatMobile;
	} else if(param.insuredPartType == 'GR') {
		param.insuredSex = param.holderSex;
	}
	
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
	if(!validPolicy()) {
		return false;
	}
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
	var holderCertType = document.getElementById("holderCertType").value;
	var holderName = jQuery.trim(document.getElementById("holderName").value);
	var holderCertNo = jQuery.trim(document.getElementById("holderCertNo").value);
	document.getElementById("holderName").value = holderName;
	document.getElementById("holderCertNo").value = holderCertNo;

	var holderMobileNo = jQuery.trim(document.getElementById("holderMobileNo").value);
	var holderSex = document.getElementById("holderSex").value;
	var holderBirthday = jQuery.trim(document.getElementById("holderBirthday").value);
	var holderEmail = jQuery.trim(document.getElementById("holderEmail").value);
	var holderCtatName = jQuery.trim(document.getElementById("holderCtatName").value);
	var holderCtatMobile = jQuery.trim(document.getElementById("holderCtatMobile").value);
	document.getElementById("holderMobileNo").value = holderMobileNo;
	document.getElementById("holderBirthday").value = holderBirthday;
	document.getElementById("holderEmail").value = holderEmail;
	document.getElementById("holderCtatName").value = holderCtatName;
	document.getElementById("holderCtatMobile").value = holderCtatMobile;
	
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
	if(holderPartType == 'QY') {
		if(holderCtatName == null || holderCtatName == "") {
			$.messager.alert('提示','“投保人”联系人名称不能为空!','info');
			return false;
		}
		if(holderCtatMobile == null || holderCtatMobile == "") {
			$.messager.alert('提示','“投保人”手机号码不能为空!','info');
			return false;
		}
	} else if(holderPartType == 'GR') {
		if(holderMobileNo == null || holderMobileNo == "") {
			$.messager.alert('提示','“投保人”手机号码不能为空!','info');
			return false;
		}
		if(holderSex == null || holderSex == "") {
			$.messager.alert('提示','“投保人”性别不能为空!','info');
			return false;
		}
		if(holderBirthday == null || holderBirthday == "") {
			$.messager.alert('提示','“投保人”生日不能为空!','info');
			return false;
		}
		if(holderEmail == null || holderEmail == "") {
			$.messager.alert('提示','“投保人”Email不能为空!','info');
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
	var insuredCertType = document.getElementById("insuredCertType").value;
	var insuredName = jQuery.trim(document.getElementById("insuredName").value);
	var insuredCertNo = jQuery.trim(document.getElementById("insuredCertNo").value);
	document.getElementById("insuredName").value = insuredName;
	document.getElementById("insuredCertNo").value = insuredCertNo;
	
	var insuredSex = document.getElementById("insuredSex").value;
	var insuredMobileNo = jQuery.trim(document.getElementById("insuredMobileNo").value);
	var insuredCtatName = jQuery.trim(document.getElementById("insuredCtatName").value);
	var insuredCtatMobile = jQuery.trim(document.getElementById("insuredCtatMobile").value);
	var phRelToIns = jQuery.trim(document.getElementById("phRelToIns").value);
	document.getElementById("insuredMobileNo").value = insuredMobileNo;
	document.getElementById("insuredCtatName").value = insuredCtatName;
	document.getElementById("insuredCtatMobile").value = insuredCtatMobile;
	document.getElementById("phRelToIns").value = phRelToIns;
	
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
	if(holderPartType == 'QY') {
		if(insuredCtatName == null || insuredCtatName == "") {
			$.messager.alert('提示','“被保人”联系人名称不能为空!','info');
			return false;
		}
		if(insuredCtatMobile == null || insuredCtatMobile == "") {
			$.messager.alert('提示','“被保人”手机号码不能为空!','info');
			return false;
		}
	} else if(insuredPartType == 'GR') {
		if(insuredMobileNo == null || insuredMobileNo == "") {
			$.messager.alert('提示','“被保人”手机号码不能为空!','info');
			return false;
		}
		if(insuredSex == null || insuredSex == "") {
			$.messager.alert('提示','“被保人”性别不能为空!','info');
			return false;
		}
	}
	return true;
}
//校验保单信息
function validPolicy() {
	var insuredAmount = document.getElementById("insuredAmount").value;
	var allPremium = document.getElementById("allPremium").value;
	
	var startDate = document.getElementById("start").value;
	var endDate = document.getElementById("end").value;
	var vehiclePlateNo = jQuery.trim(document.getElementById("vehiclePlateNo").value);
	var trailerPlateNo = jQuery.trim(document.getElementById("trailerPlateNo").value);
	var wayBillNo = jQuery.trim(document.getElementById("wayBillNo").value);
	document.getElementById("vehiclePlateNo").value = vehiclePlateNo;
	document.getElementById("trailerPlateNo").value = trailerPlateNo;
	document.getElementById("wayBillNo").value = wayBillNo;

	var cargoTransWay = document.getElementsByName("cargoTransWay")[0].value;
	var durationType = jQuery.trim(document.getElementsByName("durationType")[0].value);
	var duration = jQuery.trim(document.getElementsByName("duration")[0].value);
	var cargoItem = jQuery.trim(document.getElementsByName("cargoItem")[0].value);
	var cargoPkgAndCount = jQuery.trim(document.getElementsByName("cargoPkgAndCount")[0].value);
	var insuredAmount = jQuery.trim(document.getElementsByName("insuredAmount")[0].value);
	var premiumRate = jQuery.trim(document.getElementsByName("premiumRate")[0].value);
	document.getElementsByName("durationType")[0].value = durationType;
	document.getElementsByName("duration")[0].value = duration;
	document.getElementsByName("cargoItem")[0].value = cargoItem;
	document.getElementsByName("cargoPkgAndCount")[0].value = cargoPkgAndCount;
	document.getElementsByName("insuredAmount")[0].value = insuredAmount;
	document.getElementsByName("premiumRate")[0].value = premiumRate;

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
	if(wayBillNo == null || wayBillNo == "") {
		$.messager.alert('提示','请填写运单号!','info');
		return false;
	}

	if(cargoTransWay == null || cargoTransWay == "") {
		$.messager.alert('提示','请填写运输方式!','info');
		return false;
	}
	if(cargoItem == null || cargoItem == "") {
		$.messager.alert('提示','请填写保险货物项目!','info');
		return false;
	}
	if(cargoPkgAndCount == null || cargoPkgAndCount == "") {
		$.messager.alert('提示','请填写包装及数量!','info');
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

	return true;
}
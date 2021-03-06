var imgName = "";

$(document).ready(function() {
	//初始化页面
	initPageUI();
	//绑定事件
	bindEvents();
});
//初始化页面
function initPageUI() {
	getHolders("holderCompName");
	getReceivers("recipients");

    $("#holderCompNature").css("width", "200px");
    $("#industryType").css("width", "200px");
    $("#holderNature").css("width", "200px");

	var abc = $("#formobj").width()+17;
	$("#formobj").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
}
//绑定事件
function bindEvents() {
	$("#planId").change(function() {
		var code=$(this).children('option:selected').attr("data-code");
		$("#premium").val(code);
	});
	$("#invoiceType").change(function() {
		var value=$(this).children('option:selected').val();
    	var orgCode=$("#holderOrgCode").val();
        if(value=='3') {
        	var taxpayerNo=$("#taxpayerNop").val();
        	var compNamep=$("#compNamep").val();
        	var compName=$("#holderCompName").val();
        	if(taxpayerNo==null || taxpayerNo=="") {
                $("#taxpayerNop").val(orgCode);
        	}
        	if(compNamep==null || compNamep=="") {
                $("#compNamep").val(compName);
        	}

        	$("#invoiceTr").css("display", "none");
        	add("开具增值税专用发票","policyMainController.do?addSpe");
        } else if(value=='2') {
        	var taxpayerNo2=$("#taxpayerNo2").val();
        	if(taxpayerNo2==null || taxpayerNo2=="") {
                $("#taxpayerNo2").val(orgCode);
        	}
        	$("#invoiceTr").css("display", "table-row");
        } else if(value=='1') {
        	$("#invoiceTr").css("display", "none");
        }
	});

	$("#check1").click(function() {
	    var value=$(this).is(':checked');
    	var holderCompName = $("#holderCompName").val();
    	var holderOrgCode = $("#holderOrgCode").val();
	    if(value) {
	    	$("#insuredCompName").val(holderCompName);
	    	$("#insuredOrgCode").val(holderOrgCode);
	    } else {
	    	$("#insuredCompName").val("");
	    	$("#insuredOrgCode").val("");
	    }
	});
	$("#check2").click(function() {
	    var value=$(this).is(':checked');
	    if(value) {
	    	$("#isPaperPolicy").val("1");
	    } else {
	    	$("#isPaperPolicy").val("0");
	    }
	});
	$("#check3").click(function() {
	    var value=$(this).is(':checked');
	    if(value) {
	    	$("#isPaperInvoice").val("1");
	    } else {
	    	$("#isPaperInvoice").val("0");
	    }
	});
}

function editablePolicy() {
	//将投保人下拉框设置为可编辑的下拉框
	$("#holderCompName").editableSelect({
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
	/*$("#insuredCompName").editableSelect({
        bg_iframe: false,
        case_sensitive: false,
        items_then_scroll: 10,
        isFilter:false,
        onSelect: function(list_item) {
            console.log(list_item[0].getAttribute("data-code"));
            var insuredOrgCode=$(list_item[0]).attr("data-code");
            $("#insuredOrgCode").val(insuredOrgCode);
        }
    });*/
}
function editableInvoice() {
	//将收件人下拉框设置为可编辑的下拉框
	$("#recipients").editableSelect({
        effects: 'slide',
        bg_iframe: false,
        case_sensitive: false,
        items_then_scroll: 10,
        isFilter:false,
        onSelect: function(list_item) {
            var recipientsTel=$(list_item[0]).attr("data-num");
            var reciAddress=$(list_item[0]).attr("data-text");
            $("#recipientsTel").val(recipientsTel);
            $("#reciAddress").val(reciAddress);
        }
    });
}
function getHolderById(holderId) {
	//根据id获取投保人，带出组织机构代码、单位性质、行业类别等
  $.ajax({
      url: "policyMainController.do?getHolderById",
      type: "POST",
      data: {id: holderId},
      dataType: "json",
      error: function () {
          layer.alert("服务器异常");
      },
      success: function (data) {
          //if(console) console.log(data);
          if (data.code == 200) {
        	  var item = data.value;
          	  addHolder(item);
          	  if(item.depositBank != null && item.depositBank != "") {
      	    	  $("#invoiceType").val('3').trigger('change');
          	  } else if(item.receiverMobile != null && item.receiverMobile != "") {
      	    	  $("#invoiceType").val('2').trigger('change');
          	  } else {
        	      $("#invoiceType").val('1').trigger('change');
          	  }
      		  //tip($.i18n.prop('common.opt.success'));
              return false;
          } else {
              layer.alert(data.message);
          }
      }
  });
}
function addHolder(item) {
	//tip(item.taxpayerNo);
	document.getElementById("holderOrgCode").value = item.holderOrgCode;
	document.getElementById("holderCompNature").value = item.holderCompNature;
	document.getElementById("industryType").value = item.industryType;
	document.getElementById("taxpayerNo2").value = item.taxpayerNo;
	document.getElementById("receiverMobile").value = item.receiverMobile;
	document.getElementById("taxpayerNop").value = item.taxpayerNo;
	document.getElementById("compNamep").value = item.compName;
	document.getElementById("compAddressp").value = item.compAddress;
	document.getElementById("compPhonep").value = item.compPhone;
	document.getElementById("depositBankp").value = item.depositBank;
	document.getElementById("bankAccountp").value = item.bankAccount;

}


var index1 = 0;
function addPolicy() {
	index1++;
	var index = document.getElementById("policy_tabel").rows.length;
	//layer.msg(index);
	var trbody = `<tr name='policytr'>
		<input name='vehicles[${index}].id' type='hidden'/>
		<td><input type='text' name='vehicles[${index}].plateNo' class='policy' title='plateNo' style='width:100px;' value='未上牌'></td>
		<td><input type='text' name='vehicles[${index}].frameNo' class='policy' title='frameNo' style='width:180px;' placeholder='输入车架号'></td>
		<td><input type='text' name='vehicles[${index}].engineNo' class='policy' title='engineNo' style='width:120px;' placeholder='输入发动机号'></td>
		<td><input type='text' name='vehicles[${index}].tonCount' class='policy' title='tonCount' maxlength='2' style='width:60px;' value='0' placeholder='默认填: 0' readonly></td>
		<td><input class='btn' type='button' value='删除' onclick='removePolicy(this);' style='height:30px;width:100px !important;'/></td>
		<td><span data-event="toggleShourMode" class="radio-one"><input type="checkbox" name="dateMode" value="custom"/>自定义</span></td>
	</tr>`;
	$("#policy_tabel").find("tbody").append(trbody);
	//$("#policy_tabel").find("tbody").replaceWith(trbody);
	resetTrNum("add_policy_tabel");
}

function removePolicy(obj) {
	index1--;
	$(obj).parents("tr[name='policytr']").remove();
	resetTrNum("add_policy_tabel");
}

//初始化默认保险期间
function initDefaultInsurDate(year) {
	PolicyMainUpdateComponent.renderInsuranceDate({
		isDefaultDateBox: true,
		startDate: {
			attrs:{id: 'start', name: 'startDate', value: "${start}"}},
		startHour: {
			attrs:{id: 'shour', name: 'shour'}},
		endDate: {
			attrs:{id: 'end', name: 'endDate', value: "${end}"}},
		endHour: {
			attrs:{id: 'ehour', name: 'ehour'}},
		year: {
			attrs:{id: 'year', name: 'year', value: year}},
		month: {
			attrs:{id:'month'}}
	}, $('#defaultInsuranceDate'));
}
/**
 * 切换【保险期间】填写方式：默认 或 自定义
 */
function initToggleShourModeEvent(start, max, end, year, vehicles) {
	 $('#add_policy_tabel').on('click', '[data-event="toggleShourMode"]', function(event){
	 	if (event.target.tagName === 'SPAN') {
	 		$(this).children(':input[type="checkbox"]').click();
	 		return;
		}
	 });
	 let toggleDateByCkbox = function () {
		 let isCustom = $(this).is(':checked');
		 let $dateField = $(this).parents('td:first').children('.shour-field');
		 let rowIndex = $(this).parents('tr:first').index();
		 let startDate;
		 let endDate;
		 if (vehicles != null && vehicles[rowIndex]) {
			 startDate = new Date(vehicles[rowIndex].startDate);
			 endDate = new Date(vehicles[rowIndex].endDate);
		 } else {
			 startDate = Date.parse($('#start').val() + ' 00:00:00');
			 endDate = Date.parse($('#end').val() + ' 23:59:59');
		 }
		 let startDateStr = DateUtils.dateFormatYMD(startDate);
		 let endDateStr = DateUtils.dateFormatYMD(endDate);
		 let year = DateUtils.getDateDiffY(startDate, endDate);
		 if (isCustom) {
		 	if ($dateField.length === 0) {
				PolicyMainUpdateComponent.renderInsuranceDate({
					isDefaultDateBox: false,
					startDate: {
						attrs:{"data-field":"startDate", name: `vehicles[${rowIndex}].startDate`, value: startDateStr}},
					startHour: {
						attrs:{"data-field": "shour", name:`vehicles[${rowIndex}].startHour`}},
					endDate: {
						attrs:{"data-field":"endDate", name: `vehicles[${rowIndex}].endDate`, value: endDateStr}},
					endHour: {
						attrs:{"data-field":"ehour", name:`vehicles[${rowIndex}].endHour`}},
					year: {
						attrs:{"data-field":"year", name:`vehicles[${rowIndex}].year`, value: year}},
					month: {
						attrs:{"data-field": "month"}}
				}, $(this).parents('td:first'));
			}

			//  $(this).parents('td:first').append(`
			// <span class="shour-field">
		 	// 自
		 	// <input type="text"  class="Wdate" style="width:100px;"
		 	// 	onblur="calculateYearByParam(this);" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${minDateStr}',maxDate:'${maxDateStr}'})"/>
			// <input type="text" style="width:20px;" value="00" disabled/>
			// 起 至
			// <input type="text"  class="Wdate" style="width:100px;" readonly/>
			// <input type="text"  style="width:20px;" value="24" disabled/>
			// 止，连续
			// <input type="text"  style="width:60px;" value="${year}" onblur="calculateMonths(this);">
			// 年 共
			// <label data-field="month">12</label>
			// 月
			// </span>
		 	// `);
		 } else {
			 $dateField.remove();
		 }
	 };
	 $('#add_policy_tabel :input[type="checkbox"]').each(function(){
		 toggleDateByCkbox.apply(this);
	 });
	 $('#add_policy_tabel').on('change', ':input[type="checkbox"]', function(event){
		 toggleDateByCkbox.apply(this);
	 });
}


/**
 * 添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function add(title,addurl) {
	var url = setChildUrl();
	createwindowN('开具增值税专用发票', addurl + url,'50%','50%',true);
}

/**
 * 创建添加或编辑窗口
 * @param title
 * @param addurl
 */
function createwindowN(title,addurl,width,height,flag) {
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
		onLoad:function(){
            if(console) console.log("onLoad");
	    	var iframe = this.iframe.contentWindow;
	    	//setChildData(iframe);
		},
	    ok: function(){
	    	var iframe = this.iframe.contentWindow;
	    	if(flag) {
	    		return setParentData(iframe);
	    	} else {
	    		return updInvoice(iframe);
	    	}
	    },
	    okVal: '保存',/*$.i18n.prop('dialog.submit'),*/
	    cancelVal: $.i18n.prop('dialog.close'),
	    cancel: function(){
	    	if(flag) {
	    		$("#invoiceType").val('2').trigger('change');
	    		layer.msg("已默认选择增值税普通发票", {icon:6});
	    	}
			return true;
	    }
	    /*cancel: true 为true等价于function(){}*/
	});
}
//把父页面上的数据作为参数填入子页面
function setChildUrl() {
	var param = getParentParam();
	var url = "";
	url = url + "&compName=" + param.compName;
	url = url + "&taxpayerNo=" + param.taxpayerNo;
	url = url + "&compAddress=" + param.compAddress;
	url = url + "&compPhone=" + param.compPhone;
	url = url + "&depositBank=" + param.depositBank;
	url = url + "&bankAccount=" + param.bankAccount;
	
    if(window.console) console.log(url);
    //layer.msg(url);
	return url;
}

function setChildData(iframe) {
	var param = getParentParam();
    
	$("#compName", iframe.document).val(param.compName);
	$("#taxpayerNo", iframe.document).val(param.taxpayerNo);
	$("#compAddress", iframe.document).val(param.compAddress);
	$("#compPhone", iframe.document).val(param.compPhone);
	$("#depositBank", iframe.document).val(param.depositBank);
	$("#bankAccount", iframe.document).val(param.bankAccount);
}
function getParentParam() {
	var param = {};
	param.compName = $("#compNamep").val();
	param.taxpayerNo = $("#taxpayerNop").val();
	param.compAddress = $("#compAddressp").val();
	param.compPhone = $("#compPhonep").val();
	param.depositBank = $("#depositBankp").val();
	param.bankAccount = $("#bankAccountp").val();
	
	return param;
}
//把专票子页面上的数据写到父页面
function setParentData(iframe) {
	var param = getChildParam(iframe);

	if(!validParam(param, iframe)) {
		return false;
	}
	parseData(JSON.stringify(param));
	return true;
}
function parseData(info) {
    //layer.msg(info);
    if (info) {
        info = JSON.parse(info);
        $("#compNamep").val(info.compName);
        $("#taxpayerNop").val(info.taxpayerNo);
        $("#compAddressp").val(info.compAddress);
        $("#compPhonep").val(info.compPhone);
        $("#depositBankp").val(info.depositBank);
        $("#bankAccountp").val(info.bankAccount);
    }
}
function getChildParam(iframe) {
	debugger;
	var param = {};
	param.id = jQuery.trim($("#id", iframe.document).val());
	param.compName = jQuery.trim($("#compName", iframe.document).val());
	param.taxpayerNo = jQuery.trim($("#taxpayerNo", iframe.document).val());
	param.compAddress = jQuery.trim($("#compAddress", iframe.document).val());
	param.compPhone = jQuery.trim($("#compPhone", iframe.document).val());
	param.depositBank = jQuery.trim($("#depositBank", iframe.document).val());
	param.bankAccount = jQuery.trim($("#bankAccount", iframe.document).val());
	param.recipients = jQuery.trim($("#recipients", iframe.document).val());
	param.recipientsTel = jQuery.trim($("#recipientsTel", iframe.document).val());
	param.reciAddress = jQuery.trim($("#reciAddress", iframe.document).val());
	$("#id", iframe.document).val(param.id);
	$("#compName", iframe.document).val(param.compName);
	$("#taxpayerNo", iframe.document).val(param.taxpayerNo);
	$("#compAddress", iframe.document).val(param.compAddress);
	$("#compPhone", iframe.document).val(param.compPhone);
	$("#depositBank", iframe.document).val(param.depositBank);
	$("#bankAccount", iframe.document).val(param.bankAccount);
	$("#recipients", iframe.document).val(param.recipients);
	$("#recipientsTel", iframe.document).val(param.recipientsTel);
	$("#reciAddress", iframe.document).val(param.reciAddress);
	
	return param;
}


function updInvoice(iframe) {
	var mainTabId = "tab_402880ea6e26628b016e26665a0f0001";
	var url = "policyMainController.do?updateInvoice";
	var param = getChildParam(iframe);

	if(!validParams(param, iframe)) {
		return false;
	}
	ajaxUpdInvoice(url, param, mainTabId);
	return true;
}
//修改发票信息函数
function ajaxUpdInvoice(url, params, mainTabId) {
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
        if (data.success) {
            reloadPolicyList(mainTabId);
			layer.msg(data.msg, {icon:6});
        } else {
            layer.alert(data.msg);
        }

        return false;
    }
});
}
//Form submit方式提交表单数据
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
//Ajax方式打开支付页面
function submitPay(tabId, mainTabId) {
	var param = $("#insuranceObj").val();
    if(window.console) console.log(param);
	if(param == null || param == "") {
		$.messager.alert('提示','请先核保再进行支付!','error');
		return false;
	}
	$("#save").attr("disabled", true);
	$("#insur").attr("disabled", true);
    $("#pay").attr("disabled", true);
    window.Utils.showLoading(imgName);
	
	var params = {};
	params.params = param;
	var url = "policyDraftController.do?insurancePay";
	ajaxPay(url, params, tabId, mainTabId);
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
        if (data.success) {
		    //layer.msg(data.msg, {icon:6});
            var payUrl = result.data;
            //var payUrl = "https://devyun.guorenpcic.com/paycenter/?orderId=23a2e077d1e4fd19a61&amp;code=&amp;payOrderNo=js02&amp;platform=pc";
            if(console) console.log("payUrl == ", payUrl);
            result = JSON.stringify(result);
      	    $("#payResult").val(result);
      	    $("#payUrl").val(payUrl);
      	    $("#tabId").val(tabId);
      	    $("#mainTabId").val(mainTabId);
      	    
      	    //open window方式打开支付窗口，打开的是一个浏览器窗口
      	    //openWindow(payUrl);
      		//弹出层的方式打开支付页面，打开的不是浏览器窗口，只是显示了一个层
      	    openDiv(payUrl);
        } else {
        	$("#insur").attr("disabled", false);
            layer.alert(data.msg);
        }
        $("#pay").attr("disabled", false);
        window.Utils.closeLoading();
        return false;
    }
});
}
//公共提交核保函数：参数 params为 Json类型，可以传空参数，如:  {}
function ajaxSubmitForm(url, params, isAdd, mainTabId) {
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
          $("#insResult").val("0");
          if(isAdd && backobj != null && backobj != "") {
        	  failureCallback(backobj);
          }
          if (data.success) {
              result = JSON.stringify(result);
              //if(console) console.log("returnObj == ", result);
              $("#insuranceObj").val(result);
              $("#pay").attr("disabled", false);
              reloadPolicyList(mainTabId);
  			  layer.msg(data.msg, {icon:6});
          } else {
              layer.alert(data.msg);
          }

          $("#insur").attr("disabled", false);
          window.Utils.closeLoading();
          return false;
      }
  });
}
//核保失败回调函数
function failureCallback(result) {
    //if(console) console.log("returnObj == ", result);
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
function getSubmitParam() {
	var param = {};
	
	param.prodId = document.getElementById("prodId").value;
	param.premium = document.getElementById("premium").value;
	param.planId = document.getElementById("planId").value;
	param.startDate = document.getElementById("start").value;
	param.endDate = document.getElementById("end").value;
	param.holderNature = document.getElementById("holderNature").value;
	param.holderCompName = document.getElementById("holderCompName").value;
	param.holderOrgCode = document.getElementById("holderOrgCode").value;
	param.holderCompNature = document.getElementById("holderCompNature").value;
	param.industryType = document.getElementById("industryType").value;
	param.contactName = document.getElementById("contactName").value;
	param.policyMobile = document.getElementById("policyMobile").value;
	param.insuredCompName = document.getElementById("insuredCompName").value;
	param.insuredOrgCode = document.getElementById("insuredOrgCode").value;
	param.status = document.getElementById("status").value;
	param.invoiceType = document.getElementById("invoiceType").value;
	param.recipients = document.getElementById("recipients").value;
	param.recipientsTel = document.getElementById("recipientsTel").value;
	param.reciAddress = document.getElementById("reciAddress").value;
	param.isPaperPolicy = document.getElementById("isPaperPolicy").value;
	param.isPaperInvoice = document.getElementById("isPaperInvoice").value;
	if(param.invoiceType == '3') {
		param.taxpayerNo = document.getElementById("taxpayerNop").value;
		param.compName = document.getElementById("compNamep").value;
		param.compAddress = document.getElementById("compAddressp").value;
		param.compPhone = document.getElementById("compPhonep").value;
		param.depositBank = document.getElementById("depositBankp").value;
		param.bankAccount = document.getElementById("bankAccountp").value;
	} else if(param.invoiceType == '2') {
		param.taxpayerNo = document.getElementById("taxpayerNo2").value;
		param.receiverMobile = document.getElementById("receiverMobile").value;
	}
	var plateNos = $("[class='policy'][title='plateNo']");
	var length = plateNos.length;
	var vehicles = [];
	for(var i = 0; i < length; i++) {
		var vehicle = {};
		vehicle.plateNo = document.getElementsByName("vehicles["+i+"].plateNo")[0].value;
		vehicle.frameNo = document.getElementsByName("vehicles["+i+"].frameNo")[0].value;
		vehicle.engineNo = document.getElementsByName("vehicles["+i+"].engineNo")[0].value;
		vehicle.tonCount = document.getElementsByName("vehicles["+i+"].tonCount")[0].value;
		param["vehicles["+i+"].plateNo"] = vehicle.plateNo;
		param["vehicles["+i+"].frameNo"] = vehicle.frameNo;
		param["vehicles["+i+"].engineNo"] = vehicle.engineNo;
		param["vehicles["+i+"].tonCount"] = vehicle.tonCount;
		
		var dateObj = document.getElementsByName("vehicles["+i+"].startDate")[0];
		debugger;
		if(dateObj!=undefined) {
			vehicle.startDate = document.getElementsByName("vehicles["+i+"].startDate")[0].value;
			vehicle.startHour = document.getElementsByName("vehicles["+i+"].startHour")[0].value;
			vehicle.endDate = document.getElementsByName("vehicles["+i+"].endDate")[0].value;
			vehicle.endHour = document.getElementsByName("vehicles["+i+"].endHour")[0].value;
			//vehicle.year = document.getElementsByName("vehicles["+i+"].year")[0].value;
			param["vehicles["+i+"].startDate"] = vehicle.startDate;
			param["vehicles["+i+"].startHour"] = vehicle.startHour;
			param["vehicles["+i+"].endDate"] = vehicle.endDate;
			param["vehicles["+i+"].endHour"] = vehicle.endHour;
			//param["vehicles["+i+"].year"] = vehicle.year;
		}
		vehicles[i] = vehicle;
	}
	param.vehicleArr = vehicles;
    //if(console) console.log("param == ", param);
	
	return param;
}
function getUpdateParam(param) {
	//param.id = document.getElementById("id").value;
	param.draftId = document.getElementById("draftId").value;
	param.userId = document.getElementById("userId").value;
	param.payStatus = document.getElementById("payStatus").value;
	param.rewardStatus = document.getElementById("rewardStatus").value;
	param.createTime = document.getElementById("createTime").value;
	param.draft = document.getElementById("isDraft").value;
	var plateNos = $("[class='policy'][title='plateNo']");
	var length = plateNos.length;
	for(var i = 0; i < length; i++) {
		var vehicle = {};
		vehicle.id = document.getElementsByName("vehicles["+i+"].id")[0].value;
		param["vehicles["+i+"].id"] = vehicle.id;
	}
	
	return param;
}
function getSelectParam(param) {
	param.planName = $("#planId").children('option:selected').text();
	param.invTypeName = $("#invoiceType").children('option:selected').text();
	param.hldNatureName = $("#holderNature").children('option:selected').text();
	param.compNatureName = $("#holderCompNature").children('option:selected').text();
	param.indstTypeName = $("#industryType").children('option:selected').text();
	
	return param;
}

//校验数组中是否有重复的项
function isRepeat(ary) {
    let s = ary.join(",") + ",";
    for (let i = 0; i < ary.length; i++) {
        if (s.replace(ary[i] + ",", "").indexOf(ary[i] + ",") > -1) {
            return false;
        }
    }
    return true;
}
//校验开具专用发票页面上的数据
function validParam(param, iframe) {
	if(param.compName == null || param.compName == "") {
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
	return true;
}
//校验开具专用发票页面上的数据
function validParams(param, iframe) {
	if(param.compName == null || param.compName == "") {
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
//校验主页面上的数据
function validData() {
	if(!validVehicle()) {
		return false;
	}
	
	var holderNature = document.getElementById("holderNature").value;
	var holderCompNature = document.getElementById("holderCompNature").value;
	var industryType = document.getElementById("industryType").value;
	var holderCompName = jQuery.trim(document.getElementById("holderCompName").value);
	var holderOrgCode = jQuery.trim(document.getElementById("holderOrgCode").value);
	var contactName = jQuery.trim(document.getElementById("contactName").value);
	var policyMobile = jQuery.trim(document.getElementById("policyMobile").value);
	var insuredCompName = jQuery.trim(document.getElementById("insuredCompName").value);
	var insuredOrgCode = jQuery.trim(document.getElementById("insuredOrgCode").value);

	document.getElementById("holderCompName").value = holderCompName;
	document.getElementById("holderOrgCode").value = holderOrgCode;
	document.getElementById("contactName").value = contactName;
	document.getElementById("policyMobile").value = policyMobile;
	document.getElementById("insuredCompName").value = insuredCompName;
	document.getElementById("insuredOrgCode").value = insuredOrgCode;
	if(holderNature == null || holderNature == "") {
		$.messager.alert('提示','投保人性质不能为空!','info');
		return false;
	}
	if(holderCompName == null || holderCompName == "") {
		$.messager.alert('提示','“投保人”单位名称不能为空!','info');
		return false;
	}
	if(holderOrgCode == null || holderOrgCode == "") {
		$.messager.alert('提示','“投保人”组织机构代码不能为空!','info');
		return false;
	}
	if(holderCompNature == null || holderCompNature == "") {
		$.messager.alert('提示','单位性质不能为空!','info');
		return false;
	}
	if(industryType == null || industryType == "") {
		$.messager.alert('提示','行业类别不能为空!','info');
		return false;
	}
	if(contactName == null || contactName == "") {
		$.messager.alert('提示','联系人名称不能为空!','info');
		return false;
	}
	if(policyMobile == null || policyMobile == "") {
		$.messager.alert('提示','联系人手机不能为空!','info');
		return false;
	}
	if(insuredCompName == null || insuredCompName == "") {
		$.messager.alert('提示','“被保人”单位名称不能为空!','info');
		return false;
	}
	if(insuredOrgCode == null || insuredOrgCode == "") {
		$.messager.alert('提示','“被保人”组织机构代码不能为空!','info');
		return false;
	}

	var invoiceType = document.getElementById("invoiceType").value;
	var taxpayerNo = jQuery.trim(document.getElementById("taxpayerNo2").value);
	var receiverMobile = jQuery.trim(document.getElementById("receiverMobile").value);
	document.getElementById("taxpayerNo2").value = taxpayerNo;
	document.getElementById("receiverMobile").value = receiverMobile;
	if(invoiceType == '2') {
		if(taxpayerNo == null || taxpayerNo == "") {
			$.messager.alert('提示','请填写纳税人识别号!','info');
			return false;
		}
		if(receiverMobile == null || receiverMobile == "") {
			$.messager.alert('提示','请填写接收人手机!','info');
			return false;
		}
	}

	if(!validRecipients()) {
		return false;
	}
	return true;
}
//校验车辆信息
function validVehicle() {
	var message = "";
    var aryFrameNo = new Array();
    var aryEngineNo = new Array();
	var plateNos = $("[class='policy'][title='plateNo']");
	var frameNos = $("[class='policy'][title='frameNo']");
	var engineNos = $("[class='policy'][title='engineNo']");
	var tonCounts = $("[class='policy'][title='tonCount']");
	//var frameNos = document.getElementsByName("vehicles[*].frameNo");
	//var engineNos = document.getElementsByName("vehicles[*].engineNo");
	for(var i = 0; i < plateNos.length; i++) {
		var plateNo = jQuery.trim($(plateNos[i]).val());
		$(plateNos[i]).val(plateNo);
	    //if(window.console) console.log(plateNo);
		if(plateNo == null || plateNo == "") {
			message = "第" + (i + 1) + "行，车牌号不能为空!";
			$.messager.alert('提示',message,'info');
			return false;
		} else if(!checkLicenseNo(plateNo)) {
			message = "第" + (i + 1) + "行，车牌号不正确，请重新输入!";
            $.messager.alert('提示',message,'info');
			return false;
		}
	}
	for(var i = 0; i < frameNos.length; i++) {
		var frameNo = jQuery.trim($(frameNos[i]).val());
		$(frameNos[i]).val(frameNo);
	    //if(window.console) console.log($(frameNos[i]).parent().html());
		if(frameNo == null || frameNo == "") {
			message = "第" + (i + 1) + "行，车架号不能为空!";
			$.messager.alert('提示',message,'info');
			return false;
		} else if(frameNo.length != 17) {
			message = "第" + (i + 1) + "行，车架号必须为17位!";
            //$.messager.alert('提示',message,'info');
			//return false;
		}
		aryFrameNo.push(frameNo);
	}
	for(var i = 0; i < engineNos.length; i++) {
		var engineNo = jQuery.trim($(engineNos[i]).val());
		$(engineNos[i]).val(engineNo);
	    //if(window.console) console.log(engineNo);
		if(engineNo == null || engineNo == "") {
			message = "第" + (i + 1) + "行，发动机号不能为空!";
			$.messager.alert('提示',message,'info');
			return false;
		}
		aryEngineNo.push(engineNo);
	}
	for(var i = 0; i < tonCounts.length; i++) {
		var tonCount = $(tonCounts[i]).val();
	    //if(window.console) console.log(tonCount);
		if(tonCount == null || tonCount == "") {
			message = "第" + (i + 1) + "行，核定载重质量不能为空!";
			$.messager.alert('提示',message,'info');
			return false;
		}
	}
	if (!isRepeat(aryFrameNo)) {
		message = "车架号重复!请输入正确的车架号!";
		$.messager.alert('提示',message,'info');
        return false;
    } else if (!isRepeat(aryEngineNo)) {
		message = "发动机号重复!请输入正确的发动机号!";
		$.messager.alert('提示',message,'info');
        return false;
    }
	
	return true;
}
//校验收件人信息
function validRecipients() {
	var isPaperPolicy = document.getElementById("isPaperPolicy").value;
	var isPaperInvoice = document.getElementById("isPaperInvoice").value;
	if(isPaperPolicy == "0" && isPaperInvoice == "0") {
		return true;
	}
	var recipients = jQuery.trim(document.getElementById("recipients").value);
	var recipientsTel = jQuery.trim(document.getElementById("recipientsTel").value);
	var reciAddress = jQuery.trim(document.getElementById("reciAddress").value);
	document.getElementById("recipients").value = recipients;
	document.getElementById("recipientsTel").value = recipientsTel;
	document.getElementById("reciAddress").value = reciAddress;
	if(recipients == null || recipients == "") {
		$.messager.alert('提示','请填写收件人!','info');
		return false;
	}
	if(recipientsTel == null || recipientsTel == "") {
		$.messager.alert('提示','请填写收件人电话!','info');
		return false;
	}
	if(reciAddress == null || reciAddress == "") {
		$.messager.alert('提示','请填写发票收件地址!','info');
		return false;
	}
	
	return true;
}
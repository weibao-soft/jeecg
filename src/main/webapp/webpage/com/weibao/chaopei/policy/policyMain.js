$(document).ready(function(){
　　　$("#invoiceType").change(function() {
　　　　　　var value=$(this).children('option:selected').val();
        if(value=='3') {
        	$("#invoiceTr").css("display", "none");
        	add("开具增值税专用发票","policyMainController.do?addSpe");
        } else if(value=='2') {
        	$("#invoiceTr").css("display", "table-row");
        } else if(value=='1') {
        	$("#invoiceTr").css("display", "none");
        }
　　　});
	$("#check1").click(function() {
	    var value=$(this).is(':checked');
	    //layer.msg($(this).attr("checked"));
	    //console.log(value);
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
});

var neibuClickFlag = false;
function jeecgFormFileCallBack(data){
	if (data.success == true) {
	} else {
		if (data.responseText == '' || data.responseText == undefined) {
			$.messager.alert('错误', data.msg);
			$.Hidemsg();
		} else {
			try {
				var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
				$.messager.alert('错误', emsg);
				$.Hidemsg();
			} catch(ex) {
				$.messager.alert('错误', data.responseText + '');
			}
		}
		return false;
	}
	if (!neibuClickFlag) {
		var win = frameElement.api.opener;
		win.reloadTable();
	}
}

function editablePolicy() {
	$("#holderCompName").editableSelect({
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

function getHolders() {
	//获取投保人单位名称下拉框的数据
    $.ajax({
        url: "policyMainController.do?getHolders",
        type: "POST",
        data: {},
        dataType: "json",
        error: function () {
            layer.alert("服务器异常");
        },
        success: function (data) {
            if(console) console.log(data);
            if (data.code == 200) {
                addHldOptions(data.value);
                return false;
            } else {
                layer.alert(data.message);
            }
        }
    });
}
function getInsureds() {
	//获取投保人单位名称下拉框的数据
    $.ajax({
        url: "policyMainController.do?getInsureds",
        type: "POST",
        data: {},
        dataType: "json",
        error: function () {
            layer.alert("服务器异常");
        },
        success: function (data) {
            if(console) console.log(data);
            if (data.code == 200) {
            	addIurOptions(data.value);
                return false;
            } else {
                layer.alert(data.message);
            }
        }
    });
}
function addHldOptions(items) {
    $.each(items,function(n,value) {
        var htmlContent = $('<option data-id="'+value.id+'" value="'+value.holder_comp_name+'">'+value.holder_comp_name+'</option>');
        $('#holderCompName').append(htmlContent);
    });
}
function addIurOptions(items) {
    $.each(items,function(n,value) {
        var htmlContent = $('<option data-code="'+value.insuredOrgCode+'" value="'+value.insuredCompName+'">'+value.insuredCompName+'</option>');
        $('#insuredCompName').append(htmlContent);
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
            if(console) console.log(data);
            if (data.code == 200) {
            	addHolder(data.value);
                return false;
            } else {
                layer.alert(data.message);
            }
        }
    });
}
function addHolder(item) {
    $('#holderOrgCode').val(item.holderOrgCode);
    $('#holderCompNature').val(item.holderCompNature);
    $('#industryType').val(item.industryType);
    $('#taxpayerNo2').val(item.taxpayerNo);
    $('#receiverMobile').val(item.receiverMobile);
    $('#taxpayerNop').val(item.taxpayerNo);
    $('#compNamep').val(item.compName);
    $('#compAddressp').val(item.compAddress);
    $('#compPhonep').val(item.compPhone);
    $('#depositBankp').val(item.depositBank);
    $('#bankAccountp').val(item.bankAccount);

}


var index = 0;
function addPolicy() {
	index++;
	var trbody = "<tr name='policytr'>";
	trbody += "<td><div style='text-align:right;width:140px;'>车牌号：<BR/>（新车填写：未上牌）</div></td>";
	trbody += "<td><input type='text' name='vehicles["+index+"].plateNo' maxlength='8' value='未上牌'></td>";
	trbody += "<td><span style='color: red;'>*</span>车架号 </td>";
	trbody += "<td><input type='text' name='vehicles["+index+"].frameNo'></td>";
	trbody += "<td><span style='color: red;'>*</span>发动机号 </td>";
	trbody += "<td><input type='text' name='vehicles["+index+"].engineNo'></td>";
	trbody += "<td><input class='btn' type='button' value='删除' onclick='removePolicy(this);'";
	trbody += " style='height:30px;width:100px !important;border-radius:5px'/></td>";
	trbody += "</tr>";
	$("#policy_tabel").find("tbody").append(trbody);
	//$("#policy_tabel").find("tbody").replaceWith(trbody);
}

function removePolicy(obj) {
	index--;
	$(obj).parents("tr[name='policytr']").remove();
}

function calculateYear() {
	var oneDay = 1000*60*60*24;
	var oneYear = 1000*60*60*24*365;
	var year = $("#year").val();
	var start = $("#start").val();
	var time = Date.parse(start.replace(/-/g, "/"));
	time += (oneYear * year);
	var myDate = new Date(time);
	var str = myDate.getFullYear() + "-";
	str = str + (myDate.getMonth() + 1) + "-";
	str = str + myDate.getDate();
	$("#end").val(str);
	$("#endDate").val(str);
	//layer.msg(str);
	//console.log(myDate.toLocaleDateString());
}

function calculateMonths(obj) {
	var year = $(obj).val();
	var months = Number(year * 12);
	if(!isPositiveInteger(year)) {
		$(obj).val(1);
		months = 12;
		layer.msg("请输入正整数！", {icon:6});
	}
	
	//layer.msg(isRealNum(year));
	$("#month").text(months);
	calculateYear();
}

//是否为正整数
function isPositiveInteger(s){
    var re = /^[0-9]+$/ ;
    return re.test(s)
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
		onLoad:function(){
            if(console) console.log("onLoad");
	    	var iframe = this.iframe.contentWindow;
	    	setChildData(iframe);
		},
	    ok: function(){
	    	var iframe = this.iframe.contentWindow;
	    	return setParentData(iframe);
	    },
	    okVal: '保存',/*$.i18n.prop('dialog.submit'),*/
	    cancelVal: $.i18n.prop('dialog.close'),
	    cancel: function(){
	    	$("#invoiceType").val('2').trigger('change');
			layer.msg("已默认选择增值税普通发票", {icon:6});
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
	url = url + "&recipients=" + param.recipients;
	url = url + "&recipientsTel=" + param.recipientsTel;
	url = url + "&reciAddress=" + param.reciAddress;
	
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
	$("#recipients", iframe.document).val(param.recipients);
	$("#recipientsTel", iframe.document).val(param.recipientsTel);
	$("#reciAddress", iframe.document).val(param.reciAddress);
}
function getParentParam() {
	var param = {};
	param.compName = $("#compNamep").val();
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
//把专票子页面上的数据写到父页面
function setParentData(iframe) {
	var param = {};
	param.compName = $("#compName", iframe.document).val();
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
    //layer.msg(info);
    if (info) {
        info = JSON.parse(info);
        $("#compNamep").val(info.compName);
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
	var holderCompName = $("#holderCompName").val();
	var holderOrgCode = $("#holderOrgCode").val();
	var contactName = $("#contactName").val();
	var policyMobile = $("#policyMobile").val();
	var insuredCompName = $("#insuredCompName").val();
	var insuredOrgCode = $("#insuredOrgCode").val();
	if(holderCompName == null || holderCompName == "") {
		$.messager.alert('提示','“投保人”单位名称不能为空!','info');
		return false;
	}
	if(holderOrgCode == null || holderOrgCode == "") {
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
	if(insuredCompName == null || insuredCompName == "") {
		$.messager.alert('提示','“被投保人”单位名称不能为空!','info');
		return false;
	}
	if(insuredOrgCode == null || insuredOrgCode == "") {
		$.messager.alert('提示','“被投保人”组织机构代码不能为空!','info');
		return false;
	}
	return true;
}

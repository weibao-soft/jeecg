<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="plug-in/weibao/custom.css"/>

<style type="text/css">
.alert-info {
    color: #3a87ad;
    background-color: #d9edf7;
    border-color: #bce8f1;
}
.alert {
    padding: 10px;
    margin-bottom: 0px;
    border: 1px solid transparent;
    border-radius: 4px;
}
.line {
    font-size: 0px;
    line-height: 0px;
    border-top: solid 1px #eee;
    float: none;
}
</style>
<SCRIPT type="text/javascript">
$(document).ready(function() {
	/*$("#readedPrompt").change(function() {
		var readed=$(this).prop("checked");
        if(readed) {
        	$("#ensure").attr("disabled", false);
        } else {
        	$("#ensure").attr("disabled", true);
        }
	});*/
	
});

function getMainContent() {
	var params = getSubmitParam();
	params = getUpdateParam(params);
	params = getSelectParam(params);
	
	var content = "<b><span style='font-size:15px;color:red;'>保单主要内容：</span></b><br>方案名称："+params.planName
	+"，保险期间: 自<b>"+params.startDate+"</b>起 至<b>"+params.endDate+"</b>止，发票类型: <b>"+params.invTypeName+"</b><br>";
	var length = params.vehicleArr.length;
	for(var i = 0; i < length; i++) {
		var vehicle = params.vehicleArr[i];
		if(i > 2) {
			content = content+" 等";
			break;
		} else if(i > 0) {
			content = content+"; <br>";
		}
		content = content+"车辆[ "+(i+1)+" ]，车牌号: "+vehicle.plateNo+", 车架号: "+vehicle.frameNo+", 发动机号: "+vehicle.engineNo;
	}
	content = content+"<br>"
	/*+"<br>投保人性质："+params.hldNatureName
	+"<br>单位性质："+params.compNatureName+"，行业类别："+params.indstTypeName
	+"联系人名称："+params.contactName+"，联系人手机："+params.policyMobile*/
	+"投保人名称: <b>"+params.holderCompName+"</b>, 组织机构代码: "+params.holderOrgCode
	+", 被保人名称: <b>"+params.insuredCompName+"</b>, 组织机构代码: "+params.insuredOrgCode;
	
	$("#mainContent").html(content);
	//layer.msg(content);
}
function confirmInsurance() {
	var readed=$("#readedPrompt").prop("checked");
	if(!readed) {
		//$.messager.alert('提示','您必须在提交核保之前阅读并同意特约条款!','info');
		$.dialog.alert('您必须在提交核保之前阅读并同意特约条款!');
		return false;
	} else {
		confirmSubmit();
	}
}
function confirmData() {
	//$.dialog.confirm("请仔细核对投保内容，核对无误后再提交！确定提交核保吗？", function(){
	//}, function(){});
	
	$.messager.confirm('提示信息', "请确认投保信息无误，确定提交核保吗？", function(r){
		if (r) {
			//submitForm();
			layer.msg('YES');
		} else {
			layer.msg('NO');
		}
	});
}
function openPromptDiv() {
    $("#promptDiv").show();
}
function closePromptDiv() {
    $("#promptDiv").hide();
}
</SCRIPT>
<div id="promptDiv" class="overlay" style="z-index: 11009;display: none;">
<div class="animated zoomIn layerBox" style="width: 1086px; height: 586px; left: 77px; top: 36.8px;background: rgb(255, 255, 255);box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 3px;z-index: 11009;">
  <h4 class="layerHeader">
    <span>提示</span>
    <button id="prompt" type="button" data-dismiss="modal" aria-hidden="true" class="close close_btn" onclick="closePromptDiv();">
      <span class="white" style="font-weight: normal;">×</span></button>
  </h4>
  <div class="layerContianer layerBox" style="background-color: #d9edf7;box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 3px;overflow-x: hidden;overflow-y: auto;">
  
    <div style="height: 506px;overflow-x: hidden;overflow-y: auto;">
	  <div class="alert alert-info" id="toast_info" style="left: 60px; top: 325.5px; min-width: 200px; opacity: 0.987958;">
	    <div style="text-align: left;display:inline-block;padding-right:5px;"><b><span style="font-size:15px;color:red;">特别约定：</span></b>
	    <br>1、国任保险客户服务电话：4008667788
	    <br>2、被保险人在发生保险事故后，投保人、被保险人或受益人应在24小时之内拨打客服电话4008667788向国任财产保险公司报案，对超过24小时报案的案件，造成损失无法确定或损失扩大部分，保险公司有权不负责赔偿。
	    <br>3、本保单仅承保50吨以下营运性质的箱式货车、普货车、集装箱车，除此以外的搅拌车、工程车、自卸车、其它特种车等车型不在本保单承保范围，以具体约定的车牌号/车辆识别代码对应的车辆为准，如果保险期间内发生车牌号变更，出险时以车辆识别代码为准。
	    <br>4、本保单仅承担被保险人在本保单项下指定的机动车在使用过程中由于发生交通事故而造成第三者的财产损失和人身伤亡，依法应由被保险人承担的赔偿责任,本条所指第三者不包含本车车上人员。
	    <br>5、本保单约定对于超过该机动车综合商业险第三者责任保险赔偿金额（100万元）和该车辆机动车交通事故责任强制保险赔偿限额之和以上的部分，对于本保单约定的赔偿限额内负责赔偿。
	    <br>6、发生保险事故时，该车辆的机动车综合商业险第三者责任保险不承担保险责任的，本保单同样不承担保险责任。
	    <br>7、本保单不负责赔偿任何现金、支票（有价证券）、古董字画及其他无法估值的物品。 
	    <br>8、承保车辆为集装箱拖挂汽车时，主车和挂车连接使用时视为一体，以主车名义进行投保，主车和挂车均须悬挂车牌，否则保险人不承担保险责任。
	    <br>9、空载情况下发生的第三者保险责任也属于保险责任范围内。
	    <br>10、被保险标的被转让、改装、加装或改变使用性质的，被保险人未书面通知保险人，且因转让、改装、加装或改变使用性质导致被保险标的危险程度显著增加，保险人不承担赔偿责任。
	    <br></div>
	    <br><input id="readedPrompt" type="checkbox" style=""/><b><span style="font-size: 15px;">我已经阅读并同意特约条款</span></b>
	  </div>
	    
		<div style="padding: 0px;">
			<div class="line" style="border-top: solid 1px #1a7bb9;"></div>
		</div>
	  <div class="alert alert-info" style="width: 100%;text-align: left;">
		<div id="mainContent" style="width: 100%;display:inline-block;padding-right:5px;"></div>
	  </div>
    </div>

    <div style="margin:5px auto">
	    <input id="ensure" class="subBtnmy btn-size" type="button" value="确认提交" onclick="confirmInsurance();"/>
	    <input id="back" class="btnmy btn-size" type="button" value="取消" onclick="closePromptDiv();"/>
    </div>
    
  </div>
</div>
</div>
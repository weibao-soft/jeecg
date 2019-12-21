<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="plug-in/weibao/custom.css"/>

<style type="text/css">
.alert-info {
    color: #3a87ad;
    background-color: #d9edf7;
    border-color: #bce8f1;
}
.myalert {
    padding: 15px;
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
	
	var content = "<b><span style='color:red;'>保单主要内容：</span></b><br>方案名称："+params.planName
	+"，保险期间: 自"+params.cargoStartDate+"起 至"+params.cargoEndDate+"止<br>";
	content = content+"车辆，车牌号: "+params.vehiclePlateNo+", 挂车牌号: "+params.trailerPlateNo+", 车架号: "+params.vehicleFrameNo;
	content = content+"<br>法人姓名: "+params.corporate
	+", 投保人名称: "+params.holderName+", 证件号码: "+params.holderCertNo
	+", 被保人名称: "+params.insuredName+", 证件号码: "+params.insuredCertNo;
	
	$("#mainContent").html(content);
	//layer.msg(content);
}
function confirmInsurance() {
    var readed=$("#readedPrompt").is(':checked');
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
<div class="animated zoomIn layerBox" style="width: 1086px; height: 486px; left: 77px; top: 66.8px;background: rgb(255, 255, 255);box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 3px;z-index: 11009;">
  <h4 class="layerHeader">
    <span>提示</span>
    <button id="prompt" type="button" data-dismiss="modal" aria-hidden="true" class="close close_btn" onclick="closePromptDiv();">
      <span class="white" style="font-weight: normal;">×</span></button>
  </h4>
  <div class="layerContianer layerBox" style="background-color: #d9edf7;box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 3px;overflow-x: hidden;overflow-y: auto;">
  
    <div style="height: 406px;overflow-x: hidden;overflow-y: auto;">
	  <div class="myalert alert-info" id="my_toast" style="left: 60px; top: 325.5px; min-width: 200px; opacity: 0.987958;">
	    <div style="text-align: left;display:inline-block;padding-right:5px;"><b><span style="color:red;">特别约定</span></b>
	    <br>1、以下运输区域禁止承保：西藏地区全域、新疆地区全域。
	    <br>2、不承担私人车辆运输货物的任何责任。
	    <br>3、放弃代位追偿：经双方同意，保险人同意放弃对于投保物流企业的代位追偿权，但保留对于其他实际承运人或责任方的代位追偿权。
	    <br>4、投保人须保证使用集装箱卡车或全封闭货车或用雨布全覆盖并用绳索有效扎紧的能适用于长途运输的卡车进行运输，否则保险人不承担任何责任。
	    <br>5、酒类运输，每次事故免赔额人民币10000元或损失金额的20%，以高者为准；每次及累计赔偿限额为15万。【仅针对方案1，每次事故赔偿限额为10万的方案，此条特约调整为：酒类运输，每次事故免赔额人民币10000元或损失金额的20%，以高者为准。】
	    <br>6、本保单附加合同终止条款：兹经合同双方同意，合同任何一方可提前十天以书面通知方式提出解除本保险合同，并按短期费率计算退保保费。在此情况下，本保险合同自投保人或保险人发出解除通知次日零时起满10天后终止，但是，在解除前已经开始的任何运输仍然受保。
	    <br></div>
	    <br><br><input id="readedPrompt" type="checkbox" style=""/><b><span style="font-size: 15px;">我已经阅读并同意特约条款</span></b>
	  </div>
	    
		<div style="padding: 0px;">
			<div class="line" style="border-top: solid 1px #1a7bb9;"></div>
		</div>
	  <div class="myalert alert-info" style="width: 100%;text-align: left;">
		<div id="mainContent" style="width: 100%;display:inline-block;padding-right:5px;"></div>
	  </div>
    </div>

    <div style="margin:3px auto">
	    <input id="ensure" class="subBtnmy btn-size" type="button" value="确认提交" onclick="confirmInsurance();"/>
	    <input id="back" class="btnmy btn-size" type="button" value="取消" onclick="closePromptDiv();"/>
    </div>
    <span class="layer-size"></span>
    
  </div>
</div>
</div>
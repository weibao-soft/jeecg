<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/ace/assets/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/main.css"/>

<script type="text/javascript">
function closeDiv() {
	var id = $("#tabId").val();
    $("#payDiv").hide();
	window.Utils.closeLoading();
    closeCurrent('tab_'+id);
}
function testPay() {
	var payUrl = "https://rbt.guorenpcic.com/paycenter/?orderId=049124fffa242b9bfa2&amp;code=&amp;payOrderNo=js02&amp;platform=pc";
	var frameObj=document.getElementById("payiFrame");
	frameObj.src=payUrl;
}
</script>
<input id="tabId" name="tabId" type="hidden" value=""/>
<div id="payDiv" class="overlay" style="z-index: 11009;display: none;">
<div class="animated zoomIn layerBox" style="width: 1286px; height: 599px; left: 177px; top: 66.8px; background: rgb(255, 255, 255); box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 3px; z-index: 11009;">
  <h4 class="layerHeader">
    <span>在线支付</span>
    <button id="pay" type="button" data-dismiss="modal" aria-hidden="true" class="close close_btn" onclick="closeDiv();">
      <span class="white" style="font-weight: normal;">×</span></button>
  </h4>
  <div class="layerContianer layerBox" style="background: rgb(255, 255, 255); box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 3px; overflow: hidden;">
    <iframe id="payiFrame" name="iframe" src="" width="100%" height="100%" frameborder="0" marginheight="0" marginwidth="0" scrolling="yes" target="_self" seamless="seamless"></iframe>
    <span class="layer-size"></span>
  </div>
</div>
</div>
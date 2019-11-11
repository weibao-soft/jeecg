<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>
<script src="plug-in/ace/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="plug-in/ace/assets/js/bootstrap.min.js"></script>
<script src="plug-in/ace/assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
  <script src="plug-in/ace/assets/js/excanvas.min.js"></script>
<![endif]-->
<!-- ace scripts -->
<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>
<script src="plug-in/ace/assets/js/ace-elements.min.js"></script>
<script src="plug-in/ace/assets/js/ace.min.js"></script>
<script src="plug-in/jquery/jquery.contextmenu.js"></script>
<script src="plug-in/layer/layer.js"></script>
<script src="plug-in/ace/js/bootbox.js"></script>
<!--add-start--Author:wangkun Date:20160813 for:内部聊天修改-->
<div class="easyui-layout" fit="true" id="lywidth_demo">
  <div region="center" style="padding:0px;border:0px">
  	<img  src="plug-in/accordion/images/account.png">
  	<div>
  		<div>
  			<h4>可提现</h4>
  		</div>
  		<dl>
  			<dd>
  				<span>余额：</span>
  				<span>${companyAcct.receivedBalance}</span>
  			</dd>
  		</dl>
  		
  		<div>提现</div><div onclick="getAcctBalanceList('${companyAcct.id}')">明细</div>
  		 
  	</div>
  	
  	<div>
  		<div>
  			<h4>待分润</h4>
  		</div>
  		<dl>
  			<dd>
  				<span>余额：</span>
  				<span>${companyAcct.unreceivedBalance}</span>
  			</dd>
  		</dl>
  		<div>明细</div>
  	</div>
  </div>
</div>
 <script type="text/javascript">
	$(document).ready(function (){
	
		var abc = $("#lywidth_demo").width()+17;
		$("#lywidth_demo").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
		
		//为了给产品列表的行添加鼠标点击事件
		//该鼠标点击事件触发父页面：productMainList.jsp（包含当前iframe的页面）的getCustomerList
		//再由父页面内触发另一个
	});
	function getAcctBalanceList(id){			
		parent.getAcctBalanceList(id);
	}
 </script>
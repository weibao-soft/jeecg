<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>

<link rel="stylesheet" type="text/css" href="plug-in/weibao/policy.css"/>
<style>
</style>
<div id="lywidth_demo" style="width:100%;">
<div style="width:100%;">
  <div id="demoapp" class="nav-container">
  <div class="nav-header">
	  	<img style="width:100%;vertical-align: middle;overflow:hidden;" src="plug-in/accordion/images/account.png">
	  	<div class="nav-header-grzh">
	  		<div class="label1"><h4>可提现</h4></div>
	  		<dl class="label2">
	  			<dd>
	  				<span class="yue">余额：</span>¥ <span id="p_now_fare" class="ye-money">${personalAcct.receivedBalance}</span>
	  			</dd>
	  		</dl>
	  		
	  		<div class="label3" onclick="getReAcctBalanceList('${personalAcct.id}')">明细</div>
	  		 
	  	</div>
	  	
	  	<div class="nav-header-ddz">
	  		<div class="label1"><h4>待分润</h4></div>
	  		<dl class="label2">
	  			<dd>
	  				<span class="yue">余额：</span>¥ <span id="p_wait_fare" class="ye-money">${personalAcct.unreceivedBalance}</span>
	  			</dd>
	  		</dl>
	  		<div class="label3" onclick="getUnreAcctBalanceList('${personalAcct.id}')">明细</div>
	  	</div>
  </div>
  
  <div class="nav-body">
  	<ul>
  		<li class="icon_zhxx" onclick="bindAccount()" >账户信息</li>
  		<li class="icon_zjjyjl" onclick="getWithdrawOrderList('${companyAcct.id}');">提现记录</li>  		
  	</ul>
  </div>
  
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
	function getReAcctBalanceList(id){			
		parent.getReAcctBalanceList(id);
	}
	function getUnreAcctBalanceList(id){			
		parent.getUnreAcctBalanceList(id);
	}
	
	//绑定公司账户
	function bindAccount(){
		var addurl = "personalAcctController.do?goBindAccount";
		//openwindow("个人账户信息绑定",addurl,"bindAcct",620,400);
		return;
	}
	//查询取现记录
	function getWithdrawOrderList(id){
	    if(window.console) console.log("withdrawOrderList == ", id);
		//addTab(id, "取现记录", "personalAcctController.do?withdrawOrderList&accountId="+id);
	}

	function addTab(ids, title_, url_) {		
		addTabs({id:ids,title:title_,close: true,url: url_});	
	}
</script>
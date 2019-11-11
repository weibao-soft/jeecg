<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>


<style>
* {
    font-family: 'Microsoft YaHei UI','Microsoft YaHei',DengXian,SimSun,'Segoe UI',Tahoma,Helvetica,sans-serif;
    font-size: 14px;
}
body {
    width: 100%;
    border: 0;
    margin: 0;
}
h4 {
    font-size: 18px;
    font-weight: 400;
    font-family: "Open Sans","Helvetica Neue",Helvetica,Arial,sans-serif;
    margin-top: 10px;
    margin-bottom: 10px;
}
dl {
    margin-top: 0;
    margin-bottom: 20px;
}
dd {
    margin-left: 0;
}
dd, dt {
    line-height: 1.42857143;
}

    .nav-container {
        background: url(../../assets/images/footer-bg.png) no-repeat 0px bottom;
        background-size: 100% 345px;
        position: absolute;
        left: 0px;
        right: 0px;
        bottom: 0px;
        top: 0px;
    }

    .nav-header {
        width: 760px;
        margin: 8% auto 40px;
        text-align: center;
        position: relative;
    }

    .nav-header-grzh {
        position: absolute;
        left: 0px;
        top: 0px;
        width: 50%;
        height: 100%;
    }

    .nav-header-ddz {
        position: absolute;
        left: 50%;
        top: 0px;
        width: 50%;
        height: 100%;
    }

    .label1 {
        position: absolute;
        left: 0px;
        top: 0px;
        right: 0px;
        text-align: left;
        padding-left: 25px;
        padding-top: 15px;
        color: white;
        letter-spacing: 2px;
    }

    .label2 {
        position: absolute;
        left: 0px;
        right: 0px;
        top: 70px;
        height: 60px;
    }

    .label2 dd {
        position: absolute;
        left: 0px;
        right: 0px;
        bottom: 0px;
        text-align: center;
        color: white;
    }

    .ye-money {
        font-size: 50px;
        font-weight: 800;
        color: white;
        margin-left: 5px;
    }

    .yue {
        margin-right: 5px;
    }

    .label3 {
        position: absolute;
        left: 0px;
        bottom: 0px;
        right: 0px;
        height: 54px;
        line-height: 44px;
        color: white;
        letter-spacing: 20px;
        font-size: 23px;
        cursor: pointer;
        padding-left: 23px;
    }

    .nav-body {
        height: 32px;
        text-align: center;
    }

    .nav-body * {
        box-sizing: border-box;
    }

    .nav-body ul, .nav-body ul li {
        margin: 0px;
        list-style: none;
        display: inline-block;
        height: 30px;
        line-height: 30px;
        color: #2EAFE8;
        font-size: 18px;
        letter-spacing: 2px;
    }

    .nav-body ul li {
        margin-right: 30px;
        cursor: pointer;
        border: solid 1px transparent;
        border-radius: 5px;
    }

    .nav-body ul li:hover {
        border: solid 1px #2EAFE8;
        box-sizing: border-box;
    }

    .icon_zhxx {
        background: url(../../assets/images/icon_zhxx.png) no-repeat;
        background-size: 32px 32px;
        padding-left: 34px;
    }

    .icon_bdxx {
        background: url(../../assets/images/icon_bdxx.png) no-repeat;
        background-size: 32px 32px;
        padding-left: 34px;
    }

    .icon_zjjyjl {
        background: url(../../assets/images/icon_zjjyjl.png) no-repeat;
        background-size: 32px 32px;
        padding-left: 34px;
    }
</style>
<div id="lywidth_demo" style="width:100%;">
<div style="width:100%;">
  <div id="demoapp" class="nav-container">
  <div class="nav-header" style="width:760px;">
	  	<img style="width:100%;vertical-align: middle;" src="plug-in/accordion/images/account.png">
	  	<div class="nav-header-grzh">
	  		<div class="label1"><h4>可提现</h4></div>
	  		<dl class="label2">
	  			<dd>
	  				<span class="yue">余额：</span>¥ <span id="p_now_fare" class="ye-money">${companyAcct.receivedBalance}</span>
	  			</dd>
	  		</dl>
	  		
	  		<div class="label3">提现</div>
	  		 
	  	</div>
	  	
	  	<div class="nav-header-ddz">
	  		<div class="label1"><h4>待分润</h4></div>
	  		<dl class="label2">
	  			<dd>
	  				<span class="yue">余额：</span>¥ <span id="p_wait_fare" class="ye-money">${companyAcct.unreceivedBalance}</span>
	  			</dd>
	  		</dl>
	  		<div class="label3" onclick="getAcctBalanceList('${companyAcct.id}')">明细</div>
	  	</div>
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
	function getAcctBalanceList(id){			
		parent.getAcctBalanceList(id);
	}
 </script>
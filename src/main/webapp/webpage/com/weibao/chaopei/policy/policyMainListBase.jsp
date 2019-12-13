<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/winEVMsgBox.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/policyMain.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/policyCommon.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/utils.js"></script>

<style type="text/css">
*{font-size:14px;}
div.datagrid-cell{font-size:14px;}
</style>
<div class="easyui-layout" fit="true" id="lywidth_demo">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="myPolicysManageList" checkbox="true" pagination="true" fitColumns="false" title="保单列表" actionUrl="policyMainController.do?datagrid" 
  		 idField="id" fit="true" collapsible="false" queryMode="group" sortName="createTime" sortOrder="desc" filter="true" pageSize="20">
   <t:dgCol title="操作" frozenColumn="true" field="opt" width="200"></t:dgCol>
   <t:dgFunOpt title="编辑" exp="payStatus#eq#0" funname="addTab(id)" urlclass="ace_button" urlfont="fa-edit" urlStyle="background-color:#1a7bb9;"/>
   <t:dgDelOpt title="删除" exp="payStatus#eq#0" url="policyMainController.do?doDel&policyId={id}&payStatus={payStatus}" urlclass="ace_button" urlfont="fa-trash-o" urlStyle="background-color:#ec4758;"/>
   <t:dgFunOpt title="支付" exp="status#eq#2&&payStatus#ne#1" funname="policyPay(id)" urlclass="ace_button"  urlfont="fa-check" urlStyle="background-color:#18a689;"/>
   <t:dgFunOpt title="发票信息" exp="status#eq#3&&payStatus#eq#1" funname="" urlclass="ace_button"  urlfont="fa-cog" urlStyle="background-color:#6fb3e0;"/>
   <t:dgFunOpt title="批改" exp="status#eq#3&&payStatus#eq#1" funname="policyChange(id)" urlclass="ace_button"  urlfont="fa-cog" urlStyle="background-color:#6fb3e0;"/>
   <t:dgFunOpt title="查看批改" exp="status#eq#3&&payStatus#eq#1" funname="viewPolicyChange" urlclass="ace_button"  urlfont="fa-cog" urlStyle="background-color:#6fb3e0;"/>

   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="50"></t:dgCol>
   <t:dgCol title="草稿ID"  field="draftId" hidden="true" queryMode="single" width="50"></t:dgCol>
   	<t:dgCol title="保单链接"  field="policyUrl" hidden="true" width="100"></t:dgCol>
	<t:dgCol title="支付时间"  field="payTimeFilter" hidden="true" formatter="yyyy-MM-dd" query="true" queryMode="group" sortable="false"  width="150"></t:dgCol>
	<t:dgCol title="创建日期"  field="createTimeFilter" hidden="true" formatter="yyyy-MM-dd" query="true" queryMode="group" sortable="false"  width="150"></t:dgCol>
	
   	<t:dgCol title="保单号"  frozenColumn="true" formatterjs="policyHref" field="policyNo" query="true" queryMode="single" sortable="false" width="165"></t:dgCol>
   	<t:dgCol title="车牌号"  frozenColumn="true" field="plateNo" query="true" queryMode="single" sortable="false" width="80"></t:dgCol>
   	<t:dgCol title="车架号"   field="frameNo" query="true" queryMode="single" sortable="false" width="160"></t:dgCol>   	
   	<t:dgCol title="保单状态"  field="status" sortable="false" query="true" queryMode="single" dictionary="poliStatus" width="70"></t:dgCol>
   	<t:dgCol title="支付状态"  field="payStatus" sortable="false" query="true" queryMode="single" dictionary="payStatus" width="70"></t:dgCol>
   	<t:dgCol title="保费"  field="premium" sortable="false" queryMode="single" width="60"></t:dgCol>
   	<t:dgCol title="创建日期"  field="createTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" sortable="false"  width="150"></t:dgCol>   	
   	<t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss" sortable="false"  width="150"></t:dgCol>   	
   	<t:dgCol title="投保人"  field="holderCompName" sortable="false" queryMode="single" width="220"></t:dgCol>
   	<t:dgCol title="投保人证件号"  field="holderOrgCode" sortable="false" queryMode="single" width="160"></t:dgCol>   	
   	<t:dgCol title="被保人"  field="insuredCompName" sortable="false" queryMode="single" width="220"></t:dgCol>
   	<t:dgCol title="被保人证件号"  field="insuredOrgCode" sortable="false" queryMode="single" width="160"></t:dgCol>
   	<t:dgCol title="发票类型" field="invoiceType" query="true" showMode="checkbox" dictionary="taxiType"  sortable="false"  width="80"></t:dgCol>
   	<t:dgCol title="发票号码" field="invoiceNumb" sortable="false" queryMode="single" width="90"></t:dgCol>
   	<t:dgCol title="发票接收手机" field="receiverMobile" sortable="false" queryMode="single" width="90"></t:dgCol>
   	<t:dgCol title="纳税号" field="taxpayerNo" sortable="false" queryMode="single" width="160"></t:dgCol>
   	<t:dgCol title="公司地址" field="compAddress" sortable="false" queryMode="single" showLen="20" width="300"></t:dgCol>
   	<t:dgCol title="公司电话" field="compPhone" sortable="false" queryMode="single" width="120"></t:dgCol>
   	<t:dgCol title="开户行" field="depositBank" sortable="false" queryMode="single" width="260"></t:dgCol>
   	<t:dgCol title="账号" field="bankAccount" sortable="false" queryMode="single" width="200"></t:dgCol>
   	<t:dgCol title="邮寄地址" field="taxiAddr" sortable="false" queryMode="single" showLen="26" width="360"></t:dgCol>
   	<t:dgCol title="产品方案" 	field="prodPlan" sortable="false" queryMode="single" showLen="22" width="300"></t:dgCol>
   	<t:dgCol title="保险公司"	field="insurCompName" sortable="false" queryMode="single" dictionary="ins_comp" width="100"></t:dgCol>
   	
   <t:dgToolBar title="批量支付" icon="icon-add" url="policyDraftController.do?insurancePays" funname="batchPay"></t:dgToolBar>
   
  </t:datagrid>
  </div>
 </div>
 
<%@include file="policyPayiFrame.jsp"%>
 <script type="text/javascript">
$(document).ready(function (){
	var abc = $("#lywidth_demo").width()+17;
	$("#lywidth_demo").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
	
	/*//为了给产品列表的行添加鼠标点击事件
	//该鼠标点击事件触发父页面：policyMainList.jsp（包含当前iframe的页面）的getCustomerList
	//再由父页面内触发另一个
	$("#myPolicysManageList").datagrid({		
		onClickRow: function (index, row) {
			//getCustomerList(row.id);
		}
	});
	*/
	
});

function batchPay(title,url,gname) {
	debugger;
	gridname=gname;
    var policyids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.setting.zIndex = getzIndex(true);
    	$.dialog.confirm("确定要支付这[ "+rows.length+" ]张保单吗？", function(r) {
    	    window.Utils.showLoading();
		    if (r) {
				for ( var i = 0; i < rows.length; i++) {
					policyids.push(rows[i].id);
				}
				policyPay(policyids.join(','));				
			}
		});
	} else {
		tip('请选择需要支付的保单');
	}
}

function getCustomerList(id){
	parent.getCustomerList(id);
}

function addTab(ids) {
    if(console) console.log(ids);
	addTabs({id:ids,title:'保单修改',close: true,url: "policyMainController.do?goUpdate&policyid="+ids});
}

function policyHref(value, row, index){	
	if (value != null && value != ''){		
		return '<a href="'+row.policyUrl+'" style="color:red" target="_blank" >'+value+'</a>';
	} 
}

function payStyle(val, row, index){
	var s1 = 'background-color:#18a689;color:#FFF;';
    var s2 = 'background-color:#3a87ad;color:#FFF;';
    var s3 = 'background-color:#21B9BB;';
    if (val =='1') {
        return s1
    }
    if (val =='0') {
        return s2
    }
    return s3
	
} 

//Ajax方式打开支付页面
function policyPay(id) {
    window.Utils.showLoading();
	var params = {};
	params.policyid = id;
    var mainTabId = "tab_402880ea6e26628b016e26665a0f0001";
	var url = "policyDraftController.do?insurancePays";
	ajaxPay(url, params, id, mainTabId);
	//reload();
}
//重新加载列表数据
function reload() {
    if(console) console.log("policy reload");
	$('#myPolicysManageList').datagrid('load',{});
}

function policyChange(id) {
	createwindow("批改", "policyMainController.do?addPolicyChange&insurancePolicyId="+id);
}

function viewPolicyChange() {
	self.parent.addOneTab('保单变更','policyChangeController.do?manager','icon-add')
}

 </script>
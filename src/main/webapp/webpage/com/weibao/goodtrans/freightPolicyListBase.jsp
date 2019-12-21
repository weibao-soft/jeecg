<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>

<style type="text/css">
*{font-size:14px;}
div.datagrid-cell{font-size:14px;}
</style>
<div class="easyui-layout" fit="true" id="lywidth_demo">
  <div region="center" style="padding:0px;border:0px;">
  <t:datagrid name="yonganFreightPolicyList" checkbox="false" fitColumns="false" title="保单列表" actionUrl="freightPolicyController.do?freightDatagrid" 
  		idField="id" fit="true" collapsible="true" queryMode="group" superQuery="true" filter="true" pageSize="20">
   <t:dgCol title="操作" frozenColumn="true" field="opt" width="200"></t:dgCol>
   <t:dgFunOpt title="编辑"  funname="" urlclass="ace_button" urlfont="fa-edit" urlStyle="background-color:#1a7bb9;"/>
   <t:dgDelOpt title="删除" exp="payStatus#eq#0" url="freightPolicyController.do?doDel&freightId={id}&payStatus={payStatus}" urlclass="ace_button" urlfont="fa-trash-o" urlStyle="background-color:#ec4758;"/>
   <t:dgFunOpt title="支付" exp="status#eq#2&&payStatus#ne#1" funname="policyPay(id)" urlclass="ace_button"  urlfont="fa-check" urlStyle="background-color:#18a689;"/>
   
   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
   	<t:dgCol title="保单链接"  field="policyUrl" hidden="true" width="100"></t:dgCol>
	<t:dgCol title="支付时间"  field="payTimeFilter" hidden="true" formatter="yyyy-MM-dd" query="true" queryMode="group" sortable="false"  width="150"></t:dgCol>
	<t:dgCol title="创建日期"  field="createTimeFilter" hidden="true" formatter="yyyy-MM-dd" query="true" queryMode="group" sortable="false"  width="150"></t:dgCol>
   
   	<t:dgCol title="保单号"  frozenColumn="true" formatterjs="policyHref" field="policyNo" query="true" queryMode="single" sortable="false" width="165"></t:dgCol>
   	<t:dgCol title="车牌号"  frozenColumn="true" field="vehiclePlateNo" query="true" queryMode="single" sortable="false" width="80"></t:dgCol>
   	<t:dgCol title="挂车牌号"  frozenColumn="true" field="trailerPlateNo" query="true" queryMode="single" sortable="false" width="80"></t:dgCol>
   	<t:dgCol title="车架号"   field="vehicleFrameNo" query="true" queryMode="single" sortable="false" width="160"></t:dgCol>   	
   	<t:dgCol title="保单状态"  field="status" sortable="false" query="true" queryMode="single" dictionary="poliStatus" width="70"></t:dgCol>
   	<t:dgCol title="支付状态"  field="payStatus" sortable="false" query="true" queryMode="single" dictionary="payStatus" width="70"></t:dgCol>
   	<t:dgCol title="保费"  field="allPremium" sortable="false" queryMode="single" width="60"></t:dgCol>
   	<t:dgCol title="创建日期"  field="createTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" sortable="false"  width="150"></t:dgCol>   	
   	<t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss" sortable="false"  width="150"></t:dgCol>   	
   	<t:dgCol title="投保人"  field="holderName" sortable="false" queryMode="single" width="220"></t:dgCol>
   	<t:dgCol title="投保人证件号"  field="holderCertNo" sortable="false" queryMode="single" width="160"></t:dgCol>   	
   	<t:dgCol title="被保人"  field="insuredName" sortable="false" queryMode="single" width="220"></t:dgCol>
   	<t:dgCol title="被保人证件号"  field="insuredCertNo" sortable="false" queryMode="single" width="160"></t:dgCol>
   	<t:dgCol title="发票号码" field="invoiceNumb" sortable="false" queryMode="single" width="90"></t:dgCol>
   	<t:dgCol title="联系人名称" field="holderCtatName" sortable="false" queryMode="single" width="90"></t:dgCol>
   	<t:dgCol title="联系人手机" field="holderCtatMobile" sortable="false" queryMode="single" width="90"></t:dgCol>
   	<t:dgCol title="办公电话" field="holderCtatPhone" sortable="false" queryMode="single" width="120"></t:dgCol>
   	<t:dgCol title="公司地址" field="holderAddress" sortable="false" queryMode="single" showLen="20" width="300"></t:dgCol>
   	<t:dgCol title="法人姓名" field="corporate" sortable="false" queryMode="single" width="90"></t:dgCol>
   	<t:dgCol title="法人证件号码" field="corporCertNo" sortable="false" queryMode="single" width="200"></t:dgCol>
   	<t:dgCol title="货物名称" field="goodsName" sortable="false" queryMode="single" width="80"></t:dgCol>
   	<t:dgCol title="邮寄地址" field="taxiAddr" sortable="false" queryMode="single" showLen="26" width="360"></t:dgCol>
   	<t:dgCol title="产品方案" 	field="prodPlan" sortable="false" queryMode="single" showLen="22" width="300"></t:dgCol>
   	<t:dgCol title="保险公司"	field="insurCompName" sortable="false" queryMode="single" dictionary="ins_comp" width="100"></t:dgCol>
   
   	<t:dgToolBar title="导出数据" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
$(document).ready(function (){

	var abc = $("#lywidth_demo").width()+17;
	$("#lywidth_demo").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
	
	/*//为了给产品列表的行添加鼠标点击事件
	//该鼠标点击事件触发父页面：draftMainList.jsp（包含当前iframe的页面）的getCustomerList
	//再由父页面内触发另一个
	$("#yonganFreightPolicyList").datagrid({		
		onClickRow: function (index, row) {
			//getCustomerList(row.id);
		}
	});
	*/
	
});
function getCustomerList(id){
	parent.getCustomerList(id);
}

function addTab(ids) {
    if(console) console.log(ids);
	addTabs({id:ids,title:'保单修改',close: true,url: "freightPolicyController.do?goUpdate&freightId="+ids});	
}

function policyHref(value, row, index){	
	if (value != null && value != ''){		
		return '<a href="'+row.policyUrl+'" style="color:red" target="_blank" >'+value+'</a>';
	} 
}

//导出
function ExportXls() {
	debugger;
	JeecgExcelExport("freightPolicyController.do?exportXls","yonganFreightPolicyList");
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
    if(console) console.log("drafts reload");
	$('#yonganFreightPolicyList').datagrid('load',{});
}
 </script>
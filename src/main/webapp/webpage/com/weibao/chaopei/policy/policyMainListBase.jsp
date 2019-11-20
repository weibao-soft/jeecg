<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/winEVMsgBox.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/policy/policyMain.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>

<style type="text/css">
*{font-size:14px;}
div.datagrid-cell{font-size:14px;}
</style>
<div class="easyui-layout" fit="true" id="lywidth_demo">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="policyMainList" checkbox="true" pagination="true" fitColumns="false" title="保单列表" actionUrl="policyMainController.do?datagrid" 
  		 idField="id" fit="true" collapsible="false" queryMode="group" superQuery="true" filter="true" pageSize="20">
   <t:dgCol title="操作" frozenColumn="true" field="opt" width="160"></t:dgCol>
   <t:dgFunOpt title="编辑"  funname="addTab(draftId)" urlclass="ace_button"  urlfont="fa-edit" urlStyle="background-color:#1a7bb9;"/>
   <t:dgFunOpt title="支付" exp="status#eq#2&&payStatus#ne#1" funname="policyPay(id)" urlclass="ace_button"  urlfont="fa-cog" urlStyle="background-color:#18a689;"/>
      
   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="50"></t:dgCol>
   <t:dgCol title="草稿ID"  field="draftId" hidden="true" queryMode="single" width="50"></t:dgCol>
   
   <t:dgCol title="投保人" frozenColumn="true" field="holderCompName" query="true" queryMode="single" width="220"></t:dgCol>
   <t:dgCol title="车牌号"  frozenColumn="true" field="plateNo" query="true" queryMode="single" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="车架号"  field="frameNo" query="true" queryMode="single" sortable="false" width="200"></t:dgCol>   
   <t:dgCol title="保费"  field="premium" queryMode="single" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="保单号" field="policyNo" formatterjs="policyHref" query="false" queryMode="single" width="200"></t:dgCol>
   <t:dgCol title="保单链接"  field="policyUrl" hidden="true" width="100"></t:dgCol>
   <t:dgCol title="保单状态"  field="status" query="true" queryMode="single" sortable="false" showMode="radio" dictionary="qpolStatus" width="100"></t:dgCol>
   <t:dgCol title="支付状态"  field="payStatus" queryMode="single" sortable="false" defaultVal='N' dictionary="payStatus" width="100" extendParams="styler:payStyle"></t:dgCol>
   <t:dgCol title="创建日期"  field="createTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single" width="160"></t:dgCol>
   <t:dgCol title="投保时间"  field="lastUpdateTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single" width="160"></t:dgCol>
   <t:dgCol title="用户姓名"  field="userName" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="产品名称"  field="prodName" query="true" queryMode="single" width="150"></t:dgCol>
   <t:dgCol title="产品代码"  field="prodCode" queryMode="single" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="保险公司"  field="insurCompName" query="true" queryMode="single" sortable="false" dictionary="ins_comp" width="100"></t:dgCol>
   
   <t:dgToolBar title="批量支付" icon="icon-add" url="policyDraftController.do?insurancePay" funname="batchPay"></t:dgToolBar>
   
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
$(document).ready(function (){

	var abc = $("#lywidth_demo").width()+17;
	$("#lywidth_demo").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
	
	/*//为了给产品列表的行添加鼠标点击事件
	//该鼠标点击事件触发父页面：policyMainList.jsp（包含当前iframe的页面）的getCustomerList
	//再由父页面内触发另一个
	$("#policyMainList").datagrid({		
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
	addTabs({id:ids,title:'保单修改',close: true,url: "policyMainController.do?goUpdate&policyid="+ids+"&isDraft="+false});	
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
	//if(param == null || param == "") {
	//	$.messager.alert('提示','请先核保再进行支付!','error');
	//	return false;
	//}
    //$(this).attr("disabled", true);
	
	var params = {};
	params.policyid = id;
	var url = "policyDraftController.do?insurancePays";
	ajaxPay(url, params, id);
}
//重新加载列表数据
function reload() {
	var iframe;// iframe操作对象
	var win;//窗口对象
	var windowapi;
	try {
		iframe = this.iframe.contentWindow;
		iframe.searchs();
		windowapi = frameElement.api;//内容页中调用窗口实例对象接口
		win = frameElement.api.opener;
		frameElement.api.close();
		win.reloadTable();
	} catch (e) {
	}
}
 </script>
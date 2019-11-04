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

<div class="easyui-layout" fit="true" id="lywidth_demo">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="draftMainList" checkbox="false" fitColumns="true" title="草稿列表" actionUrl="policyDraftController.do?datagrid" 
  		idField="id" fit="true" collapsible="true" queryMode="group" superQuery="true" filter="true" >
   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
   
   <t:dgCol title="方案保障"  field="productDetailEntity.prodPlan" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="投保单位名称"  field="holderCompName"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="收件人"  field="recipients" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="投保车辆(台)"  field="truckNums" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="保存时间"  field="saveTime" queryMode="single" width="120"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt title="修改保单"  funname="addTab(id)" urlclass="ace_button"  urlfont="fa-check" />
   <t:dgDelOpt title="删除" url="policyDraftController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/>
      
   <t:dgToolBar title="测试" icon="icon-edit" url="" funname="" width="100%" height="100%"></t:dgToolBar>
   
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
	$("#draftMainList").datagrid({		
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
	addTabs({id:ids,title:'保单修改',close: true,url: 'policyMainController.do?goUpdate&draftId='+ids});	
}
 </script>
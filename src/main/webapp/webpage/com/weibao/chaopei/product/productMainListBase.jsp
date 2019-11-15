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
  <t:datagrid name="productMainList" checkbox="false" fitColumns="false" title="产品列表" actionUrl="productMainController.do?datagrid" 
  		idField="id" fit="true" collapsible="true" queryMode="group" superQuery="true" filter="true" >
  	<t:dgCol title="操作" field="opt" width="100"  ></t:dgCol>
  	<t:dgFunOpt title="立即投保" funname="addTab(id)" urlclass="ace_button"  urlfont="fa-check" />
  	      
   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
   
   <t:dgCol title="商品名称"  field="prodName"  queryMode="single" width="260"></t:dgCol>
   <t:dgCol title="商品代码"  field="goodCode"  queryMode="single" width="150"></t:dgCol>
   <t:dgCol title="产品类型"  field="prodType" queryMode="single" width="150"></t:dgCol>
   <t:dgCol title="期限"  field="period"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="保险公司"  field="insurCompName" dictionary="ins_comp"  queryMode="single" width="200"></t:dgCol>   
   <t:hasPermission code="addProduct">
   <t:dgToolBar title="新增" icon="icon-add" url="productMainController.do?goAdd" funname="add" width="980" height="500"></t:dgToolBar>
   </t:hasPermission>
   <t:hasPermission code="editProduct">
   <t:dgToolBar title="编辑" icon="icon-edit" url="productMainController.do?goUpdate" funname="update" width="980" height="500"></t:dgToolBar>
   </t:hasPermission>
   <t:hasPermission code="batchDelProduct">
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="jformOrderMainController.do?doBatchDel" operationCode="" funname="deleteALLSelect"></t:dgToolBar>
   </t:hasPermission>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
$(document).ready(function (){

	var abc = $("#lywidth_demo").width()+17;
	$("#lywidth_demo").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
	
	//为了给产品列表的行添加鼠标点击事件
	//该鼠标点击事件触发父页面：productMainList.jsp（包含当前iframe的页面）的getCustomerList
	//再由父页面内触发另一个
	$("#productMainList").datagrid({		
		onClickRow: function (index, row) {
			getCustomerList(row.id);
		}
	});
	
});
function getCustomerList(id){
	parent.getCustomerList(id);
}
function addTab(ids) {		
	addTabs({id:ids,title:'保单投保',close: true,url: 'policyMainController.do?add&paramId='+ids});	
}

/**
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'jformOrderMainController.do?upload', "jformOrderMainList");
}

//导出
function ExportXls() {
	JeecgExcelExport("jformOrderMainController.do?exportXls","jformOrderMainList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("jformOrderMainController.do?exportXlsByT","jformOrderMainList");
}
*/
 </script>
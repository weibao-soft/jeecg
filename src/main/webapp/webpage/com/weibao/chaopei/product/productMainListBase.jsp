<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="lywidth_demo">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="productMainList" checkbox="false" fitColumns="true" title="产品列表" actionUrl="productMainController.do?datagrid" 
  		idField="id" fit="true" collapsible="true" queryMode="group" superQuery="true" filter="true" >
   <t:dgCol title="主键"  field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="产品名称"  field="name"  queryMode="single" query="true"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title" queryMode="single" query="true" width="120"></t:dgCol>
   <t:dgCol title="期限"  field="period"  queryMode="single" query="true" width="120"></t:dgCol>
   <t:dgCol title="保险公司"  field="company"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>   
   <t:dgDefOpt url="productMainController.do?goAdd" title="三生三世" />
   <t:dgDelOpt title="删除" url="jformOrderMainController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/>   
   <t:dgToolBar title="新增" icon="icon-add" url="productMainController.do?goAdd" funname="add" width="50%" height="50%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="jformOrderMainController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="jformOrderMainController.do?doBatchDel" operationCode="" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="导入数据" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出数据" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true" id="lywidth_demo">
  <div region="center" style="padding:0px;border:0px">
  
  <t:datagrid name="withdrawOrderList" checkbox="false" title="提现记录" actionUrl="companyAcctController.do?withdrawOrderDatagrid&accountId=${accountId}" 
  	idField="id" fit="true" fitColumns="true" queryMode="group" collapsible="true" pageSize="100" sortName="applyTime" sortOrder="desc">
      <t:dgCol title="主键" hidden="true" field="id"  queryMode="single"  width="0"></t:dgCol>

      <t:dgCol title="公司账户"  field="company" query="true" sortable="false" width="100"></t:dgCol>
      <t:dgCol title="个人账户"  field="person" query="true" sortable="false" width="100"></t:dgCol>
      <t:dgCol title="联系电话"  field="telphone" sortable="false" width="100"></t:dgCol>
      <t:dgCol title="提现金额"  field="amount" sortable="false" width="80"></t:dgCol>
      <t:dgCol title="支付宝账号"  field="alipayAcct" sortable="false"  width="100"></t:dgCol>
      <t:dgCol title="支付宝实名"  field="alipayName" sortable="false" width="100"></t:dgCol>
      <t:dgCol title="银行信息"  field="bankInfo" sortable="false" width="300"></t:dgCol>
      <t:dgCol title="申请日期"  field="applyTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="到账日期"  field="approvalTime" formatter="yyyy-MM-dd hh:mm:ss" sortable="false" width="100"></t:dgCol>
      <t:dgCol title="取现状态"  field="status" dictionary="wthdStatus" query="true" showMode="radio" sortable="false" width="80"></t:dgCol>
	
  </t:datagrid>
  
  </div>
</div>
<script type="text/javascript">
	$(document).ready(function (){
		var abc = $("#lywidth_demo").width()+17;
		$("#lywidth_demo").css("min-width", abc).css("padding-right","17px").css("box-sizing","border-box");
		
		//为了给产品列表的行添加鼠标点击事件
		//该鼠标点击事件触发父页面：withdrawOrderList.jsp（包含当前iframe的页面）的getOrderList
		//再由父页面内触发另一个
	
		$("#withdrawOrderList").datagrid({
			onClickRow: function (index, row) {
				getOrderList(row.id);
			}
		});
	
	});
	
	function getOrderList(id){
		parent.getOrderList(id);
	}


/*
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
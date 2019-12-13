<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="comAcctReceiveDetailList" checkbox="true" title="已分润明细" actionUrl="companyAcctController.do?acctReceiveDetailDatagrid&companyAccountId=${companyAccountId }" 
  	idField="id" fit="true" fitColumns="true" queryMode="group" collapsible="true" pageSize="100" sortName="payTime" sortOrder="desc">
   <t:dgCol title="主键" hidden="true" field="id"  queryMode="single"  width="0"></t:dgCol>   
   
   <t:dgCol title="保单号"  field="policyNo"  width="150" sortable="false"></t:dgCol>
   <t:dgCol title="车牌号"  field="plateNo" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="签单日期"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
   <t:dgCol title="产品名称"  field="prodName" sortable="false" width="300"></t:dgCol>
   <t:dgCol title="保费"  field="premium"  queryMode="single" sortable="false" width="120"></t:dgCol>
   <t:dgCol title="出单机构"  field="departName" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="出单员"  field="userName"  sortable="false" width="100"></t:dgCol>
   <t:dgCol title="分润金额"  field="amount" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="分润时间"  field="divideTime" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
   <t:dgCol title="结算状态"  field="rewardStatus" dictionary="rwdstatus" sortable="false" width="100"></t:dgCol>
   
	<t:dgToolBar  title="提现" icon="icon-save" url="companyAcctController.do?withdraw" funname="withdraw"></t:dgToolBar>
	
  </t:datagrid>
  </div>
</div>
 
<script type="text/javascript">

function getAcctBalanceList(id){		
	$('#comAcctReceiveDetailList').datagrid('load',{
		companyAccountId : id
	});
}

//提现
function withdraw(title,url,gname){
	debugger;
	var isNeedBind = ${isNeedBind}; 
	if(isNeedBind){
		var addurl = "companyAcctController.do?goBindAccount";
		openwindow("公司账户信息绑定",addurl,gname,620,400);
		return;
	}
	
	var rows = $("#"+gname).datagrid('getSelections');
				
	if (rows.length > 0) {
  	$.dialog.setting.zIndex = getzIndex(true);
  	$.dialog.confirm("确定要申请提现支付这[ "+rows.length+" ]笔分润保单吗？", function(r) {
			if (r) {
			var params=[];
				debugger;
				for ( var i = 0; i < rows.length; i++) {					
					params.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						params : params.join(',')
					},
					cache : false,
					success : function(data) {
						debugger;
						var d = $.parseJSON(data);
						if (d.success) {							
							var msg = d.msg;
							tip(msg);
							$("#"+gname).datagrid('unselectAll');
							$('#comAcctReceiveDetailList').datagrid('load',{								
							});							
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要申请提现的明细！")
		return false;
	}
}
</script>
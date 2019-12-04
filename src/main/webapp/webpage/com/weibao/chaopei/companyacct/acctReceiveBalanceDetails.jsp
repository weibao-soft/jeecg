<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="comAcctReceiveDetailList" checkbox="true" fitColumns="true" title="已分润明细" actionUrl="companyAcctController.do?acctReceiveDetailDatagrid" 
  	idField="id" fit="true" queryMode="group" collapsible="true" pageSize="100" sortName="payTime" sortOrder="desc">
   <t:dgCol title="主键" hidden="true" field="id"  queryMode="single"  width="0"></t:dgCol>   
   
   <t:dgCol title="保单号"  field="policyNo"  width="200" sortable="false"></t:dgCol>
   <t:dgCol title="车牌号"  field="plateNo" sortable="false" width="100"></t:dgCol>
   <t:dgCol title="签单日期"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
   <t:dgCol title="产品名称"  field="prodName" sortable="false" width="200"></t:dgCol>
   <t:dgCol title="保费"  field="price"  queryMode="single"  width="120"></t:dgCol>
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

 var selectIframe;//iframe对象
 var iframeCloseFlag = 1;//避免多次创建弹框
 $(function(){
	 //为了给列表添加双击变编辑模式的事件
	 /*
	  $('#productDetailList').datagrid({		  
		  onDblClickCell: function(index,field,value){
			  alert('bbb');
				$(this).datagrid('beginEdit', index);
				var ed = $(this).datagrid('getEditor', {index:index,field:"name"});
			 	//点击弹框
			 	$(ed.target).bind('click', function(){
					if(iframeCloseFlag==1){
						var defaultval = $(ed.target).val();
						openSelect(defaultval,"树选择弹框测试demo",this);
						iframeCloseFlag=0;
						$(this).focus();
					}
				}); 
				//输入事件
				$(ed.target).bind('input propertychange', function(){
					var defaultval = $(ed.target).val();
					selectIframe.iframe.contentWindow.initSelectTree(defaultval);
				});
			}
		}); 
	 */

 });
 
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
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
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
 
function getAcctBalanceList(id){		
	$('#acctReceiveDetailList').datagrid('load',{
		companyAccountId : id
	});
}
//打开选择框
function openSelect(dv,title,obj) {
	$.dialog.setting.zIndex = getzIndex(); 
	selectIframe = $.dialog({content: 'url:jformOrderMainController.do?departSelect&name='+dv, zIndex: getzIndex(),title: title, lock: false, width: '400px', height: '350px', opacity: 0.4, button: [
	   {name: '<t:mutiLang langKey="common.confirm"/>', callback: function (){callbackSelect(obj);}, focus: true}
	   /* ,
	   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){iframeCloseFlag = 1;}} */
   ], cancel:function (){iframeCloseFlag = 1;}}).zindex();
}

//回调函数,把值赋值到对应的属性上
function callbackSelect(obj) {
	var iframe = selectIframe.iframe.contentWindow;
	var treeObj = iframe.$.fn.zTree.getZTreeObj("departSelect");
	var nodes = treeObj.getCheckedNodes(true);
	if(nodes.length>0){
		var ids='',names='',codes='';
		for(i=0;i<nodes.length;i++){
		   var node = nodes[i];
		   ids += node.id+',';
		   names += node.name+',';
		   codes += node.code+',';
		}
		$(obj).val(nodes[0].name);
		iframeCloseFlag = 1;
	}
}

 </script>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>分配产品</title>
    <style>  
	  input.ui_state_highlight {
	    background: #18a689 none repeat scroll 0 0;
	    border: 1px solid #18a689;
	    color: #fff;
	    text-shadow: 0 -1px 1px #1c6a9e;
	    height: 30px;
	}  
	.ui_buttons input {
	    border: 1px solid #999;
	    border-radius: 3px;    
	    cursor: pointer;
	    display: inline-block;
	    height: 30px;
	    letter-spacing: 3px;
	    line-height: 1;
	    margin-left: 6px;
	    overflow: visible;
	    padding: 3px 10px 3px 12px;
	    text-align: center;
	    transition: box-shadow .2s linear 0s;
	}
  </style>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
		<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
		<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
 </head>
 <body style="overflow-x: hidden;">
 	<form id="formobj" action="productMainController.do?udpateAssignProd" name="formobj" method="post"> 			
		<%-- 
		<input id="id" name="id" type="hidden" value="${productEntityPage.id }"/>
		--%>
		<c:forEach items="${refList}" var="reflation">
			<c:if test="${reflation.assignStatus==1}">
				<%-- 
				<span class="icon ${operation.TSIcon.iconClas}">&nbsp;</span>
				--%>
				<input style="width: 20px;" type="checkbox" name="checkedProdctAssign" value="${reflation.productPlanId}" checked="checked" />${reflation.company} | ${reflation.productPlan}
			 </c:if>
			<c:if test="${empty reflation.assignStatus}">
				<%--
				<span class="icon group_add">&nbsp;</span>
				--%>
				<input style="width: 20px;" type="checkbox" name="checkedProdctAssign" value="${reflation.productPlanId}" />${reflation.company} | ${reflation.productPlan}
			 </c:if>
			<br>
		</c:forEach>
		<div class="ui_buttons">
		<input type="button" value="保存" class="ui_state_highlight" onclick="doSubmitForm()" />
		</div>
	</form>
 </body>
 <script src = "webpage/com/weibao/chaopei/product/productMain-add.js"></script>
  	<script type="text/javascript">
  		function jeecgFormFileCallBack(data){
  			if (data.success == true) {
				uploadFile(data);
			} else {
				if (data.responseText == '' || data.responseText == undefined) {
					$.messager.alert('错误', data.msg);
					$.Hidemsg();
				} else {
					try {
						var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
						$.messager.alert('错误', emsg);
						$.Hidemsg();
					} catch(ex) {
						$.messager.alert('错误', data.responseText + '');
					}
				}
				return false;
			}
			if (!neibuClickFlag) {
				var win = frameElement.api.opener;
				win.reloadTable();
			}
  		}
  		function upload() {
				$('#content').uploadify('upload', '*');
				$('#ctype').uploadify('upload', '*');
		}
		
		var neibuClickFlag = false;
		function neibuClick() {
			neibuClickFlag = true; 
			$('#btn_sub').trigger('click');
		}
		function cancel() {
				$('#content').uploadify('cancel', '*');
				$('#ctype').uploadify('cancel', '*');
		}
		function uploadFile(data){
			if(!$("input[name='id']").val()){
				if(data.obj!=null && data.obj!='undefined'){
					$("input[name='id']").val(data.obj.id);
				}
			}
			if($(".uploadify-queue-item").length>0){
				upload();
			}else{
				if (neibuClickFlag){
					alert(data.msg);
					neibuClickFlag = false;
				}else {
					var win = frameElement.api.opener;
					win.reloadTable();
					win.tip(data.msg);
					frameElement.api.close();
				}
			}
		}
		
		function doSubmitForm(){
			var form = document.getElementById('formobj');
			//再次修改input内容
			form.submit()
		}
  	</script>
	
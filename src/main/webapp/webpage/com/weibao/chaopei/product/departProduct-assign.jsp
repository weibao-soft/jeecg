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
		
		<input id="departid" name="departid" type="hidden" value="${departid}"/>
		
		<c:if test="${empty  refList }">
			无产品可分配，请先联系上级机构分配产品
		</c:if>
		
		<c:forEach items="${refList}" var="reflation">
			<c:if test="${reflation.assignStatus==1}">
				<%-- 
				<span class="icon ${operation.TSIcon.iconClas}">&nbsp;</span>
				--%>
				<input style="width: 20px;" type="checkbox" name="checkedProdctAssign" value="${reflation.id},${reflation.productPlanId}" checked="checked" />${reflation.company} | ${reflation.productName} | ${reflation.productPlan} | ${reflation.premium}
			 </c:if>
			<c:if test="${empty reflation.assignStatus}">
				<%--
				<span class="icon group_add">&nbsp;</span>
				--%>
				<input style="width: 20px;" type="checkbox" name="checkedProdctAssign" value="${reflation.id},${reflation.productPlanId}" />${reflation.company} | ${reflation.productName} | ${reflation.productPlan} | ${reflation.premium}
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

  	$(document).ready(function (){
		var updSuccess = ${updSuccess};
  		if(updSuccess){  			  			
  			$.messager.alert('提示','产品分配成功','info');
  		}
  		
  	});
  	
	function doSubmitForm(){
		//再次修改input内容
		var form = document.getElementById('formobj');
		form.submit()						
	}
			
</script>
	
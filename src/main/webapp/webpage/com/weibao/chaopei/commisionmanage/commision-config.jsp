<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>佣金配置</title>
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
	
	
	.table-tr-title{
	height: 26px;
	font-size: 12px;
	text-align: center;
	}
	.table-tr-content{
	height: 46px;
	background: #FFFFFF;
	text-align: center;
	font-size: 12px;
	}
	.normalEvenTD{
	background: #DFD8D8;
	}
	.normalOddTD{
	background: #FFFFFF;
	}
	.hoverTD{
	background-color: #eafcd5;
	height: 46px;
	text-align: center;
	font-size: 12px;
	}
	.trSelected{
	background-color: #51b2f6;
	height: 46px;
	text-align: center;
	font-size: 12px;
	}
	
  </style>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
		<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
		<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
 </head>
 <body style="overflow-x: hidden;"> 	
 	<t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" tiptype="1" action="commissionManager.do?udpateCommConf" callback="jeecgFormFileCallBack@Override"> 			
		
		<input id="departid" name="departid" type="hidden" value="${departid}"/>
		<input id="orgType" name="orgType" type="hidden" value="${orgType}"/>
		当前组织机构:${departname}
		<table  width="99%" class="list" style="word-break: break-all" border="0"
				align="center" cellpadding="0" cellspacing="1" bgcolor="#c0de98">
			<tr class="table-tr-title">
				<th align="left">保险公司</th>
				<th align="left">产品名称</th>
				<th align="left">产品方案</th>
				<th align="left">保费</th>
				<th align="left">分账时间</th>
<<<<<<< HEAD
				<c:if test="${isParent}">
				<th align="left">我的佣金比例</th>
				</c:if>
=======
				<th align="left">我的佣金比例</th>
>>>>>>> branch 'master' of https://github.com/weibao-soft/jeecg.git
				<th align="left">佣金比例设置</th>
			</tr>
			<c:if test="${empty  refList }">
				<tr  class="table-tr-content">
					<td colspan="7" align="left"> 无产品可设置佣金比例，请先联系上级机构设置佣金比例</td>
				</tr>
			</c:if>
			<c:forEach items="${refList}" var="reflation" varStatus="stuts">
			<tr  class="table-tr-content">
				<input name="commisConfList[${stuts.index}].id" type="hidden" value="${reflation.id}"/>				
				<input name="commisConfList[${stuts.index}].productPlanId" type="hidden" value="${reflation.productPlanId}"/>
				<td align="left">&nbsp;${reflation.company}</td>
				<td align="left">&nbsp;${reflation.prodName}</td>				
				<td align="left">&nbsp;${reflation.productPlan}</td>
				<td align="left">&nbsp;${reflation.premium}</td>		
				<td align="left">
					<c:choose>
						<c:when test="${isAdmin and isParent}">
							<input name="commisConfList[${stuts.index}].period" type="text" value="${reflation.period}" onblur="calculateDays(this);" style="width: 30px;"/>天	
						</c:when>
						<c:when test="${isAdminSub and isParent }">
							<input name="commisConfList[${stuts.index}].period" type="text" value="${reflation.period}" onblur="calculateDays(this);" style="width: 30px;"/>天
						</c:when>
						<c:when test="${isParent }">
							&nbsp;${reflation.parentPeriod}&nbsp;天
							<input name="commisConfList[${stuts.index}].period" type="hidden" value="${reflation.parentPeriod}" />
						</c:when>
						<c:otherwise>
							&nbsp;${reflation.period}&nbsp;天	
							<input name="commisConfList[${stuts.index}].period" type="hidden" value="${reflation.period}" />
						</c:otherwise>
					</c:choose>					
				</td>
				<c:if test="${isParent}">
				<td align="left">&nbsp;${reflation.parentRate}%</td>
				</c:if>
				<td align="left">
					<c:choose>
						<c:when test="${isParent}">
							<input name="commisConfList[${stuts.index}].rate" type="text" value="${empty reflation.rate ? 0.0 : reflation.rate}" style="width: 50px;"/>%
						</c:when>
						<c:otherwise>
							&nbsp;${empty reflation.rate ? 0.0 : reflation.rate}%
						</c:otherwise>
					</c:choose>					
				</td>	
			</tr>
			</c:forEach>			
		</table>
		
		<c:if test="${isParent}">
			<div class="ui_buttons">
			<input type="button" value="保存" class="ui_state_highlight" onclick="doSubmitForm()" />		
			</div>
		</c:if>
	</t:formvalid>
 </body>
  	<script type="text/javascript">		
	  	$(document).ready(function (){
			var updSuccess = ${updSuccess};
	  		if(updSuccess){  			  			
	  			$.messager.alert('提示','产品分配成功','info');
	  		}	  		
	  	});
  		
	  	function calculateDays(obj) {
	  		var numb = obj.value;	  		
	  		if(!isPositiveInteger(numb)) {
	  			//obj.value = 1;	  			
	  			layer.msg("分账时间只能输入正整数！", {icon:6});
	  			obj.focus();
	  		}
	  	}
	  	
	  	function isPositiveInteger(s){
	  	    var re = /^[0-9]+$/ ;
	  	    return re.test(s)
	  	}
	  	
		function doSubmitForm(){
			var form = document.getElementById('formobj');
			//再次修改input内容
			form.submit()
		}
		
		$(function(){
		///////datagrid选中行变色与鼠标经过行变色///////////////
		var dtSelector = ".list";
		var tbList = $(dtSelector);

		tbList.each(function() {
		var self = this;
		$("tr:even:not(:first)", $(self)).addClass("normalEvenTD"); // 从标头行下一行开始的奇数行，行数：（1，3，5...）
		$("tr:odd", $(self)).addClass("normalOddTD"); // 从标头行下一行开始的偶数行，行数：（2，4，6...）

		// 鼠标经过的行变色
		$("tr:not(:first)", $(self)).hover(
		function () { $(this).addClass('hoverTD');$(this).removeClass('table-td-content'); },
		function () { $(this).removeClass('hoverTD');$(this).addClass('table-td-content'); }
		);

		// 选择行变色
		$("tr", $(self)).click(function (){
		var trThis = this;
		$(self).find(".trSelected").removeClass('trSelected');
		if ($(trThis).get(0) == $("tr:first", $(self)).get(0)){
		return;
		}
		$(trThis).addClass('trSelected');
		});
		});
		});
		
  	</script>
	
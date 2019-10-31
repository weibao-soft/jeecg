<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>产品主信息</title>
    <style>
  .ui-button {
  	  display: inline-block;
	  padding: 2px 2px;
	  margin-bottom: 0;
	  font-size: 8px;
	  font-weight: normal;
	  line-height: 1.42857143;
	  text-align: center;
	  white-space: nowrap;
	  vertical-align: middle;
	  -ms-touch-action: manipulation;
      touch-action: manipulation;
	  cursor: pointer;
	  -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
      user-select: none;
	  background-image: none;
	  border: 1px solid transparent;
	  border-radius: 4px;
  }
  </style>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
		<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
		<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="productMainController.do?doAdd" callback="jeecgFormFileCallBack@Override">
					<input id="id" name="id" type="hidden" value="${productEntityPage.id }"/>
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">产品名称:</label>
			</td>
			<td class="value">
		     	<input id="prodName" name="prodName" type="text" style="width: 150px" class="inputxt"  datatype="*"  ignore="checked" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">产品名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">产品类型:</label>
			</td>
			<td class="value">
				<input id="prodType" name="prodType" type="text" style="width: 150px"  class="inputxt" datatype="*" ignore="checked"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">产品类型</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">产品期限:</label>
			</td>
			<td class="value">
		     	 <input id="period" name="period" type="text" style="width: 150px" class="inputxt"  datatype="*"  ignore="checked" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">产品期限</label>
			</td>
			<td align="right">
				<label class="Validform_label">保险公司:</label>
			</td>
			<td class="value">
				  <t:dictSelect field="compName" type="list" 
						typeGroupCode="ins_comp" defaultVal="N" hasLabel="false"  title="保险公司" ></t:dictSelect>     
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">保险公司</label>
			</td>
		</tr>
	</table>
	<div style="width: auto;height: 200px;">
		<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
		<div style="width:800px;height:1px;"></div>
		<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
		 <t:tab href="productMainController.do?productDetailList&id=${productEntityPage.id}" icon="icon-add" title="产品方案" id="productDetails"></t:tab>				
		</t:tabs>
	</div>
</t:formvalid>
			<!-- 添加 附表明细 模版 -->
	<table style="display:none">
	<tbody id="add_productDetails_table_template">
		<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
			  <td align="left">
				       	<input name="productDetailsList[#index#].prodPlan" maxlength="100" type="text" class="inputxt"  style="width:320px;" ignore="ignore" />
				  <label class="Validform_label" style="display: none;">产品计划</label>
			  </td>
			  <td align="left">
				  	<input name="productDetailsList[#index#].planType" maxlength="10" type="text" class="inputxt"  style="width:120px;"  ignore="ignore" />
				  <label class="Validform_label" style="display: none;">营运性质</label>
			  </td>			  
			  <td align="left">
				<input name="productDetailsList[#index#].price" maxlength="10" type="text" class="inputxt"  style="width:120px;"  datatype="d"  ignore="ignore" >
				<label class="Validform_label" style="display: none;">保费</label>
			  </td>
		</tr>
	</tbody>	
	</table>
 </body>
 <script src = "webpage/com/weibao/chaopei/product/productMain-add.js"></script>
  	<script type="text/javascript">
  		function jeecgFormFileCallBack(data){
  			if (data.success == true) {
  				debugger
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
  	</script>
	
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script type="text/javascript" src="webpage/com/weibao/chaopei/product/bootstrap-tab-product.js"></script>

<script type="text/javascript">
function commonQuery(url, params) {
 	//获取下拉框的数据
     $.ajax({
         url: url,
         type: "POST",
         data: params,
         dataType: "json",
         error: function () {
             layer.alert("服务器异常");
         },
         success: function (data) {
             //if(console) console.log("getCommonSelect == ", data);
             if (data.code == 200) {
             	if(console) console.log("value == ", data.value);
                 return false;
             } else {
                 layer.alert(data.message);
             }
         }
     });
}
function testzTree() {
 	var params = {};
 	params.parentIds = $("#citySelids").val();
 	layer.msg(params.parentIds);
 	var url = "policySubordController.do?getAllChildDeparts";
 	commonQuery(url, params);
}

function addTab() {
 	var ids = $("#citySelids").val();
	if(console) console.log(ids);
	addTabs({id:'getAllChildDeparts',title:'下级保单查询',close: true,url: 'policySubordController.do?subordinQuery&parentIds='+ids});	
}
 </script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <br/>
	<fieldset>
	<legend>选择下级机构</legend>
	<table>
		<tr>
			<td align="center" width="100px"><label class="Validform_label">机构树:</label></td>
			<td class="value"><t:selectZTree id="citySel" url="policySubordController.do?getTreeData" windowWidth="400px"></t:selectZTree> <span class="Validform_checktip"></span></td>
		</tr>

		<tr>
			<td align="center" width="100px"><label class="Validform_label"></label></td>
			<td class="value"><input class="btn" type="button" id="zTree" value="查询保单" onclick="addTab();" style="height:30px;width:100px !important;border-radius:5px"/></td>
		</tr>

	</table>
	</fieldset>
  </div>
 </div>

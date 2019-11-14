<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addProductDetailBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delProductDetailBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addProductDetailBtn').bind('click', function(){   		
 		var tr = $("#add_productDetails_table_template tr").clone();
	 	$("#add_productDetail_table").append(tr);
	 	resetTrNum('add_productDetail_table');
	 	return false;
    });  
	$('#delProductDetailBtn').bind('click', function(){   
      	$("#add_productDetail_table").find("input[name$='ck']:checked").parent().parent().remove();   
        resetTrNum('add_productDetail_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addProductDetailBtn" href="#">添加</a> <a id="delProductDetailBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="2" cellspacing="0" id="productDetail_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">序号</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">操作</td>
				  <td align="center" bgcolor="#EEEEEE" style="width: 86px;">
						方案代码
				  </td>
				  <td align="center" bgcolor="#EEEEEE" style="width: 226px;">
						方案名称
				  </td>
				  <td align="center" bgcolor="#EEEEEE" style="width: 356px;">
						产品计划
				  </td>
				  <td align="center" bgcolor="#EEEEEE" style="width: 86px;">
						营运性质
				  </td>
				  <td align="center" bgcolor="#EEEEEE" style="width: 86px;">
						保费
				  </td>
				  <td align="center" bgcolor="#EEEEEE" style="width: 26px;">
						排序
				  </td>				  
	</tr>
	<tbody id="add_productDetail_table">
	<c:if test="${fn:length(productDetailEntityList)  <= 0 }">
		<tr>
			<td align="center"><div style="width: 25px;" name="xh">1</div></td>
			<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
				<input name="productDetailsList[0].id" type="hidden"/>
				<input name="productDetailsList[0].prodId" type="hidden"/>
			<td align="left">
				<input name="productDetailsList[0].planCode" maxlength="10" type="text" class="inputxt"  style="width:80px;"  ignore="ignore"  >
			  	<label class="Validform_label" style="display: none;">方案代码</label>
			</td>
			<td align="left">
				<input name="productDetailsList[0].planName" maxlength="100" type="text" class="inputxt"  style="width:220px;"  ignore="ignore"  >
			  	<label class="Validform_label" style="display: none;">方案名称</label>
			</td>
			<td align="left">
				<input name="productDetailsList[0].prodPlan" maxlength="100" type="text" class="inputxt"  style="width:350px;"  ignore="ignore"  >
			  	<label class="Validform_label" style="display: none;">产品计划</label>
			</td>
			<td align="left">
				<input name="productDetailsList[0].planType" maxlength="10" type="text" class="inputxt"  style="width:80px;"  ignore="ignore" >
				<label class="Validform_label" style="display: none;">营运性质</label>
			</td>			
			<td align="left">
				<input name="productDetailsList[0].premium" maxlength="10" type="text" class="inputxt"  style="width:80px;"  datatype="d"  ignore="ignore" >
				<label class="Validform_label" style="display: none;">保费</label>
			</td>
			<td align="left">
				<input name="productDetailsList[0].sortNo" maxlength="10" type="text" class="inputxt"  style="width:20px;"  datatype="d"  ignore="ignore" >
				<label class="Validform_label" style="display: none;">排序</label>
			</td>
  			</tr>
	</c:if>
	<c:if test="${fn:length(productDetailEntityList)  > 0 }">
		<c:forEach items="${productDetailEntityList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
						<input name="productDetailsList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
						<input name="productDetailsList[${stuts.index }].prodId" type="hidden" value="${poVal.prodId }"/>
				<td align="left">
					<input name="productDetailsList[${stuts.index }].planCode" maxlength="100" type="text" class="inputxt"  style="width:80px;"  ignore="ignore" value="${poVal.planCode }" >
			  		<label class="Validform_label" style="display: none;">方案代码</label>
				</td>
				<td align="left">
					<input name="productDetailsList[${stuts.index }].planName" maxlength="100" type="text" class="inputxt"  style="width:220px;"  ignore="ignore" value="${poVal.planName }" >
			  		<label class="Validform_label" style="display: none;">方案名称</label>
				</td>
			   <td align="left">
				       	<input name="productDetailsList[${stuts.index }].prodPlan" maxlength="100" type="text" class="inputxt"  style="width:350px;"  ignore="ignore"  value="${poVal.prodPlan }"/>
				  <label class="Validform_label" style="display: none;">产品计划</label>
			   </td>
			   <td align="left">
				  	<input name="productDetailsList[${stuts.index }].planType" maxlength="10" type="text" class="inputxt"  style="width:80px;"  ignore="ignore"  value="${poVal.planType }"/>
				  <label class="Validform_label" style="display: none;">营运性质</label>
			   </td>
			   <td align="left">
				  	<input name="productDetailsList[${stuts.index }].premium" maxlength="10" type="text" class="inputxt"  style="width:80px;"  datatype="d"  ignore="ignore"  value="${poVal.premium }"/>
				  <label class="Validform_label" style="display: none;">保费</label>
			   </td>
			   <td align="left">
				  	<input name="productDetailsList[${stuts.index }].sortNo" maxlength="10" type="text" class="inputxt"  style="width:20px;"  datatype="d"  ignore="ignore"  value="${poVal.sortNo }"/>
				  <label class="Validform_label" style="display: none;">排序</label>
			   </td>				   			   
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>

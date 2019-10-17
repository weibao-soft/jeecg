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
		debugger
 		var tr =  $("#add_productDetails_table_template tr").clone();
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
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						产品计划
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						营运性质
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						保费
				  </td>				  
	</tr>
	<tbody id="add_productDetail_table">
	<c:if test="${fn:length(productDetailEntityList)  <= 0 }">
		<tr>
			<td align="center"><div style="width: 25px;" name="xh">1</div></td>
			<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
				<input name="productDetailsList[0].id" type="hidden"/>
				<input name="productDetailsList[0].productId" type="hidden"/>
			<td align="left">
				<input name="productDetailsList[0].productPlan" maxlength="100" type="text" class="inputxt"  style="width:120px;"  ignore="ignore"  >
			  	<label class="Validform_label" style="display: none;">产品计划</label>
			</td>
			<td align="left">
				<input name="productDetailsList[0].type" maxlength="10" type="text" class="inputxt"  style="width:120px;"  ignore="ignore" >
				<label class="Validform_label" style="display: none;">营运性质</label>
			</td>			
			<td align="left">
				<input name="productDetailsList[0].price" maxlength="10" type="text" class="inputxt"  style="width:120px;"  datatype="d"  ignore="ignore" >
				<label class="Validform_label" style="display: none;">保费</label>
			</td>
  			</tr>
	</c:if>
	<c:if test="${fn:length(productDetailEntityList)  > 0 }">
		<c:forEach items="${productDetailEntityList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
						<input name="productDetailsList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
						<input name="productDetailsList[${stuts.index }].productId" type="hidden" value="${poVal.productId }"/>
			   <td align="left">
				       	<input name="productDetailsList[${stuts.index }].productPlan" maxlength="100" type="text" class="inputxt"  style="width:120px;"  ignore="ignore"  value="${poVal.productPlan }"/>
				  <label class="Validform_label" style="display: none;">产品计划</label>
			   </td>
			   <td align="left">
				  	<input name="productDetailsList[${stuts.index }].type" maxlength="10" type="text" class="inputxt"  style="width:120px;"  ignore="ignore"  value="${poVal.type }"/>
				  <label class="Validform_label" style="display: none;">营运性质</label>
			   </td>
			   <td align="left">
				  	<input name="productDetailsList[${stuts.index }].price" maxlength="10" type="text" class="inputxt"  style="width:120px;"  datatype="d"  ignore="ignore"  value="${poVal.price }"/>
				  <label class="Validform_label" style="display: none;">保费</label>
			   </td>				   			   
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>

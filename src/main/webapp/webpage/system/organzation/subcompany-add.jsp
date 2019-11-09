<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加一级公司</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" callback="@Override callbackOrg" action="systemController.do?saveDepart" >
					<input id="id" name="id" type="hidden" />
					<input id="cc" type="hidden" name="TSPDepart.id" value="${pid }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="200">
						<label class="Validform_label">
							机构名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="departname" name="departname" type="text" style="width: 250px" class="inputxt" datatype="*" ignore="checked" placeholder="请输入机构名称" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">机构名称</label>
						</td>
				</tr>				
				<tr>
					<td align="right">
						<label class="Validform_label">
							机构类型:							
						</label>
					</td>
					<td class="value">
					     	 <%-- <select name="orgType" id="orgType"> 
					                 <option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>>公司</option> 
					                 <option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>>部门</option> 
					                 <option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>>岗位</option>
					         </select> --%>
					         <input name="orgType" id="orgType" type="radio" value="1" checked="checked"/> 机构 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">机构类型</label>
						</td>
				</tr>
				<tr>
					<td colspan="2">机构管理员信息</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							登录账号:
						</label>
					</td>
					<td class="value">
						<input id="userName" name="userName" type="text" style="width: 250px" class="inputxt"  datatype="s2-18" validType="t_s_base_user,userName,id" ignore="checked" placeholder="英文开头或加数字组成，例如：abcd1234"  />													
						<span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to18"/></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							登录密码:
						</label>
					</td>
					<td class="value">
						<input type="password" class="inputxt" value="" name="password" plugin="passwordStrength" datatype="*6-18" errormsg="" />
                    	<span class="passwordStrength" style="display: none;">
	                    <span><t:mutiLang langKey="common.weak"/></span>
                        <span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    </span>
                    <span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
                </td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							密码确认:
						</label>
					</td>
					<td class="value">
					    <input id="repassword" class="inputxt" type="password" recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！"/>
                    	<span class="Validform_checktip"><t:mutiLang langKey="common.repeat.password"/></span>
					</td>
				</tr>
				<tr>
					<td align="right" ><label class="Validform_label"> <t:mutiLang langKey="common.real.name"/>: </label></td>
					<td class="value">
		                <input id="realName" class="inputxt" name="realName" value="${user.realName }" datatype="s2-10"/>
		                <span class="Validform_checktip"><t:mutiLang langKey="fill.realname"/></span>
		            </td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
						</label>
					</td>
					<td class="value">
					     	<button type="button" class="blueButton" style="width:80px;height:30px" onclick="formSubmit();">保存 </button>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/system/position/tSCompanyPositionList.js"></script>		
<script type="text/javascript">
function formSubmit(){
	$('#btn_sub').click();
}

function callbackOrg(data){

	if(data.success==true){
		parent.tip(data.msg);
		parent.loadTree();
		location.reload();
		/* parent.layer.alert(data.msg, {
	        icon: 1,
	        shadeClose: false,
	        title: '提示'
	    },function(index){
	    	parent.loadTree();
			location.reload();
	    	parent.layer.close(index);
	    }); */
	}else{
		parent.tip(data.msg);
		/* parent.layer.alert(data.msg, {
	        icon: 0,
	        shadeClose: false,
	        title: '提示'
	    },function(index){
	    	parent.layer.close(index);
	    }); */
	}

	//tip(data.msg);
}
</script>
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
					<input id="cc" type="hidden" name="TSPDepart.id">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" disabled="disabled">
				<tr>
					<td align="right" style="width: 30%;height:30px">
						<label class="Validform_label">
							上级编码:
						</label>
					</td>
					<td class="value">
					         ${depart.TSPDepart.orgCode }
						</td>
				</tr>
				<tr>
					<td align="right" style="width: 30%;height:30px">
						<label class="Validform_label">
							上级名称:
						</label>
					</td>
					<td class="value">
					         ${depart.TSPDepart.departname }
						</td>
				</tr>
				<tr>
					<td align="right" style="width: 30%;height:30px">
						<label class="Validform_label">
							编码:
						</label>
					</td>
					<td class="value">
					         ${depart.orgCode }
						</td>
				</tr>
				<tr>
					<td align="right" style="width: 30%;height:30px">
						<label class="Validform_label">
							公司名称:
						</label>
					</td>
					<td class="value">
					         ${depart.departname }
					     	 <%-- <input id="departname" name="departname" value="${depart.departname }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公司名称</label> --%>
						</td>
				</tr>
				<tr>
					<td align="right" style="height:30px">
						<label class="Validform_label">
							机构类型:
						</label>
					</td>
					<td class="value">
					         机构
					     	<%--  <select name="orgType" id="orgType"> 
					                 <option value="1" >公司</option> 
					                 <option value="2" >部门</option> 
					                 <option value="3" >岗位</option>
					         </select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">机构类型</label> --%>
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
						${orgAdmin.userName }						
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							真实姓名:
						</label>
					</td>
					<td class="value">
						${orgAdmin.realName }
                	</td>
				</tr>
				
				<tr>
					<td colspan="2">提现账户信息</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							银行户名:
						</label>
					</td>
					<td class="value">
						${ empty companyAcct.bankAcctName ? "未绑定" : companyAcct.bankAcctName}						
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							银行账号:
						</label>
					</td>
					<td class="value">
						${empty companyAcct.bankNo ? "未绑定" :  companyAcct.bankNo}						
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							开户银行信息:
						</label>
					</td>
					<td class="value">
						${empty companyAcct.bankInfo ? "未绑定" : companyAcct.bankNo}						
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/system/position/tSCompanyPositionList.js"></script>		

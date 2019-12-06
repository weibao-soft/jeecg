<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>jeecg_demo</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
.subBtnmy{border:none;
	outline:none;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    color: #ffffff;
    display: block;
    cursor:pointer;
    margin: 0px auto;
    clear:both;
    padding: 5px 40px;
    text-shadow: 0 1px 1px #777;
    font-weight:bold;
    font-family:"Century Gothic", Helvetica, sans-serif;
    font-size:22px;
    -moz-box-shadow:0px 0px 3px #aaa;
    -webkit-box-shadow:0px 0px 3px #aaa;
    box-shadow:0px 0px 3px #aaa;
    background:#18a689;
}
.subBtnmy:hover {
    background:#d8d8d8;
    color:#666;
    text-shadow:1px 1px 1px #fff;
}
  </style>
  <script type="text/javascript">
  $(".ui_buttons").css("display","none");
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="personalAcctController.do?doBindAccount" >
		<input id="id" name="id" type="hidden" value="${personalAcct.id }"/>		
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							真实姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="realName" name="realName" type="text" style="width: 200px;height: 15px" class="inputxt"  datatype="*" value="${personalAcct.realName }"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">真实姓名</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							身份证号:
						</label>
					</td>
					<td class="value">
					     	 <input id="certiNo" name="certiNo" type="text" style="width: 200px;height: 15px" class="inputxt" datatype="*" value="${personalAcct.certiNo }"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">身份证号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							开户银行:
						</label>
					</td>
					<td class="value">
					     	 <input id="bankInfo" name="bankInfo" type="text" style="width: 200px;height: 15px" datatype="*" class="inputxt" placeholder="请输入XX银行XX省XX市XX支行" value="${personalAcct.bankInfo }"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">开户银行</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							银行账号:
						</label>
					</td>
					<td class="value">
					     	 <input id="bankNo" name="bankNo" type="text" style="width: 200px;height: 15px" datatype="d" class="inputxt" placeholder="请输入银行卡号" value="${personalAcct.bankNo }" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">银行账号</label>
						</td>
				</tr>
				
				<tr>
				<td align="right"><label class="Validform_label"> 提现密码: </label></td>
					<td class="value">
	                    <input type="password" class="inputxt" value="" name="withdrawPasswd" plugin="passwordStrength" datatype="*6-18" errormsg="" />
	                    <span class="passwordStrength" style="display: none;">
	                        <span><t:mutiLang langKey="common.weak"/></span>
	                        <span><t:mutiLang langKey="common.middle"/></span>
	                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
	                    </span>
	                    <span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
	                </td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.repeat.password"/>: </label></td>
					<td class="value">
	                    <input id="withdrawPasswd" class="inputxt" type="password" value="" recheck="withdrawPasswd" datatype="*6-18" errormsg="两次输入的密码不一致！"/>
	                    <span class="Validform_checktip"><t:mutiLang langKey="common.repeat.password"/></span>
	                </td>
				</tr>
							
			</table>
			<div style="margin:3px auto"><button type="submit" class="subBtnmy">提交</button></div>
		</t:formvalid>
 </body>

</html>
 <script type="text/javascript">
 $(function(){
	 $(".ui_buttons").css("display","none");
 });
 </script>
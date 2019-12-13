<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
    Long batchNum = new Date().getTime();
    request.setAttribute("batchNum", batchNum);
%>
<!DOCTYPE html>
<html>
<head>
    <title>文件列表</title>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="myBeforeSubmit">
    <input type="hidden" id="docId" <c:if test="${not empty doc }">value="${doc.id }"</c:if> >
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 变更说明: </label>
            <input name="desc" id="desc" datatype="s2-500" value="${doc.desc}" type="text">
            <span class="Validform_checktip">说明内容在0~500位字符，且不为空</span>
        </div>
        <div class="uploader-contener">
        <div class="form">
            <t:upload name="fiels" buttonText="上传文件" uploader="policyMainController.do?savePolicyChangeContent&insurancePolicyId=${insurancePolicyId}&batchNum=${batchNum}" extend="" id="file_upload" formData="desc"></t:upload>
        </div>
        <div class="form" id="filediv" style="height: 50px">
            <c:if test="${not empty attachment }">
                <div class="row" style="margin-left:20px;">
                        ${attachment.attachmenttitle }.${attachment.extend }
                </div>
            </c:if>
        </div>
        </div>
    </fieldset>
</t:formvalid>
<input type="hidden" id = "attachment" <c:if test="${not empty attachment }">value="1"</c:if> />
<script type="text/javascript">
    function myBeforeSubmit(){
        var fileLen = $("#filediv").find(".uploadify-queue-item").length;
        if($("#attachment").val()=="1"){
            //编辑页面
            if(fileLen>0){
                return upload();
            }else{
                var id = $("#docId").val();
                var desc = $("#desc").val();
                $.ajax({
                    async:false,
                    url:"policyMainController.do?updateDoc",
                    type:"POST",
                    data:{id:id,desc:desc},
                    dataType:"JSON",
                    success:function(data){
                        if(data.success){
                            var win = frameElement.api.opener;
                            win.reloadTable();
                            win.tip(data.msg);
                            frameElement.api.close();
                        }else{
                            tip(data.msg);
                        }
                    }
                });
            }
        }else{
            //新增页面
            if(fileLen>0){
                return upload();
            }else{
                tip("请选择文件")
                return false;
            }
        }
    }
</script>
</body>
</html>

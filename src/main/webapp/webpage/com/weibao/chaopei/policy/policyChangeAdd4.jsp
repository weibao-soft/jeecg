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
    <!-- http://hayageek.com/docs/jquery-upload-file.php#doc -->
    <t:base type="jquery-uploadfile"></t:base>
    <style>
        .hide-btn {
            width: 0;
            height: 0;
            border: 0;
            padding: 0;
            margin: 0;
        }
        .input-error-msg {
            font-size: 12px;
            display: none;
            color: red;
            white-space: nowrap;
            margin-left: 10px;
        }
        #fileuploader .input-error-msg {
            line-height: 200px;
        }
        .input-error-msg.show {
            display: inline-block;
        }
        .field-name {
            font-family: 'Microsoft YaHei UI','Microsoft YaHei',DengXian,SimSun,'Segoe UI',Tahoma,Helvetica,sans-serif;
            font-size: 14px;
            vertical-align: middle;
            display: -moz-inline-box;
            display: inline-block;
            width: 90px;
            text-align: left;
            text-overflow: ellipsis;
            -o-text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
            color: #666;
            text-shadow: 0px 0px 0px #fff;
        }
        input[type=text] {
            background-color: #fff;
            border: 1px solid #D7D7D7;
            border-radius: 3PX;
            height: 20PX;
            width: 300px;
            padding: 7px 0 7px 5px;
            line-height: 14PX;
            font-size: 12px;
            display: inline-block;
        }
        #fileuploader {
            margin-top: 20px;
        }
        .ajax-upload-dragdrop {
            padding: 0!important;
            display: inline-block;
        }
        .ajax-file-upload {
            margin: 80px 20px 80px 20px !important;
        }

    </style>
</head>
<body style="overflow-y: auto" scroll="no">
    <div class="input-field">
        <span class="field-name">变更说明：</span>
        <input type="text" id="desc" maxlength="500" minlength="2"/>
        <span class="input-error-msg">长度要求2～500个字符，且不能为空</span>
    </div>
    <div id="fileuploader">Upload</div>
    <button id="btn_sub" class="hide-btn"></button>
<script type="text/javascript">
    class PolicyChangeAdd {
        uploadObj;
        selectCount = 0;
        constructor() {
            this.submit = this.submit.bind(this);
            this.validDesc = this.validDesc.bind(this);
            this.validFile = this.validFile.bind(this);
            this.initDom();
            this.initEvent();
        }
        initDom() {
            $(document).ready(() =>
            {
                this.uploadObj = $("#fileuploader").uploadFile({
                    url:"policyMainController.do?savePolicyChangeContent&insurancePolicyId=${insurancePolicyId}&batchNum=${batchNum}",
                    fileName:"file",
                    multiple: true,
                    autoSubmit: false,
                    showProgress: true,
                    showPreview: true,
                    showDelete: true,
                    allowedTypes: "jpg,jpeg,png,gif",
                    uploadStr: "选择文件上传",
                    dynamicFormData() {
                        return {desc: $('#desc').val()};
                    },
                    onLoad: () => {
                        $('#fileuploader .ajax-upload-dragdrop').after('<span class="input-error-msg">没有选择任何文件</span>');
                    },
                    onSelect: () => {
                        this.selectCount++;
                        this.validFile();
                    },
                    deleteCallback: () => {
                        this.selectCount--;
                    },
                    afterUploadAll: () => {
                        frameElement.api.close();
                    }
                });
            });
        }
        initEvent() {
            $('#btn_sub').bind('click', this.submit);
            $('#desc').bind('input', this.validDesc);
        }
        submit() {
            let valid = this.valid();
            if (!valid) {
                return false;
            }
            this.uploadObj.startUpload();
        }
        valid() {
            let validArr = [];
            validArr.push(this.validDesc());
            validArr.push(this.validFile());
            for(let i = 0; i < validArr.length; i++) {
                if (!validArr[i]) {
                    return false;
                }
            }
            return true;
        }
        validDesc() {
            let descVal = $('#desc').val();
            if (!descVal || descVal.trim().length < 2 || descVal.length > 500) {
                $('#desc').next('.input-error-msg').addClass('show');
                return false;
            } else {
                $('#desc').next('.input-error-msg').removeClass('show');
            }
            return true;
        }
        validFile() {
            if (this.selectCount===0) {
                $('#fileuploader').children('.input-error-msg').addClass('show');
                return false;
            } else {
                $('#fileuploader').children('.input-error-msg').removeClass('show');
            }
            return true;
        }
    }
    new PolicyChangeAdd();
</script>
</body>
</html>

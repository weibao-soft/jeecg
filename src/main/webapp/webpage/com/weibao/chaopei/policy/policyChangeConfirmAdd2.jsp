<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
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
<div id="fileuploader">Upload</div>
<button id="btn_sub" class="hide-btn"></button>
<script type="text/javascript">
    class PolicyChangeConfirmAdd {
        uploadObj;
        selectCount = 0;
        constructor() {
            this.submit = this.submit.bind(this);
            this.validFile = this.validFile.bind(this);
            this.initDom();
            this.initEvent();
        }
        initDom() {
            $(document).ready(() =>
            {
                this.uploadObj = $("#fileuploader").uploadFile({
                    url:"policyChangeController.do?savePolicyChangeConfirm&policyChangeId=${policyChangeId}",
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
                        $('#policyChangeConfirmIframe',
                            frameElement.api.opener.parent.document)[0].contentWindow.PolicyChangeConfirm.initData();
                        frameElement.api.close();
                    }
                });
            });
        }
        initEvent() {
            $('#btn_sub').bind('click', this.submit);
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
            validArr.push(this.validFile());
            for(let i = 0; i < validArr.length; i++) {
                if (!validArr[i]) {
                    return false;
                }
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
    new PolicyChangeConfirmAdd();
</script>
</body>
</html>

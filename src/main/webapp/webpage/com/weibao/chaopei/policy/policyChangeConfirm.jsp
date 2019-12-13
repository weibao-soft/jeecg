<%--
  Created by IntelliJ IDEA.
  User: lilele
  Date: 2019/12/11
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html>
<head>
    <title>批改回复内容</title>
    <style>
        ul li {
            display: inline-block;
            margin: 10px;
        }
        li img {
            height: 100px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<ul id="container">
</ul>
<script type="text/javascript">
    class PolicyChangeConfirm {
        static mainId;
        static load(mainId) {
            this.mainId = mainId;
            this.initData();
        }
        static initData() {
            $.ajax({
                type: 'POST',
                url : 'policyChangeController.do?policyChangeConfirm',
                //dataType : 'json',
                data : {'policyChangeId': this.mainId},
                success: (data) => {
                    let dataObj = JSON.parse(data);
                    this.render(dataObj.obj);

                }
            });
        }
        static initEvents() {
            $('body').on('click', 'ul li img', function() {
                // let src = $(this).attr('src');
                let record = $(this).data('record')
                parent.PolicyChangeManager.viewImage(record.realpath, record);
            });
        }
        static render(contentArr) {
            $('#container').empty();
            let htmlArr = [];
            if (contentArr) {
                contentArr.forEach(function(content) {
                    let img = $('<img src="'+content.realpath+'">');
                    img.data('record', content);
                    let li = $('<li></li>');
                    li.append(img);
                    $('#container').append(li);
                    // htmlArr.push('<li><img src="'+content.realpath+'"></li>');
                });
            }
            // $('#container').html(htmlArr.join(""));
        }
    }
    PolicyChangeConfirm.initEvents();
    window.PolicyChangeConfirm = PolicyChangeConfirm;
    window.loadPage = PolicyChangeConfirm.load.bind(PolicyChangeConfirm);
</script>
</body>
</html>

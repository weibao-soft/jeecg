<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" href="plug-in/easyui/themes/metrole/easyui.css" type="text/css"></link>
    <link rel="stylesheet" href="plug-in/easyui/themes/metrole/icon.css" type="text/css"></link>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css" type="text/css"></link>
    <link rel="stylesheet" href="plug-in/mutitables/mutitables.mainpage.css" type="text/css"></link>
    <t:base type="jquery,easyui,tools"></t:base>
    <script type="text/javascript" src="plug-in/themes/fineui/jquery/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="plug-in/layer/layer.js"></script>
    <script type="text/javascript" src="plug-in/mutitables/mutitables.mainpage.js" ></script>
    <script type="text/javascript" src="plug-in/mutitables/jquery.resize.y.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:1px;overflow-x:hidden;">
        <div class="tab-opts-menu" id="tab-menus-main" style="font-size:14px">
            <div class="opts-menu-box">
                <div class="menus active table-menu-1" style="top:30px">
                    <!-- 主表菜单 -->
                    <t:menuButtons codes="" name="jformOrderMain2" mm="true"></t:menuButtons>
                </div>
                <div class="menus table-menu-0" style="top:-1px">
                    <a title="查询" onclick="PolicyChangeManager.queryPolicyChangeList()" href="####" class="btn-menu fa fa-search menu-more"></a>
                </div>
            </div>
        </div>

        <!-- 上方 -->
        <div  id="accDiv" class="easyui-accordion" data-options="multiple:true" style="padding-right:0px;overflow-x:hidden;box-sizing: border-box;">
            <div title="批改信息查询" data-options="iconCls:'icon-ok'" style="padding-top:5px;">
                <!-- 查询DIV 主表必须配置有高级查询才可用 -->
                <div id="associated_query" style="">
                    <form class = "page-query">
			  	<span style="max-width: 83%;display: inline-block;display:-moz-inline-box;">
					<span style="display:-moz-inline-box;display:inline-block;margin-bottom:2px;text-align:justify;">
						<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;" >
							保单号：
						</span>
						<input type="text" id="policyNo" style="width: 180px" class="inuptxt">
					</span>
				</span>
                    </form>
                </div>
            </div>

            <!-- 主表 -->
            <div title="变更信息" data-options="iconCls:'icon-ok',selected:true"  >
                <div id="easyui_mainList" class="resize-y-iframe" style="height:286px;padding-bottom:6px;">
                    <iframe id="mainList" height="99%" width="100%" frameborder="0"
                            src="${webRoot}/policyChangeController.do?list"></iframe>
                </div>
            </div>
        </div>
        <!-- 上方end -->

        <!-- 从表菜单 -->
        <div class="tab-opts-menu" id="tab-menus-attached" style="font-size:14px">
            <div class="opts-menu-box">
                <div class="menus testContractItem-ul active">
                    <t:menuButtons codes="" name="policyChangeContent"></t:menuButtons>
                </div>

                <div class="menus testContractPart-ul">
                    <t:menuButtons codes="" name="policyChangeConfirm"></t:menuButtons>
                </div>
            </div>
        </div>

        <!-- 从表 -->
        <div id="tabsok" style="height:300px">
            <div title="变更内容" data-options="closable:false" style="overflow:hidden;box-sizing: border-box;">
                <iframe id="policyChangeContentIframe" scrolling="yes" frameborder="0" height="100%" width="100%"
                        src="${webRoot}/policyChangeController.do?contentList">
                </iframe>
            </div>
            <div title="变更回复" data-options="closable:false" style="overflow:hidden;box-sizing: border-box;">
                <iframe id="policyChangeConfirmIframe" scrolling="yes" frameborder="0" height="100%" width="100%"
                        src="${webRoot}/policyChangeController.do?confirmList">
                </iframe>
            </div>
        </div>
        <!-- 从表end -->

    </div>
    <!-- center end -->
</div>

<div style="display:none">
    <!-- 激活选项卡再刷新页面需要该隐藏域 -->
    <input type="hidden" id="mainPageHiddenId">
    <select id="mainPageFrameActived" style="display:none">
        <option value="policyChangeContent" selected="selected"></option>
        <option value="policyChangeConfirm"></option>
    </select>
</div>
<script type="text/javascript">
    class PolicyChangeManager {
        static mainTabSelectedItem = {};
        static init() {
            $(function(){
                initdivwidth();
                $(window).resize(function(){
                    initdivwidth();
                });
                var tabsok = $('#tabsok').tabs({
                    narrow: true,
                    tabPosition:'top',
                    noheader:true,
                    tools:[{iconCls:'accordion-collapse',handler:function(){diyAccordianForTabs(this,'tabsok',500)}}],
                    onSelect:function(title,index){
                        toggleMenus(index);
                        PolicyChangeManager.initSubTab(index);
                    }
                });

                $("#tabsok").find(".tabs-header .tabs-wrap").click(function(event){
                    //event.stopPropagation();
                    var tagname = event.target.tagName.toLowerCase();
                    if(tagname=='ul'){
                        $("#tabsok").find(".tabs-header .tabs-tool").find("a.l-btn").trigger("click");
                        //event.stopPropagation();
                    }else{
                        if($("#tabsok").find(".tabs-tool").find('span.l-btn-icon').hasClass("accordion-expand")){
                            return false;
                        }
                    }
                });

                var lenOffset = getQueryareaRow();
                var menu_top1 = (78+lenOffset*30)+"px",menu_top2 = '30px';
                $('#accDiv').accordion({
                    firstbuybuy:true,
                    secondbuybuy:function(go){
                        var state = go=="collapse"?true:false;
                        toggleMainMenusTop(menu_top1,menu_top2,state);
                    },
                    onSelect:function(title,index){
                        $('#tab-menus-main').find('.table-menu-'+index).addClass("active");
                    },
                    onUnselect:function(title,index){
                        $('#tab-menus-main').find('.table-menu-'+index).removeClass("active");
                    }
                });
            });
        }
        static onMainTabSelected(id) {
            this.mainTabSelectedItem.id = id;
            this.initSubTab(0);
        }
        static initSubTab(idx) {
            let loadPageContent = $('#policyChangeContentIframe')[0].contentWindow.loadPage;
            let loadPageConfirm = $('#policyChangeConfirmIframe')[0].contentWindow.loadPage;
            if (loadPageContent) {
                loadPageContent(this.mainTabSelectedItem.id);
            }
            if (loadPageConfirm) {
                loadPageConfirm(this.mainTabSelectedItem.id);
            }
        }
        static viewImage(path, record) {
            let imageDialog = $.dialog({
                id: 'imageView',
                title: "图片查看",
                max: true,
                min: false,
                drag: true,
                resize: true,
                width: 800,
                top: 20,
                content: '<img src="'+path+'" style="width:100%"/></div>',
                lock:true,
                cancel: true,
                cancelVal: '确定',

            });
            imageDialog.button({
                name: "下载",
                callback() {
                    var url = "commonController.do?viewFile&fileid="+record.id+"&subclassname="+record.subclassname;
                    var xhr = new XMLHttpRequest();
                    xhr.open('GET', url, true);//get请求，请求地址，是否异步
                    xhr.responseType = "blob";  // 返回类型blob
                    xhr.onload = function () {// 请求完成处理函数
                        if (this.status === 200) {
                            var blob = this.response;// 获取返回值
                            var a = document.createElement('a');
                            a.download = record.attachmenttitle+"."+record.extend;
                            a.href=window.URL.createObjectURL(blob);
                            a.style.display = 'none';
                            a.click();
                        }
                    };
                    // 发送ajax请求
                    xhr.send();
                    return false;
                }
            });
        }
        static queryPolicyChangeList() {
            let policyNo = $('#policyNo').val();
            $('#mainList')[0].contentWindow.queryPolicyChangeList(policyNo);
        }
    }
    PolicyChangeManager.init();
    window.PolicyChangeManager = PolicyChangeManager;
</script>
<!-- 需要改变的是每个iframe的src;子表iframe的id ;mainPageFrameActived中的option的value -->
</body>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>佣金配置</title>
<t:base type="jquery,easyui,tools"></t:base>
<link rel="stylesheet" href="plug-in/ztree/css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/ztreeCreator.js"></script>
</head>
<body>
	<div class="easyui-layout" fit="true" scroll="no">
		<div  data-options="region:'west',title:'机构用户列表',split:true" style="width:200px;overflow: auto;">
		   <!-- update-begin--Author:Yandong  Date:20180402 for： TASK #2601 【严重样式问题】我的组织机构，在shortcut风格下样式有问题-->		   
			<!-- update-begin--Author:Yandong  Date:20180402 for： TASK #2601 【严重样式问题】我的组织机构，在shortcut风格下样式有问题-->
			 <div class="clear"></div> 
	        <div id="orgTree" class="ztree"></div>
	        <input type="hidden" id="userName" name="userName" value="${userName}"/>
		</div>
		<div data-options="region:'center'" title="">
    <!-- <iframe width="100%" height="100%" id="center"  src="" style="border:1px #fff solid; background:#fff;"></iframe> -->
		<div id="tt" tabPosition="top" border=flase style="width:95%;height:100%;margin:0px;padding:0px;overflow-x:hidden;width:auto;" class="easyui-tabs" fit="true"></div>
        </div>
	</div>
</body>
</html>
<script>

$(function() {	
	loadTreeNodes();
});
var flag = true;
var TimeFn = null;
var currOrgId,currOrgOpt;//设置当前右击事件的部门Id以及操作类型0-add;1-edit;2-del

function addtt(title, url, id, icon, closable) {
	
	$('#tt').tabs('add',{
						id : id,
						title : title,
						content : createFramett(id,url),
						closable : closable = (closable == 'false') ? false
								: true,
						icon : icon
	});
}

function createFramett(id,url) {
	var s = '<iframe id="'+id+'" scrolling="yes" frameborder="0"  src="'+url+'" width="100%" height="100%"></iframe>';
	return s;
}


//beforeDblClick事件
function beforeDbl(){
	flag = false;	
	return true;
}
//加载树
var orgTree ;

function showIndex(a,b,c){
	if(!c){
		//第一次进来加载第一个节点信息
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var node =treeObj.getNodes()[0];
		if(!!node){
			$("#"+node.tId+" a").click();
		}
	}else{
		if(currOrgOpt==1){
			//编辑点击当前结点
			var treety = $.fn.zTree.getZTreeObj("orgTree");
			var currentNode = treety.getNodeByParam("id",currOrgId, null);
			$("#"+currentNode.tId+" a").click();
		}else if(currOrgOpt==0){
			//新增点击新增节点
			var childs = c.children;
			if(!!childs && childs.length>0){
				var last = childs[childs.length-1];
				$("#"+last.tId+" a").click();
			}
		}
		
	}
}

function loadTreeNodes() {
	var zNodes;
	debugger
	var ztreeCreator = new ZtreeCreator('orgTree',"","")
	
 			.setCallback({onClick:zTreeOnLeftClick,onDblClick:zTreeOnDblClick,beforeDblClick:beforeDbl,onAsyncSuccess:showIndex})
 			
 			.setAsync({
                enable: true,
				url: "commissionManager.do?getMyTreeDataAsync",
				autoParam: ["id","parentId"],//提交参数
				type: 'get',
			    dataType: 'json',
			    dataFilter: filter,
            }) 
 			.initZtree({},function(treeObj){
 				orgTree = treeObj
 				});
};

function filter(treeId, parentNode, childNodes){
	if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}

//左击
function zTreeOnLeftClick(event, treeId, treeNode) {
	
	var selectNode = getSelectNode();	
	flag = true;
	clearTimeout(TimeFn);
	setTimeout(function(){
		if(flag){
			curSelectNode = treeNode;
			var parentId = treeNode.id;
			var orgType = treeNode.orgType;			
			closeAllTab();			
			/*
			if(orgType=="1"){
				var url = "organzationController.do?myUserOrgList&departid="+treeNode.id;
				addtt('用户信息', url, '02','icon-user-set', 'false');
				url = "tSCompanyPositionController.do?list&companyId="+treeNode.id;;
				addtt('职务信息', url, '03','icon-chart-organisation', 'false');
				url = "organzationController.do?comDetail&id="+treeNode.id;
				addtt('基本信息', url, '01','icon-comturn', 'false');
			}else if(orgType=="4"){
				var url = "organzationController.do?myUserOrgList&departid="+treeNode.id;
				addtt('用户信息', url, '02','icon-user-set', 'false');
				url = "tSCompanyPositionController.do?list&companyId="+treeNode.id;;
				addtt('职务信息', url, '03','icon-chart-organisation', 'false');
				url = "organzationController.do?comDetail&id="+treeNode.id;
				addtt('基本信息', url, '01','icon-comturn', 'false');
			}else if(orgType=="9"){
				//var url = "organzationController.do?comDetail&id="+treeNode.id;
				//addtt('基本信息', url, '01','icon-comturn', 'false');
			}else{
				var url = "organzationController.do?myUserOrgList&departid="+treeNode.id;
				addtt('用户信息', url, '02','icon-user-set', 'false');
				url = "organzationController.do?comDetail&id="+treeNode.id;
				addtt('基本信息', url, '01','icon-comturn', 'false');
			}
			*/
			//	判断点击的节点是否当前操作用户所属机构的直接下属机构			
			var url = "commissionManager.do?departCommissionList&departid="+treeNode.id+"&orgType="+orgType;
			addtt('产品佣金配置列表', url, '01','icon-user-set', 'false');

			$("#tt").tabs("select", 0);
		}
	},301);
};


//双击事件
function zTreeOnDblClick(event, treeId, treeNode) {
	var selectNode = getSelectNode();
	curSelectNode = treeNode;	
	//var url = "functionGroupController.do?groupRel&id="+selectNode.id;
	//$("#listFrame").attr("src", url);
	//var url = "autoFormController/af/employee_leave_form/goAddPage.do";
	//addtt('基本信息', url, '1','icon-search', 'false');
}

function refreshNode() {
	loadTreeNodes();
};

//添加下级公司
function addSubCompany() {
	var selectNode = getSelectNode();
	//if(selectNode.level == 1) {
	//	tip('不可再添加下级节点');
	//	return false;
	//}
	if (!selectNode) 	return;
	closeAllTab();
	//var url = "functionGroupController.do?add&id="+selectNode.id;
	//$("#listFrame").attr("src", url);
	var url = "organzationController.do?toAddSubCompany&pid="+selectNode.id;
	addtt('添加下级公司', url, '01','icon-search', 'false');
};



function closeAllTab(){
	var tabs = $('#tt').tabs("tabs");
	var length = tabs.length;
	
    for(var i=0; i<length; i++){
    	var onetab = tabs[0];
        var title = onetab.panel('options').tab.text();
        $("#tt").tabs("close", title);
    }
	
}

//选择资源节点。
function getSelectNode() {
	orgTree = $.fn.zTree.getZTreeObj("orgTree");
	var nodes = orgTree.getSelectedNodes();
	var node = nodes[0];
	return node;
};

</script>

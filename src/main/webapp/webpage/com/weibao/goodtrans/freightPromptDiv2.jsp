<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="plug-in/ace/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/toast.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/utils.js"></script>
<script type="text/javascript" src="webpage/com/weibao/chaopei/common/driveValid.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/ace/assets/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/main.css"/>
<link rel="stylesheet" type="text/css" href="plug-in/weibao/popdiv.css"/>

<style type="text/css">
.alert-info {
    color: #3a87ad;
    background-color: #d9edf7;
    border-color: #bce8f1;
}
.alert {
    padding: 15px;
    margin-bottom: 0px;
    border: 1px solid transparent;
    border-radius: 4px;
}
.line {
    font-size: 0px;
    line-height: 0px;
    border-top: solid 1px #eee;
    float: none;
}
</style>
<SCRIPT type="text/javascript">
$(document).ready(function() {
});

function openRemindDiv() {
    $("#toast_jzcbl").show();
}
function closeRemindDiv() {
    $("#toast_jzcbl").hide();
}
</SCRIPT>

<div id="toast_jzcbl" class="alert alert-info" style="display: block;opacity: 1; min-width: 200px; top: 96px; left: 107.5px;">
  <button type="button" class="close" data-dismiss="" onclick="closeRemindDiv();">
    <i class="ace-icon fa fa-times"></i>
  </button>
  <strong style="margin-right:10px">
    <i class="ace-icon fa fa-info-circle" style="margin-right:5px"></i>
  </strong>
  <div style="display:inline-block;padding-right:30px;"><b><span style='font-size:16px;color:red;'>禁止承保类：</span></b>
    <br>1、鲜活动植物、瓜果蔬菜、农产品如花生、玉米、豆类、大小麦、豆粕、鱼粉、大蒜、白糖、葵花子、冷藏冷冻品、罐头食品。
    <br>2、超过60吨或高度超5米的变压器、精密仪器及设备、大件设备、大型港口机械设备、大型铸件、风力发电相关设备、商品车、航空器及航空发动机、游艇、船舶及船舶发动机及有关运输设备。
    <br>3、国际及国内法律法规规定的危险货物、各种压缩气体、武器弹药及其零配件、原油、各类矿砂、矿粉（铁精粉除外）、直接还原铁、原木。
    <br>4、玻璃制品、陶瓷制品、泥及石膏制品、石材类等易碎物品。
    <br>5、疫苗针剂等液体药品及血液制品、乐器、贵金属、工艺品（包括各类雕刻、编织、艺术品、刺绣、古董、字画、瓷器、玉雕及其他玉石制品等）、除主要材料为板材的其他材质家具。
    <br>6、行李、文件、计算机软件、现金、有价证券、旧品、二手品、展览后之展品和陈列品及其他难以客观确定保险价值的货物。
    <br>7、任何投保物流企业不具备承运资质情况下营运的货物。
    <br>8、剔除高危货物：
    <br>1）易腐品、易蛀品、农产品，如果仁、花生、大豆、豆粕、鱼粉、菜籽饼、地瓜干、木薯干、肉类、蛋类、水果类等；易变质品、活品，如海鲜、动植物等；
    <br>2）危险品（参照GB12268-2005《危险货物品名表》）；
    <br>3）金银、珠宝、纪念币、钻石、首饰、玉石制品、稀有金属等；
    <br>4）艺术品（如古董、雕塑、名画等）、文物、邮票；
    <br>5）散装品、裸装货、散装油、原木；
    <br>6）手表、手机、原棉；
    <br>7）军火，军品；
    <br>8）现金、有价证券、票据、文件、档案、账册、图纸、技术资料、信用证、护照等无法确定其价值的货品；
    <br>9）非全新货物、展览后之展品；
    <br>10）整车、轮船及飞机/航空器；
    <br>11）需冷藏或冷冻的货物，甲板货；
    <br>12）精密仪器；
    <br>13）重大件机械设备
    <br>9、管控地区为：江西、山东临沂、东莞；广东地区散单不承接
    <br></div>
</div>
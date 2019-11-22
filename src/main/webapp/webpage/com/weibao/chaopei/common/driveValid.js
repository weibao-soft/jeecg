//验证身份证号码
function checkIDNo(IDNo) {
    if (IDNo != '' && IDNo != null) {
		if (IDNo.length > 0) {
			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			isok = reg.test(IDNo);
			if (!isok) {
				$.msg('error', "身份证格式错误!");
				return false;
			}
			else {
                return true;
			}
		}
    }
    else {
        return false;
    }
}
//验证邮件格式
function checkMail(valueMail) {
    if (valueMail != '' && valueMail != null) {
        if (valueMail.match(/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/)) {

            return true;
        }
        else {
            return false;
        }
    }
    else {
        return false;
    }
}
//验证车牌号
function checkLicenseNo(valueLicenseNo) {
    if (valueLicenseNo != '' && valueLicenseNo != null) {

        if (valueLicenseNo == "未上牌") return true;

        if (valueLicenseNo.match(/^[\u4E00-\u9FA5][\da-zA-Z]{5}[\u4E00-\u9FA5\da-zA-Z]$/)) {
            return true;
        }
        else {
            return false;
        }
    }
    else {
        return false;
    }
}

//验证载重
function checkKerbWeight(valueKerbWeight) {
    if (valueKerbWeight != '' && valueKerbWeight != null) {
        if (valueKerbWeight.match(/^[0-9]+(.[0-9]{2})?$|^[0-9]+(.[0-9]{1})?$/)) {
            return true;
        }
        else {
            $.msg('error', '只能输入两位小数的数字!');
            return false;
        }
    }
    else {
        return false;
    }

}

//显示特别约定
function tbydDiv() {
    ymPrompt.alert({
        title: '特别约定', message: '1、国任保险公司服务电话：4008667788<br />' +
            '2、公司最近季度的 偿付能力信息详见国任保险公司官网。<br />' +
            '3、本保单所指的车辆类型为各类车型的营运车辆 （包括特种车辆、危险货物运输车辆），以具体约定的车牌号/车架号对应的车辆为准。 <br />' +
            '4、本保单仅承担被保险人在本保单项下指定的机动车在使用过程中由于发生交通事故而造 成第三者 的财产损失和人身伤亡，依法应由被保险人承担的赔偿责任,本条所指第三者不包 含本车车上人员。 <br />' +
            '5、本保单约定对 于超过该机动车综合商业险第三者责任保险赔偿金额 （100万元）和该车辆机动车交通事故责任强制保险赔偿限额之和 以上的部分，对于本保 单约定的赔偿限额内负责赔偿。<br />' +
            '6、本保单不负责赔偿任何现金、支票（有价证券）、古 董字 画及其他无法估值的物品。<br />' +
            '7、移动车辆（车头、挂车视为一体，以车头名义投保） 。 <br />' +
            '8、空载情况下发生的第三者保 险责任也属于保险责任范围内。 <br />' +
            '9、本保单为指定 车辆物流安全责任险，指定车辆认定以车牌号、车辆识别代码为准 ，如果保险期间内发生车 牌号变更，出险时以车辆识别代码为准。 <br />' +
            '10、出险时无需提供安全生产证明资料。<br />'
    });
}
//显示承保要求
function cbyqDiv() {
    ymPrompt.alert({
        title: '承保要求', message: '1，投保人只能是单位，被保险人是个人，只能走线下出单，公对公转账，需要提供挂靠协议，协议盖章提交。被保险人是个人只能线下出单，提交出单表格和发票信息，行驶证，营业执照，挂靠协议，支付凭证截图，打包发给总部出单人员，由总部出单人员联系出单。<br />' +
            '2，线下出单发票，以表格形式提交给国任，或者在系统编辑发票信息。<br />' +
            '3，发票只能用投保人的信息开 <br />' +
            '4，承保车型政策：搅拌，泥头罐车，自卸，汞车不属于可保车型，公司名带有石土方，渣土运输，混凝土搅拌这一类公司，均禁止承保。 <br />' +
            '5，批改：国任不可以批改投保人和起保日期。批改手续：批改申请书说明原因盖章，营业执照，行驶证<br />' +
            '6，退保：当天出的不可以在当天退保。退保手续：退保申请书盖章，营业执照，退保账户证明。<br />'
    });
}
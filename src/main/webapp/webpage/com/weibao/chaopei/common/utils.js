'use strict';

window.Ajax = {};

/**
 * 遍历数组
 * @param {*} func 
 */
Array.prototype.each = function (callback) {
    var context = arguments.length <= 1 || arguments[1] === undefined ? window : arguments[1];

    for (var i = 0, l = this.length; i < l; i++) {
        callback.call(context, this[i], i, this);
    }
};

/**
 * 删除项
 * @param {*} item
 */
Array.prototype.remove = function (item) {
    var i = this.indexOf(item);
    if (i != -1) this.splice(i, 1);
};

String.prototype.startWith = function (str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
}

String.prototype.endWith = function (str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
}

window.Ajax.get = function (url, params, successCallback, failedCallback, completeCallback) {
    var isAsync = params && params.async != void 0 ? params.async : true;
    var isLoading = params && params.loading != void 0 ? params.loading : true;
    $.ajax({
        type: 'get',
        url: url,
        cache: false,
        dataType: 'text',
        beforeSend: function beforeSend(xhr) {
            if (isLoading) window.Utils.showLoading();
        },
        success: function success(data) {
            if (data) {
                if (successCallback) successCallback(data);
            } else {
                if (failedCallback) failedCallback(data);
            }
        },
        error: function error(e) {
            if (failedCallback) failedCallback(e); else $.msg('error', e.responseText);
            if (isLoading) window.Utils.closeLoading();
        },
        complete: function complete(XMLHttpRequest, status) {
            if (completeCallback) completeCallback();
            if (status == 'timeout') {
                //xhr.abort();
            }
            if (isLoading) window.Utils.closeLoading();
        }
    });
};

window.Ajax.post = function (url, params, successCallback, failedCallback, completeCallback) {
    var isAsync = params && params.async != void 0 ? params.async : true;
    var isLoading = params && params.loading != void 0 ? params.loading : true;
    $.ajax({
        type: 'Post',
        url: url,
        //contentType: "application/json;charset=utf-8",
        data: params,
        timeout: 30 * 1000,
        dataType: 'Json',
        cache: false, //-- 是否缓存
        async: isAsync, //-- 是否异步
        beforeSend: function beforeSend(xhr) {
            //xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8"); 
            if (isLoading) window.Utils.showLoading();
        },
        success: function success(data) {
            if (data && data.Success) {
                if (successCallback) successCallback(data.Result);
            } else {
                if (failedCallback) failedCallback(data); else {
                    if (data.Code === -99) {
                        setTimeout(function () {
                            window.location.href = '/SystemLogin/Login';
                        }, 3000);
                    }
                    $.msg('error', data.Description);
                }
            }
        },
        error: function error(e) {
            if (failedCallback) failedCallback(e); else $.msg('error', e.responseText);
            if (isLoading) window.Utils.closeLoading();
        },
        complete: function complete(XMLHttpRequest, status) {
            if (completeCallback) completeCallback();
            if (status == 'timeout') {
                //xhr.abort();
            }
            if (isLoading) window.Utils.closeLoading();
        }
    });
};

window.Ajax.send = function (url, params, successCallback, failedCallback, completeCallback) {
    var isAsync = params && params.async != void 0 ? params.async : true;
    var isLoading = params && params.loading != void 0 ? params.loading : true;
    $.ajax({
        type: "POST", //访问WebService使用Post方式请求
        contentType: "application/json;charset=utf-8", //WebService 会返回Json类型
        url: url, //调用WebService
        data: params, //Email参数
        dataType: 'json',
        beforeSend: function beforeSend(x) {
            x.setRequestHeader("Content-Type", "application/json; charset=utf-8");
        },
        error: function error(x, e) { },
        success: function success(result) {
            //回调函数，result，返回值
            if (successCallback) successCallback(result);
        }
    });
};

window.Ajax.postJson = function (url, params, successCallback, failedCallback, completeCallback) {
    var isAsync = params && params.async != void 0 ? params.async : true;
    var isLoading = params && params.loading != void 0 ? params.loading : true;
    $.ajax({
        type: 'Post',
        url: url,
        data: params,
        timeout: 30 * 1000,
        contentType: "application/json",
        cache: false, //-- 是否缓存
        async: isAsync, //-- 是否异步
        beforeSend: function beforeSend(xhr) {
            if (isLoading) window.Utils.showLoading();
        },
        success: function success(data) {
            if (data && data.Success) {
                if (successCallback) successCallback(data.Result);
            } else {
                if (failedCallback) failedCallback(data); else $.msg('error', data.Description);
            }
        },
        error: function error(e) {
            if (failedCallback) failedCallback(e); else $.msg('error', e.responseText);
            if (isLoading) window.Utils.closeLoading();
        },
        complete: function complete(XMLHttpRequest, status) {
            if (completeCallback) completeCallback();
            if (status == 'timeout') {
                //xhr.abort();
            }
            if (isLoading) window.Utils.closeLoading();
        }
    });
};

//时间格式转换
window.getLocalTime = function (nS) {
    if (nS != null) {
        var str = nS.substr(6, nS.length - 8);
        var getdate = new Date(parseInt(str));
        return getdate;
    }
    return "";
};

window.Utils = {};

window.Utils.showLoading = function (image) {
	if(image == null || image == '') {
		image = "images/loading.gif";
	}
    var loading = $('#global_loading');
    if (loading.length > 0) {
        loading.show();
    } else {
        $('<div id="global_loading" />').css({
            position: 'fixed',
            width: '100%',
            height: '100%',
            top: 0,
            'z-index': 11001
        }).append('<img src="'+image+'" alt="Alternate Text" style="width: 100px;height: 100px;position: absolute;top: 50%;left: 50%;margin: -50px 0 0 -50px;z-index: 51;">').appendTo('body');
    }
};

window.Utils.closeLoading = function () {
    var loading = $('#global_loading');
    if (loading.length > 0) {
        loading.hide();
    }
};

window.getToken = function () {
    return $('#guidToken').val();
};

window.getLoginCompany = function () {
    return $("#currentLoginCompanyID").val();
};

window.getLoginUser = function () {
    return $("#userName").val();
};
window.ymPrompt = {};
window.ymPrompt.alert = function (opt) {
    if (opt && opt.message) {
        if (opt.message.indexOf("成功") != -1) {
            $.msg('success', opt.message);
        } else if (opt.message.indexOf("失败") != -1 || opt.message.indexOf("不能") != -1) {
            $.msg('error', opt.message);
        } else {
            $.msg('info', opt.message);
        }
    }
};

function dateFormat(date, format) {
    date = new Date(date);
    var o = {
        'M+': date.getMonth() + 1, //month
        'd+': date.getDate(), //day
        'H+': date.getHours(), //hour
        'm+': date.getMinutes(), //minute
        's+': date.getSeconds(), //second
        'q+': Math.floor((date.getMonth() + 3) / 3), //quarter
        'S': date.getMilliseconds() //millisecond
    };

    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));

    for (var k in o) {
        if (new RegExp('(' + k + ')').test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
    } return format;
}

var _getTemplate = function _getTemplate(data) {
    var template = /<(template)(?:[^>]*)?>([\s\S]*?)(?:<\/\1>[^\"\']|<\/\1>$)/ig;
    template = template.exec(data);
    template = template && template.length >= 3 ? template[2] : '';
    return template;
};

var _getStyle = function _getStyle(data) {
    var style = /<(style)(?:[^>]*)?>([\s\S]*?)(?:<\/\1>[^\"\']|<\/\1>$)/ig;
    style = style.exec(data);
    style = style && style.length >= 3 ? style[2] : '';
    return style;
};

var _getScript = function _getScript(data) {
    var script = /<(script)(?:[^>]*)?>([\s\S]*?)(?:<\/\1>[^\"\']|<\/\1>$)/ig;
    script = script.exec(data);
    script = script && script.length >= 3 ? script[2] : '';
    return script;
};

window.parseComponent = function (url, callback, scope) {
    Ajax.get(url, {}, function (e) {
        if (e) {
            var style = _getStyle(e);
            callback.call(scope, Object.assign({
                template: '<div class="sys_panel">' + _getTemplate(e).trim() + '</div>'
            }, eval("(" + _getScript(e) + ")")()), style);
        }
    });
};

window.Ra = {
    format: function format(e, t) {
        var a = e.getFullYear(),
            r = e.getMonth() + 1,
            d = e.getDate(),
            o = e.getHours(),
            n = e.getMinutes(),
            i = e.getSeconds(),
            l = e.getMilliseconds() + "";
        for (r = r > 9 ? r : "0" + r, d = d > 9 ? d : "0" + d, o = o > 9 ? o : "0" + o, n = n > 9 ? n : "0" + n, i = i > 9 ? i : "0" + i; l.length < 3;) {
            l = "0" + l;
        } return t || (t = "yyyy-MM-dd HH:mm:ss"), t = (t = (t = (t = (t = (t = (t = t.replace(/yyyy/g, a)).replace(/MM/g, r)).replace(/dd/g, d)).replace(/HH/gi, o)).replace(/mm/g, n)).replace(/ss/g, i)).replace(/SSS/g, l);
    }, formatTime: function formatTime(e, t) {
        var a = (t = t || "00:00:00").split(":"),
            r = a[0],
            d = a.length > 1 ? a[1] : 0,
            o = a.length > 2 ? a[2] : 0;
        return "HH" !== r && e.setHours(r), "mm" !== d && this.setMinutes(d), "ss" !== o && e.setSeconds(o), e;
    }, diffYear: function diffYear(e, t) {
        var a = moment(e);
        return moment(t).diff(a, "years");
    }, diff: function diff(e, t) {
        return Math.ceil((e - t) / 864e5);
    }, addDay: function addDay(e, t, a) {
        t = parseInt(t);
        var r = new Date(e.getTime() + 864e5 * t);
        return a ? Ra.format(r, a) : r;
    }, addHour: function addHour(e, t) {
        return new Date(e.getTime() + 36e5 * t);
    }, addMonth: function addMonth(e, t, a) {
        if (!parseInt(t)) return a ? Ra.format(e, a) : e;
        t = parseInt(t);
        var r = e.getDate(),
            d = new Date(e.getTime());
        return d.setMonth(d.getMonth() + t), r !== d.getDate() && d.setDate(0), a ? Ra.format(d, a) : d;
    }, addYear: function addYear(e, t, a) {
        if (!parseInt(t)) return a ? Ra.format(e, a) : e;
        t = parseInt(t);
        var r = e.getDate(),
            d = new Date(e.getTime());
        return d.setYear(d.getFullYear() + t), r !== d.getDate() && d.setDate(0), a ? Ra.format(d, a) : d;
    }, compare: function compare(e, t) {
        return new Date(e).getTime() - new Date(t).getTime();
    }, isToday: function isToday(e) {
        return Ra.format(new Date(e.replace(/-/g, "/")), "yyyy-MM-dd") === Ra.format(new Date(), "yyyy-MM-dd");
    }, isLeapYear: function isLeapYear(e) {
        return e.getYear() % 4 == 0 && (e.getYear() % 100 != 0 || e.getYear() % 400 == 0);
    }, diffYearDayStr: function diffYearDayStr(e, t) {
        if (!e || !t) return "";
        t = Ra.addDay(t, 1);
        var a = parseInt(Ra.diffYear(e, t)),
            r = new Date(Ra.addYear(e, a, "yyyy/MM/dd")),
            d = Ra.diff(t, r),
            o = "";
        return 0 === a ? d = Ra.diff(t, e) : o = a + "年", o + (0 === d ? "" : d + "天");
    }, newDate: function newDate(e, t) {
        var a = null;
        return a = e ? Ba()(e) ? new Date(e) : new Date(e.replace(/-/g, "/")) : new Date(), t ? Ra.format(a, t) : a;
    }, isCorrectDate: function isCorrectDate(e) {
        if (!e) return !1;
        var t = e.match(/(19\d{2}|2\d{3})(0[1-9]|[1][0-2])(0[1-9]|[12][0-9]|3[01])/);
        return !!t && !(t[3] > Ha(t[1], t[2]));
    }
};

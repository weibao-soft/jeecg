class DateUtils {
    static getDateAfterDMY(afterDay, afterMonth, afterYear, date){
        if (!date) {
            date = new Date();
        }
        if (afterDay!=null) {
            date.setDate(date.getDate() + afterDay);
        }
        if (afterMonth!=null) {
            date.setMonth(date.getMonth() + afterMonth);
        }
        if (afterYear!=null) {
            date.setFullYear(date.getFullYear() + afterYear);
        }
        return date;
    }
    static getDateDiffY(startDate, endDate) {
        let diffTime;
        if (startDate > endDate) {
            diffTime = startDate - endDate;
        } else {
            diffTime = endDate - startDate;
        }
        return Math.ceil(diffTime/(365*24*60*60*1000));
    }
    static dateFormat(date, format) {
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
    static dateFormatYMD(date) {
        return this.dateFormat(date, 'yyyy-MM-dd');
    }
    static dateMoreThanYMD(aDateYMD, bDateYMD) {
        let aDate = this.parse(aDateYMD, 'yyyy-MM-dd');
        let bDate = this.parse(bDateYMD, 'yyyy-MM-dd');
        return bDate > aDate;
    }
    static parse(str, format) {
        let pattern = format.replace("yyyy", "(\\~1{4})").replace("yy", "(\\~1{2})")
            .replace("MM", "(\\~1{2})").replace("M", "(\\~1{1,2})")
            .replace("dd", "(\\~1{2})").replace("d", "(\\~1{1,2})").replace(/~1/g, "d");

        var returnDate;
        if (new RegExp(pattern).test(str)) {
            var yPos = format.indexOf("yyyy");
            var mPos = format.indexOf("MM");
            var dPos = format.indexOf("dd");
            if (mPos == -1) mPos = format.indexOf("M");
            if (yPos == -1) yPos = format.indexOf("yy");
            if (dPos == -1) dPos = format.indexOf("d");
            var pos = new Array(yPos + "y", mPos + "m", dPos + "d").sort();
            var data = { y: 0, m: 0, d: 0 };
            var m = str.match(pattern);
            for (var i = 1; i < m.length; i++) {

                if (i == 0) return;
                var flag = pos[i - 1].split('')[1];
                data[flag] = m[i];
            };

            if (data.y.toString().length == 2) {
                data.y = parseInt("20" + data.y);
            }
            data.m = data.m - 1;
            returnDate = new Date(data.y, data.m, data.d);
        }
        if (returnDate == null || isNaN(returnDate)) returnDate = new Date();
        return returnDate;
    }
}
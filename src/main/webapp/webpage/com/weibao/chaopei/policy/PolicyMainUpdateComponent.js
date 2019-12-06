class PolicyMainUpdateComponent {
    /**
     * 保险日期选择控件
     * params: {
     *     startDate: {attrs:{id, name, value}, minDate, maxDate},
     *     startHour: {attrs:{id, name}},
     *     endDate: {attrs:{id, name, value}},
     *     endHour: {attrs:{id, name}},
     *     year: {attrs:{id, name, value}},
     *     month: {attrs:{id, value}}
     * }
     */
    static renderInsuranceDate(params, $el) {

        let {startDate, startHour, endDate, endHour, year, month, isDefaultDateBox} = params;
        let minDate = DateUtils.getDateAfterDMY(1);
        let maxDate = DateUtils.getDateAfterDMY(-1, null, 1);
        let minDateStr = DateUtils.dateFormatYMD(minDate);
        let maxDateStr = DateUtils.dateFormatYMD(maxDate);
        let startDateVal = startDate.attrs.value;
        let yearVal = year.attrs.value;
        if (DateUtils.dateMoreThanYMD(startDateVal, minDateStr)) { // 最小日期大于当前选择的日期
            // 强制将起始日期小于最小日期的，改成等于最小日期
            startDate.attrs.value = minDateStr;
            endDate.attrs.value = DateUtils.dateFormatYMD(DateUtils.getDateAfterDMY(-1, null, yearVal, minDate));
        }
        let startDateAttrs = this.generateAttrs(startDate.attrs);
        let startHourAttrs = this.generateAttrs(startHour.attrs);
        let endDateAttrs = this.generateAttrs(endDate.attrs);
        let endHourAttrs = this.generateAttrs(endHour.attrs);
        let yearAttrs = this.generateAttrs(year.attrs);
        let monthAttrs = this.generateAttrs(month.attrs);
        let startDateBlurFn = isDefaultDateBox ? 'calculateYear()' : 'calculateYearByParam(this)';
        let endDateDisabled = isDefaultDateBox ? 'disabled' : 'readonly';
        let html =  `<span class="shour-field">
        自 
        <input type="text" ${startDateAttrs} class="Wdate" style="width:100px;" 
        onblur="${startDateBlurFn};" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${minDateStr}',maxDate:'${maxDateStr}'})"/> 
        <input type="text" ${startHourAttrs} style="width:20px;" value="00" disabled/> 
        起 至 
        <input type="text" ${endDateAttrs} class="Wdate" style="width:100px;" ${endDateDisabled}/>
        <input type="text" ${endHourAttrs} style="width:20px;" value="24" disabled/>
        止，连续
        <input type="text" ${yearAttrs} style="width:60px;" onblur="calculateMonths(this);">
        年 共
        <label ${monthAttrs}>12</label>月 
        </span>`;
        return this.render(html, $el);
    }

    /**
     * 车辆信息
     */
    static renderVehicleRow(params, $el) {

    }

    static render(html, $el) {
        if ($el) {
            $el.append(html);
        } else {
            return html;
        }
    }

    static generateAttrs(attrsObj) {
        let attrsHtml = [];
        for(let attrName in attrsObj) {
            let attrHtml = this.generateAttr(attrName, attrsObj[attrName]);
            if (attrHtml) {
                attrsHtml.push(attrHtml);
            }
        }
        return attrsHtml.join(' ');
    }

    static generateAttr(attrName, attrVal) {
        if (this.isNullOrUndefined(attrVal)) {
            return '';
        }
        return `${attrName}="${attrVal}"`;
    }

    static isNullOrUndefined(obj) {
        return obj === null || obj === undefined;
    }

}
package org.jeecgframework.tag.core;

import com.alibaba.fastjson.JSON;

public class ElCommons {
    /**
     * 将对象中存在值的字段转换成为json串
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj){
        // 将java对象转换为json字符串
        // * 注意：我这里使用的是阿里巴巴的fastjson转换，可根据自己使用的json库调用转换方法
        String jsonString = JSON.toJSONString(obj);
        return jsonString;
    }
}

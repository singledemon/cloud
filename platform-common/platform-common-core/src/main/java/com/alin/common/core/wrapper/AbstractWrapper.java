package com.alin.common.core.wrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @Description 控制器查询结果的包装类基类
 */
public abstract class AbstractWrapper {

    public Object obj = null;

    public AbstractWrapper(Object obj) {
        this.obj = obj;
    }

    public Object wrap() {
        if (this.obj instanceof List) {
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(this.obj));
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                wrapTheMap(jsonObject);
            }
            return this.obj;
        } else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            wrapTheMap(map);
            return map;
        } else if (this.obj instanceof Page) {
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(((Page) this.obj).getRecords()));
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                wrapTheMap(jsonObject);
            }
            return this.obj;
        } else {
            return this.obj;
        }
    }

    protected abstract void wrapTheMap(Map<String, Object> map);
}

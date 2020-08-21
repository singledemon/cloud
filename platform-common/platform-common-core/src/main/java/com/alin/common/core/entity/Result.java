package com.alin.common.core.entity;

import com.alin.common.core.constant.ExceptionCodeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;

/**
 * 对象返回封装类
 *
 */
public class Result extends HashMap<String,Object> {

    public Result(){
        put("code",200);
    }

    public static Result error(){
        return error(ExceptionCodeEnum.SERVER_ERROR.getCode(), ExceptionCodeEnum.SERVER_ERROR.getMessage());
    }

    public  static Result error(String message) {
        return error(500,message);
    }


    public static Result error(Integer code, String msg){
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        r.put("data",null);
        return r;
    }

    public static Result ok(){
        return new Result();
    }

    public static Result ok(String msg){
        Result re = new Result();
        re.put("code",200);
        re.put("msg",msg);
        re.put("data",null);
        return  re;
    }

    //封装json数据返回结果集
    public static Result ok(Integer code, String msg, Object data){
        Result r = new Result();
        r.put("msg", msg);
        r.put("code", code);
        r.put("data", JSON.toJSONString(data, SerializerFeature.WRITE_MAP_NULL_FEATURES,SerializerFeature.QuoteFieldNames));
        return r;
    }


    public static Result okFormat(Integer code, String msg, Object data) {
        Result r = new Result();
        r.put("msg", msg);
        r.put("code", code);
        r.put("data", data);
        return r;
    }

    public Result preError(String msg){
        Integer code = (Integer) this.get("code");
        if(!code.equals(200)){
            this.put("msg",msg);
            return this;
        }else{
            return this;
        }
    }
    public Result preSuccess(String msg){
        Integer code = (Integer) this.get("code");
        if(code.equals(200)){
            this.put("msg",msg);
            return this;
        }else{
            return this;
        }
    }
    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }




}

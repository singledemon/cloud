package cn.com.oumeng.common.core.util;

import cn.com.oumeng.common.core.constant.ExceptionCodeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JSONResultUtil {
    public static final String KEY_CODE = "code";
    public static final String KEY_MESSAGE = "msg";
    public static final String KEY_DATA = "data";

    public static String fillResultString(ExceptionCodeEnum codeEnum) {
        return fillResultString(codeEnum.getCode(),codeEnum.getMessage());
    }

    private static String fillResultString(Integer code, String message) {
        return fillResultString(code,message,null);
    }

    private static String fillResultString(Integer code, String message, Object o) {
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put(KEY_CODE,code);
        map.put(KEY_MESSAGE,message);
        map.put(KEY_DATA, JSON.toJSONString(o));
        return JSONObject.toJSONString(map);
    }

    public static String fillSuccessResultString(ExceptionCodeEnum codeEnum){
        return fileSuccessResultString(codeEnum.getCode(),codeEnum.getMessage());
    }

    private static String fileSuccessResultString(Integer code, String message) {
        return fileSuccessResultString(code,message,null);
    }

    private static String fileSuccessResultString(Integer code, String message, Object o) {
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put(KEY_CODE, ExceptionCodeEnum.SUCCESS.getCode());
        map.put(KEY_MESSAGE, message);
        map.put(KEY_DATA, JSON.toJSONString(o));
        return JSONObject.toJSONString(map);
    }
    public static Map<String, Object> fillResult(ExceptionCodeEnum codeEnum) {
        return fillResult(codeEnum.getCode(), codeEnum.getMessage());
    }

    public static Map<String, Object> fillResult(Integer code, String message) {
        return fillResult(code, message, null);
    }

    public static Map<String, Object> fillResult(Integer code, String message, Object data) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put(KEY_CODE, code);
        map.put(KEY_MESSAGE, message);
        map.put(KEY_DATA, JSON.toJSONString(data));
        return map;
    }

    public static Map<String, Object> fillSuccessResult(String message) {
        return fillSuccessResult(message, null);
    }

    public static Map<String, Object> fillSuccessResult(String message, Object o) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put(KEY_CODE, ExceptionCodeEnum.SUCCESS.getCode());
        map.put(KEY_MESSAGE, message);
        map.put(KEY_DATA, JSON.toJSONString(o, SerializerFeature.WRITE_MAP_NULL_FEATURES,SerializerFeature.QuoteFieldNames));
        return map;
    }

    public static Map<String, Object> fillSuccessFormatResult(String message, Object data) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put(KEY_CODE, ExceptionCodeEnum.SUCCESS.getCode());
        map.put(KEY_MESSAGE, message);
        map.put(KEY_DATA, data);
        return map;
    }

    public static String fillErrorResultString(String message) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put(KEY_CODE, ExceptionCodeEnum.SERVER_ERROR.getCode());
        map.put(KEY_MESSAGE, message);
        map.put(KEY_DATA, null);
        return JSONObject.toJSONString(map);
    }
}

package com.alin.common.core.controller;

import com.alin.common.core.constant.ExceptionCodeEnum;
import com.alin.common.core.util.JSONResultUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractBaseSpringController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Object success() {
        return JSONResultUtil.fillSuccessResult(ExceptionCodeEnum.SUCCESS.getMessage());
    }

    protected Object success(String message) {
        return JSONResultUtil.fillSuccessResult(message);
    }

    protected Object success(Object data) {
        return success("ok",data);
    }

    protected Object success(String message, Object data) {
        return JSONResultUtil.fillSuccessResult(message, data);
    }

    protected Object successFormat(String message, Object data) {
        return JSONResultUtil.fillSuccessFormatResult(message, data);
    }

    protected Object error(String message) {
        return JSONResultUtil.fillResult(ExceptionCodeEnum.SERVER_ERROR.getCode(), message);
    }

    protected Object generateReturnError(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage() + ";");
        }
        return error(sb.toString());
    }

    public Page createPage(Map<String, Object> params) {
        Integer pageSize = null;
        Integer pageIndex = null;
        if (Objects.nonNull((Integer)params.get("pageIndex"))) {
            pageIndex = (Integer)params.get("pageIndex");
        } else {
            pageIndex = 0;
        }
        if (Objects.nonNull((Integer)params.get("pageSize"))) {
            pageSize = (Integer)params.get("pageSize");
        } else {
            pageSize = 1;
        }
        return new Page<>(pageIndex, pageSize);
    }

}
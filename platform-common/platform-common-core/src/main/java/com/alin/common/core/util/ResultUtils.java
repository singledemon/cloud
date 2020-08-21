package com.alin.common.core.util;


import com.alin.common.core.constant.ExceptionCodeEnum;
import com.alin.common.core.entity.Result;

public class ResultUtils {

	/**
	 * 判断整数是否大于零
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isIntThanZero(int number) {
		if (number > 0)
			return true;
		return false;
	}

	/**
	 * 判断flag是否为true
	 *
	 * @param flag
	 * @return
	 */
	public static boolean isBooleanThanTrue(boolean flag) {
		if (flag)
			return true;
		return false;
	}

	/**
	 * 新增，修改提示
	 * 
	 * @param count
	 * @return
	 */
	public static Result check(int count) {
		if (isIntThanZero(count))
			return Result.ok(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(),null);
		return Result.error(ExceptionCodeEnum.SERVER_ERROR.getCode(),"操作失败");
	}

	/**
	 * 新增，修改提示
	 *
	 * @param flag
	 * @return
	 */
	public static Result check(boolean flag) {
		if (isBooleanThanTrue(flag))
			return Result.ok(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(),null);
		return Result.error(ExceptionCodeEnum.SERVER_ERROR.getCode(),"操作失败");
	}

	/**
	 *
	 * @param data
	 * @return
	 */
	public static Result data(Object data){
		return Result.ok(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(),data);
	}

    public static Result dataFormat(Object data) {
        return Result.okFormat(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), data);
    }

	public static Result ex(int code, String e){
		return Result.error(code,e);
	}
	
}

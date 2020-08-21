package com.alin.common.log.model;

import com.alin.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志记录表 oper_log
 * 
 * @author ruoyi
 */
@Data
@TableName("sys_opr_log")
public class OperationLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 操作模块 */
    @TableField("title")
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    @TableField("business_type")
    private Integer businessType;

    /** 业务类型数组 */
    private Integer[] businessTypes;

    /** 请求方法 */
    @TableField("method")
    private String method;

    /** 请求方式 */
    @TableField("request_method")
    private String requestMethod;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @TableField("operator_type")
    private Integer operatorType;

    /** 操作人员 */
    @TableField("oper_name")
    private String operName;

    /** 部门名称 */
    @TableField("dept_name")
    private String deptName;

    /** 请求url */
    @TableField("oper_url")
    private String operUrl;

    /** 操作地址 */
    @TableField("oper_ip")
    private String operIp;

    /** 请求参数 */
    @TableField("oper_param")
    private String operParam;

    /** 返回参数 */
    @TableField("json_result")
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    @TableField("status")
    private Integer status;

    /** 错误消息 */
    @TableField("error_msg")
    private String errorMsg;

    /** 操作时间 */
    @TableField("oper_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;


}

package com.alin.biz.crm.rest;


import com.alin.biz.crm.service.OperationLogService;
import com.alin.common.core.controller.AbstractSpringController;
import com.alin.common.core.entity.Result;
import com.alin.common.log.annotation.Log;
import com.alin.common.log.enums.BusinessType;
import com.alin.common.log.model.OperationLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * className：RestApiController
 * description： TODO
 * time：2020-05-29.15:14
 *
 * @author Tank
 * @version 1.0.0
 */
@RestController
@RequestMapping("/operationLog")
public class OperationLogController extends AbstractSpringController<OperationLog, OperationLogService> {


    /**
     * 添加操作日志。
     * @param log
     * @return
     */
    @Log(title = "操作日志", businessType = BusinessType.INSERT)
    @RequestMapping("/addOperationLog")
    public Result addOperationLog(@RequestBody OperationLog log){
        return baseService.addOperationLog(log);
    }

    @DeleteMapping("/{operIds}")
    public Object remove(@PathVariable List<String> operIds)
    {
        return baseService.deleteOperLogByIds(operIds);
    }


    @Log(title = "操作日志", businessType = BusinessType.UPDATE)
    @RequestMapping("/getOperationPage")
    public Object getOperationPage(@RequestBody Map<String, Object> params){

        Page<Map<String, Object>> page =  baseService.getOperationPage(createPageObject(params),params);
        return success("ok", page);
    }

}

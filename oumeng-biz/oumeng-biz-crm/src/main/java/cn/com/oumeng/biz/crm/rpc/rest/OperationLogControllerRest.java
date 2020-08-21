package cn.com.oumeng.biz.crm.rpc.rest;

import cn.com.oumeng.biz.crm.service.OperationLogService;
import cn.com.oumeng.common.core.entity.Result;
import cn.com.oumeng.common.log.model.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rpc/systemLogController")
@Transactional(rollbackFor = Exception.class)
public class OperationLogControllerRest {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 保存日志记录 服务接口。
     * @param log
     * @return
     */
    @PostMapping("/saveOperationLog")
    public Result saveOperationLog(@RequestBody OperationLog log){
        Result result = operationLogService.addOperationLog(log);
        return result;
    }


}

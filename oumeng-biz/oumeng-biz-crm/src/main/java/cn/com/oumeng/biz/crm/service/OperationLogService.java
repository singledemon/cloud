package cn.com.oumeng.biz.crm.service;


import cn.com.oumeng.biz.crm.mapper.OperationLogMapper;
import cn.com.oumeng.common.core.entity.Result;
import cn.com.oumeng.common.core.service.impl.AbstractServiceImpl;
import cn.com.oumeng.common.core.util.ResultUtils;
import cn.com.oumeng.common.log.model.OperationLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OperationLogService extends AbstractServiceImpl<OperationLogMapper, OperationLog> {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     * @return 结果
     */
    public Result addOperationLog(OperationLog operLog)
    {
        int insert = baseMapper.insert(operLog);
        Result result = ResultUtils.check(insert);
        return result;
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<OperationLog> getOperationLogList(OperationLog operLog)
    {
        return baseMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(List<String> operIds) {

        return baseMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public OperationLog selectOperLogById(Long operId)
    {
        return baseMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    public void cleanOperLog()
    {
        baseMapper.cleanOperLog();
    }

    /**
     *
     * @param page
     * @param params
     * @return
     */
    public Page<Map<String, Object>> getOperationPage(Page<Map<String, Object>> page, Map<String, Object> params) {
        List<OperationLog> operationLogs = baseMapper.selectOperLogList(new OperationLog());
        return  page;
    }
}

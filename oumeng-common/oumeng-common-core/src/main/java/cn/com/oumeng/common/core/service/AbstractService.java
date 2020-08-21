package cn.com.oumeng.common.core.service;

import cn.com.oumeng.common.core.mapper.AbstractMapper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AbstractService<M extends AbstractMapper<T>, T> extends IService<T> {

}
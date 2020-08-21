package com.alin.common.core.service;

import com.alin.common.core.mapper.AbstractMapper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AbstractService<M extends AbstractMapper<T>, T> extends IService<T> {

}
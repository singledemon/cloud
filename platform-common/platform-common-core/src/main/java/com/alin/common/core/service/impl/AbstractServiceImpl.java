package com.alin.common.core.service.impl;

import com.alin.common.core.mapper.AbstractMapper;
import com.alin.common.core.service.AbstractService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

public class AbstractServiceImpl<M extends AbstractMapper<T>, T> extends ServiceImpl<M, T> implements
        AbstractService<M, T> {

    @Override
    public List<T> list(Wrapper<T> wrapper) {
        return baseMapper.selectList(wrapper);
    }
}
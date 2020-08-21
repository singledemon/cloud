package com.alin.file.service;


import com.alin.common.core.service.impl.AbstractServiceImpl;
import com.alin.file.mapper.SystemFileMapper;
import com.alin.file.model.SystemFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemFileService extends AbstractServiceImpl<SystemFileMapper, SystemFile> {

}

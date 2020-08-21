package cn.com.oumeng.file.service;


import cn.com.oumeng.common.core.service.impl.AbstractServiceImpl;
import cn.com.oumeng.file.mapper.SystemFileMapper;
import cn.com.oumeng.file.model.SystemFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemFileService extends AbstractServiceImpl<SystemFileMapper, SystemFile> {

}

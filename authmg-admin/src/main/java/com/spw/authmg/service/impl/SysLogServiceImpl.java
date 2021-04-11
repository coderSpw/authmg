package com.spw.authmg.service.impl;

import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.SysLogMapper;
import com.spw.authmg.pojo.SysLog;
import com.spw.authmg.pojo.SysLogExample;
import com.spw.authmg.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysLog record) {
        return sysLogMapper.insertSelective(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(SysLog record) {
        return sysLogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysLog> record) {
        int num = 0;
        for (SysLog sysLog : record) {
            delete(sysLog);
            num ++;
        }
        return num;
    }

    @Override
    public SysLog findById(Long id) {
        return null;
    }

    @Override
    public List<SysLog> findAll() {
        return sysLogMapper.selectByExample(new SysLogExample());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyPageHelper.findPage(pageRequest,sysLogMapper);
    }
}

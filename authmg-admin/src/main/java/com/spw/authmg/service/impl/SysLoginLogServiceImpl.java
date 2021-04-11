package com.spw.authmg.service.impl;

import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.SysLoginLogMapper;
import com.spw.authmg.pojo.SysLoginLog;
import com.spw.authmg.pojo.SysLoginLogExample;
import com.spw.authmg.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysLoginLog record) {
        return sysLoginLogMapper.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(SysLoginLog record) {
        return sysLoginLogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysLoginLog> record) {
        int num = 0;
        for (SysLoginLog sysLoginLog : record) {
            delete(sysLoginLog);
            num ++;
        }
        return num;
    }

    @Override
    public SysLoginLog findById(Long id) {
        return null;
    }

    @Override
    public List<SysLoginLog> findAll() {
        return sysLoginLogMapper.selectByExample(new SysLoginLogExample());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyPageHelper.findPage(pageRequest,sysLoginLogMapper);
    }
}

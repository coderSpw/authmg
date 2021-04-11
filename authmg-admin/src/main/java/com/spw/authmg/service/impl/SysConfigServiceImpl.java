package com.spw.authmg.service.impl;

import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.SysConfigMapper;
import com.spw.authmg.pojo.SysConfig;
import com.spw.authmg.pojo.SysConfigExample;
import com.spw.authmg.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysConfig record) {
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(record.getId());
        if (sysConfig != null){
            return sysConfigMapper.updateByPrimaryKeySelective(record);
        }
        return sysConfigMapper.insertSelective(sysConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(SysConfig record) {
        return sysConfigMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysConfig> record) {
        int num = 0;
        for (SysConfig sysConfig : record) {
            delete(sysConfig);
            num ++;
        }
        return num;
    }

    @Override
    public SysConfig findById(Long id) {
        return null;
    }

    @Override
    public List<SysConfig> findAll() {
        return sysConfigMapper.selectByExample(new SysConfigExample());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyPageHelper.findPage(pageRequest,sysConfigMapper);
    }

    @Override
    public List<SysConfig> findByLabel(String label) {
        SysConfigExample query = new SysConfigExample();
        query.createCriteria().andLabelEqualTo(label);
        return sysConfigMapper.selectByExample(query);
    }
}

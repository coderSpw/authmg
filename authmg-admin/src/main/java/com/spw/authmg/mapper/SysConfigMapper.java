package com.spw.authmg.mapper;

import com.spw.authmg.pojo.SysConfig;
import com.spw.authmg.pojo.SysConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysConfigMapper {
    long countByExample(SysConfigExample example);

    int deleteByExample(SysConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    List<SysConfig> selectByExample(SysConfigExample example);

    SysConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysConfig record, @Param("example") SysConfigExample example);

    int updateByExample(@Param("record") SysConfig record, @Param("example") SysConfigExample example);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);
}
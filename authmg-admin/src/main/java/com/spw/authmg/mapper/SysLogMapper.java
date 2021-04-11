package com.spw.authmg.mapper;

import com.spw.authmg.pojo.SysLog;
import com.spw.authmg.pojo.SysLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogMapper {
    long countByExample(SysLogExample example);

    int deleteByExample(SysLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    List<SysLog> selectByExample(SysLogExample example);

    SysLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysLog record, @Param("example") SysLogExample example);

    int updateByExample(@Param("record") SysLog record, @Param("example") SysLogExample example);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}
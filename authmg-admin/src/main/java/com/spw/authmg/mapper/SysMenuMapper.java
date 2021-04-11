package com.spw.authmg.mapper;

import com.spw.authmg.pojo.SysMenu;
import com.spw.authmg.pojo.SysMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuMapper {
    long countByExample(SysMenuExample example);

    int deleteByExample(SysMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectByExample(SysMenuExample example);

    SysMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByExample(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> findRoleMenus(@Param("roleId") Long roleId);

    List<SysMenu> findByUsername(@Param("username") String username);
}
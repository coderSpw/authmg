package com.spw.authmg.security;

import com.spw.authmg.pojo.SysUser;
import com.spw.authmg.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 用户登录信息
 * @Author spw
 * @Date 2021/3/29
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        SysUser user = sysUserService.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //加载用户权限列表
        Set<String> permissions = sysUserService.findPermissions(user.getName());
        List<GrantedAuthorityImpl> grantedAuthorities = permissions.stream()
                .map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        return new JwtUserDetails(user.getName(), user.getPassword(), user.getSalt()
                            , grantedAuthorities);
    }
}

package com.spw.authmg.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Description 权限封装
 * @Author spw
 * @Date 2021/3/29
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}

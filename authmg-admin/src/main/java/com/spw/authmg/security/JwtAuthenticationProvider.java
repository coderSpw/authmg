package com.spw.authmg.security;

import com.spw.authmg.security.utils.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description 身份验证提供者
 * @Author spw
 * @Date 2021/3/29
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (userDetails == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            //重写密码加密逻辑 加盐加密
            String presentedPassword = authentication.getCredentials().toString();
            String salt = ((JwtUserDetails) userDetails).getSalt();
            if (!new PasswordEncoder(salt).matches(userDetails.getPassword(), presentedPassword)) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }
}

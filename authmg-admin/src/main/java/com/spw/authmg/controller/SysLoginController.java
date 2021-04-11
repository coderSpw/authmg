package com.spw.authmg.controller;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.spw.authmg.security.JwtAuthenticationToken;
import com.spw.authmg.security.utils.PasswordUtils;
import com.spw.authmg.security.utils.SecurityUtils;
import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.pojo.SysUser;
import com.spw.authmg.service.SysUserService;
import com.spw.authmg.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @Description 登录模块
 * @Author spw
 * @Date 2021/03/29
 */
@Slf4j
@Api(tags = "登录模块")
@RestController
public class SysLoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 生成验证码
     * @param request
     * @param response
     */
    @ApiOperation("获取验证码")
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String text = producer.createText();
        log.info("验证码：{}", text);
        //生成图片
        BufferedImage image = producer.createImage(text);
        //将验证码保存到session中
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        try(ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public RespResult login(@RequestBody LoginVO login, HttpServletRequest request) {
        String username = login.getUsername();
        String password = login.getPassword();
        String kaptcha = login.getKaptcha();
        //从session中获取验证码
        Object sessionKaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (kaptcha == null) {
            return RespResult.failResult("请输入验证码");
        }
        if (!kaptcha.equals(sessionKaptcha)) {
            return RespResult.failResult("验证码输入错误");
        }
        //查询用户是否存在
        SysUser sysUser = sysUserService.findByName(username);
        if (sysUser == null) {
            return RespResult.failResult("用户不存在");
        }
        if (!PasswordUtils.matches(sysUser.getSalt(), password, sysUser.getPassword())) {
            return RespResult.failResult("密码错误");
        }
        //认证
        JwtAuthenticationToken token = SecurityUtils.login(request, username, password, authenticationManager);
        log.info("token: {}", token.getToken());
        return RespResult.sucessResult(token);
    }
}
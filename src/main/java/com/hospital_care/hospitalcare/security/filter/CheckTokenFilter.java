package com.hospital_care.hospitalcare.security.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hospital_care.hospitalcare.jwt.JwtUtils;
import com.hospital_care.hospitalcare.security.detailservice.CustomerUserDetailsService;
import com.hospital_care.hospitalcare.security.exception.TokenException;
import com.hospital_care.hospitalcare.security.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("checkTokenFilter")
public class CheckTokenFilter extends OncePerRequestFilter {
    @Value("${hospitalcare.loginUrl}")
    private String loginUrl;
    @Value("${hospitalcare.imgUrl}")
    private String imgUrl;
    @Value("${hospitalcare.icon}")
    private String icon;

    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println(url);
        if(url.equals(loginUrl)){//如果是登录，否则做验证码验证
            try {
                // 校验验证码合法性
                //validate(request);
            }catch (AuthenticationException e) {
                // 交给失败处理器进行处理异常
                loginFailureHandler.onAuthenticationFailure(request, response, e);
                // 一定要记得结束
                return;
            }
        } else {
            //验证token,验证码请求不需要验证token
            if(!url.equals(icon) &&!url.equals(imgUrl) && !url.startsWith("/swagger-ui") && !url.endsWith(".jpg")){
                try{
                    validateToken(request);
                }catch (AuthenticationException e){
                    loginFailureHandler.onAuthenticationFailure(request,response,e);
                    return;
                }
            }
        }
        // 放行请求
        filterChain.doFilter(request, response);
    }

    //验证token
    private void validateToken(HttpServletRequest request){
        //获取前端传来的token
        String token = request.getHeader("token");
        //解析token，获取用户名
        String username = jwtUtils.getUsernameFromToken(token);
        //如果token或者用户名为空的话，不能通过认证
        if(StringUtils.isBlank(token) || StringUtils.isBlank(username)){
            throw new TokenException("token验证失败!");
        }
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new TokenException("token验证失败!");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置为已登录
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    ////验证验证码
    //private void validate(HttpServletRequest request){
    //    //1.获取登录请求的验证码
    //    String inputCode = request.getParameter("code");
    //    System.out.println(inputCode);
    //    //2.获取Session中的验证码
    //    String code = (String)request.getSession().getAttribute(LoginController.SESSION_KEY);
    //    //3.判断验证码是否为空
    //    if(StringUtils.isBlank(inputCode)){
    //        throw new ImageCodeException("验证码不能为空!");
    //    }
    //    //4.判断验证码是否相等
    //    if(!inputCode.equalsIgnoreCase(code)){
    //        throw new ImageCodeException("验证码输入错误!");
    //    }
    //}

}

package com.hospital_care.hospitalcare.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.permission.Permission;
import com.hospital_care.hospitalcare.entity.permission.vo.MakeMenuTree;
import com.hospital_care.hospitalcare.entity.permission.vo.MenuVo;
import com.hospital_care.hospitalcare.jwt.JwtUtils;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录认证成功处理器
 * 登录成功要返回 json 和 token
 */
@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    //@Autowired
    //private LoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        //1.获取用户信息（即CustomerUserDetailsService返回的user）
        User user = (User) authentication.getPrincipal();
        //2.生成token
        String token = jwtUtils.generateToken(user);
        MenuVo vo = new MenuVo();
        vo.setToken(token);
        vo.setUserId(user.getId());
        vo.setUserName(user.getUsername());
        //vo.setIsAdmin(user.getIsAdmin());

        //3.获取用户菜单权限
        List<Permission> permissionList = user.getPermissionList();
        //4.获取code字段，返回给前端使用
        List<String> auth = permissionList.stream().filter(item -> item != null)
                .map(item -> item.getCode())
                .collect(Collectors.toList());
        vo.setAuthList(auth);
        //5.生成菜单数据树
        List<Permission> permissions = permissionList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
        List<Permission> listMenu = MakeMenuTree.makeTree(permissions, 0);
        vo.setMenuList(listMenu);
        //6.查询路由url
        List<Permission> routerList = permissionList.stream().filter(item -> item != null && item.getType().equals("1")).collect(Collectors.toList());
        vo.setRouterList(routerList);

        String res = JSONObject.toJSONString(ResultUtils.success("认证成功",vo), SerializerFeature.DisableCircularReferenceDetect);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();

        //String ipAddresses = getIpAddresses(request);

        //LoginLog loginLog = new LoginLog();
        //loginLog.setLoginPerson(user.getUsername());
        //loginLog.setLoginTime(LocalDateTime.now().toString());
        //loginLog.setLoginIp(ipAddresses);
        //loginLogService.save(loginLog);
    }

    public String getIpAddresses(HttpServletRequest request){
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ipAddresses = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getRemoteAddr();
        }

        // ip配置
        if (ipAddresses.equals("127.0.0.1") || ipAddresses.endsWith("0:0:0:0:0:0:1")) {
            // 根据网卡取本机配置的IP
            ipAddresses = "127.0.0.1";
        }
        return ipAddresses;
    }

}

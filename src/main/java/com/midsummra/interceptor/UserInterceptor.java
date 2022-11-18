package com.midsummra.interceptor;

import com.midsummra.pojo.User;
import com.midsummra.service.UserService;
import com.midsummra.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String realIP = IpUtils.getRealIP(request);
        System.out.println("["+realIP+"]访问了/admin/login");

        //放行login页面
        if (request.getRequestURI().contains("login")||request.getRequestURI().contains("verifyUser")) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInfo");

        if (ObjectUtils.isEmpty(user)){
            request.getRequestDispatcher("html/error/forbidden.html").forward(request,response);
            return false;
        }

        User tempUser = userService.getUser(user.getUsername());

        if (!ObjectUtils.isEmpty(tempUser)){
            if (tempUser.getPassword().equals(user.getPassword()) && "admin".equals(tempUser.getPermission())){
                return true;
            }
        }

        request.getRequestDispatcher("html/error/forbidden.html").forward(request,response);
        return false;
    }


}

package com.flink.streaming.web.interceptor;

import com.flink.streaming.web.common.util.UserSessionUtil;
import com.flink.streaming.web.model.dto.UserSession;
import com.flink.streaming.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuhuipei
 * @Description:
 * @date 2020-07-10
 * @time 01:27
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        log.debug("进入LoginInterceptor拦截器 {}",request.getRequestURI());
        UserSession userSession = UserSessionUtil.userSession(request);
        if (userSession == null) {
            response.sendRedirect("/admin/index?message=nologin");
            return false;
        }
        boolean isCheckSession = userService.checkLogin(userSession);
        if (!isCheckSession) {
            response.sendRedirect("/admin/index?message=nologin");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }


}
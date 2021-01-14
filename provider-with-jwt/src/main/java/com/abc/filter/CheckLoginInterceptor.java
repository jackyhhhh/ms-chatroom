package com.abc.filter;

import com.abc.bean.MyResponse;
import com.abc.myAnnotation.LoginFree;
import com.abc.service.JwtService;
import com.abc.utils.CookieUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwt;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LoginFree loginFreeAnnotation = method.getAnnotation(LoginFree.class);
        if(loginFreeAnnotation != null){
            return true;
        }
        String token = CookieUtil.getToken(request);
        if(token != null && jwt.checkToken(token)){
            return true;
        }
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(JSON.toJSONString(MyResponse.fail("token无效, 请重新获取")));
        pw.flush();
        pw.close();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

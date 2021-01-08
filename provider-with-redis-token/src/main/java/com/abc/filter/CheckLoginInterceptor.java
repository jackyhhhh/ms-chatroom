package com.abc.filter;

import com.abc.entity.form.MyResponse;
import com.abc.myAnnotation.LoginFree;
import com.abc.service.CookieService;
import com.abc.service.UserService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token_name = cookieService.getToken(request);
        if(! (handler instanceof HandlerMethod)){
            if(token_name == null){
                return true;
            }
        }else{
            response.setContentType("application/json;charset=UTF-8");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> cls = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            // 检查handler方法是否存在注解CheckLogin
            if(cls.isAnnotationPresent(LoginFree.class) || method.isAnnotationPresent(LoginFree.class)){
                return true;
            }
        }
        if(token_name != null){
            log.debug("token_name:"+token_name);
            int index = token_name.indexOf("_");
            int uid = Integer.parseInt(token_name.substring(0, index));
            String val = redisTemplate.boundValueOps(token_name).get();
            if(val != null && val.split("&").length > 0 && Integer.parseInt(val.split("&")[0]) == uid){
                redisTemplate.boundValueOps(token_name).expire(1, TimeUnit.MINUTES);
                return true;
            }
            userService.logout(uid, request);
        }
        response.setStatus(400);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(JSON.toJSONString(MyResponse.fail("ACCESS DENIED: NO_LOGIN !")));
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

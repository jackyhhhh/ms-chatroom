package com.abc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static String getToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0 ){
            for(Cookie c: cookies){
                if("token".equals(c.getName())){
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public static void setTokenInCookie(String token, HttpServletResponse response){
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

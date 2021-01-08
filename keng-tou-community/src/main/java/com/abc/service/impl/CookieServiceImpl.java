package com.abc.service.impl;

import com.abc.service.CookieService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for(Cookie c: cookies){
                if("token".equals(c.getName())){
                    return c.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void addTokenInCookie(String tokenName, String tokenValue, HttpServletResponse response) {
        Cookie cookie = new Cookie(tokenName, tokenValue);
        response.addCookie(cookie);
    }
}

package com.abc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {
    String getToken(HttpServletRequest request);
    void addTokenInCookie(String tokenName, String tokenValue, HttpServletResponse response);
}

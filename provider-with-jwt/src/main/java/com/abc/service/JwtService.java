package com.abc.service;

public interface JwtService {
    String sign(int uid);
    Integer getUid(String token);
    Long getTimeExpireAt(String token);
    boolean checkToken(String token);
}

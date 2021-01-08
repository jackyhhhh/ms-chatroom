package com.abc.service.impl;

import com.abc.service.JwtService;
import com.abc.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String sign(int uid) {
        return JwtUtil.createToken(uid);
    }

    @Override
    public Integer getUid(String token) {
        return JwtUtil.getUid(token);
    }

    @Override
    public Long getTimeExpireAt(String token) {
        return JwtUtil.getTimeExpireAt(token);
    }

    @Override
    public boolean checkToken(String token) {
        return JwtUtil.checkToken(token);
    }
}

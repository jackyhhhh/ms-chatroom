package com.abc.service.impl;

import com.abc.service.JwtService;
import com.abc.utils.AesEncryptUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
@Service
public class JwtServiceImpl implements JwtService {

    /**
     * 过期时间
     */
    @Value("${jwt.expire}")
    private String expire;

    /**
     * jwt 密钥
     */
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String sign(int uid) {
        try {
            long expireTime = 0L;
            if(expire.endsWith("s")){
                expireTime = (Long.parseLong(expire.substring(0, expire.length()-1))) * 1000;
            }else if (expire.endsWith("m")){
                expireTime = (Long.parseLong(expire.substring(0, expire.length()-1))) *60 * 1000;
            }else if (expire.endsWith("h")){
                expireTime = (Long.parseLong(expire.substring(0, expire.length()-1))) * 60 * 60 * 1000;
            }else if (expire.endsWith("d")){
                expireTime = (Long.parseLong(expire.substring(0, expire.length()-1))) * 24 * 60 * 60 * 1000;
            }
            String expireAt = System.currentTimeMillis() + expireTime + "";
            Map<String, String> info = new LinkedHashMap<>();
            info.put("type", "aes");
            info.put("uid", "" + uid);
            info.put("expireAt", expireAt);
            String tokenJson = JSON.toJSONString(info);
            log.debug("createToken: token= " + tokenJson);

            return AesEncryptUtils.encrypt(tokenJson, secret);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getUid(String token) {
        try {
            String tokenJson = AesEncryptUtils.decrypt(token, secret);
            Map info = JSON.parseObject(tokenJson, Map.class);
            return Integer.parseInt((String) info.get("uid"));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Long getTimeExpireAt(String token) {
        try {
            String tokenJson = AesEncryptUtils.decrypt(token, secret);
            Map info = JSON.parseObject(tokenJson, Map.class);
            return Long.parseLong((String) info.get("expireAt"));
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public boolean checkToken(String token) {
        try {
            long now = System.currentTimeMillis();
            long expireAt = getTimeExpireAt(token);
            log.debug("checkToken: token=" + AesEncryptUtils.decrypt(token));
            log.debug("checkToken: (now-expire)/1000="+((now-expireAt)/1000));
            if(now < expireAt){
                return true;
            }
        } catch (Exception exception) {
            return false;
        }
        return false;
    }

    @Override
    public String invalidToken(String token) {
        try {
            int uid = getUid(token);
            String expireAt = 0+"";
            Map<String, String> info = new LinkedHashMap<>();
            info.put("type", "aes");
            info.put("uid", "" + uid);
            info.put("expireAt", expireAt);
            String tokenJson = JSON.toJSONString(info);
            log.debug("invalidToken: token= " + tokenJson);

            return AesEncryptUtils.encrypt(tokenJson, secret);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

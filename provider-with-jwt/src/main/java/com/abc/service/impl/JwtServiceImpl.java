package com.abc.service.impl;

import com.abc.service.JwtService;
import com.abc.utils.AesEncryptUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    /**
     * jwt 密钥
     */
    private static final String SECRET = "yanzhou__jghuang";

    @Override
    public String sign(int uid) {
        try {
            String expireAt = System.currentTimeMillis() + EXPIRE_TIME + "";
            Map<String, String> info = new LinkedHashMap<>();
            info.put("type", "aes");
            info.put("uid", "" + uid);
            info.put("expireAt", expireAt);
            String tokenJson = JSON.toJSONString(info);
//            System.out.println("createToken: token= " + tokenJson);

            return AesEncryptUtils.encrypt(tokenJson);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getUid(String token) {
        try {
            String tokenJson = AesEncryptUtils.decrypt(token);
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
            String tokenJson = AesEncryptUtils.decrypt(token);
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
//            System.out.println("checkToken: token=" + AesEncryptUtils.decrypt(token));
//            System.out.println("checkToken: (now-expire)/1000="+((now-expireAt)/1000));
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
//            System.out.println("invalidToken: token= " + tokenJson);

            return AesEncryptUtils.encrypt(tokenJson);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

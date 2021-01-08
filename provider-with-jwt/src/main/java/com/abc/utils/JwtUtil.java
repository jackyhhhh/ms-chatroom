package com.abc.utils;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    /**
     * jwt 密钥
     */
    private static final String SECRET = "jwt_secret";

    /**
     * 生成签名，五分钟后过期
     * @param uid 用户id
     * @return token
     */
    public static String createToken(int uid) {
        try {
            String expireAt = System.currentTimeMillis() + EXPIRE_TIME + "";
            Map<String, String> info = new HashMap<>();
            info.put("type", "aes");
            info.put("uid", "" + uid);
            info.put("expireAt", expireAt);
            String tokenJson = JSON.toJSONString(info);

            return AesEncryptUtils.encrypt(tokenJson);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据token获取uid
     * @param token 请求中携带的token令牌
     * @return uid
     */
    public static int getUid(String token) {
        try {
            String tokenJson = AesEncryptUtils.decrypt(token);
            Map info = JSON.parseObject(tokenJson, Map.class);
            return (int) info.get("uid");
        } catch (Exception e) {
            return -1;
        }
    }

    public static long getTimeExpireAt(String token) {
        try {
            String tokenJson = AesEncryptUtils.decrypt(token);
            Map info = JSON.parseObject(tokenJson, Map.class);
            return (Long) info.get("expireAt");
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 校验token
     * @param token token令牌
     * @return true for pass
     */
    public static boolean checkToken(String token) {
        try {
            long now = System.currentTimeMillis();
            long expireAt = getTimeExpireAt(token);
            if(now > expireAt){
                return true;
            }
        } catch (Exception exception) {
            throw new RuntimeException("checkToken发生错误!");
        }
        return false;
    }
}
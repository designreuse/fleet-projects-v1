package com.comodin.fleet.util;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.constants.ConstantsFinalValue;
import org.apache.commons.lang3.StringUtils;


/**
 * The type Session util.
 */
public class SessionUtil {

    /**
     * 获取session里面的值
     *
     * @param token the token
     * @param key   the key
     * @return attribute
     */
    public static String getAttribute(String token, String key) {
        return RedisUtil.hget(ConstantsFinalValue.REDIS_SESSION_MARK + token, key);
    }

    /**
     * 判断 token 是否存在
     *
     * @param token token为用户登陆之后，在Redis缓存的标识
     * @return true ：代表token已存在缓存； flase：代表token 在缓存已经失效
     */
    public static boolean getTokenExists(String token) {
        return RedisUtil.exists(ConstantsFinalValue.REDIS_SESSION_MARK + token);
    }

    /**
     * 根据token 从Redis获取当前登陆用户实体对象
     *
     * @param <T>   the type parameter
     * @param token the token
     * @param clazz the clazz
     * @return user entity by token
     */
    public static <T> T getUserEntityByToken(String token, Class<T> clazz) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String userEntityJSONStr = getAttribute(token, ConstantsFinalValue.REDIS_SESSION_USERINFO);
        if (StringUtils.isEmpty(userEntityJSONStr)) {
            return null;
        }

        return JSON.parseObject(userEntityJSONStr, clazz);
    }

    /**
     * 设置值到session里面
     *
     * @param token      UUID生成的32位
     * @param key        the key
     * @param value      the value
     * @param expiration the expiration
     * @return attribute
     */
    public static Long setAttribute(String token, String key, String value, int expiration) {
        return RedisUtil.hset(ConstantsFinalValue.REDIS_SESSION_MARK + token, key, value, expiration);
    }

    /**
     * 删除 用户token
     *
     * @param token 用户Session Redis缓存中的标识
     * @return 返回redis.del的结果 long
     */
    public static Long removeAttribute(String token) {
        return RedisUtil.del(ConstantsFinalValue.REDIS_SESSION_MARK + token);
    }

    /**
     * 刷新，token对应 Redis缓存的时间，默认为：1800秒
     *
     * @param toKen the to ken
     */
    public static void refreshToken(String toKen) {
        RedisUtil.expire(ConstantsFinalValue.REDIS_SESSION_MARK + toKen, ConstantsFinalValue.REDIS_SESSION_EXPIRES_IN);
    }

}

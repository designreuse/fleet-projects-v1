
package com.comodin.fleet.util;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Redis 工具类
 * Created by supeng on 2016-04-16 0016.
 */
public class RedisUtil {

    /**
     * The constant logger.
     */
    protected static Logger logger = Logger.getLogger(RedisUtil.class);


    private static JedisPool jedisPool; // 池化管理jedis链接池

    static {

        //读取相关的配置
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc-redis");
        int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
        int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
        int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));

        String ip = resourceBundle.getString("redis.ip");
        int port = Integer.parseInt(resourceBundle.getString("redis.port"));

        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(maxActive);
        //设置最大空闲数
        config.setMaxIdle(maxIdle);
        //设置超时时间
        config.setMaxWaitMillis(maxWait);

        //初始化连接池
        jedisPool = new JedisPool(config, ip, port);
    }


    /**
     * 判断 key 是否在redis缓存中
     *
     * @param key the key
     * @return boolean
     */
    public static boolean exists(String key) {
        try (Jedis jd = jedisPool.getResource()) {
            return jd.exists(key);
        } catch (Throwable t) {
            logger.error("jedis error key = " + key, t);
            throw t;
        }
    }

    /**
     * 为key 设置有效时间
     *
     * @param key        the key
     * @param expiration the expiration
     */
    public static void expire(String key, int expiration) {

        try (Jedis jd = jedisPool.getResource()) {
            jd.expire(key, expiration);
        } catch (Throwable t) {
            logger.error("jedis error key = " + key + "; expiration = " + expiration, t);
            throw t;
        }
    }

    /**
     * 字符串操作，set
     * 向缓存中设置字符串内容
     *
     * @param key        key     对应Redis的字符串，key
     * @param value      value   为字符串形式
     * @param expiration the expiration
     * @return string
     * @throws Exception
     */
    public static String set(String key, String value, int expiration) {
        try (Jedis jd = jedisPool.getResource()) {
            String status = jd.set(key, value);
            if (expiration > 0) {
                jd.expire(key, expiration);
            }
            return status;
        } catch (Throwable t) {
            logger.error("jedis error key = " + key + "value = " + value + "; expiration = " + expiration, t);
            throw t;
        }
    }

    /**
     * 字符串操作，set 【Value 为对象， 内部会将对象转换为JSON格式的字符串形式，存在到Redis中】
     *
     * @param key        the key
     * @param value      the value
     * @param expiration 小于0时不设置过期时间
     * @return string
     */
    public static String set(String key, Object value, int expiration) {
        String valueJsonString = JSON.toJSONString(value);
        return set(key, valueJsonString, expiration);
    }

    /**
     * 删除key
     *
     * @param key the key
     * @return long
     */
    public static Long del(String key) {
        try (Jedis jd = jedisPool.getResource()) {
            Long l = jd.del(key);
            return l;
        } catch (Throwable t) {
            logger.error("jedis error key = " + key, t);
            throw t;
        }
    }

    /**
     * 字符串操作，get
     *
     * @param key the key
     * @return string
     */
    public static String get(String key) {
        try (Jedis jd = jedisPool.getResource()) {
            return jd.get(key);
        } catch (Throwable t) {
            logger.error("jedis error key = " + key, t);
            throw t;
        }
    }

    /**
     * 字符串操作，get 【返回对象】
     *
     * @param <T>   要卖传递的clazz类型，返回
     * @param key   对应的redis中的key
     * @param clazz 需要转换对象的类型，class
     * @return t
     */
    public static <T> T get(String key, Class<T> clazz) {
        String result = get(key);
        return (result == null || "".equals(result.trim())) ? null : JSON.parseObject(result, clazz);
    }

    /**
     * hget
     *
     * @param key   the key
     * @param field the field
     * @return string
     */
    public static String hget(String key, String field) {
        try (Jedis jd = jedisPool.getResource()) {
            return jd.hget(key, field);
        } catch (Throwable t) {
            logger.error("jedis error key = " + key, t);
            throw t;
        }
    }

    /**
     * Hget t.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param field the field
     * @param clazz the clazz
     * @return the t
     */
    public static <T> T hget(String key, String field, Class<T> clazz) {
        String result = hget(key, field);
        return (result == null || "".equals(result.trim())) ? null : JSON.parseObject(result, clazz);
    }

    /**
     * hgetAll
     *
     * @param key the key
     * @return map
     */
    public static Map<String, String> hgetAll(String key) {
        try (Jedis jd = jedisPool.getResource()) {
            return jd.hgetAll(key);
        } catch (Throwable t) {
            logger.error("jedis error key = " + key, t);
            throw t;
        }
    }

    /**
     * hgetAllObject
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param clazz the clazz
     * @return map
     */
    public static <T> Map<String, ?> hgetAllObject(String key, Class<T> clazz) {

        HashMap<String, T> result = new HashMap<>();

        Map<String, String> stringStringMap = hgetAll(key);
        if (stringStringMap == null) {
            return null;
        }

        Set<Map.Entry<String, String>> set = stringStringMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            String entryKey = entry.getKey();
            T t = JSON.parseObject(entry.getValue(), clazz);
            result.put(entryKey, t);
        }
        return result;
    }

    /**
     * Hset long.
     *
     * @param key        the key
     * @param field      the field
     * @param value      the value
     * @param expiration the expiration
     * @return the long
     */
    public static Long hset(String key, String field, String value, int expiration) {

        try (Jedis jd = jedisPool.getResource()) {
            Long l = jd.hset(key, field, value);
            if (expiration > 0) {
                jd.expire(key, expiration);
            }
            return l;
        } catch (Throwable t) {
            logger.error("jedis error key = " + key, t);
            throw t;
        }
    }

    /**
     * Hset long.
     *
     * @param key        the key
     * @param field      the field
     * @param value      the value
     * @param expiration the expiration
     * @return the long
     */
    public static Long hset(String key, String field, Object value, int expiration) {
        String valueJsonString = JSON.toJSONString(value);
        return RedisUtil.hset(key, field, valueJsonString, expiration);
    }

    /**
     * Pipelined.
     *
     * @param key           the key
     * @param mapFieldValue the map field value
     * @param expiration    the expiration
     */
    public static void pipelined(String key, Map<String, String> mapFieldValue, int expiration) {

        try (Jedis jd = jedisPool.getResource()) {

            Pipeline pl = jd.pipelined();

            Set<Map.Entry<String, String>> set = mapFieldValue.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String entryKey = entry.getKey();
                String entryValue = entry.getValue();
                pl.hset(key, entryKey, entryValue);
            }

            if (expiration > 0) {
                jd.expire(key, expiration);
            }

            pl.sync();
        } catch (Throwable t) {
            logger.error("jedis error key = " + key, t);
            throw t;
        }
    }

}

package com.comodin.fleet.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * The type Properties util.
 */
public class PropertiesUtil {

    private static PropertiesUtil util = null;
    private static Map<String, Properties> maps = new HashMap<String, Properties>();

    private PropertiesUtil() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PropertiesUtil getInstance() {
        if (util == null) {
            util = new PropertiesUtil();
            maps = new HashMap<>();
        }
        return util;
    }

    /**
     * Load properties.
     *
     * @param name the name
     * @return the properties
     */
    public Properties load(String name) {
        if (maps.get(name) != null) {
            return maps.get(name);
        } else {
            Properties prop = new Properties();
            try {
                prop.load(PropertiesUtil.class.getResourceAsStream("/" + name));
                Iterator<Map.Entry<Object, Object>> it = prop.entrySet().iterator();
//                                while (it.hasNext()) {
//                                    Map.Entry<Object, Object> entry = it.next();
//                                    Object key = entry.getKey();
//                                    Object value = entry.getValue();
//                                    System.out.println("key   :" + key);
//                                    System.out.println("value :" + value);
//                                    System.out.println("---------------");
//                                }
                maps.put(name, prop);
                return prop;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Gets property value.
     *
     * @param propertiesFilePath the properties file path
     * @param key                the key
     * @return the property value
     */
    public static String getPropertyValue(String propertiesFilePath, String key) {
        return getInstance().load(propertiesFilePath).getProperty(key);
    }

    /**
     * Gets property value int.
     *
     * @param propertiesFilePath the properties file path
     * @param key                the key
     * @return the property value int
     */
    public static int getPropertyValueInt(String propertiesFilePath, String key) {
        return Integer.valueOf(getPropertyValue(propertiesFilePath, key));
    }

    /**
     * Gets property value boolean.
     *
     * @param propertiesFilePath the properties file path
     * @param key                the key
     * @return the property value boolean
     */
    public static boolean getPropertyValueBoolean(String propertiesFilePath, String key) {
        return Boolean.valueOf(getPropertyValue(propertiesFilePath, key));
    }

    /**
     * 使用map来实现第二种单例模式
     *
     * @param name the name
     * @return properties
     */
    public static Properties createProperties(String name) {
        if (maps.get(name) != null) {
            return maps.get(name);
        } else {
            Properties properties = new Properties();
            try {
                properties.load(PropertiesUtil.class
                        .getClassLoader()
                        .getResourceAsStream(name + ".properties"));
            } catch (IOException e) {
                return null;
            }
            maps.put(name, properties);
            return properties;
        }
    }

    /**
     * 使用map来实现第二种单例模式
     * 读取某个路径下的name
     *
     * @param path 路径的最后一定要增加/，xxx/
     * @param name
     * @return
     */
    private static Properties createProperties(String path, String name) {
        String realName = path + name;
        return createProperties(realName);
    }

}

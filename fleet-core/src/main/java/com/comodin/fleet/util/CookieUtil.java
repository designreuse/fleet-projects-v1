package com.comodin.fleet.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Cookie util.
 */
public class CookieUtil {

    /**
     * 添加cookie
     *
     * @param name     cookie的key
     * @param value    cookie的value
     * @param domain   domain                 ＠param  path path
     * @param maxage   最长存活时间 单位为秒
     * @param path     the path
     * @param response the response
     */
    public static void addCookie(String name, String value, String domain, int maxage, String path, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxage);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 往根下面存一个cookie
     * * @param name cookie的key
     *
     * @param name     the name
     * @param value    cookie的value
     * @param domain   domain
     * @param maxage   最长存活时间 单位为秒
     * @param response the response
     */
    public static void addCookie(String name, String value, String domain, int maxage, HttpServletResponse response) {
        addCookie(name, value, domain, maxage, "/", response);
    }

    /**
     * 从cookie值返回cookie值，如果没有返回 null
     *
     * @param request the request
     * @param name    the name
     * @return cookie的值 cookie value by name
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = getCookieByName(request, name);
        return cookie != null ? cookie.getValue() : null;
    }


    /**
     * Gets cookie by name.
     *
     * @param request the request
     * @param name    the name
     * @return the cookie by name
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * Remove cookie.
     *
     * @param name     the name
     * @param domain   the domain
     * @param request  the request
     * @param response the response
     */
    public static void removeCookie(String name, String domain, HttpServletRequest request, HttpServletResponse response) {
        String cookieVal = getCookieValueByName(request, name);
        if (cookieVal != null) {
            CookieUtil.addCookie(name, null, domain, 0, response);
        }
    }

    /**
     * Remove cookie.
     *
     * @param name     the name
     * @param request  the request
     * @param response the response
     */
    public static void removeCookie(String name, HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.removeCookie(name, ".dhgate.com", request, response);
    }
}

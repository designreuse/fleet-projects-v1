package com.comodin.fleet.util;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.core.bean.DepartmentBean;
import com.comodin.fleet.core.bean.RoleBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.json.ResultEntity;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by supeng on 5/11/2016.
 */
public class FleetBasiUtil {
    private static final Logger log = Logger.getLogger(FleetBasiUtil.class);

    /**
     * 获取所有的，角色列表数据，查询Redis缓存
     *
     * @return 返回RolesList redis role map
     */
    public static Map<String, RoleBean> getRedisRoleMap() {
        return (Map<String, RoleBean>) RedisUtil.hgetAllObject(ConstantsFinalValue.REDIS_ROLE_MARK, RoleBean.class);
    }

    /**
     * 往 Redist缓存，存入 所有的角色记录；
     *
     * @param roleBeanList 数据库对应的角色表中，所有的记录缓存到Redis中；
     */
    public static void setRedisRoleList(List<RoleBean> roleBeanList) {

        Map<String, String> map = new HashMap<>();
        log.info("===================================== role List Size：" + roleBeanList.size());
        for (RoleBean roleBean : roleBeanList) {
            String key = roleBean.getId().toString();
            String jsonString = JSON.toJSONString(roleBean);
            map.put(key, jsonString);
            log.info("===================================== role List id " + key + " varlue " + jsonString);
        }
        RedisUtil.pipelined(ConstantsFinalValue.REDIS_ROLE_MARK, map, -1);
    }

    /**
     * 获取所有的，部门列表数据，查询Redis缓存
     *
     * @return 返回Department List
     */
    public static Map<String, DepartmentBean> getRedisDepartmentMap() {
        return (Map<String, DepartmentBean>) RedisUtil.hgetAllObject(ConstantsFinalValue.REDIS_DEPARTMENT_MARK, DepartmentBean.class);
    }

    /**
     * 获取所有司机，当前经纬度信息，查询Redis缓存
     *
     * @return 返回 LatLngDriverBean JSON List
     */
    public static Map<String, LatLngDriverBean> getAllDriverCurrentCoordinates() {
        return (Map<String, LatLngDriverBean>) RedisUtil.hgetAllObject(ConstantsFinalValue.REDIS_DRIVER_ALL_CURRENT_COORDINATES, LatLngDriverBean.class);
    }

    /**
     * 往 Redist缓存，存入 所有的 部门 记录；
     *
     * @param departmentBeanList 数据库对应的 部门表中，所有的记录缓存到Redis中；
     */
    public static void setRedisDepartmentBeanList(List<DepartmentBean> departmentBeanList) {

        Map<String, String> map = new HashMap<>();
        log.info("===================================== department List Size：" + departmentBeanList.size());
        for (DepartmentBean departmentBean : departmentBeanList) {

            String key = departmentBean.getId().toString();
            String jsonString = JSON.toJSONString(departmentBean);
            map.put(key, jsonString);
            log.info("===================================== department List id " + key + " varlue " + jsonString);

        }
        RedisUtil.pipelined(ConstantsFinalValue.REDIS_DEPARTMENT_MARK, map, -1);
    }

    /**
     * 组装，用户对应的，所有角色中，所有包含的，功能权限列表
     *
     * @param rolesString 用户对应的角色，字符串
     * @return 返回用户所有角色中 ，对应的所有的权限，拼装后的字符串
     */
    public static String assemblingUserPrivilege(String rolesString) {

        //根据：用户对象的所有角色，查询redis区域中【role】获取角色对应功能权限数据，并将功能权限数据，附加到用户对象中；
        String[] roleArray = rolesString.split("\\" + ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR);

        Map<String, RoleBean> roleMap = getRedisRoleMap();

        Set<String> privilegeSet = new HashSet<>();
        for (String roleId : roleArray) {

            //获取所有角色列表数据，Redis缓存中读取，不从数据库查询
            RoleBean roleBean = roleMap.get(roleId);

            //判断，role对象，是否存在，以及role是否有效，并且对应的功能字符串是否为空；若为空就跳出当前循环
            if (roleBean != null
                    && roleBean.getStatus() != null && roleBean.getStatus().trim().equals(ConstantsFinalValue.ROLE_STATUS_ENABLE)
                    && roleBean.getPrivilege() != null && !"".equals(roleBean.getPrivilege().trim())) {

                //提取，角色对应的功能权限列表，并每个角色中，功能权限进行排重处理；
                String[] privilegeArray = roleBean.getPrivilege().split("\\" + ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR);
                for (String privilegeTemp : privilegeArray) {
                    if (!privilegeSet.contains(privilegeTemp)) {
                        privilegeSet.add(privilegeTemp);
                    }
                }
            }
        }

        //将所有的已经被排重的，功能权限，进行拼接。
        StringBuilder privilegeBuffer = new StringBuilder(20);
        for (String s : privilegeSet) {
            privilegeBuffer.append(s).append(ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR);
        }

        //log.info("user privilege: " + privilegeBuffer.toString());

        return privilegeBuffer.toString();
    }

    /**
     * 组装，查询结果集，带分页信息的。
     *
     * @param resultList 查询返回的结果集
     * @param draw       客户端请求的次数的编号
     * @return 返回 ，带分页信息的结果集 Map集合
     */
    public static Map<String, Object> assemblingReturnResultListBandPageInfo(List<?> resultList, Integer draw) {
        Map<String, Object> map = new HashMap<>();
        PageInfo page = new PageInfo(resultList);//用PageInfo对结果进行包装  PageInfo包含了非常全面的分页属性
        map.put("draw", draw);
        map.put("recordsTotal", page.getTotal());
        map.put("recordsFiltered", page.getTotal());
        map.put("data", resultList);
        return map;
    }

    /**
     * Assembling cron expression week string.
     *
     * @param week the week
     * @return the string
     */
    public static String assemblingCronExpressionWeek(String week) {
        //* * * ? * SUN,TUE,THU *
        String strCron = "* * * ? * WEEK *";
        strCron = strCron.replaceAll("WEEK", week);
        return strCron;
    }

    /**
     * 组装，成功返回给客户，实体对象。
     *
     * @param messageObj 返回给客户端，成功，简单消息信息
     * @return 返回 ResultEntity 实体
     */
    public static ResultEntity assemblingReturnResultList(Object messageObj) {
        return new ResultEntity(ConstantsFinalValue.RESULTS_CODE_SUCCESS, messageObj);
    }

    /**
     * Assembling user full name by last name and first name string.
     *
     * @param userBean the user bean
     * @return the string
     */
    public static String assemblingUserFullNameByLastNameAndFirstName(UserBean userBean) {
        return (userBean == null) ? "" : userBean.getLastName() + " " + userBean.getFirstName();
    }

    /**
     * Send response json message.
     *
     * @param resultCode 为返回结果的代码，都在 ConstantsFinalValue 类已经定义好了
     * @param messageObj 为返回结果的内容  ResultEntity 格式
     * @param response   使用response流
     * @throws IOException 抛出
     */
    public static void sendResponseJSONMessage(Integer resultCode, Object messageObj, HttpServletResponse response) throws IOException {
        ResultEntity resultEntity = new ResultEntity(resultCode, JSON.toJSONString(messageObj));
        String jsonString = JSON.toJSONString(resultEntity);
        log.info(jsonString);

        response.getWriter().write(jsonString);
        response.flushBuffer();
    }


    /**
     * Gets token by rquest cookie.
     *
     * @param request the request
     * @return the token by rquest cookie
     */
    public static String getTokenByRquestCookie(HttpServletRequest request) {
        //判断，用户URL请求头，是否包含 access_token
        String token = CookieUtil.getCookieValueByName(request, ConstantsFinalValue.TOKEN);
        return (StringUtils.isEmpty(token)) ? null : token;
    }

    /**
     * Gets token by rquest header.
     *
     * @param request the request
     * @return the token by rquest header
     */
    public static String getTokenByRquestHeader(HttpServletRequest request) {
        //判断，用户URL请求头，是否包含 access_token
        String token = request.getHeader(ConstantsFinalValue.TOKEN);
        return (StringUtils.isEmpty(token)) ? null : token;
    }

    /**
     * Gets session user bean by rquest cookie.
     *
     * @param <T>     the type parameter
     * @param request the request
     * @param clazz   the clazz
     * @return the session user bean by rquest cookie
     */
    public static <T> T getSessionUserBeanByRquestCookie(HttpServletRequest request, Class<T> clazz) {
        String tokenByRquestCookie = getTokenByRquestCookie(request);
        if (StringUtils.isEmpty(tokenByRquestCookie)) {
            return null;
        }
        return SessionUtil.getUserEntityByToken(tokenByRquestCookie, clazz);
    }

    /**
     * Gets session user bean by rquest header.
     *
     * @param <T>     the type parameter
     * @param request the request
     * @param clazz   the clazz
     * @return the session user bean by rquest header
     */
    public static <T> T getSessionUserBeanByRquestHeader(HttpServletRequest request, Class<T> clazz) {
        String tokenByRquestCookie = getTokenByRquestHeader(request);
        if (StringUtils.isEmpty(tokenByRquestCookie)) {
            return null;
        }
        return SessionUtil.getUserEntityByToken(tokenByRquestCookie, clazz);
    }

    /**
     * 用于，service层，getListByVo 方法中，进行模糊查询是，增加%value%
     *
     * @param value 需要模糊查询的字段
     * @return string
     */
    public static String likePercent(String value) {
        StringBuffer sb = new StringBuffer();
        return sb.append("%").append(value).append("%").toString();
    }

    /**
     * 根据请求的方式，判断是否为Ajax请求，和普通请求，进行区分
     * 如果为：Ajax请求，即返回 结果消息体，里面包含，跳转页面，以及 代码号为：-1即为跳转
     * 如果为：普通方式请求，直接 浏览器 跳转至 用户登陆页面
     *
     * @param tips     the tips
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */
    public static void returnRequestTypeResult(String tips, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            //如果是ajax请求响应头会有，x-requested-with
            Map<String, Object> map = new HashMap<>();
            map.put("redirect_URL", "/userLogin" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX);
            map.put("redirect_Tips", tips);
            FleetBasiUtil.sendResponseJSONMessage(ConstantsFinalValue.RESULTS_CODE_TOKEN_FAILURE, map, response);
        } else {
            request.getRequestDispatcher("/userLogin" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX).forward(request, response);
        }
    }

    /**
     * 检查，登陆账号是否为，客户；
     * 通过 user.sn 是否为有值来判断，若有值该值即为客户表中的id；若为空，即代表员工账号
     *
     * @param userBean 登陆过的用户账号对象
     * @return boolean true代表为客户账号；false：代表为员工账号
     */
    public static boolean checkLoginAccountIsClient(UserBean userBean) {
        if (StringUtils.isEmpty(userBean.getSn())) {
            return false;
        }
        return true;
    }
}

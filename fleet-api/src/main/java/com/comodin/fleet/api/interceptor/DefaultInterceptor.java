package com.comodin.fleet.api.interceptor;

import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.util.CommonUtil;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultInterceptor implements HandlerInterceptor {

    private static final Logger log = Logger.getLogger(DefaultInterceptor.class);

    /**
     * 该方法在，目标方法之前，调用。 若返回值为true，则继续调用后续的拦截器和目标方法。 若返回值为false，则不会再调用后续的拦截器和目标方法。
     * <p>
     * 可谓考虑做，权限，日志，事务，等等
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //audit log
        logAllRequest(request);

        // all request URI suffix should be .php
        boolean checkURISuffix = checkRequestURISuffix(request);
        if (!checkURISuffix) {
            FleetBasiUtil.returnRequestTypeResult("No request path suffix .php", request, response);
            return false;
        }

        return checkAuth(request, response);
    }


    /**
     * 检查用户是否登陆，并且是否拥有该访问权限
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @SuppressWarnings("Duplicates")
    private boolean checkAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(ConstantsFinalValue.TOKEN);
        log.info("request parameters cookie token=" + token);

        // 如果token为空不给访问，并通过 response.getWriter 输出提示信息为，token is null
        if (CommonUtil.isEmpty(token)) {
            log.debug("token is null");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_CHECK_AUTH, "false");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_AUTH_REASON, "token=null");

            FleetBasiUtil.sendResponseJSONMessage(ConstantsFinalValue.RESULTS_CODE_ERROR_PARAMETER, "token is null", response);
            return false;
        }

        //如果token，在Redis不存在，代表 token失效，提示给用户
        boolean tokenExists = SessionUtil.getTokenExists(token);
        if (!tokenExists) {
            log.debug("token failure");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_CHECK_AUTH, "token failure");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_AUTH_REASON, "token=" + token);
            FleetBasiUtil.sendResponseJSONMessage(ConstantsFinalValue.RESULTS_CODE_TOKEN_FAILURE, "Your login timeout or do not operate for a long time, please re-visit!", response);
            return false;
        }


//        String uri = request.getRequestURI();
//        String regExp = "^/([a-zA-Z0-9]+)\\\\.php$";
//        String requestMapping = CommonUtil.regExpFindMatch(regExp, uri);
//        // 如果访问路径为空不给访问
//        if (CommonUtil.isEmpty(requestMapping)) {
//            log.debug("requestMapping cannot be null");
//            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_CHECK_AUTH, "false; requestMapping=null");
//            return false;
//        }
//
//
//        //获取 当前token 对应的权限列表
//        String privileges = SessionUtil.getAttribute(token, ConstantsFinalValue.REDIS_SESSION_PRIVILEGES);
//        // 如果没有权限不给访问
//        boolean hasPrivileges = privileges.contains("|" + requestMapping + "|");
//        if (!hasPrivileges) {
//            log.debug("authentication fail; request privilege=" + requestMapping + "; user has privileges=" + privileges);
//            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_CHECK_AUTH, "false; have_no_privilege");
//
//            String resultMessage = "authentication fail; request privilege";
//            FleetBasiUtil.returnRequestTypeResult(resultMessage, request, response);
//            return false;
//        } else {
//            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_CHECK_AUTH, "success");
//        }

        //刷新token 在缓存中的时间
        SessionUtil.refreshToken(token);
        return true;
    }

    /**
     * 请求地址必须以.php为后缀, 且目录只有一层
     *
     * @return
     */
    private boolean checkRequestURISuffix(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String regExp = "^/fleet-api/[a-zA-Z0-9]+\\.php$";
        return CommonUtil.regExpMatch(regExp, uri);
    }

    /**
     * 记录audit日志
     *
     * @param request
     */
    @SuppressWarnings("Duplicates")
    private void logAllRequest(HttpServletRequest request) {
        // 生成request id
        String requestId = UUID.randomUUID().toString();
        request.setAttribute(ConstantsFinalValue.AUDIT_LOG_REQUEST_ID, requestId);

        // 记录本服务器ip
        String localIP = CommonUtil.getLocalServerIp();
        CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_LOCAL_IP, localIP);

        // 记录客户端ip
        String remoteIP = CommonUtil.getRemoteIp(request);
        CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_REMOTE_IP, remoteIP);

        // 记录访问时间
        CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_TIMESTAMP, System.currentTimeMillis() + "");

        // 记录访问的uri
        CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_REQ_URI, request.getRequestURI());

        // 记录请求方式
        CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_REQ_METHOD, request.getMethod());

        // 记录请求参数
        CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_PARAMS, request.getParameterMap().toString());

        // 记录请求路径
        CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_REQ_URI, request.getRequestURI());
    }


    /**
     * 方法在，调用目标方法之后，但渲染视图之前
     * <p>
     * 可以对，请求域中的属性，或 视图做出修改。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // log.info("AuthIterceptor  postHandle");
    }

    /**
     * 方法在，调用渲染视图之后。
     * <p>
     * 可以，进行释放资源所用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // log.info("AuthIterceptor  afterCompletion");
    }
}

package com.comodin.fleet.portal.interceptor;

import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.util.CommonUtil;
import com.comodin.fleet.util.FleetBasiUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 记录audit日志
 */
public class AuditLogInterceptor implements HandlerInterceptor {
    private static final Logger log = Logger.getLogger(AuditLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AuditLogInterceptor......");
        //audit log
        logAllRequest(request);

        // all request URI suffix should be .php
        boolean checkURISuffix = checkRequestURISuffix(request);
        if (!checkURISuffix) {
            FleetBasiUtil.returnRequestTypeResult("No request path suffix .php", request, response);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 请求地址必须以.php为后缀, 且目录只有一层
     *
     * @return
     */
    private boolean checkRequestURISuffix(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String regExp = "^/fleet-portal/[a-zA-Z0-9]+\\.php$";
        return CommonUtil.regExpMatch(regExp, uri);
    }

    /**
     * 记录audit日志
     *
     * @param request
     */
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
}

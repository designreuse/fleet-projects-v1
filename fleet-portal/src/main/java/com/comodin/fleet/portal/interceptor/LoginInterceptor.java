package com.comodin.fleet.portal.interceptor;

import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.util.CommonUtil;
import com.comodin.fleet.util.CookieUtil;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 登陆认证的拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger log = Logger.getLogger(LoginInterceptor.class);

    /**
     * <pre>
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     * </pre>
     */
    @SuppressWarnings("Duplicates")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = CookieUtil.getCookieValueByName(request, ConstantsFinalValue.TOKEN);
        //String token = request.getHeader(ConstantsFinalValue.TOKEN);
        log.info("request parameters cookie token=" + token);

        // 如果token为空不给访问，跳转到登陆页面
        if (StringUtils.isEmpty(token)) {
            log.debug("token is null");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_CHECK_AUTH, "false");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_AUTH_REASON, "token=null");

            FleetBasiUtil.returnRequestTypeResult("token is null", request, response);
            return false;
        }

        //如果token，在Redis不存在，代表 token失效，跳到到登陆页面，重新登陆
        boolean tokenExists = SessionUtil.getTokenExists(token);
        if (!tokenExists) {
            log.debug("token failure");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_CHECK_AUTH, "token failure");
            CommonUtil.logForAudit(request, ConstantsFinalValue.AUDIT_LOG_AUTH_REASON, "token=" + token);
            String resultMessage = "Your login timeout or do not operate for a long time, please re-visit!";
            FleetBasiUtil.returnRequestTypeResult(resultMessage, request, response);
            return false;
        }

//        String uri = request.getRequestURI();
//        String regExp = "^/([a-zA-Z0-9]+)\\\\.php$";
//        String requestMapping = CommonUtil.regExpFindMatch(regExp, uri);
//
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

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}

package com.comodin.fleet.util;

import com.comodin.fleet.constants.ConstantsFinalValue;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Common util.
 */
public class CommonUtil {
    /**
     * logger
     */
    private static final Logger log = Logger.getLogger(CommonUtil.class);


    /**
     * 本机ip地址
     */
    private static String localIp = null;

    /**
     * 获取本机ip地址，主要是记录日志或本机信息相关的时候用
     *
     * @return local server ip
     */
    public static String getLocalServerIp() {
        if (localIp == null) {
            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
                localIp = addr.getHostAddress();
            } catch (UnknownHostException e) {
                log.error("get local server address error", e);
            }
        }
        return localIp;
    }

    /**
     * 打印audit的日志, type是数据的类型，比如username、token、ip。。。；value对应的值
     */
    private static final Logger auditLogger = Logger.getLogger("com.comodin.audit");

    /**
     * Log for audit.
     *
     * @param request the request
     * @param type    the type
     * @param value   the value
     */
    public static void logForAudit(HttpServletRequest request, String type, String value) {
        String reqId = (String) request.getAttribute(ConstantsFinalValue.AUDIT_LOG_REQUEST_ID);
        //如果reqId不存在就生成一个
        if (reqId == null) {
            reqId = UUID.randomUUID().toString();
            request.setAttribute(ConstantsFinalValue.AUDIT_LOG_REQUEST_ID, reqId);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(reqId);
        sb.append(", ");
        sb.append(type);
        sb.append(", ");
        sb.append(value);
        auditLogger.info(sb.toString());
    }

    /**
     * 如果取不到request对象，就直接传reqId
     *
     * @param reqID the req id
     * @param type  the type
     * @param value the value
     */
    public static void logForAudit(String reqID, String type, String value) {

        StringBuffer sb = new StringBuffer();
        sb.append(reqID);
        sb.append(", ");
        sb.append(type);
        sb.append(", ");
        sb.append(value);
        auditLogger.info(sb.toString());
    }

    /**
     * Gets remote ip.
     *
     * @param request the request
     * @return the remote ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ipAddress = null;
        // ipAddress = this.getRequest().getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * 判断字符串是否符合正则表达式
     *
     * @param regExp the reg exp
     * @param args   the args
     * @return boolean
     */
    public static boolean regExpMatch(String regExp, String args) {

        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(args);
        return matcher.matches();
    }

    /**
     * 取出第一个符合正则表达式的字符串
     *
     * @param regExp the reg exp
     * @param args   the args
     * @return string
     */
    public static String regExpFindMatch(String regExp, String args) {

        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(args);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


    /**
     * 判断字符串是否为空
     *
     * @param str the str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() == 0 || str.trim().equals("")
                || str.trim().equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String value = "/kkxins122g.php";
        //String regExp = "^/([a-zA-Z0-9]+)\\.php$";
        String regExp = "^/([a-zA-Z0-9]+)\\.php$";

        String result = regExpFindMatch(regExp, value);
        System.out.println(result);
    }
}

package com.pandaz.commons.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取ip
 *
 * @author Carzer
 * @since 2019-08-22
 */
@Component
public final class IpUtil {

    /**
     * 构造方法
     */
    private IpUtil() {

    }

    /**
     * unknown标识
     */
    private static String unknown;

    /**
     * 设置UNKNOWN
     *
     * @param unknown unknown
     */
    @Value("${custom.unknown}")
    public static void setUnknown(String unknown) {
        IpUtil.unknown = unknown;
    }

    /**
     * @param request 请求
     * @return java.lang.String
     * <p>
     * getIpAddress 方法的注释
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

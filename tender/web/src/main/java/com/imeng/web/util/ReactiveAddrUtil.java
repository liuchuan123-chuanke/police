package com.imeng.web.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author zlt
 * @date 2019/10/7
 * <p>
 * Blog: https://blog.csdn.net/zlt2000
 * Github: https://github.com/zlt2000
 */
@Slf4j
public class ReactiveAddrUtil {
    private final static String UNKNOWN_STR = "unknown";
    /**
     * 获取客户端IP地址
     */
    public static String getRemoteAddr(ServerHttpRequest request) {
        Map<String, String> headers = request.getHeaders().toSingleValueMap();
        String ip = headers.get("X-Forwarded-For");
        if (isEmptyIP(ip)) {
            ip = headers.get("Proxy-Client-IP");
            if (isEmptyIP(ip)) {
                ip = headers.get("WL-Proxy-Client-IP");
                if (isEmptyIP(ip)) {
                    ip = headers.get("HTTP_CLIENT_IP");
                    if (isEmptyIP(ip)) {
                        ip = headers.get("HTTP_X_FORWARDED_FOR");
                        if (isEmptyIP(ip)) {
                            //@TODO 为了满足webTestClient的测试 需求 可能为空的情况
                            if(request.getRemoteAddress()==null){
                                return "127.0.0.1";
                            }
                            ip = request.getRemoteAddress().getAddress().getHostAddress();
                            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                                // 根据网卡取本机配置的IP
                                ip = getLocalAddr();
                            }
                        }
                    }
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!isEmptyIP(ip)) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    private static boolean isEmptyIP(String ip) {
        return StrUtil.isEmpty(ip) || UNKNOWN_STR.equalsIgnoreCase(ip);
    }

    /**
     * 获取本机的IP地址
     */
    public static String getLocalAddr() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("InetAddress.getLocalHost()-error", e);
        }
        return "";
    }

    /**
     * 是否在两个ip范围内
     * @param ip
     * @param startIp
     * @param endIp
     * @return
     */
    public static boolean isBetweenIps(String ip,String startIp,String endIp){
        long addressIp = IP2Long(ip);
        long start = IP2Long(startIp);
        long end = IP2Long(endIp);
        return (addressIp >= start && addressIp <= end);
    }

    /**
     * ip转long
     * @param ip
     * @return
     */
    private static long IP2Long(String ip)
    {
        String[] ipBytes;
        double num = 0;
        if(!StringUtils.isEmpty(ip))
        {
            ipBytes = ip.split("\\.");
            for (int i = ipBytes.length - 1; i >= 0; i--)
            {
                num += ((Integer.parseInt(ipBytes[i]) % 256) * Math.pow(256, i));
            }
        }
        System.out.println(num);
        return (long)num;
    }

    public static void main(String[] args) {
        System.out.println(isBetweenIps("192.168.20.153", "192.168.20.1","192.168.20.158"));
    }
}

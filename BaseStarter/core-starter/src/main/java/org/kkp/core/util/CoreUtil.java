package org.kkp.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kkp.core.CoreConstant;
import org.kkp.core.CoreProperties;
import org.kkp.core.exception.SystemRuntimeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
@Slf4j
public class CoreUtil {

    /**
     * @param response
     * @param req
     * @param coreProperties
     */
    public static void fixCors(HttpServletResponse response, HttpServletRequest req, CoreProperties coreProperties) {
        final String originHeaderName = "Origin";
        final String headerAccessControlAllowOrigin = "Access-Control-Allow-Origin";
        final String headerAccessControlAllowMethods = "Access-Control-Allow-Methods";
        final String headerAccessControlAllowHeaders = "Access-Control-Allow-Headers";
        final String headerValue = "*";
        if (StringUtils.isBlank(response.getHeader(headerAccessControlAllowOrigin))) {
            String originHeader = req.getHeader(originHeaderName);
            if (StringUtils.isNotBlank(originHeader)) {
                String[] corsAllowedOrigins = coreProperties.getCorsAllowedOrigins().split(",");
                Set<String> allowedOrigins = new HashSet<>(Arrays.asList(corsAllowedOrigins));
                if (allowedOrigins.contains(originHeader)) {
                    response.setHeader(headerAccessControlAllowOrigin, originHeader);
                }
            } else {
                response.setHeader(headerAccessControlAllowOrigin, headerValue);
            }
        }
        if (StringUtils.isBlank(response.getHeader(headerAccessControlAllowMethods))) {
            response.setHeader(headerAccessControlAllowMethods, headerValue);
        }
        if (StringUtils.isBlank(response.getHeader(headerAccessControlAllowHeaders))) {
            response.setHeader(headerAccessControlAllowHeaders, headerValue);
        }
    }

    /**
     * 读取字节信息
     *
     * @param buf 字节流
     * @return String
     */
    public static String getPayLoad(byte[] buf) {
        try {
            return new String(buf, 0, buf.length, CoreConstant.CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            throw new SystemRuntimeException(e);
        }
    }

    /**
     * 属性拷贝(忽略为空的属性)
     *
     * @param src    src
     * @param target target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 属性拷贝(忽略为空的属性)
     *
     * @param source source
     * @return String[]
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String getTraceId() {
        try {
            Object traceIdObj = RequestContextHolder.currentRequestAttributes().getAttribute(CoreConstant.REQUEST_TRACE_ID_KEY, RequestAttributes.SCOPE_REQUEST);
            if (traceIdObj != null) {
                return (String) traceIdObj;
            }
        } catch (Exception ex) {
            log.debug("get traceId failed");
        }
        // 自动生成
        String traceId = UUID.randomUUID().toString();
        // 放入作用域
        try {
            RequestContextHolder.getRequestAttributes().setAttribute(CoreConstant.REQUEST_TRACE_ID_KEY, traceId, RequestAttributes.SCOPE_REQUEST);
        } catch (Exception ex) {
            log.debug("set traceId failed");
        }
        return traceId;
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获得局域网IP地址
     *
     * @return InetAddress
     * @throws UnknownHostException
     */
    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        //排除docker
        final String excludeDocker = "docker";
        //处理
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                //排除docker
                if (!iface.getName().contains(excludeDocker)) {
                    // 在所有的接口下再遍历IP
                    for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                        InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                        if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                            if (inetAddr.isSiteLocalAddress()) {
                                // 如果是site-local地址，就是它了
                                return inetAddr;
                            } else if (candidateAddress == null) {
                                // site-local类型的地址未被发现，先记录候选地址
                                candidateAddress = inetAddr;
                            }
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}

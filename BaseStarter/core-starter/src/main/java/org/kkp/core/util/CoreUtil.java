package org.kkp.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kkp.core.CoreConstant;
import org.kkp.core.CoreProperties;
import org.kkp.core.exception.SystemRuntimeException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
}

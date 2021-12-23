package org.kkp.core.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 包装类
 * 继承了tomcat的包装类，使用了包装器与装饰器模式
 *
 * @author Klaus
 * @since 2021/12/22
 **/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * The default behavior of this method is to return getHeader(String name)
     * on the wrapped request object.
     *
     * @param name
     */
    @Override
    public String getHeader(String name) {
        return XssUtil.xssEncode(super.getHeader(name));
    }

    /**
     * The default behavior of this method is to return getQueryString() on the
     * wrapped request object.
     */
    @Override
    public String getQueryString() {
        return XssUtil.xssEncode(super.getQueryString());
    }

    @Override
    public String getParameter(String name) {
        return XssUtil.xssEncode(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = XssUtil.xssEncode(values[i]);
            }
            return escapseValues;
        }
        return values;
    }
}

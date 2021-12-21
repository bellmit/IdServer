package org.kkp.core.exception.handler;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
public interface IExceptionAlert {

    void alert(String traceId, Exception exception);
}

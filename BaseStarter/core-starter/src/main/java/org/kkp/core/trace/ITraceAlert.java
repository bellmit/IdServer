package org.kkp.core.trace;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
public interface ITraceAlert {

    /**
     * 提醒
     *
     * @param traceDto traceDto
     */
    void alert(TraceDto traceDto);
}

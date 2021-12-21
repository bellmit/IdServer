package org.kkp.core.trace;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
public interface IDataHis {

    /**
     * 保存
     *
     * @param traceId
     * @param name
     * @param pk
     * @param content
     * @param userId
     */
    void save(String traceId, String name, String pk, Object content, String userId);
}

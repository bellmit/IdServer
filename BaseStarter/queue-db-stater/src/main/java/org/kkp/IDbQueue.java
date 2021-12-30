package org.kkp;

public interface IDbQueue {
    /**
     * 生产消息
     *
     * @param dbQueueMessage 消息
     * @return String 消息ID
     */
    String producer(DbQueueMessage dbQueueMessage);
}

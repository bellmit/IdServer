package org.kkp;

public interface IDbQueueLock {
    /**
     * 锁定
     *
     * @param key key
     */
    boolean lock(String key);

    /**
     * 解锁
     *
     * @param key key
     */
    void unlock(String key);
}

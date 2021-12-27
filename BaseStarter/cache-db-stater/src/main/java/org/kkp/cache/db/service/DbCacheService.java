package org.kkp.cache.db.service;

import lombok.extern.slf4j.Slf4j;
import org.kkp.cache.db.entity.DbCacheEntity;
import org.kkp.cache.db.repository.IDbCacheRepository;
import org.kkp.core.lock.FileLockWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

/**
 * @author Klaus
 * @since 2021/12/28
 **/
@Service
@Slf4j
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class DbCacheService {

    /**
     * key最大长度
     */
    private static final long MAX_KEY_LENGTH = 1000;

    /**
     * 环境变量
     */
    @Autowired
    Environment env;

    /**
     * iDbCacheRepository
     */
    @Autowired
    IDbCacheRepository iDbCacheRepository;

    /**
     * 设置ttl
     *
     * @param key    key
     * @param expire expire
     * @param wait   wait
     */
    public void setTtl(String key, Long expire, boolean wait) throws IOException {
        String localName = getKey(key);
        try (FileLockWrapper fileLockWrapper = new FileLockWrapper(localName, wait)) {
            if (fileLockWrapper.isValid()) {
                this.setTtl(key, expire);
            }
        }
    }

    /**
     * 设置ttl
     *
     * @param key
     * @param expire
     */
    private void setTtl(String key, Long expire) {
        //构造key
        key = getKey(key);
        //获得缓存实体
        DbCacheEntity entity = getEntity(key);
        //判空
        if (entity != null) {
            entity = new DbCacheEntity();
            entity.setK(key);
            entity.setCreateTime(System.currentTimeMillis());
            entity.setExpire(expire);
            entity.setExpireTime(entity.getCreateTime() + entity.getExpire());
            entity.setUpdateDate(new Date());
            iDbCacheRepository.updateById(entity);
        }
    }

    /**
     * @param key
     * @return
     */
    private DbCacheEntity getEntity(String key) {
        return null;
    }


    public boolean setKey() {
        return false;
    }

    public void delete(String key) {

    }

    public void setNx() {

    }

    /**
     * 获得key
     *
     * @param key key
     * @return 前缀 + key
     */
    private String getKey(String key) {
        return keyPrefix() + key;
    }

    /**
     * 获取前缀
     *
     * @return 前缀
     */
    private String keyPrefix() {
        String profileActive = env.getProperty("spring.profiles.active");
        String applicationName = env.getProperty("spring.application.name");
        return applicationName + ":" + profileActive + ":";
    }

    public void set(String key, String writeValueAsString, long expire) {

    }

    public String gets(String keyPrefix) {
        return null;
    }
}

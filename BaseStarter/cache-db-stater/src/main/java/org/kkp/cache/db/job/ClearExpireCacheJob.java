package org.kkp.cache.db.job;

import lombok.extern.slf4j.Slf4j;
import org.kkp.core.job.IJobHandle;
import org.springframework.stereotype.Component;

/**
 * @author Klaus
 * @since 2021/12/28
 **/
@Component
@Slf4j
public class ClearExpireCacheJob implements IJobHandle {


    /**
     * 执行方法
     */
    @Override
    public void execution() {

    }
}

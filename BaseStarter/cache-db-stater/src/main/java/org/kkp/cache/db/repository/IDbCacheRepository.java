package org.kkp.cache.db.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.kkp.cache.db.entity.DbCacheEntity;

/**
 * @author Klaus
 * @since 2021/12/28
 **/
@Mapper
public interface IDbCacheRepository extends BaseMapper<DbCacheEntity> {
}

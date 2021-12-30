package org.kkp.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.kkp.entity.DbFileInfoEntity;

@Mapper
public interface IDbFileInfoRepository extends BaseMapper<DbFileInfoEntity> {

}

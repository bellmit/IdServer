package org.kkp.cache.db.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Klaus
 * @since 2021/12/28
 **/
@Data
@Accessors(chain = true)
@ToString(callSuper = false)
@EqualsAndHashCode
public class DbCacheDto {
    /**
     * ID
     */
    private static final long serialVersionUID = -1369076998407328491L;
    /**
     * 值
     */
    private String value;
    /**
     * 过期时间(毫秒)
     */
    private Long expireTime;
}

package org.kkp.core.registration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
public class ServiceDto implements Serializable {
    private static final long serialVersionUID = 7861008601320896731L;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * port
     */
    private String port;

    /**
     * ip
     */
    private String ip;

    /**
     * managementPort
     */
    private String managementPort;

}

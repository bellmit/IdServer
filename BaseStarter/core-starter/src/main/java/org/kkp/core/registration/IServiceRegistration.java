package org.kkp.core.registration;

import java.util.List;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
public interface IServiceRegistration {

    /**
     * 本地服务
     *
     * @return ServiceDto
     */
    ServiceDto local();

    /**
     * 是否本地
     *
     * @param serviceDto 服务对象
     * @return boolean true false
     */
    boolean isLocal(ServiceDto serviceDto);

    /**
     * 服务清单
     *
     * @return List<ServiceDto>
     */
    List<ServiceDto> list();

    /**
     * 服务清单
     *
     * @param serviceName 服务名称
     * @return List<ServiceDto> 服务清单
     */
    List<ServiceDto> list(String serviceName);
}

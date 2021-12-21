package org.kkp.core.trace;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
public class TraceDto implements Serializable {

    private static final long serialVersionUID = -7389225710618594581L;
    private String profilesActive;
    private String traceId;
    private String url;
    private String method;
    private String queryString;
    private Map<String, String[]> parameterMap;
    private String requestBody;
    private String responseBody;
    private String requestContextBody;
    private String responseContextBody;
    private Integer httpStatus;
    private Long startTime;
    private Long endTime;
    private Long timeCost;


}

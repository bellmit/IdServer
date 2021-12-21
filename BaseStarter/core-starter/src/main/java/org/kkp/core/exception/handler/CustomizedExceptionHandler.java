package org.kkp.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.kkp.core.CoreProperties;
import org.kkp.core.exception.SystemException;
import org.kkp.core.util.CoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
@RestControllerAdvice
@Slf4j
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 配置
     */
    @Autowired
    private CoreProperties coreProperties;


    @Autowired(required = false)
    private IExceptionAlert iExceptionAlert;

    @ExceptionHandler(value = {Exception.class})
    private ResponseEntity<Object> handle(WebRequest request, SystemException exception) {
        HttpStatus httpStatus = null;
        HttpHeaders httpHeaders = null;
        Object body = null;
        try {
            ResponseEntity<Object> entity = handleException(exception, request);
            httpStatus = entity.getStatusCode();
            httpHeaders = entity.getHeaders();
            body = entity.getBody();
        } catch (Exception ex) {
            log.debug("not system error:{}", ex.getMessage());
        }
        return this.handleExceptionInternal(exception, body, httpHeaders, httpStatus, request);
    }

    /**
     * A single place to customize the response body of all exception types.
     * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
     * request attribute and creates a {@link ResponseEntity} from the given
     * body, headers, and status.
     *
     * @param ex      the exception
     * @param body    the body for the response
     * @param headers the headers for the response
     * @param status  the response status
     * @param request the current request
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CoreUtil.fixCors(httpServletResponse, httpServletRequest, coreProperties);
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}

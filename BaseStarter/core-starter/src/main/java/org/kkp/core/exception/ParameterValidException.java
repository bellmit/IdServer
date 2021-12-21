package org.kkp.core.exception;

import org.kkp.core.CoreConstant;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
public class ParameterValidException extends RuntimeException {
    private static final long serialVersionUID = -2846108356382840452L;
    private List<ObjectError> allErrors;
    /**
     * ParameterValidException
     *
     * @param message message
     */
    public ParameterValidException(String message) {
        super(message);
        this.allErrors = null;
    }

    /**
     * ParameterValidException
     *
     * @param allErrors allErrors
     */
    public ParameterValidException(List<ObjectError> allErrors) {
        super(CoreConstant.PARAMETER_VALID_EXCEPTION_MSG);
        this.allErrors = allErrors;
    }

    /**
     * ParameterValidException
     *
     * @param message   message
     * @param allErrors allErrors
     */
    public ParameterValidException(String message, List<ObjectError> allErrors) {
        super(message);
        this.allErrors = allErrors;
    }

    /**
     * ParameterValidException
     *
     * @param cause     cause
     * @param allErrors allErrors
     */
    public ParameterValidException(Throwable cause, List<ObjectError> allErrors) {
        super(cause);
        this.allErrors = allErrors;
    }

    /**
     * ParameterValidException
     *
     * @param message   message
     * @param cause     cause
     * @param allErrors allErrors
     */
    public ParameterValidException(String message, Throwable cause, List<ObjectError> allErrors) {
        super(message, cause);
        this.allErrors = allErrors;
    }


    /**
     * 获得所有错误
     *
     * @return
     */
    public List<ObjectError> getAllErrors() {
        return allErrors;
    }

}

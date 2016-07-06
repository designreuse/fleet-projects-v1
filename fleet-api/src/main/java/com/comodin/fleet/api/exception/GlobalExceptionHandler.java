package com.comodin.fleet.api.exception;


import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.json.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public ResultEntity handleSystemException(Exception ex) {
        return new ResultEntity(ConstantsFinalValue.RESULTS_CODE_ERROR_SYSTEM, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ParameterException.class})
    public ResultEntity handleParameterException(Exception ex) {
        return new ResultEntity(ConstantsFinalValue.RESULTS_CODE_ERROR_PARAMETER, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({BusinessLogicException.class})
    public ResultEntity handleBusinessException(Exception ex) {
        return new ResultEntity(ConstantsFinalValue.RESULTS_CODE_ERROR_BUSINESS, ex.getMessage());
    }

}

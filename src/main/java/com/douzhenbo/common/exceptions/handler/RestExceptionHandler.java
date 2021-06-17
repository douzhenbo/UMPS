package com.douzhenbo.common.exceptions.handler;

import com.douzhenbo.common.exceptions.BusinessException;
import com.douzhenbo.common.exceptions.code.BaseResponseCode;
import com.douzhenbo.common.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 9:47
 **/
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public DataResult handleException(Exception e){
        log.error("handleException....{}",e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public DataResult handlerBusinessException(BusinessException e){
        log.error("BusinessException ...{}",e);
        return DataResult.getResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        log.error("handlerMethodArgumentNotValidException  AllErrors:{} MethodArgumentNotValidException:{}",e.getBindingResult().getAllErrors(),e);
        String msg=null;
        for(ObjectError error:allErrors){
            msg=error.getDefaultMessage();
            break;
        }
        return DataResult.getResult(BaseResponseCode.VALIDATOR_ERROR.getCode(),msg);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public DataResult unauthorizedException(UnauthorizedException e){
        log.error("UnauthorizedException:{}",e);
        return DataResult.getResult(BaseResponseCode.NOT_PERMISSION);
    }


}

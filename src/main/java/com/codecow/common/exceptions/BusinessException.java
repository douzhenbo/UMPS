package com.codecow.common.exceptions;

import com.codecow.common.exceptions.code.ResponseCodeInterface;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 9:23
 **/
public class BusinessException extends RuntimeException{
    /**
     * 提示编码
     */
    private final  int code;

    /**
     * 后端提示语
     */
    private final String msg;

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ResponseCodeInterface responseCodeInterface) {
        this(responseCodeInterface.getCode(),responseCodeInterface.getMsg());
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}


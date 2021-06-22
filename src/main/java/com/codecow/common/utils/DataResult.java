package com.codecow.common.utils;

import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.exceptions.code.ResponseCodeInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 9:32
 **/

@Data
public class DataResult<T>{
    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码")
    private int code;

    /**
     * 响应提示语
     */
    @ApiModelProperty(value = "响应提示语")
    private String msg;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public DataResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
        this.msg=null;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResult() {
        this.code= BaseResponseCode.SUCCESS.getCode();
        this.msg=BaseResponseCode.SUCCESS.getMsg();
        this.data=null;
    }

    public DataResult(T data) {
        this.data = data;
        this.code=BaseResponseCode.SUCCESS.getCode();
        this.msg=BaseResponseCode.SUCCESS.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }
    /**
     * 操作成功 data为null
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param
     * @return       com.xh.lesson.utils.DataResult<T>
     * @throws
     */
    public static DataResult success(){
        return new DataResult();
    }
    /**
     * 操作成功 data 不为null
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param data
     * @return       com.xh.lesson.utils.DataResult<T>
     * @throws
     */
    public static <T>DataResult success(T data){
        return new DataResult(data);
    }
    /**
     * 自定义 返回操作 data 可控
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param code
     * @param msg
     * @param data
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static <T>DataResult getResult(int code,String msg,T data){
        return new DataResult(code,msg,data);
    }
    /**
     *  自定义返回  data为null
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param code
     * @param msg
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static DataResult getResult(int code,String msg){
        return new DataResult(code,msg);
    }
    /**
     * 自定义返回 入参一般是异常code枚举 data为空
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param responseCode
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static DataResult getResult(BaseResponseCode responseCode){
        return new DataResult(responseCode);
    }
    /**
     * 自定义返回 入参一般是异常code枚举 data 可控
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param responseCode
     * @param data
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static <T>DataResult getResult(BaseResponseCode responseCode, T data){

        return new DataResult(responseCode,data);
    }

}

package com.dream.utils;

import lombok.Data;

import java.io.Serializable;


@Data
public class ServiceResult<T> implements Serializable {
    
    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 信息描述
     */
    private String message;
    /**
     * 返回参数
     */
    private T result;
    
    public ServiceResult() {
    }
    
    public ServiceResult(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
    
    /**
     * 业务成功返回业务代码和描述信息
     */
    public static <T> ServiceResult<T> success() {
        ServiceResult<T> result = new ServiceResult<T>();
        result.setCode(ResultStatus.SUCCESS.getCode());
        result.setMessage(ResultStatus.SUCCESS.getMessage());
        return result;
    }
    
    /**
     * 业务成功返回业务代码,描述和返回的参数
     */
    public static <T> ServiceResult<T> success(T data) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(ResultStatus.SUCCESS.getCode());
        result.setMessage(ResultStatus.SUCCESS.getMessage());
        result.setResult(data);
        return result;
    }
    
    /**
     * 业务成功返回业务代码,描述和返回的参数
     */
    public static <T> ServiceResult<T> success(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return success(data);
        }
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(resultStatus.getCode());
        result.setMessage(resultStatus.getMessage());
        result.setResult(data);
        return result;
    }
    
    /**
     * 业务异常返回业务代码,描述和返回的参数
     */
    public static <T> ServiceResult<T> failure(ResultStatus resultStatus, T data) {
        ServiceResult<T> result = new ServiceResult<T>();
        if (resultStatus == null) {
            result.setCode(ResultStatus.SYS_ERROR.getCode());
            result.setMessage(ResultStatus.SYS_ERROR.getMessage());
            return result;
            
        }
        
        result.setCode(resultStatus.getCode());
        result.setMessage(resultStatus.getMessage());
        result.setResult(data);
        return result;
    }
}

package com.dream.utils;

import cn.hutool.http.HttpStatus;
import lombok.Getter;

/**
 * 业务状态码
 *
 * @author Zhutao
 * @since 2021-3-30
 */
@Getter
public enum ResultStatus {

    SUCCESS(HttpStatus.HTTP_OK,  "OK"),
    BAD_REQUEST(HttpStatus.HTTP_BAD_REQUEST, "Bad Request"),
    SYS_ERROR(HttpStatus.HTTP_INTERNAL_ERROR,  "系统繁忙"),
    REQUEST_PARAMETER_ERROR(HttpStatus.HTTP_BAD_REQUEST,  "请求参数异常"),
    UNAUTHORIZED(HttpStatus.HTTP_UNAUTHORIZED,  "认证失败"),
    FORBIDDEN(HttpStatus.HTTP_FORBIDDEN, "未授权"),
    ;

    /**
     * 业务异常码
     */
    private final Integer code;
    /**
     * 业务异常信息描述
     */
    private final String message;

    ResultStatus(Integer httpStatus, String message) {
        this.code = httpStatus;
        this.message = message;
    }
}

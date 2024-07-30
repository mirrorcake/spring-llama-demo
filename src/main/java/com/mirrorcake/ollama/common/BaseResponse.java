package com.mirrorcake.ollama.common;

/**
 * @author mirrorcake
 */
public class BaseResponse<T> {

    private final String provider = "ollama";

    private T data;

    private Integer code;

    private String message;

    public BaseResponse(T data) {
        this.code = BaseCodeEnum.SUCCESS.getCode();
        this.data = data;
    }

    public BaseResponse(BaseCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getProvider() {
        return provider;
    }

    public T getData() {
        return data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

package com.mirrorcake.ollama.common;

/**
 * @author mirrorcake
 * @date 2024/7/30
 */
public enum BaseCodeEnum {
    SUCCESS(0, "success"),
    FAILED(1, "failed");

    private Integer code;
    private String message;

    private BaseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

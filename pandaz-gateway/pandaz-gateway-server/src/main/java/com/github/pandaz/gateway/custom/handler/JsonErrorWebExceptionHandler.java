package com.github.pandaz.gateway.custom.handler;

import com.github.pandaz.commons.code.RCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义全局返回异常
 *
 * @author Carzer
 * @since 2020-10-16
 */
@Slf4j
@SuppressWarnings("unchecked")
public class JsonErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    /**
     * 错误信息
     */
    private final ErrorAttributes errorAttributes;

    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resourceProperties the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     */
    public JsonErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
        this.errorAttributes = errorAttributes;
    }

    /**
     * Extract the error attributes from the current request, to be used to populate error
     * views or JSON payloads.
     *
     * @param request the source request
     * @param options options to control error attributes
     * @return the error attributes as a Map
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = this.errorAttributes.getErrorAttributes(request, options);
        Map<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("code", RCode.FAILED.getCode());
        resultMap.put("message", "服务器开小差了，请稍后尝试。");
        resultMap.put("data", errorMap);
        return resultMap;
    }

    /**
     * Get the HTTP error status information from the error map.
     *
     * @param errorAttributes the current error information
     * @return the error HTTP status
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        int status = 500;
        try {
            Map<String, Object> errorMap = (Map<String, Object>) errorAttributes.get("data");
            if (errorMap != null) {
                status = (Integer) errorMap.get("status");
            }
        } catch (Exception e) {
            log.error("获取错误信息异常：", e);
        }
        return status;
    }
}

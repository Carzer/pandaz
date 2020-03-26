package com.pandaz.usercenter.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.pandaz.commons.util.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 动态修改日志级别
 *
 * @author Carzer
 * @since 2019-07-17
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class LogController {

    /**
     * 更改日志级别
     *
     * @param map 以键值对的情况来表示需要修改的logger的日志级别，例：
     *            {
     *            "com.pandaz":"info",
     *            "org.springframework":"info"
     *            }
     * @return java.lang.String
     */
    @PutMapping(value = "/logLevel")
    public ExecuteResult<String> changeLogLevel(@RequestBody Map<String, String> map) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            map.forEach((loggerName, logLevel) -> loggerContext.getLogger(loggerName).setLevel(Level.valueOf(logLevel)));
            result.setData("修改成功！");
        } catch (Exception e) {
            log.error("动态修改日志级别出错", e);
            result.setError(String.format("修改失败：%s", e.getMessage()));
        }
        return result;
    }

}
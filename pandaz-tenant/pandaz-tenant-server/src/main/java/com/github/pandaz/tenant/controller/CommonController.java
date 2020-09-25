package com.github.pandaz.tenant.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 通用方法，不校验权限
 *
 * @author Carzer
 * @since 2020-04-27
 */
@RestController
@Slf4j
@RequestMapping("/common")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Common", tags = "通用方法")
@SuppressWarnings("unused")
public class CommonController {
    /**
     * 唤醒方法
     */
    @ApiIgnore
    @GetMapping("wakeUp")
    public void wake() {
        log.debug("wake up a person who pretends to be asleep");
    }
}

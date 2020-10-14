package com.github.pandaz.auth.util;

import com.github.pandaz.auth.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 简单的定时任务
 *
 * @author Carzer
 * @since 2020-04-07
 */
@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("unused")
public class SimpleTask {

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 定时清理脏数据
     */
    public void clear() {
        // 清理菜单脏数据
        clearMenus();
    }

    /**
     * 清理菜单脏数据
     */
    private void clearMenus() {
        try {
            List<String> list = menuService.listMenusWithoutParent();
            menuService.logicDeleteByCodes("schedule", LocalDateTime.now(), list);
            log.info("清理菜单脏数据完成");
        } catch (Exception e) {
            log.error("清理菜单脏数据异常：", e);
        }
    }
}

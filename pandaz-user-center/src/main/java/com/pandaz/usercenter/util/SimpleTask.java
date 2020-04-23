package com.pandaz.usercenter.util;

import com.pandaz.usercenter.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
public class SimpleTask {

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 定时清理脏数据
     * <p>
     * 暂定6小时
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 6)
    public void clear() {
        // 清理菜单脏数据
        clearMenus();
    }

    /**
     * 拉取权限信息
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void loadResourceDefineMap() {
        AuthUtil.loadResourceDefineMap();
    }

    /**
     * 清理菜单脏数据
     */
    private void clearMenus() {
        try {
            List<String> list = menuService.listMenusWithoutParent();
            menuService.deleteByCodes("schedule", LocalDateTime.now(), list);
            log.info("清理菜单脏数据完成");
        } catch (Exception e) {
            log.error("清理菜单脏数据异常：", e);
        }
    }

}

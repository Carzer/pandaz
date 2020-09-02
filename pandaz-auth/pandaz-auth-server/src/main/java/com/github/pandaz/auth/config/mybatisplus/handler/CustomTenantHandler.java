package com.github.pandaz.auth.config.mybatisplus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.github.pandaz.commons.constants.CommonConstants;
import net.sf.jsqlparser.expression.Expression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户handler
 *
 * @author Carzer
 * @since 2020-09-02
 */
@Component
public class CustomTenantHandler implements TenantLineHandler {

    /**
     * 需要过滤的表
     */
    private static final List<String> IGNORE_TENANT_TABLES = new ArrayList<>();

    /**
     * 获取租户ID字段
     *
     * @return 租户ID字段
     */
    @Override
    public String getTenantIdColumn() {
        return CommonConstants.SYSTEM_TENANT_ID_COLUMN;
    }

    /**
     * 判断表名是否被忽略
     *
     * @param tableName 表名
     * @return 结果
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return IGNORE_TENANT_TABLES.parallelStream().anyMatch(e -> e.equalsIgnoreCase(tableName));
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public Expression getTenantId() {
        return null;
    }
}

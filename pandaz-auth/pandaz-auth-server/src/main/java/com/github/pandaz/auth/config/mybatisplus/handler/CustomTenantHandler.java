package com.github.pandaz.auth.config.mybatisplus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.github.pandaz.auth.custom.CustomProperties;
import com.github.pandaz.auth.util.TenantUtil;
import com.github.pandaz.commons.constants.CommonConstants;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 租户handler
 *
 * @author Carzer
 * @since 2020-09-02
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomTenantHandler implements TenantLineHandler {

    /**
     * 自定义属性
     */
    private final CustomProperties customProperties;

    /**
     * 租户工具类
     */
    private final TenantUtil tenantUtil;

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
     * 忽略语句
     *
     * @param tableName 表名
     * @return 结果
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return customProperties.getIgnoreTenantTables().stream().anyMatch(e -> e.equalsIgnoreCase(tableName));
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public Expression getTenantId() {
        Long tenantId = tenantUtil.getTenantId();
        return tenantId == null ? null : new LongValue(tenantId);
    }
}

package com.github.pandaz.auth.config.mybatisplus.interceptor;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.github.pandaz.commons.annotations.tenant.SqlParserIgnoreEnum;
import com.github.pandaz.commons.aspects.tenant.SqlParserIgnoreHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.schema.Table;

/**
 * 自定义租户控制
 *
 * @author Carzer
 * @since 2020-09-02
 */
public class TenantInterceptor extends TenantLineInnerInterceptor {

    /**
     * 租户handler
     */
    private final TenantLineHandler tenantLineHandler;

    /**
     * 构造方法
     *
     * @param tenantLineHandler 租户handler
     */
    public TenantInterceptor(final TenantLineHandler tenantLineHandler) {
        super(tenantLineHandler);
        this.tenantLineHandler = tenantLineHandler;
    }

    /**
     * 构建表达式
     * 如果没有获取到租户ID，则不进行构建
     *
     * @param currentExpression 表达式
     * @param table             表
     * @return 表达式
     */
    @Override
    protected Expression builderExpression(Expression currentExpression, Table table) {
        if (tenantLineHandler.getTenantId() == null || ((LongValue) tenantLineHandler.getTenantId()).getValue() <= 0L || ignoreOnce()) {
            return currentExpression;
        }
        return super.builderExpression(currentExpression, table);
    }

    /**
     * 忽略这次构建
     *
     * @return 是否忽略
     */
    private boolean ignoreOnce() {
        Integer ignoreScope = SqlParserIgnoreHolder.getIgnoreScope();
        if (ignoreScope != null && ignoreScope > 0) {
            return (ignoreScope & SqlParserIgnoreEnum.TENANT.getValue()) == SqlParserIgnoreEnum.TENANT.getValue();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

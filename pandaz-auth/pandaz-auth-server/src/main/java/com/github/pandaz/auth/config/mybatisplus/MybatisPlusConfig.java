package com.github.pandaz.auth.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import com.github.pandaz.auth.config.mybatisplus.handler.CustomTenantHandler;
import com.github.pandaz.auth.config.mybatisplus.interceptor.TenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus相关配置
 *
 * @author Carzer
 * @since 2019-11-05
 */
@SuppressWarnings("all")
@EnableTransactionManagement
@Configuration
@MapperScan("com.github.pandaz.auth.mapper")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MybatisPlusConfig {

    /**
     * 租户handler
     */
    private final CustomTenantHandler customTenantHandler;

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
//        interceptor.addInnerInterceptor(illegalSQLInnerInterceptor());
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 租户插件
     *
     * @return 租户插件
     */
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantInterceptor(customTenantHandler);

    }

    /**
     * 分页插件
     *
     * @return 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(false);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件
     *
     * @return 乐观锁插件
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 性能规范插件
     *
     * @return 性能规范插件
     */
    @Bean
    public IllegalSQLInnerInterceptor illegalSQLInnerInterceptor() {
        return new IllegalSQLInnerInterceptor();
    }

    /**
     * 防止全表更新与删除插件
     *
     * @return 防止全表更新与删除插件
     */
    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }
}
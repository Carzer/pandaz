package com.github.pandaz.tenant.api.fallback;

import com.github.pandaz.auth.dto.MenuDTO;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.tenant.api.AuthApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 权限相关接口 fallback
 *
 * @author Carzer
 * @since 2020-06-03
 */
@Slf4j
@Component
public class AuthApiFallbackFactory implements FallbackFactory<AuthApi> {

    /**
     * 默认方法
     *
     * @param cause 异常
     * @return 执行结果
     */
    @Override
    public AuthApi create(Throwable cause) {
        AuthApiFallbackFactory.log.error("fallback; reason was: {}", cause.getMessage());
        return new AuthApi() {
            @Override
            public R<MenuDTO> getAll(MenuDTO menuDTO) {
                return R.fail();
            }

            @Override
            public R<List<String>> getPermissions(String osCode, String roleCode) {
                return new R<>(RCode.FAILED);
            }

            @Override
            public R<Map<String, List<String>>> getAllPermissions(String osCode) {
                return new R<>(RCode.FAILED);
            }
        };
    }
}

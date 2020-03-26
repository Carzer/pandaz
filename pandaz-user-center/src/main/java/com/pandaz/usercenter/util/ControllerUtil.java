package com.pandaz.usercenter.util;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.service.UcBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ControllerUtil
 *
 * @author Carzer
 * @since 2020-03-26
 */
@SuppressWarnings("rawtypes")
@Component
@Slf4j
public class ControllerUtil<S extends UcBaseService> {

    /**
     * 执行删除方法并获取结果
     *
     * @param service     调用服务
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       删除编码
     * @return 执行结果
     */
    public ExecuteResult<String> getDeleteResult(
            S service, String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            service.deleteByCodes(deletedBy, deletedDate, codes);
            result.setData("删除成功");
        } catch (Exception e) {
            log.error("删除方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

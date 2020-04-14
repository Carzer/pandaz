package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.OsInfoDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.OsInfoEntity;
import com.pandaz.usercenter.service.OsInfoService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 系统信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/osInfo")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OsInfoController {

    /**
     * 系统信息服务
     */
    private final OsInfoService osInfoService;

    /**
     * 工具类
     */
    private final ControllerUtil<OsInfoService> controllerUtil;

    /**
     * 查询方法
     *
     * @param osInfoDTO 查询条件
     * @return 系统信息
     */
    @GetMapping(UrlConstants.GET)
    public ExecuteResult<OsInfoDTO> get(@Valid OsInfoDTO osInfoDTO) {
        ExecuteResult<OsInfoDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(osInfoService.findByCode(osInfoDTO.getCode()), OsInfoDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param osInfoDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public ExecuteResult<HashMap<String, Object>> getPage(OsInfoDTO osInfoDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<OsInfoEntity> page = osInfoService.getPage(BeanCopyUtil.copy(osInfoDTO, OsInfoEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, OsInfoDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @GetMapping("/listAll")
    public ExecuteResult<ArrayList<OsInfoDTO>> listAll() {
        ExecuteResult<ArrayList<OsInfoDTO>> result = new ExecuteResult<>();
        try {
            result.setData((ArrayList<OsInfoDTO>) BeanCopyUtil.copyList(osInfoService.list(), OsInfoDTO.class));
        } catch (Exception e) {
            log.error("获取全部系统信息异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param osInfoDTO 系统信息
     * @return 系统信息
     */
    @PostMapping(UrlConstants.INSERT)
    public ExecuteResult<OsInfoDTO> insert(@RequestBody OsInfoDTO osInfoDTO, Principal principal) {
        ExecuteResult<OsInfoDTO> result = new ExecuteResult<>();
        try {
            check(osInfoDTO);
            OsInfoEntity osInfoEntity = BeanCopyUtil.copy(osInfoDTO, OsInfoEntity.class);
            osInfoEntity.setId(UuidUtil.getId());
            osInfoEntity.setCreatedBy(principal.getName());
            osInfoEntity.setCreatedDate(LocalDateTime.now());
            osInfoService.insert(osInfoEntity);
            result.setData(BeanCopyUtil.copy(osInfoEntity, osInfoDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param osInfoDTO 系统信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public ExecuteResult<String> update(@Valid @RequestBody OsInfoDTO osInfoDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(osInfoDTO);
            OsInfoEntity osInfoEntity = BeanCopyUtil.copy(osInfoDTO, OsInfoEntity.class);
            osInfoEntity.setUpdatedBy(principal.getName());
            osInfoEntity.setUpdatedDate(LocalDateTime.now());
            osInfoService.updateByCode(osInfoEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 系统信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(osInfoService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 检查方法
     *
     * @param osInfoDTO 系统信息
     */
    private void check(OsInfoDTO osInfoDTO) {
        Assert.hasText(osInfoDTO.getName(), "系统名称不能为空");
    }

}

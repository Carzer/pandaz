package com.github.pandaz.auth.controller;


import com.github.pandaz.auth.entity.DictInfoEntity;
import com.github.pandaz.auth.service.DictInfoService;
import com.github.pandaz.auth.util.ControllerUtil;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.dto.auth.DictInfoDTO;
import com.github.pandaz.commons.dto.auth.DictTypeDTO;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典信息
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/dict/info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DictInfoController extends BaseController<DictInfoDTO, DictInfoEntity> {

    /**
     * 字典信息服务
     */
    private final DictInfoService dictInfoService;

    /**
     * 工具类
     */
    private final ControllerUtil controllerUtil;

    /**
     * 获取服务方法
     *
     * @return 获取服务
     */
    @Override
    protected BaseService<DictInfoEntity> getBaseService() {
        return this.dictInfoService;
    }

    /**
     * 获取全部字典类型
     *
     * @return 字典类型
     */
    @GetMapping("/listAllTypes")
    public R<List<DictTypeDTO>> listAllTypes() {
        return new R<>(controllerUtil.listAllTypes());
    }

    /**
     * 检查方法
     *
     * @param dictInfoDTO 字典信息
     */
    @Override
    protected void check(DictInfoDTO dictInfoDTO) {
        Assert.hasText(dictInfoDTO.getName(), "字典信息名称不能为空");
        Assert.hasText(dictInfoDTO.getTypeCode(), "请关联字典类型");
    }
}

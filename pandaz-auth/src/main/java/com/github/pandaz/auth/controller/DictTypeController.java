package com.github.pandaz.auth.controller;


import com.github.pandaz.auth.entity.DictTypeEntity;
import com.github.pandaz.auth.service.DictTypeService;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.dto.auth.DictTypeDTO;
import com.github.pandaz.commons.service.BaseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典类型
 *
 * @author Carzer
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/dict/type")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "DictType", tags = "字典类型")
public class DictTypeController extends BaseController<DictTypeDTO, DictTypeEntity> {

    /**
     * 字典类型服务
     */
    private final DictTypeService dictTypeService;

    /**
     * 获取服务方法
     *
     * @return 获取服务
     */
    @Override
    protected BaseService<DictTypeEntity> getBaseService() {
        return this.dictTypeService;
    }

    /**
     * 检查相关信息
     *
     * @param dictTypeDTO 字典类型
     */
    @Override
    protected void check(DictTypeDTO dictTypeDTO) {
        Assert.hasText(dictTypeDTO.getName(), "字典类型名称不能为空");
    }
}

package com.pandaz.usercenter.controller;


import com.pandaz.commons.controller.BaseController;
import com.pandaz.commons.dto.usercenter.DictTypeDTO;
import com.pandaz.commons.service.BaseService;
import com.pandaz.usercenter.entity.DictTypeEntity;
import com.pandaz.usercenter.service.DictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典类型
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/dict/type")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

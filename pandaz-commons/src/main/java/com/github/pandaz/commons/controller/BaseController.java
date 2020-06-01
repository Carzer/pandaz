package com.github.pandaz.commons.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.commons.constants.UrlConstants;
import com.github.pandaz.commons.dto.BaseDTO;
import com.github.pandaz.commons.entity.BaseEntity;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * controller父类
 *
 * @author Carzer
 * @since 2020-04-30
 */
@SuppressWarnings("unchecked")
public abstract class BaseController<T extends BaseDTO, E extends BaseEntity> {

    /**
     * 类型
     */
    private Class<E> entityClass;

    /**
     * 获取服务方法
     *
     * @return 服务
     */
    protected abstract BaseService<E> getBaseService();

    /**
     * 检查方法
     *
     * @param dto dto
     */
    protected void check(T dto) {
    }

    /**
     * 查询方法
     *
     * @param dto 查询信息
     * @return 查询结果
     */
    @ApiOperation(value = "查询方法", notes = "查询方法")
    @ApiOperationSupport(ignoreParameters = {"id", "createdBy", "createdDate", "updatedBy", "updatedDate", "version"})
    @GetMapping(UrlConstants.GET)
    public R<T> get(@Valid T dto, @ApiIgnore Principal principal) {
        E entity = BeanCopyUtil.copy(dto, getEntityClass());
        T result = BeanCopyUtil.copy(getBaseService().findByCode(entity), dto);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param dto 查询信息
     * @return 分页信息
     */
    @ApiOperation(value = "分页方法", notes = "分页方法")
    @ApiOperationSupport(ignoreParameters = {"id", "createdBy", "createdDate", "updatedBy", "updatedDate", "version"})
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(T dto, @ApiIgnore Principal principal) {
        IPage<E> page = getBaseService().getPage(BeanCopyUtil.copy(dto, getEntityClass()));
        return new R<>(BeanCopyUtil.convertToMap(page, dto.getClass()));
    }

    /**
     * 插入方法
     *
     * @param dto 插入信息
     * @return 执行结果
     */
    @ApiOperation(value = "插入方法", notes = "插入方法")
    @ApiOperationSupport(ignoreParameters = {"id", "createdBy", "createdDate", "updatedBy", "updatedDate", "version"})
    @PostMapping(UrlConstants.INSERT)
    public R<String> insert(@Valid @RequestBody T dto, @ApiIgnore Principal principal) {
        check(dto);
        E entity = BeanCopyUtil.copy(dto, getEntityClass());
        entity.setCreatedBy(principal.getName());
        entity.setCreatedDate(LocalDateTime.now());
        getBaseService().insert(entity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param dto 更新信息
     * @return 执行结果
     */
    @ApiOperation(value = "更新方法", notes = "更新方法")
    @ApiOperationSupport(ignoreParameters = {"id", "createdBy", "createdDate", "updatedBy", "updatedDate", "version"})
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody T dto, @ApiIgnore Principal principal) {
        check(dto);
        E entity = BeanCopyUtil.copy(dto, getEntityClass());
        entity.setUpdatedBy(principal.getName());
        entity.setUpdatedDate(LocalDateTime.now());
        getBaseService().updateByCode(entity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 编码信息
     * @return 执行结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codes", value = "编码信息", dataType = "string", paramType = "body", allowMultiple = true)
    })
    @ApiOperation(value = "删除方法", notes = "删除方法")
    @ApiOperationSupport(ignoreParameters = {"id", "createdBy", "createdDate", "updatedBy", "updatedDate", "version"})
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, @ApiIgnore Principal principal) {
        getBaseService().deleteByCodes(principal.getName(), LocalDateTime.now(), codes);
        return R.success();
    }

    /**
     * 上传方法
     *
     * @param file file
     * @return 执行结果
     */
    @ApiOperation(value = "上传方法", notes = "上传方法", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = UrlConstants.IMPORT, headers = "content-type=multipart/form-data")
    public R<String> upload(@ApiParam(value = "file", required = true) MultipartFile file, @ApiIgnore Principal principal) {
        return R.success();
    }

    /**
     * 导出方法
     *
     * @param dto 查询条件
     */
    @ApiOperation(value = "导出方法", notes = "导出方法")
    @ApiOperationSupport(ignoreParameters = {"id", "createdBy", "createdDate", "updatedBy", "updatedDate", "version"})
    @GetMapping(UrlConstants.EXPORT)
    public void export(T dto, @ApiIgnore Principal principal) {

    }

    /**
     * 获取范型实际类型
     *
     * @return 类型
     */
    private Class<E> getEntityClass() {
        if (this.entityClass == null) {
            this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return this.entityClass;
    }
}

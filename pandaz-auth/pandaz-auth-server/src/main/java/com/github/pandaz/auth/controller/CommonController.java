package com.github.pandaz.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pandaz.auth.custom.CustomProperties;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.MenuEntity;
import com.github.pandaz.auth.entity.OsInfoEntity;
import com.github.pandaz.auth.service.*;
import com.github.pandaz.auth.util.AuthUtil;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.dto.auth.AuthMenuDTO;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.commons.util.ServerConfig;
import com.github.pandaz.file.api.FtpApi;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用方法，不校验权限
 *
 * @author Carzer
 * @since 2020-04-27
 */
@RestController
@Slf4j
@RequestMapping("/common")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Common", tags = "通用方法")
@SuppressWarnings("unused")
public class CommonController {

    /**
     * 配置
     */
    private final ServerConfig serverConfig;

    /**
     * 验证码服务
     */
    private final CaptchaService captchaService;

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 角色-权限服务
     */
    private final RolePermissionService rolePermissionService;

    /**
     * 角色服务
     */
    private final RoleService roleService;

    /**
     * 系统服务
     */
    private final OsInfoService osInfoService;

    /**
     * 通用配置
     */
    private final CustomProperties customProperties;

    /**
     * 上传客户端
     */
    private final FtpApi ftpApi;

    /**
     * 生成验证码
     *
     * @param key      前端上传key
     * @param response response
     * @throws IOException IOException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "随机码[UI生成为24位]", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "生成验证码", notes = "生成验证码")
    @GetMapping(value = "/captcha", produces = "image/png")
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        captchaService.create(response, key);
    }

    /**
     * 获取所有授权菜单
     *
     * @return 所有菜单
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "osCode", value = "系统编码", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "获取所有授权菜单", notes = "获取所有授权菜单")
    @GetMapping("/getAuthMenu")
    public R<List<AuthMenuDTO>> getAuthMenu(@RequestParam String osCode, @ApiIgnore Principal principal) {
        R<List<String>> roleList = AuthUtil.getRoleListFromContext();
        if (RCode.SUCCESS.getCode() != roleList.getCode()) {
            return new R<>(RCode.getEnum(roleList.getCode()));
        }
        List<String> roles = roleList.getData();
        List<MenuEntity> menuList;
        // 如果开启超级管理员，并且当前用户拥有超级管理员角色，则返回所有菜单并将权限全部放开
        if (customProperties.getSuperAdmin().isEnable() && roles.contains(customProperties.getSuperAdmin().getName())) {
            menuList = menuService.list();
            menuList.forEach(menuEntity -> menuEntity.setBitResult(SysConstants.TOTAL_DIGIT_RESULT));
        } else {
            menuList = menuService.getAuthorizedMenu(osCode, roles);
        }
        // 无论何种权限，都默认一个404页面
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCode("404");
        menuEntity.setSorting(0);
        menuEntity.setBitResult(1);
        menuList.add(menuEntity);
        return new R<>(BeanCopyUtil.copyList(menuList, AuthMenuDTO.class));
    }

    /**
     * 唤醒方法
     */
    @ApiIgnore
    @GetMapping("wakeUp")
    public void wake() {
        log.debug("wake up a person who pretends to be asleep");
    }

    /**
     * 上传方法
     *
     * @param pathname 存储路径
     * @param filename 存储文件名
     * @param file     文件
     * @return 执行结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pathname", value = "存储路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "filename", value = "存储文件名", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "上传方法", notes = "上传方法", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public R<String> upload(@RequestParam String pathname,
                            @RequestParam String filename,
                            @ApiParam(value = "file", required = true) MultipartFile file) {
        return ftpApi.handleFileUpload(pathname, filename, file);
    }

    /**
     * 下载方法
     *
     * @param pathname   存储路径
     * @param filename   文件名
     * @param originPath 下载路径
     * @return 执行结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pathname", value = "存储路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "filename", value = "文件名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "originPath", value = "本地存储路径", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "下载方法", notes = "下载方法")
    @GetMapping("/download")
    public R<String> download(@RequestParam String pathname,
                              @RequestParam String filename,
                              @RequestParam String originPath
    ) {
        return ftpApi.handleFileDownload(pathname, filename, originPath);
    }

    /**
     * 删除方法
     *
     * @param pathname 存储路径
     * @param filename 文件名
     * @return 执行结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pathname", value = "存储路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "filename", value = "文件名", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "删除方法", notes = "删除方法")
    @DeleteMapping("/removeFile")
    public R<String> removeFile(@RequestParam String pathname,
                                @RequestParam String filename
    ) {
        return ftpApi.handleFileDelete(pathname, filename);
    }

    /**
     * 根据系统编码及角色编码获取权限列表
     *
     * @param osCode    系统编码
     * @param roleCode  角色编码
     * @param principal 当前用户信息
     * @return 权限列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "osCode", value = "系统编码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "获取权限列表", notes = "根据系统编码及角色编码获取权限列表")
    @GetMapping("/getPermissions")
    public R<List<String>> getPermissions(@RequestParam String osCode,
                                          @RequestParam String roleCode,
                                          @ApiIgnore Principal principal) {
        List<String> permissions = rolePermissionService.getByOsCodeAndRoleCode(osCode, roleCode);
        return new R<>(permissions);
    }

    /**
     * 根据系统编码及角色编码获取权限列表
     *
     * @param osCode    系统编码
     * @param principal 当前用户信息
     * @return 权限列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "osCode", value = "系统编码", dataType = "string", paramType = "query"),
    })
    @ApiOperation(value = "获取权限列表", notes = "根据系统编码取权限列表")
    @GetMapping("/getAllPermissions")
    public R<Map<String, List<String>>> getAllPermissions(@RequestParam String osCode,
                                                          @ApiIgnore Principal principal) {
        QueryWrapper<OsInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", osCode);
        int osCount = osInfoService.count(queryWrapper);
        if (osCount > 0) {
            List<String> roleList = roleService.listAllRoleCode();
            if (!CollectionUtils.isEmpty(roleList)) {
                Map<String, List<String>> permissions = new HashMap<>(roleList.size());
                roleList.parallelStream().forEach(roleCode ->
                        permissions.put(roleCode, rolePermissionService.getByOsCodeAndRoleCode(osCode, roleCode))
                );
                return new R<>(permissions);
            }
        }
        return new R<>(RCode.FAILED);
    }

    /**
     * 应用启动后执行
     */
    public void onStartUp() {
        new RestTemplate().getForEntity(String.format("%s/common/wakeUp", serverConfig.getWakeUrl()), String.class);
    }
}

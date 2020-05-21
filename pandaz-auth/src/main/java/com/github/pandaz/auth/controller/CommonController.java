package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.client.FtpClient;
import com.github.pandaz.auth.custom.CustomProperties;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.MenuEntity;
import com.github.pandaz.auth.service.CaptchaService;
import com.github.pandaz.auth.service.MenuService;
import com.github.pandaz.auth.util.AuthUtil;
import com.github.pandaz.auth.util.ServerConfig;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.dto.auth.AuthMenuDTO;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

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
     * 通用配置
     */
    private final CustomProperties customProperties;

    /**
     * 上传客户端
     */
    private final FtpClient ftpClient;

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
        R<List<String>> roleList = AuthUtil.getRoleListFromPrincipal(principal);
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
        return ftpClient.handleFileUpload(pathname, filename, file);
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
                              @RequestParam String originPath) {
        return ftpClient.handleFileDownload(pathname, filename, originPath);
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
                                @RequestParam String filename) {
        return ftpClient.handleFileDelete(pathname, filename);
    }

    /**
     * 应用启动后执行
     */
    public void onStartUp() {
        new RestTemplate().getForEntity(String.format("%s/common/wakeUp", serverConfig.getWakeUrl()), String.class);
    }
}

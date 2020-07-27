package com.github.pandaz.file.controller;

import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.ConvertUtil;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.file.util.FileTypeEnum;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * MongoDB Controller
 *
 * @author Carzer
 * @since 2020-07-24
 */
@RestController
@RequestMapping("/mongo")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("unused")
@Api(value = "Mongo", tags = "MongoDB操作")
public class MongoController {

    /**
     * 主mongo服务
     */
    @Qualifier("gridFsPrimaryTemplate")
    private final GridFsTemplate gridFsPrimaryTemplate;

    /**
     * 从mongo服务
     */
    @Qualifier("gridFsSecondaryTemplate")
    private final GridFsTemplate gridFsSecondaryTemplate;

    /**
     * 查询
     * <p>
     * URLEncoder.encode方法只适用于谷歌浏览器的中文乱码问题
     *
     * @param type 存储类型
     * @param id   文件ID
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "存储类型", value = "type", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "文件ID", value = "id", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "查询方法", notes = "查询方法")
    @GetMapping
    public void get(String type, String id, HttpServletResponse response) throws IOException {
        if (FileTypeEnum.MONGO_PRIMARY.getValue().equalsIgnoreCase(type)) {
            GridFSFile one = gridFsPrimaryTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
            assert one != null;
            GridFsResource resource = gridFsPrimaryTemplate.getResource(one);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    String.format("attachment; filename=%s", URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8)));
            try (OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(ConvertUtil.input2byte(resource.getInputStream()));
            }
        }
    }

    /**
     * 保存
     *
     * @param type 存储类型
     * @param file 文件
     * @return 保存结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "存储类型", value = "type", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "file")
    })
    @ApiOperation(value = "保存方法", notes = "保存方法")
    @PostMapping
    public R<String> save(String type, @RequestPart(value = "file") MultipartFile file) throws IOException {
        if (FileTypeEnum.MONGO_PRIMARY.getValue().equalsIgnoreCase(type)) {
            gridFsPrimaryTemplate.store(file.getInputStream(),
                    Optional.ofNullable(file.getOriginalFilename()).orElse(file.getName())
                    , file.getContentType());
            return new R<>(RCode.SUCCESS);
        }
        return R.fail();
    }
}

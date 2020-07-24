package com.github.pandaz.file.controller;

import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.ConvertUtil;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.file.util.FileTypeEnum;
import com.mongodb.client.gridfs.model.GridFSFile;
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
     *
     * @param type 存储类型
     * @param id   文件ID
     */
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
    @PostMapping
    public R<String> save(String type, @RequestPart(value = "file") MultipartFile file) throws IOException {
        if (FileTypeEnum.MONGO_PRIMARY.getValue().equalsIgnoreCase(type)) {
            gridFsPrimaryTemplate.store(file.getInputStream(), file.getOriginalFilename() == null ? file.getName() : file.getOriginalFilename(), file.getContentType());
            return new R<>(RCode.SUCCESS);
        }
        return R.fail();
    }
}

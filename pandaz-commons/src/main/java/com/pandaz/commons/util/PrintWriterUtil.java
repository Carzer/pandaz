package com.pandaz.commons.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 响应输出工具类
 *
 * @author Carzer
 * @since 2020-01-22
 */
public class PrintWriterUtil {

    /**
     * 构造方法
     */
    private PrintWriterUtil() {

    }

    /**
     * json mapper
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 输出方法
     *
     * @param httpServletResponse httpServletResponse
     * @param o                   输出内容
     * @throws IOException IOException
     */
    public static void write(HttpServletResponse httpServletResponse, Object o) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        String s = MAPPER.writeValueAsString(o);
        try (PrintWriter out = httpServletResponse.getWriter()) {
            out.write(s);
            out.flush();
        }
    }

}

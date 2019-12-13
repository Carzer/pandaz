package com.pandaz.usercenter.custom.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * pandaz:com.pandaz.usercenter.custom.filter
 * <p>
 * 允许跨域
 *
 * @author Carzer
 * @date 2019-08-22 09:09
 */
@WebFilter(filterName = "CorsFilter ")
@Configuration
public class CorsFilter implements Filter {

    /**
     * filter方法
     *
     * @param req   req
     * @param res   res
     * @param chain chain
     * @author Carzer
     * @date 2019/10/25 09:11
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }
}
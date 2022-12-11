package com.project.filter;

import com.project.utils.IpUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String realIP = IpUtils.getRealIP((HttpServletRequest) servletRequest);
        Date date = new Date();

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        this.encoding = config.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();

        if (!(uri.endsWith("png") || uri.endsWith("css") || uri.endsWith("jpg") || uri.endsWith("js"))) {
            if (this.encoding != null) {
                req.setCharacterEncoding(this.encoding);
                resp.setCharacterEncoding(this.encoding);
            }
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}

package com.nju.software.SellingCell.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "/api/*")
public class AuthorizationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthorizationFilter.class);
    @Value("${token_name}")
    private static String token_name;
    private static Set<String> ALLOW_URLS=new HashSet<String>(Arrays.asList("/api/login"));
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;
        String path = httpServletRequest.getServletPath();//获取url
        logger.info("url access: "+path);
        if(!ALLOW_URLS.contains(path)&&StringUtils.isEmpty(servletRequest.getAttribute(token_name))){
            /*
            当不是访问/api/login的时候，需要检测有没有token，没有token直接返回success=false
            其实最好检测token的有效性，防止知道了jsessionid后就能够访问了
             */
            logger.info("url: "+path+" is filtered");
            httpServletResponse.getWriter().write("{success:false");

        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

        System.out.println("过滤器销毁");
    }

}

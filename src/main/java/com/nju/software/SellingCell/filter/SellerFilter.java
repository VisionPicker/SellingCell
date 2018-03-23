package com.nju.software.SellingCell.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nju.software.SellingCell.controller.AuthorizationController;
import com.nju.software.SellingCell.controller.vo.Result;
import com.nju.software.SellingCell.controller.vo.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(value = "/api/user/seller/*")
public class SellerFilter implements Filter {
    private static Logger logger= LogManager.getLogger(SellerFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;
        String path = httpServletRequest.getServletPath();//获取url
        logger.info("url access: "+path);
        HttpSession session=((HttpServletRequest) servletRequest).getSession();
        String role=(String)session.getAttribute(AuthorizationController.user_role);
        if(role==null||!role.equals("seller")){
            /*
            当不是访问/api/login的时候，需要检测有没有token，没有token直接返回success=false
            其实最好检测token的有效性，防止知道了jsessionid后就能够访问了
             */
            logger.info("url: "+path+" is filtered");
            Result result=new Result();
            result.setSuccess(false);
            result.setCode(ResultCode.PERMISSION_DENIED);
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonResult=objectMapper.writeValueAsString(result);
            servletResponse.setContentType("application/json");
            servletResponse.setCharacterEncoding("utf-8");
            PrintWriter writer=httpServletResponse.getWriter();
            writer.write(jsonResult);
            writer.flush();
            writer.close();

        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}

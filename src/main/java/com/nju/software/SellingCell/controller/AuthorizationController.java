package com.nju.software.SellingCell.controller;

import com.nju.software.SellingCell.controller.vo.AuthRequest;
import com.nju.software.SellingCell.controller.vo.AuthResult;
import com.nju.software.SellingCell.controller.vo.Result;
import com.nju.software.SellingCell.data.entity.User;
import com.nju.software.SellingCell.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by keroro on 2018/2/24.y
 *
 */
@RestController
public class AuthorizationController{

    @Value("${token_name}")
    private static String token_name;
    private static Logger logger= LogManager.getLogger(AuthorizationController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value="/api/login",method = RequestMethod.POST)
    public AuthResult login(HttpServletRequest request, @RequestBody AuthRequest authRequest){
        Result result = new Result();
        AuthResult authResult =new AuthResult();
        logger.info("user login request:username = "+authRequest.getUsername());
        User user = userService.authorization(authRequest.getUsername(), authRequest.getPassword());
        if(user==null){
            result.setSuccess(false);
        }else{
            result.setSuccess(true);
            result.setData(authResult);//设置返回值
            authResult.setName(user.getUsername());
            authResult.setRole(user.getRole());
            authResult.setId(user.getId());
            String token=UUID.randomUUID().toString();
            HttpSession session= request.getSession();
            session.setMaxInactiveInterval(900);//设置session失效时间为15min
            session.setAttribute(token_name, token);//装入session token
        }
        return authResult;


    }

    @RequestMapping(value = "/api/logout",method = RequestMethod.POST)
    public Result logout(HttpServletRequest request, @RequestBody AuthRequest authRequest){
        Result result=new Result();
        request.getSession().invalidate();//让session失效
        result.setSuccess(true);
        return result;
    }
}

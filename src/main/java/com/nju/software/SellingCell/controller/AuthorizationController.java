package com.nju.software.SellingCell.controller;

import com.nju.software.SellingCell.controller.vo.AuthRequest;
import com.nju.software.SellingCell.controller.vo.AuthResult;
import com.nju.software.SellingCell.controller.vo.Result;
import com.nju.software.SellingCell.controller.vo.ResultCode;
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
import java.util.regex.Pattern;

/**
 * Created by keroro on 2018/2/24.y
 *
 */
@RestController
public class AuthorizationController{


    public static String token_name;
    public static String user_role;
    private static Logger logger= LogManager.getLogger(AuthorizationController.class);

    @Value("${token_name}")
    public void setToken_name(String value){
        token_name=value;
    }

    @Value("${user_role}")
    public void setUser_role(String value){
        user_role=value;
    }

    @Autowired
    UserService userService;

    @RequestMapping(value="/api/login",method = RequestMethod.POST)
    public Result login(HttpServletRequest request, @RequestBody AuthRequest authRequest){
        Result result = new Result();
        AuthResult authResult =new AuthResult();
        //验证参数的有效性
        String namePattern="^[a-zA-Z0-9_]{4,16}$";
        String passwordPattern="^[a-zA-Z0-9_]{4,20}$";
        if(!Pattern.matches(namePattern,authRequest.getUsername())||
                !Pattern.matches(passwordPattern,authRequest.getPassword())){
            result.setSuccess(false);
            result.setCode(ResultCode.PARAMS_INVALID);
        }
        logger.info("user login request:username = "+authRequest.getUsername());
        User user = userService.authorization(authRequest.getUsername(), authRequest.getPassword());
        if(user==null){
            result.setSuccess(false);
        }else{
            result.setSuccess(true);
            result.setData(authResult);//设置返回值
            authResult.setUsername(user.getUsername());
            authResult.setRole(user.getRole());
            authResult.setUserid(user.getUserid());
            HttpSession session= request.getSession();
            session.setMaxInactiveInterval(900);//设置session失效时间为15min
            session.setAttribute(token_name, user.getUserid());//将用户名装入session
            session.setAttribute(user_role,user.getRole());//将用户角色装入session
        }
        return result;


    }

    @RequestMapping(value = "/api/user/logout",method = RequestMethod.POST)
    public Result logout(HttpServletRequest request){
        Result result=new Result();
        request.getSession().invalidate();//让session失效
        result.setSuccess(true);
        return result;
    }
}

package com.nju.software.SellingCell.controller;

import com.nju.software.SellingCell.controller.vo.LoginRequest;
import com.nju.software.SellingCell.controller.vo.LoginResult;
import com.nju.software.SellingCell.data.entity.User;
import com.nju.software.SellingCell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by keroro on 2018/2/24.y
 *
 */
@RestController
public class AuthorizationController{

    @Autowired
    UserService userService;

    @RequestMapping(value="/api/login",method = RequestMethod.POST)
    public LoginResult login(@RequestBody LoginRequest loginRequest){
        LoginResult loginResult=new LoginResult();
        System.out.println("用户名:"+loginRequest.getUsername()+" 密码："+loginRequest.getPassword());
        User user = userService.authorization(loginRequest.getUsername(),loginRequest.getPassword());
        if(user==null){
            loginResult.setSuccess(false);
        }else{
            loginResult.setSuccess(true);
            loginResult.setName(user.getUsername());
            loginResult.setRole(user.getRole());
            loginResult.setId(user.getId());

        }
        return loginResult;


    }

    @RequestMapping
    public List getGoodsList(){
        return null;
    }
}

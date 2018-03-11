package com.nju.software.SellingCell.service.imp;

import com.nju.software.SellingCell.data.UserDao;
import com.nju.software.SellingCell.data.entity.User;
import com.nju.software.SellingCell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User authorization(String username, String password) {
        User user=userDao.selectUser(username,password);
        return user;
    }


}

package com.nju.software.SellingCell.service;


import com.nju.software.SellingCell.data.entity.User;

public interface UserService {

    public User authorization(String username, String password);

}

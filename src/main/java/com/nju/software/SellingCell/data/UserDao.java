package com.nju.software.SellingCell.data;

import com.nju.software.SellingCell.data.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    public User selectUser(@Param("username") String name, @Param("password") String password);
}

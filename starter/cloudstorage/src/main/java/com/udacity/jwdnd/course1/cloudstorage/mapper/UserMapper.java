package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username=#{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstName, lastName) " +
            "VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    int insert(User user);
}

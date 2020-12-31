package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url,userName,key,password,userId) VALUES(#{url},#{userName},#{key},#{password},#{userId});")
    Integer insertCredential(Credential credential);
    @Options(useGeneratedKeys=true, keyProperty = "credentialId")

    @Update("UPDATE CREDENTIALS SET url=#{url},userName=#{userName}, key=#{key}, password=#{password} where credentialId=#{credentialId};")
    Integer updateCredential(Credential credential);

    @Delete("Delete from CREDENTIALS where credentialId=#{credentialId};")
    Integer deleteCredential(Integer credentialId, Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId};")
    List<Credential> getAllCredentials(Integer userId);
}

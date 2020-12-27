package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url,userName,key,password,userId) VALUES(#{url},#{userName},#{key},#{password},#{userId});")
    int insertCredential(Credential credential);
    @Options(useGeneratedKeys=true, keyProperty = "credentialId")

    @Update("UPDATE CREDENTIALS SET url=#{url},userName=#{userName}, key=#{key}, password=#{password} where credentialId=#{credentialId};")
    int updateCredential(Credential credential);

    @Delete("Delete from CREDENTIALS where credentialId=#{credentialId};")
    Integer deleteCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userName=#{userName};")
    Credential getCredential();

    @Select("SELECT userId FROM USERS WHERE userName=#{userName};")
    int getUserId(String userName);

    @Select("SELECT credentialId FROM CREDENTIALS INNER JOIN USERS on CREDENTIALS.userId=users.userId where users.username=#{userName};")
    int getCredentialId(String userId);

    @Select("SELECT * FROM CREDENTIALS where credentialId=#{credentialId};")
    Credential getUserByCredentialId(int credentialId);

    @Select("SELECT key FROM CREDENTIALS where credentialId=#{credentialId};")
    String getCredentialKey(int credentialId);

    @Select("SELECT * FROM CREDENTIALS inner join USERS on credentials.userId=users.userId where users.username=#{userName};")
    List<Credential> getAllCredentials(String userName);

    @Select("SELECT url FROM CREDENTIALS;")
    List<String> getAllUrlCredentials();

    @Select("SELECT username FROM CREDENTIALS;")
    List<String> getAllUsernameCredentials();

    @Select("SELECT password FROM CREDENTIALS;")
    List<String> getAllPasswordCredentials();
}

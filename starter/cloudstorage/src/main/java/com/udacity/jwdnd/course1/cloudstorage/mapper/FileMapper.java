package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (fileId,fileName,contentType,fileSize,userId,fileData) " +
            "VALUES (#{fileId},#{fileName},#{contentType},#{fileSize},#{userId},#{fileData});")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFiles(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId};")
    Integer deleteFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId};")
    File downloadFileById(Integer fileId);

//    @Select("Select 1 from FILES WHERE filename=#{fileName};")
//    Integer fileNameAvailable(String fileName);

    @Select("SELECT * from FILES INNER JOIN USERS on FILES.userId=USERS.userId " +
            "WHERE USERS.username=#{username};")
    List<File> getAllFiles(String username);
}

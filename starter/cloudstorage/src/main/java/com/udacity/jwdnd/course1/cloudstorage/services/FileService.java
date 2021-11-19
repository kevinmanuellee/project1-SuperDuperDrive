package com.udacity.jwdnd.course1.cloudstorage.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Async
    public void uploadFile(MultipartFile multipartFile, Integer userId) {
        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setUserId(userId);
        String fileName = String.format("user%s||%s", String.valueOf(userId),multipartFile.getOriginalFilename());
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            PutObjectResult result = amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), objectMetadata).
                            withCannedAcl(CannedAccessControlList.PublicRead));
            file.setPath(amazonS3.getUrl(bucketName, fileName).toString());
            fileMapper.uploadFile(file);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public File getFile(Integer fileId, Integer userId){
        return fileMapper.getFile(fileId, userId);
    }

    @Async
    public byte[] downloadFile(final String fileName, int userId) {
        String fileName2 = String.format("user%s||%s", String.valueOf(userId),fileName);
        byte[] content = null;
        final S3Object s3Object = amazonS3.getObject(bucketName, fileName2);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(stream);
            s3Object.close();
        } catch(final IOException ex) {
            ex.printStackTrace();
        }
        return content;
    }

    public List<File> getAllFiles(Integer userId){
        return fileMapper.getAllFiles(userId);
    }

    public Integer deleteFile(Integer fileId, Integer userId, String fileName){
        String fileName2 = String.format("user%s||%s", String.valueOf(userId),fileName);
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName2);
        amazonS3.deleteObject(deleteObjectRequest);
        return fileMapper.deleteFile(fileId, userId);
    }

    public boolean isDuplicate (MultipartFile multipartFile, Integer userId){
        return fileMapper.isExistingFile(multipartFile.getOriginalFilename(), userId);
    }
}
package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private int fileId;
    private String fileName;
    private String path;
    private Integer userId;

    public File(int fileId, String fileName, String path, Integer userId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.path = path;
        this.userId = userId;
    }

    public File(){};

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
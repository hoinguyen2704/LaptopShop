package com.hoz.laptopshop.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUpLoadFileService {
    String uploadFile(String uploadFolder, MultipartFile file);

    String getUploadedFileName(String originalFileName); // This method seems to be for generating a unique file name

    void deleteFile(String uploadDir, String fileName) throws Exception; // Added method for deleting files
}

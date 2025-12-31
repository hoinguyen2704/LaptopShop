package com.hoz.laptopshop.service.validator.impl;

import com.hoz.laptopshop.service.IUpLoadFileService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@RequiredArgsConstructor
@Service
public class UpLoadFileServiceImpl implements IUpLoadFileService {
    private final ServletContext servletContext;


    @Override
    public String uploadFile(String uploadFolder, MultipartFile file) {
        String rootPath = this.servletContext.getRealPath("/resources/images");
        String fileName = "";
        try {
            byte[] bytes = file.getBytes();
            File dir = new File(rootPath + File.separator + uploadFolder);
            if (!dir.exists())
                dir.mkdirs();
            // Create the file on server
            fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator +
                    fileName);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            return fileName;
        } catch (Exception e) {
            System.out.println("Cannot get file size");
        }
        return fileName;
    }

    @Override
    public String getUploadedFileName(String originalFileName) {
        return "";
    }

    @Override
    public void deleteFile(String uploadDir, String fileName) throws Exception {

    }
}

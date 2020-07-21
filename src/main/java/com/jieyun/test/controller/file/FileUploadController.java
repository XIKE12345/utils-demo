package com.jieyun.test.controller.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author huike
 */

@RestController
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    public final static String UPLOAD_PATH_PREFIX = "uploadFile/";

    @PostMapping("/upload/file")
    public String upload(@RequestParam MultipartFile uploadFile, HttpServletRequest request) {
        return "";
    }
}

package com.jieyun.test.controller.file;

import com.jieyun.test.service.FileService;
import com.jieyun.test.utils.BaseResult;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author huike
 * zip文件的上传解析
 */
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public BaseResult<Map<String,Object>> uploadFileZip(@RequestParam MultipartFile file, @RequestParam String projectLocalNum) {

        // 上传.zip文件,之后解压,再之后遍历过滤出需要的文件，进行业务处理，入库
        try {
            this.fileService.fileParse(file,projectLocalNum);
        } catch (IOException e) {
            return BaseResult.create(HttpStatus.SC_INTERNAL_SERVER_ERROR, "zip文件处理失败，请联系系统管理员");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.create(HttpStatus.SC_OK, "Success");
    }
}

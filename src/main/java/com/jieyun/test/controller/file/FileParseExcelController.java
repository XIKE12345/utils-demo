package com.jieyun.test.controller.file;

import com.jieyun.test.service.FileParseExcelService;
import com.jieyun.test.utils.BaseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author huike
 */
@RestController
public class FileParseExcelController {

    public final static String EXCEL_LOAD = "excel";

    private final FileParseExcelService fileParseExcelService;

    public FileParseExcelController(FileParseExcelService fileParseExcelService) {
        this.fileParseExcelService = fileParseExcelService;
    }

    /**
     * 文件转置
     */
    @PostMapping(value = "/file/transpose/excel")
    public void excelTranspose(@RequestParam MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        //存放上传zip文件的文件夹
        String excelDirPath = "src/main/resources/" + EXCEL_LOAD;
        File fileDir = new File(excelDirPath);
        // 将文件上传至服务器
        File excelFile = new File(fileDir.getAbsolutePath() + File.separator + fileName);
        // 获取上传文件的地址
        String excelFilePath = excelFile.getAbsolutePath();

        // 将文件地址作为参数，调用python程序进行转置
        String[] params = new String[]{"python3", "/Users/huike/IdeaProjects/test/src/main/java/com/jieyun/test/demo/demo.py",
                excelFilePath, excelFilePath};
        Runtime.getRuntime().exec(params);
    }


    /**
     * 单孔文件解析
     *
     * @param file
     * @throws IOException
     */
    @PostMapping(value = "/upload/singleHole/excel")
    public BaseResult<String> uploadFileZip(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {

       return fileParseExcelService.parseExcelFile(file, request);

    }
}

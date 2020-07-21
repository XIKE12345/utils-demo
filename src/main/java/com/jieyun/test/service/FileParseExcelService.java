package com.jieyun.test.service;

import com.jieyun.test.utils.BaseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author huike
 */
public interface FileParseExcelService {
    /**
     * 解析Excel文件
     *
     * @param files
     * @param request
     * @throws IOException
     * @return BaseResult<String>
     */
    BaseResult<String> parseExcelFile(MultipartFile files, HttpServletRequest request) throws IOException;
}

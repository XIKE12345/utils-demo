package com.jieyun.test.service;

import com.jieyun.test.utils.BaseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author huike
 */
public interface FileService {
    /**
     * 处理 .zip类型的文件
     *
     * @param zipFile         zip文件
     * @param projectLocalNum 工程编号
     * @throws IOException IO异常
     */
    BaseResult<Map<String,Object>> fileParse(MultipartFile zipFile, String projectLocalNum) throws Exception;
}

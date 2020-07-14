package com.jieyun.test.utils.zip;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author huike
 * @desc 解压缩工具类
 */
@Slf4j
public class UnZipUtils {

    public final static String IMG_PATH_PREFIX = "unzip";

    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        File zipFile = new File(zipPath);
        System.err.println(zipFile.getName());
        if (!zipFile.exists()) {
            throw new IOException("需解压文件不存在.");
        }
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            log.info("zipEntryName is {}", zipEntryName);
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");
            log.info("outPath is {}", outPath);
            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 创建解压缩文件的暂存路径
     *
     * @return
     */
    public static File getUnZipDirFile() {
        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = new String("src/main/resources/" + IMG_PATH_PREFIX);

        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }
}

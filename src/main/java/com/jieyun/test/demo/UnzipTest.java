package com.jieyun.test.demo;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author huike
 */
public class UnzipTest {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("请输入正确参数：java UnzipTest 需解压的文件(e.g. d:/test.zip)");
        } else {
            Unzip unzip = new Unzip();
            if (unzip.unzip(args[0])) {
                System.out.println("文件解压成功。");
            } else {
                System.out.println("文件解压失败。");
            }
        }
    }
}

class Unzip {
    public Unzip() {
    }


    /**
     * @param srcZipFile 需解压的文件名
     * @return 如果解压成功返回true
     */
    public boolean unzip(String srcZipFile) {
        boolean isSuccessful = true;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcZipFile));
            ZipInputStream zis = new ZipInputStream(bis);

            BufferedOutputStream bos = null;

            //byte[] b = new byte[1024];
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                bos = new BufferedOutputStream(new FileOutputStream("d:/" + entryName));
                int b = 0;
                while ((b = zis.read()) != -1) {
                    bos.write(b);
                }
                bos.flush();
                bos.close();
            }
            zis.close();
        } catch (IOException e) {
            isSuccessful = false;
        }
        return isSuccessful;
    }
}

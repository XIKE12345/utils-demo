package com.jieyun.test.utils.zip;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author huike
 * @desc 递归遍历文件夹
 * @date 2020/06/18
 */
public class RecursionFolder {
      Set<String> filePathSet = new HashSet<>(16);

    /**
     * 递归遍历文件夹
     *
     * @param file 文件
     * @return Set<String> 文件夹下文件的路径
     */
    public  Set<String> func(File file) {
        File[] fs = file.listFiles();
        for (File f : Objects.requireNonNull(fs)) {
            //若是目录，则递归打印该目录下的文件
            if (f.isDirectory()) {
                func(f);
            }
            //若是文件，将文件路径放入Set中
            if (f.isFile()) {
                filePathSet.add(f.getPath());
            }
        }
        return filePathSet;
    }
}

package com.jieyun.test.entity.common;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @CalssName StudentDo
 * @Desc 公共的学生实体（测试使用）
 * @Author huike
 * @email <link>754873891@qq.com </link>
 * @Date 2019-12-21
 * @Version 1.0
 **/
@Data
public class StudentDo {
    @ExcelProperty(index = 0)
    private int id;
    @ExcelProperty(index = 1)
    private String stuName;
    @ExcelProperty(index = 2)
    private String stuGender;
    @ExcelProperty(index = 3)
    private int stuAge;
}

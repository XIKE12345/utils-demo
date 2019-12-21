package com.jieyun.test.demo;

import com.jieyun.test.entity.common.StudentDo;
import com.jieyun.test.utils.object.HumpToUnderLine;

/**
 * @CalssName HumpToUnderLineDemo
 * @Desc 驼峰对象和下划线对象互转Json
 * @Author huike
 * @email <link>754873891@qq.com </link>
 * @Date 2019-12-21
 * @Version 1.0
 **/
public class HumpToUnderLineDemo {
    public static void main(String[] args) throws Exception {
        StudentDo studentDo = new StudentDo();
        studentDo.setId(1);
        studentDo.setStuName("张三");
        studentDo.setStuAge(18);
        studentDo.setStuGender("男");

        // 驼峰类型的对象转成下划线类型的json字符串
        String string = HumpToUnderLine.toUnderlineJSONString(studentDo);
        System.out.println(string);
        // 下划线类型的Json字符串转驼峰类型的对象
        StudentDo studentDo1 = HumpToUnderLine.toSnakeObject(string, StudentDo.class);
        System.out.println(studentDo1.toString());
    }
}

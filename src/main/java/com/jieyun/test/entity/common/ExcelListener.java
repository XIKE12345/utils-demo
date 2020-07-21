package com.jieyun.test.entity.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huike
 */
public class ExcelListener extends AnalysisEventListener<StudentDo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<StudentDo> list = new ArrayList<StudentDo>();


    @Override
    public void invoke(StudentDo studentDo, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

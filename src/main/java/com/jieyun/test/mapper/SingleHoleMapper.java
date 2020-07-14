package com.jieyun.test.mapper;

import com.jieyun.test.entity.TSingleHole;
import com.jieyun.test.entity.dto.DkOrgDataParseDto;
import com.jieyun.test.entity.dto.SingleHoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface SingleHoleMapper {

    /**
     * 查寻工程Id
     *
     * @param projectNum 工程编号
     * @return String
     */
    String selectProjectIdByProjectNum(@Param("projectNum") String projectNum);

    /**
     * 插入单孔数据（导入）
     *
     * @param tSingleHoles 单孔数据对象
     * @return int
     */

    int batchInsertIntoDkData(@Param("list") List<TSingleHole> tSingleHoles);

    /**
     * 查询孔号，工程编码
     *
     * @param projectNum 工程编号
     * @return List<SingleHoleDto>
     */
    List<SingleHoleDto> selectSingleHoleNum(@Param("projectNum") String projectNum);

    /**
     * 查孔的id
     *
     * @param holeNum    孔号
     * @param projectNum 工程编号
     * @return String 孔对应的编码
     */
    String selectDrillIdByHoleNum(@Param("holeNum") String holeNum, @Param("projectNum") String projectNum);

}

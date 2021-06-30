package com.codecow.dao;

import com.codecow.common.vo.req.SysLogPageReqVO;
import com.codecow.entity.SysLog;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);


    List<SysLog>selectAll(SysLogPageReqVO vo);


    int batchDeletedLog(List<String> logIds);
}

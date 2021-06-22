package com.codecow.service;

import com.codecow.common.vo.req.DeptAddReqVO;
import com.codecow.common.vo.resp.DeptRespNodeVO;
import com.codecow.entity.SysDept;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/22 13:44
 **/
public interface IDeptService {
    /**
     * 查询所有部门
     * @return
     */
    List<SysDept>selectAll();

    /**
     * 获取部门树（用于添加新部门）
     */
    List<DeptRespNodeVO> getDeptTreeList();

    SysDept addDept(DeptAddReqVO vo);
}

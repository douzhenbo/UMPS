package com.codecow.service;

import com.codecow.common.vo.req.DeptAddReqVO;
import com.codecow.common.vo.req.DeptUpdateReqVO;
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
     * 获取部门树（添加部门和编辑部门时使用）
     * @param deptId 如果传入数据代表编辑部门（排除该部门和其子部门）；如果没有数据传入则代表新增部门（获取完整的部门树）
     * @return
     */
    List<DeptRespNodeVO> getDeptTreeList(String deptId);

    SysDept addDept(DeptAddReqVO vo);

    void updateDept(DeptUpdateReqVO vo);

    void deleteDept(String id);
}

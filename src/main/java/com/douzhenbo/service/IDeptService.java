package com.douzhenbo.service;

import com.douzhenbo.entity.SysDept;

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
}

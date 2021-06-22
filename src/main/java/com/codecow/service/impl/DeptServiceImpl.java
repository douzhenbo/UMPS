package com.codecow.service.impl;

import com.codecow.dao.SysDeptMapper;
import com.codecow.entity.SysDept;
import com.codecow.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/22 13:45
 **/
@Service
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 获取所有的部门信息
     * @return
     */
    @Override
    public List<SysDept> selectAll() {
        List<SysDept>list=sysDeptMapper.selectAll();
        for(SysDept s:list){
            SysDept parent=sysDeptMapper.selectByPrimaryKey(s.getPid());
            if(parent!=null){
                s.setPidName(parent.getName());
            }
        }
        return list;
    }

}

package com.codecow.service.impl;

import com.codecow.common.constants.Constant;
import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.codeUtil.CodeUtil;
import com.codecow.common.vo.req.DeptAddReqVO;
import com.codecow.common.vo.resp.DeptRespNodeVO;
import com.codecow.dao.SysDeptMapper;
import com.codecow.entity.SysDept;
import com.codecow.service.IDeptService;
import com.codecow.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/22 13:45
 **/
@Service
@Slf4j
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private RedisService redisService;
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

    @Override
    public List<DeptRespNodeVO> getDeptTreeList() {
        List<SysDept>list=selectAll();
        DeptRespNodeVO deptRespNodeVO=new DeptRespNodeVO();
        deptRespNodeVO.setId("0");
        deptRespNodeVO.setTitle("默认顶级部门");
        deptRespNodeVO.setChildren(getTree(list));
        List<DeptRespNodeVO>result=new ArrayList<>();
        result.add(deptRespNodeVO);
        return result;
    }


    @Override
    public SysDept addDept(DeptAddReqVO vo) {
        String relationCode;
        long deptCount=redisService.incrby(Constant.DEPT_CODE_KEY,1);
        String deptCode= CodeUtil.deptCode(String.valueOf(deptCount),7,"0");
        SysDept parent=sysDeptMapper.selectByPrimaryKey(vo.getPid());
        if(vo.getPid().equals("0")){
            relationCode=deptCode;
        }else if(null==parent){
            log.info("父级数据不存在{}",vo.getPid());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }else {
            relationCode=parent.getRelationCode()+deptCode;
        }


        SysDept sysDept=new SysDept();
        BeanUtils.copyProperties(vo,sysDept);
        sysDept.setId(UUID.randomUUID().toString());
        sysDept.setCreateTime(new Date());
        sysDept.setDeptNo(deptCode);
        sysDept.setRelationCode(relationCode);
        int insert=sysDeptMapper.insertSelective(sysDept);
        if(insert==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return null;
    }

    private List<DeptRespNodeVO>getTree(List<SysDept>all){

        List<DeptRespNodeVO>list=new ArrayList<>();

        for(SysDept s:all){
            if("0".equals(s.getPid())){
                DeptRespNodeVO deptRespNodeVO=new DeptRespNodeVO();
                BeanUtils.copyProperties(s,deptRespNodeVO);
                deptRespNodeVO.setTitle(s.getName());
                deptRespNodeVO.setId(s.getId());
                deptRespNodeVO.setChildren(getChild(s.getId(),all));
                list.add(deptRespNodeVO);
            }
        }
        return list;
    }


    private List<DeptRespNodeVO> getChild(String id,List<SysDept>all){
        List<DeptRespNodeVO>list=new ArrayList<>();
        for(SysDept s:all){
            if(id.equals(s.getPid())){
                DeptRespNodeVO deptRespNodeVO=new DeptRespNodeVO();
                BeanUtils.copyProperties(s,deptRespNodeVO);
                deptRespNodeVO.setTitle(s.getName());
                deptRespNodeVO.setId(s.getId());
                deptRespNodeVO.setChildren(getChild(s.getId(),all));
                list.add(deptRespNodeVO);
            }
        }
        return list;
    }

}

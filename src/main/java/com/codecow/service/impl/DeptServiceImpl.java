package com.codecow.service.impl;

import com.codecow.common.constants.Constant;
import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.codeUtil.CodeUtil;
import com.codecow.common.vo.req.DeptAddReqVO;
import com.codecow.common.vo.req.DeptUpdateReqVO;
import com.codecow.common.vo.resp.DeptRespNodeVO;
import com.codecow.dao.SysDeptMapper;
import com.codecow.entity.SysDept;
import com.codecow.service.IDeptService;
import com.codecow.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public List<DeptRespNodeVO> getDeptTreeList(String deptId) {
        List<SysDept>list=selectAll();

        //直接在数据源去除这个部门的叶子节点
        if(!StringUtils.isEmpty(deptId)&&!list.isEmpty()){
            for(SysDept s:list){
                if(s.getId().equals(deptId)){
                    list.remove(s);
                    break;
                }
            }
        }


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

    /**
     * 更新部门信息
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(DeptUpdateReqVO vo) {
        //保存更新部门
        SysDept sysDept=sysDeptMapper.selectByPrimaryKey(vo.getId());

        if(sysDept==null){
            log.error("传入的部门id：{}错误",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        SysDept updateDept=new SysDept();
        BeanUtils.copyProperties(vo,updateDept);
        sysDept.setUpdateTime(new Date());

        int update=sysDeptMapper.updateByPrimaryKeySelective(updateDept);
        if(update!=1){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        //维护层级关系
        if(vo.getPid().equals(sysDept.getPid())){
            //子集部门关系的层级编码=父级部门层级关系编码+本身的部门编码
            SysDept newParent=sysDeptMapper.selectByPrimaryKey(vo.getPid());
            if(!vo.getPid().equals("0")&&newParent==null){
                log.info("修改后的部门在数据库中查找不到！");
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }

            SysDept oldParent=sysDeptMapper.selectByPrimaryKey(sysDept.getPid());
            String oldRelation;
            String newRelation;

            //根目录挂靠到其他目录
            if(sysDept.getPid().equals("0")){
                oldRelation=sysDept.getDeptNo();
                newRelation=newParent.getRelationCode()+sysDept.getDeptNo();
            }else if(vo.getPid().equals("0")){
                oldRelation=sysDept.getRelationCode();
                newRelation=sysDept.getDeptNo();
            }else {
                oldRelation=oldParent.getRelationCode();
                newRelation=newParent.getRelationCode();
            }


            sysDeptMapper.updateRelationCode(oldRelation,newRelation,sysDept.getRelationCode());
        }

    }


    @Override
    public void deleteDept(String id) {

    }
}

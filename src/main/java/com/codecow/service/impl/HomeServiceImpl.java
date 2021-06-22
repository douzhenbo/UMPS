package com.codecow.service.impl;

import com.codecow.common.vo.resp.HomeRespVO;
import com.codecow.common.vo.resp.PermissionRespNodeVO;
import com.codecow.common.vo.resp.UserInfoRespVO;
import com.codecow.dao.SysUserMapper;
import com.codecow.entity.SysUser;
import com.codecow.service.IHomeService;
import com.codecow.service.IPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/18 9:30
 **/

@Service
public class HomeServiceImpl implements IHomeService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private IPermissionService permissionService;


    @Override
    public HomeRespVO getHome(String userId) {
        HomeRespVO homeRespVO=new HomeRespVO();
        List<PermissionRespNodeVO>list= permissionService.permissionTreeList(userId);
        System.out.println(list);
        homeRespVO.setMenus(list);
        SysUser user=sysUserMapper.selectByPrimaryKey(userId);
        UserInfoRespVO userInfoRespVO=new UserInfoRespVO();
        if(user!=null){
            BeanUtils.copyProperties(user,userInfoRespVO);

            userInfoRespVO.setDeptId("1");

            userInfoRespVO.setDeptName("研发三部");
        }
        homeRespVO.setUserInfoRespVO(userInfoRespVO);

        return homeRespVO;
    }
}

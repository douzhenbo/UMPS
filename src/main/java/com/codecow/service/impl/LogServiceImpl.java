package com.codecow.service.impl;

import com.codecow.common.utils.pageutils.PageUtil;
import com.codecow.common.vo.req.SysLogPageReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.dao.SysLogMapper;
import com.codecow.entity.SysLog;
import com.codecow.service.ILogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/29 16:18
 **/
@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Override
    public PageVO<SysLog> pageInfo(SysLogPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysLog> sysLogs = sysLogMapper.selectAll(vo);
        return PageUtil.getPageVo(sysLogs);
    }

    @Override
    public int deleted(List<String> logIds) {
        return sysLogMapper.batchDeletedLog(logIds);
    }
}

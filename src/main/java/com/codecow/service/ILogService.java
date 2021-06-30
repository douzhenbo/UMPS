package com.codecow.service;

import com.codecow.common.vo.req.SysLogPageReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.entity.SysLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/29 16:16
 **/
public interface ILogService {
    /**
     * 分页查询日志信息
     * @param vo
     * @return
     */
    PageVO<SysLog>pageInfo(SysLogPageReqVO vo);

    int deleted(List<String> logIds);
}

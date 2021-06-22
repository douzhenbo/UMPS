package com.codecow.service;

import com.codecow.common.vo.resp.HomeRespVO;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/18 9:28
 **/
public interface IHomeService {
    HomeRespVO getHome(String userId);
}

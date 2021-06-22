package com.douzhenbo.common.utils.pageutils;

import com.douzhenbo.common.vo.resp.PageVO;
import com.github.pagehelper.Page;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 13:31
 **/

public class PageUtil {
    private PageUtil(){}
    public static <T> PageVO getPageVo(List<T> list){
        PageVO<T> pageVO=new PageVO<>();
        if(list instanceof Page){
            Page page = (Page)list;
            long total=page.getTotal();
            int i=(int)total;
            pageVO.setTotalAccount(i);
            pageVO.setList(page.getResult());
            pageVO.setTotalPages(page.getPages());
            pageVO.setCurPageSize(page.size());
            pageVO.setPageNum(page.getPageNum());
            pageVO.setPageSize(page.getPageSize());
        }
        return pageVO;
    }
}


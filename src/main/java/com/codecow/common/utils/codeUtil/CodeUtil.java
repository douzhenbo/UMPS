package com.codecow.common.utils.codeUtil;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/22 16:27
 **/
public class CodeUtil {

    private static final String DEPT_TPYE="YXD";
    private static final String PERMISSION_TPYE="YXP";
    /**
     * 右补位，左对齐
     * @pparam oriStr  原字符串
     * @param len  目标字符串长度
     * @param alexin  补位字符
     * @return  目标字符串
     * 以alexin 做为补位
     */
    public  static String padRight(String oriStr,int len,String alexin){
        String str = "";
        int strlen = oriStr.length();
        if(strlen < len){
            for(int i=0;i<len-strlen;i++){
                str=str+alexin;
            }
        }
        str=str+oriStr;
        return str;
    }
    /**
     * 获取机构编码 YXD0001
     * @Author:      codecow
     * @param oriStr 初始值
     * @param len 位数
     * @param alexin 不足 以什么补充
     * @return       java.lang.String
     * @throws
     */
    public static String deptCode(String oriStr,int len,String alexin){
        return DEPT_TPYE+padRight(oriStr, len, alexin);
    }
}

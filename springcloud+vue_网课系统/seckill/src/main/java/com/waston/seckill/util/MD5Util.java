package com.waston.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/17 18:20
 */
public class MD5Util {

    public static String salt = "springboot";

    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    //第一次加盐（和前端传输加盐一致）
    public static String inputToBack(String str){
        return md5(str + salt);
    }

    //第二次加盐
    public static String backToDb(String str,String dbSalt){
        return md5(str + dbSalt);
    }

    //整合，两次加盐，两次md5
    public static String inputToDb(String str,String dbSalt){
        return backToDb(inputToBack(str),dbSalt);
    }
}

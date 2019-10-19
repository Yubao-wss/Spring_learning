package com.waston.seckill.util;

import java.util.UUID;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/19 20:05
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

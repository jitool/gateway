package com.executor.gateway.core.util;

import com.google.common.base.Strings;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/9 21:16
 * @Description:
 */
public class CommonUtils {
    /**
     * 将url转换成多匹配
     * @param url
     * @return
     */
     public static String convertUrlForMatchingAll(String url){
         if (!Strings.isNullOrEmpty(url) && url.contains("/")) {
             return url.substring(0, url.lastIndexOf("/")).concat("/*");
         } else {
             return "/*";
         }
     }
}

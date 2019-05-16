package com.bear.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Util {

    public static String md5Pwd(String pwd, String salt, Integer num){
        // 加密：MD5 + 盐 + 散列次数
        Md5Hash md5Hash = new Md5Hash(pwd,salt,num);
        return md5Hash.toString();
    }

    public static void main(String... args){
        System.out.println(md5Pwd("111","zhangsan",3));
    }
}

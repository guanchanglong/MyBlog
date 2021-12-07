package com.gcl.demo1.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 6:33
 */

/**
 * 密码加密类，使用
 */
public class MD5Utils {

    /**
     * MD5加密类
     * @param str 要加密的字符串
     * @return    加密后的字符串
     */
    public  static String code(String str){
        try{
            //MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
            // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，输出固定长度的哈希值。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //MessageDigest对象开始被初始化,update（）方法里面是待加密的字符串
            md.update(str.getBytes());
            //加密之后生成的密文的字节数组
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer stringBuffer = new StringBuffer("");
            for (int offset = 0;offset<byteDigest.length;offset++){
                i = byteDigest[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            //32位加密
            return stringBuffer.toString();
            //16位加密
            //return stringBuffer.toString().substring(8,24);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[]args){
//        System.out.println(code("20001114"));
    }

}

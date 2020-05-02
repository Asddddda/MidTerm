package com.own.midterm.util.Glide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String getMD5(String url) {
        String result="";
        try {
            MessageDigest md=MessageDigest.getInstance("md5");
            md.update(url.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb=new StringBuilder();
            for(byte b:bytes){
                String str=Integer.toHexString(b&0xFF);
                if(str.length()==1){
                    sb.append("0");
                }
                sb.append(str);
            }
            result=sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
package base.xuelianx.com.encryptlib;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Title: MD5Util.java
 * Description: MD5加密工具类
 * Copyright (c) 个人版权所有 2019/5/12
 * Created DateTime: 2019/5/12 14:28
 * Created by xuelianXiong.
 */
public class MD5Util {

    /**
     * 文件的32位md5值
     *
     * @param sourceFile 文件
     * @return 文件的md5值
     */
    public static String getMD5(File sourceFile) {
        BigInteger bi = null;
        try {
            //设置输入流的缓存大小 字节
            byte[] buffer = new byte[8192];
            int len ;
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(sourceFile);
            //将整个文件全部读入到加密器中
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            //对读入的数据进行加密
            byte[] bytes = md.digest();
            //生成16进制字符串
            return ByteUtil.bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符串的32位md5值
     *
     * @param sourceStr 待加密内容
     * @return 字符串的md5值
     */
    public static String getMD5(String sourceStr) {
        if (TextUtils.isEmpty(sourceStr)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(sourceStr.getBytes());
            //生成16进制字符串
            return ByteUtil.bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 32位md5加盐加密
     *
     * @param sourceStr 待加密字符串
     * @param slat      盐值
     * @return md5加密后的字符串
     */
    public static String getSaltMD5(String sourceStr, String slat) {
        if (TextUtils.isEmpty(sourceStr)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest((sourceStr + slat).getBytes());
            //生成16进制字符串
            return ByteUtil.bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}

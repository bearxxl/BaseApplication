package base.xuelianx.com.encryptlib;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Title: ByteUtil.java
 * Description: 字节转换工具
 * Copyright (c) 个人版权所有 2019/5/12
 * Created DateTime: 2019/5/12 15:35
 * Created by xuelianXiong.
 */
public class ByteUtil {
    /**
     * 字节数组转成十六进制字符串
     *
     * @param bArr 待转换的字节数组
     * @return 字节数组的十六进制字符串
     */
    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String temp;
        for (byte b : bArr) {
            temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转成字节数组
     *
     * @param sourceStr 待转换的十六进制字符串
     * @return 字节数组
     */
    private static byte[] hexToBytes(String sourceStr) {
        if (sourceStr == null || sourceStr.length() < 2) {
            return new byte[0];
        }
        sourceStr = sourceStr.toUpperCase();
        int l = sourceStr.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = sourceStr.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

    /**
     * 16进制转字节
     *
     * @param hexStr 十六进制字符串
     * @return 字节
     * 注意：Hex的字符串必须为十六进制的字符，否则会抛出异常。Hex的范围为0x00到0xFF
     */
    public static byte hexToByte(String hexStr) {
        return (byte) Integer.parseInt(hexStr, 16);
    }

    /**
     * 字节转16进制
     *
     * @param mByte 待转换字节
     * @return 16进制字符串
     */
    public static String byteToHex(byte mByte) {
        String hex = Integer.toHexString(mByte & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    /**
     * bitmap转成字节数组
     *
     * @param bm bitmap
     * @return 字节数组
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 字节数组转成bitmap
     * @param bytes 待转字节数组
     * @param opts  可不传
     * @return  bitmap
     */
    public static Bitmap bytesToBitmap(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }


}

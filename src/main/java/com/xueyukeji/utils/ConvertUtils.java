package com.xueyukeji.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 转换工具类
 *
 * @author durance
 */
public class ConvertUtils {

    /**
     * 输入流转换为xml字符串
     *
     * @param inputStream 输入流
     * @return 转化结果
     */
    public static String convertToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inputStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        return result;
    }

}

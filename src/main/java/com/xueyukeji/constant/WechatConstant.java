package com.xueyukeji.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 常量类
 * 配置基本信息
 * @author durance
 */
@Configuration
@ConfigurationProperties(prefix = "wxpay")
public class WechatConstant {

    /**
     * 商户号
     */
    public static String MCH_ID = "";

    /**
     * API v2 密钥
     */
    public static String MCH_KEY = "";

    /**
     * 小程序 appId
     */
    public static String APPID = "";

    /**
     * 小程序 app secret
     */
    public static String SECRET = "";

    /**
     * 支付成功后回调通知地址
     */
    public static String SUCCESS_NOTIFY = "";

    public void setMchId(String mchId) {
        MCH_ID = mchId;
    }

    public void setMchKey(String mchKey) {
        MCH_KEY = mchKey;
    }

    public void setAPPID(String APPID) {
        WechatConstant.APPID = APPID;
    }

    public void setSECRET(String SECRET) {
        WechatConstant.SECRET = SECRET;
    }

    public void setSuccessNotify(String successNotify) {
        SUCCESS_NOTIFY = successNotify;
    }

}

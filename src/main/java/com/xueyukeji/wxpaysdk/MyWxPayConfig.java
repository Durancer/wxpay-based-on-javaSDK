package com.xueyukeji.wxpaysdk;


import com.xueyukeji.constant.WechatConstant;

import java.io.InputStream;

/**
 * @author durance
 */
public class MyWxPayConfig extends WXPayConfig{


    @Override
    String getAppID() {
        return WechatConstant.APPID;
    }

    @Override
    String getMchID() {
        return WechatConstant.MCH_ID;
    }

    @Override
    String getKey() {
        return WechatConstant.MCH_KEY;
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API,true);
            }
        };
    }
}

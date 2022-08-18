package com.xueyukeji.service;

import java.util.Map;

public interface WxpayService {

    /**
     * 返回五个字段参与签名(区分大小写)：appId，nonceStr，package，signType，timeStamp，paySign
     * @param openid
     * @param ip
     * @return
     * @throws Exception
     */
    Map<String,String> wxpay(String openid, String ip) throws Exception;
}

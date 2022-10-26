package com.xueyukeji.service;

import java.util.Map;

/**
 * @author durance
 */
public interface WxpayService {

    /**
     * 返回五个字段参与签名(区分大小写)：appId，nonceStr，package，signType，timeStamp，paySign
     * @param openid
     * @param ip
     * @return
     * @throws Exception
     */
    Map<String,String> wxpay(String openid, String ip) throws Exception;

    /**
     * 二维码支付
     * @param ip 传入ip地址
     * @return 二维码转换码
     * @throws Exception
     */
    Map<String,String> qrcode(String ip) throws Exception;

}

package com.xueyukeji.service.impl;


import com.xueyukeji.constant.WechatConstant;
import com.xueyukeji.service.WxpayService;
import com.xueyukeji.wxpaysdk.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.xueyukeji.constant.WechatConstant.SUCCESS_NOTIFY;

/**
 * @author durance
 */
@Slf4j
@Service
public class WxpayServiceImpl implements WxpayService {

    @Override
    public Map<String, String> wxpay(String openid,String ip) throws Exception {
        //1、拼接请求头
        Map<String,String> paraMap = new HashMap<String, String>();
        //商品描述
        paraMap.put("body","");
        paraMap.put("openid",openid);
        //随机字符串
        paraMap.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-",""));
        paraMap.put("spbill_create_ip",ip);
        // 1表示1分  100 表示 1元
        paraMap.put("total_fee","100");
        //支付类型
        paraMap.put("trade_type","JSAPI");


        // 2、创建wxpay对象，用于支付请求
        //是否使用沙箱环境
        boolean useSandbox = false;
        WXPayConfig wxPayConfig = new MyWxPayConfig();
        WXPay wxPay = new WXPay(wxPayConfig,SUCCESS_NOTIFY,false,useSandbox);
        // 3、发送请求
        Map<String, String> map = wxPay.unifiedOrder(wxPay.fillRequestData(paraMap), 15000, 15000);
        // 4、统一下单接口，返回预支付id
        String prepayID = map.get("prepay_id");
        System.out.println("预支付id 为"+prepayID);
        // 整合返回信息
        Map<String,String> payMap = new HashMap<String ,String>();
        payMap.put("appId", WechatConstant.APPID);
        payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp()+"");
        payMap.put("nonceStr",WXPayUtil.generateNonceStr());
        if(useSandbox){
            payMap.put("signType", WXPayConstants.MD5);
        }else{
            payMap.put("signType",WXPayConstants.HMACSHA256);
        }
        payMap.put("package","prepay_id="+prepayID);
        // 5、将上述包装进行key=value形式加密
        String paySign = null;
        if(useSandbox){
            paySign = WXPayUtil.generateSignature(payMap,WechatConstant.MCH_KEY,WXPayConstants.SignType.MD5);
        }else{
            paySign = WXPayUtil.generateSignature(payMap,WechatConstant.MCH_KEY,WXPayConstants.SignType.HMACSHA256);
        }
        payMap.put("paySign",paySign);
        //6、返回这些参数
        log.info("payMap:"+payMap);
        return payMap;
    }

    @Override
    public Map<String, String> qrcode(String ip) throws Exception {
        MyWxPayConfig myConfig=new MyWxPayConfig();
        WXPay wxPay=new WXPay(myConfig);
        //根据官方文档 必填值封装数据
        Map<String,String> map = new HashMap<>();
        //商品描述
        map.put("body", "");
        map.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-",""));
        //价格  1表示1分
        map.put("total_fee", "1");
        map.put("spbill_create_ip", ip);
        //支付成功通知地址
        // todo 扫码支付的回调地址可以和JSAPI不相同，来处理对应的回调逻辑
        map.put("notify_url", SUCCESS_NOTIFY);
        //支付方式，选择扫码支付
        map.put("trade_type", "NATIVE");
        return wxPay.unifiedOrder(map);
    }

}

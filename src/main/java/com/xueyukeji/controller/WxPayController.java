package com.xueyukeji.controller;

import com.xueyukeji.service.WxpayService;
import com.xueyukeji.utils.ConvertUtils;
import com.xueyukeji.wxpaysdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @author durance
 */
@RestController
@RequestMapping("pay")
@Slf4j
public class WxPayController {

    @Autowired
    private WxpayService wxpayService;

    /**
     * JSAPI调起支付
     *
     * @param request 请求体
     * @param openid  用户openid
     * @return 调起支付所需参数
     */
    @PostMapping("wxpay")
    public Map<String,String> pay(HttpServletRequest request,String openid) throws Exception {
        //获取请求ip, service中需要
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip.indexOf(",") != -1){
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        return wxpayService.wxpay(openid,ip);
    }

    /**
     * 生成二维码支付
     *
     * @param request 请求体
     * @return 二维码订单相关信息
     */
    @GetMapping("qrcode")
    public Map<String,String> getQRcode(HttpServletRequest request) throws Exception {
        //获取请求ip, service中需要
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip.indexOf(",") != -1){
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        return wxpayService.qrcode(ip);
    }

    /**
     * 接收支付成功的接口，返回 return_code为 SUCCESS
     * 如果没有这个接口，微信服务器会隔一段时间通知一次
     * JSPAI 支付在JS中可以进行回调，可以简单通知微信服务器成功支付，也可直接处理
     * native 扫码支付需要在此回调中处理对应的业务逻辑，和JSAPI支付可以有两个不同的回调地址
     *
     * @param request 请求题
     * @return 响应结果
     */
    @GetMapping("success")
    public String success(HttpServletRequest request, @RequestBody String returnCode) throws Exception {
        // 支付成功后微信讲通知我们订单相关信息，如下所示

        // <xml>
        //     <appid><![CDATA[wx8397f8696bxxxxxx]]></appid>
        //     <bank_type><![CDATA[CFT]]></bank_type>
        //     <cash_fee><![CDATA[1]]></cash_fee>
        //     <fee_type><![CDATA[CNY]]></fee_type>
        //     <is_subscribe><![CDATA[N]]></is_subscribe>
        //     <mch_id><![CDATA[1473426802]]></mch_id>
        //     <nonce_str><![CDATA[c6bea293399a40e0a873df51e667f45a]]></nonce_str>
        //     <openid><![CDATA[oNpSGwbtNBQROpN_dL8WUZG3wRkM]]></openid>
        //     <out_trade_no><![CDATA[1553063775279]]></out_trade_no>
        //     <result_code><![CDATA[SUCCESS]]></result_code>
        //     <return_code><![CDATA[SUCCESS]]></return_code>
        //     <sign><![CDATA[DD4E5DF5AF8D8D8061B0B8BF210127DE]]></sign>
        //     <time_end><![CDATA[20190320143646]]></time_end>
        //     <total_fee>1</total_fee>
        //     <trade_type><![CDATA[NATIVE]]></trade_type>
        //     <transaction_id><![CDATA[4200000248201903206581106357]]></transaction_id>
        // </xml>

        // 获取微信带来的订单信息
        String xml = ConvertUtils.convertToString(request.getInputStream());
        Map<String, String> orderMap = WXPayUtil.xmlToMap(xml);
        // 拿到订单号，进行我们需要的业务逻辑，如：修改改订单状态为已支付
        String outTradeNo = orderMap.get("out_trade_no");
        // todo 订单相关的业务逻辑
        log.info("订单号为{}，完成了支付", outTradeNo);

        // 响应微信服务器
        Map<String, String> result = new HashMap<>();
        if ("SUCCESS".equals(returnCode)) {
            result.put("return_code", "SUCCESS");
            result.put("return_msg", "OK");
        }
        return WXPayUtil.mapToXml(result);
    }

}

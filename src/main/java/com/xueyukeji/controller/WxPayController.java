package com.xueyukeji.controller;


import com.xueyukeji.service.WxpayService;
import com.xueyukeji.wxpaysdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("pay")
@Slf4j
public class WxPayController {

    @Autowired
    private WxpayService wxpayService;

    /**
     * 小程序支付
     * @param request
     * @param openid
     * @return
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
     * @param request
     * @return
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
     * @param request
     * @return
     */
    @GetMapping("success")
    public String success(HttpServletRequest request,@RequestBody String return_code) throws Exception {
        Map<String,String> result = new HashMap<>();
        if ("SUCCESS".equals(return_code)) {
            result.put("return_code","SUCCESS");
            result.put("return_msg","OK");
        }
        log.info(return_code);
        return WXPayUtil.mapToXml(result);
    }

}

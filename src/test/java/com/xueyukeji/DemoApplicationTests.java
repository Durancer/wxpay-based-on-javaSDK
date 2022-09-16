package com.xueyukeji;

import com.xueyukeji.wxpaysdk.MyWXPayConfig;
import com.xueyukeji.wxpaysdk.WXPay;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = WxpayApplication.class)
class DemoApplicationTests {

    @Test
    public void contextLoads()throws Exception {
        MyWXPayConfig myConfig=new MyWXPayConfig();
        WXPay wxPay=new WXPay(myConfig);

        //根据官方文档 必填值封装数据
        Map<String,String> map = new HashMap<>();
        map.put("body", "布踏科技色卡");
        map.put("out_trade_no", "123654");
        map.put("total_fee", "1");
        map.put("spbill_create_ip", "127.0.0.1");
        map.put("notify_url", "http://www.buta.vip/pay/success");
        map.put("trade_type", "NATIVE");

        Map<String, String> resultMap = wxPay.unifiedOrder(map);
        System.out.println(resultMap);
    }

}

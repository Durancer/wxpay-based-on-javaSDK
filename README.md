# Wxpay-based-on-javaSDK

#### 介绍
基于微信支付javaSDK的接口层封装，调用接口直接返回 JSAPI 调起支付所需参数。适用于 微信小程序JSAPI 支付 及 扫码支付等场景。使用简单，不用理解太多的逻辑，在service层直接封装了签名过程，直接调用controller层接口。

#### 软件架构
本程序是基于微信提供的javaSDK，使用springboot进行的一个再封装。（已经设计好了controller层、service层等）封装后，跳过（封装）了各个签名算法和请求的过程。使用一个常量类来配置基本信息，灵活地给不同的用户使用。

#### 功能描述

仅需配置基本信息 如 appId 等四项信息 ，传入 openid 即可直接获取调起支付请求
支持JSAPI支付及扫码native等支付场景

目前封装两个接口分别对应以上两个功能
扫码支付返回 微信二维码转换码，在前端使用qrcode转化成图片进行显示即可进行支付

#### 使用条件

1. 需要企业单位才能使用，个人不行，因为需要申请对应的商户号、商户密钥等信息
2. 有一定 **Springboot** 基础
3. 仅使用 修改常量类相关的信息 及 serviceimpl 实现的 商品相关的信息即可使用


#### 使用说明

1. 需要通过企业信息申请所需相关资源

   ​	首页需要接入微信支付、关联相关小程序
    ![into](https://gitee.com/Durancer/wxpay-based-on-javaSDK/raw/master/src/main/resources/static/image/image-20220819100351753.png)
   

​			获取小程序  **AppId、AppSercet、商户号 MCH_ID 、API lv2 密钥** （自己前往微信支付平台设置的，如下图），并安装  **操作证书**。（此图来源于网络，侵权联系删！！！）

​			最后，需要在  **产品中心** ==>  **开发配置** 中配置  JSAPI  微信服务器通知接口（支付成功后微信会发送 return_code:SUCCESS 信息到改接口）此项没有配置也可进行微信支付，但微信服务器没有收到回复会持续发送信息

![20170925145901_33411](https://gitee.com/Durancer/wxpay-based-on-javaSDK/raw/master/src/main/resources/static/image/20170925145901_33411.jpg)

​		建议将开发人员拉入员工账号，操作如下，拉入后即可登录  **微信支付商家助手** 小程序查看账户信息

![emploer](https://gitee.com/Durancer/wxpay-based-on-javaSDK/raw/master/src/main/resources/static/image/image-20220819101641785.png)

到这里准备工作就完成了，接下来只需要将本项目导入，就能完成接口请求了。

#### 安装教程

1. 将本项目内容整合到你的项目当中

2. 配置这些常量信息
![image-20220819103017711](https://gitee.com/Durancer/wxpay-based-on-javaSDK/raw/master/src/main/resources/static/image/image-20220819103017711.png)

3. 进入WxpayServiceImpl.java 配置 金额 以及商品描述  及支付回调路径，跟在微信支付平台配置的一样（图倒2行）

   ![image-20220819103347435](https://gitee.com/Durancer/wxpay-based-on-javaSDK/raw/master/src/main/resources/static/image/image-20220819103347435.png)

 4. controller层使用，仅需在小程序端传入 用户的 **openid** 就可以辣

    ![image-20220819103604928](https://gitee.com/Durancer/wxpay-based-on-javaSDK/raw/master/src/main/resources/static/image/image-20220819103604928.png)

5. 最后 接口返回参数就是调起支付接口的参数，接下来就可以完成支付流程了。
    ![args](https://gitee.com/Durancer/wxpay-based-on-javaSDK/raw/master/src/main/resources/static/image/image-20220819104945582.png)
    
（如果是扫码支付的情况，由于我们在web端没有办法判断用户是否进行了支付，所以需要通过微信服务器通知进行回调之后的逻辑处理，可以参考下方我的博客）


如果是在微信开发者工具里做测试时，接口调起是提供扫码便于测试。真机调试时就是正常的支付。
#### 项目相关

- 项目地址：https://gitee.com/durancer/wxpay-based-on-javaSDK
- 作者主页：https://gitee.com/durancer    https://github.com/durancer
- 扫码支付回调处理：https://blog.csdn.net/programming132/article/details/126942491?spm=1001.2014.3001.5501
- 有相关问题可以发表 issue 或 评论

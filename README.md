# Wxpay-based-on-javaSDK

#### 介绍
基于微信支付javaSDK的接口层封装，调用接口直接返回 JSAPI 调起支付所需参数。适用于 微信小程序 等 JSAPI 支付场景。使用简单，不用理解太多的逻辑，帮助小白少走弯路。

#### 软件架构
本程序是基于微信提供的javaSDK，使用springboot进行的一个再封装。（已经设计好了controller层、service层等）封装后，跳过了各个签名算法和请求的过程。使用一个常量类来配置基本信息，灵活地给不同的用户使用。

#### 功能描述

仅需配置基本信息 如 appId 等四项信息 ，传入 openid 即可直接获取调起支付请求

#### 使用条件

1. 需要企业单位才能使用，个人不行
2. 需要有一定 **Springboot** 或 **SSM** 基础


#### 使用说明

1. 需要通过企业信息申请所需相关资源

   ​	首页需要接入微信支付、关联相关小程序

   ![image-20220819100351753](C:\Users\86188\AppData\Roaming\Typora\typora-user-images\image-20220819100351753.png)

​			获取小程序  **AppId、AppSercet、商户号 MCH_ID 、API lv2 密钥** （自己前往微信支付平台设置的，如下图），并安装  **操作证书**。（此图来源于网络，侵权联系删！！！）

​			最后，需要在  **产品中心** ==>  **开发配置** 中配置  JSAPI  微信服务器通知接口（支付成功后微信会发送 return_code:SUCCESS 信息到改接口）此项没有配置也可进行微信支付，但微信服务器没有收到回复会持续发送信息

![20170925145901_33411](C:\Users\86188\Desktop\20170925145901_33411.jpg)

​		建议将开发人员拉入员工账号，操作如下，拉入后即可登录  **微信支付商家助手** 小程序查看账户信息

![image-20220819101641785](C:\Users\86188\AppData\Roaming\Typora\typora-user-images\image-20220819101641785.png)

<img src="C:\Users\86188\Desktop\A2C8D93FA1BC450B84C31A135CDB484A.jpg" alt="A2C8D93FA1BC450B84C31A135CDB484A" style="zoom:25%;" />

到这里准备工作就完成了，接下来只需要将本项目导入，就能完成接口请求了。

#### 安装教程

1. 将本项目内容整合到你的项目当中

2. 配置这些常量信息![image-20220819103017711](C:\Users\86188\AppData\Roaming\Typora\typora-user-images\image-20220819103017711.png)

3. 进入WxpayServiceImpl.java 配置 金额 以及商品描述  及支付回调路径，跟在微信支付平台配置的一样（图倒2行）

   ![image-20220819103347435](C:\Users\86188\AppData\Roaming\Typora\typora-user-images\image-20220819103347435.png)

 4. controller层使用，仅需在小程序端传入 用户的 **openid** 就可以辣

    ![image-20220819103604928](C:\Users\86188\AppData\Roaming\Typora\typora-user-images\image-20220819103604928.png)

5. 最后 接口返回参数就是调起支付接口的参数，接下来就可以完成支付流程了。

   <img src="C:\Users\86188\AppData\Roaming\Typora\typora-user-images\image-20220819104945582.png" alt="image-20220819104945582" style="zoom:50%;" />

#### 项目相关

- 项目地址：https://gitee.com/ITzhboy/wxpay-based-on-javaSDK
- 作者主页：https://gitee.com/ITzhboy/    https://github.com/ITzhboy/
- 有相关问题可以发表 issue 或 评论

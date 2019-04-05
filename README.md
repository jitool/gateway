# gateway管理

#### 介绍
Api-gateway+可视化操作管理平台，最初做该项目的想法是在公司中搭建了这么一个基于本公司业务特点的网关，做完之后突然心血来潮，为什么不做一个更公用化的网关系统呢？于是我开始搞了这么一个项目，第一次做开源的东西，有很多不足，希望大家批评指正。<br>
<br>
项目中遇到了好些spring cloud gateway的坑，网上找不到好的解决方案，最后只能通过读源码，不停的debug。为了能正确读取请求体，项目中使用了attribute进行缓存。灰度路由参考了ribbon-discovery-filter-spring-cloud-starter进行改写。顺便说一句：抄源码很舒服。

#### 项目特点
1.基于spring cloud gateway框架做的功能扩展<br>
2.使用ruoyi管理系统做的可视化，若依项目链接：https://gitee.com/y_project/RuoYi<br>
3.考虑到gateway会集群部署，考虑到不增加额外的组件，所以使用redis topic进行gateway节点动态刷新<br>
4.支持多种限流策略（ip、服务id等）、参数验证、权限校验、灰度路由、swagger文档聚合等<br>
5.管理系统动态刷新网关配置基于spring事件监听机制

#### 项目结构
  **_整体结构说明_**  :<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/222816_b1a1b14f_1505497.png "TIM图片20190405222806.png")<br>
 **gateway项目说明** <br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/223516_1d15fc58_1505497.png "TIM图片20190405223506.png")<br>


#### 使用说明

一、如何开始本项目？<br>
答：1.将下图中sql导入<br>
    ![输入图片说明](https://gitee.com/uploads/images/2019/0405/220859_aab976d9_1505497.png "屏幕截图.png")<br>
    2.配置ruoyi项目的mysql和redis<br>
    ![输入图片说明](https://gitee.com/uploads/images/2019/0405/224453_a862b3bc_1505497.png "屏幕截图.png")<br>
    3.配置gateway项目mysql、redis和zookeeper地址<br>
    ![输入图片说明](https://gitee.com/uploads/images/2019/0405/224755_4385ec01_1505497.png "屏幕截图.png")<br>
    4.启动ruoyi-admin和executor-api-gateway<br>
    5.输入http://localhost:8090/gateway/login进入管理界面，默认账号：admin 密码：admin<br>

二、如何进入swagger聚合界面？<br>
答：localhost:8081/swagger-ui.html<br>

三、如何进行线上gateway动态配置?<br>
答：在开始这个操作前，请先了解spring cloud gateway的filter和predicate运转机制，最好先看一下官方文档，如已了解清楚，请看以下操作。<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/225533_18bf2b94_1505497.png "屏幕截图.png")<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/225819_fe33c00a_1505497.png "屏幕截图.png")<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/225850_f59e3242_1505497.png "屏幕截图.png")<br>

四、如何配置灰度路由策略？<br>
答：由于spring cloud对服务发现实例的父类（com.netflix.loadbalancer.Server）缺乏足够的抽象，所以在本项目中只实现了zk转发,基本思路是在后端服务添加metainfo,如下图：<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/230715_b2845525_1505497.png "屏幕截图.png")<br>
然后配置指定的请求头key,如下图：<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/230912_ffb0b2ec_1505497.png "屏幕截图.png")<br>
此时通过gateway请求，只会路由到指定版本号的后端服务，如下图这样操作：<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/231053_a7c568ec_1505497.png "屏幕截图.png")<br>

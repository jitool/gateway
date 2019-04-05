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

#### 项目结构
  **_整体结构说明_**  :<br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/222816_b1a1b14f_1505497.png "TIM图片20190405222806.png")<br>
 **gateway项目说明** <br>
![输入图片说明](https://gitee.com/uploads/images/2019/0405/223516_1d15fc58_1505497.png "TIM图片20190405223506.png")<br>


#### 使用说明
 如何开始本项目？<br>
答：1.![输入图片说明](https://gitee.com/uploads/images/2019/0405/220859_aab976d9_1505497.png "屏幕截图.png")将图中sql导入<br>
    2.
 如何进入swagger聚合界面？<br>
答：ip+端口号/swagger-ui.html<br>


#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request1. 
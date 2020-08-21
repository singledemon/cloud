## 平台简介

* 采用前后端分离的模式，微服务版本。
* 后端采用Spring Boot、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos。
* 流量控制框架选型Sentinel。


## 系统模块

~~~
oumengCloud     
├── oumeng-gateway         // 网关模块 [8080]
├── oumeng-auth            // 认证中心 [8081] 引入登录鉴权中台
├── oumeng-common          // 通用模块 用于其他模块引用
│       └── oumeng-common-core                         // 核心模块
│       └── oumeng-common-redis                        // 缓存服务
│       └── oumeng-common-security                     // 安全模块
│       └── oumeng-common-swagger                      // 系统接口
├── oumeng-biz            // 业务模块
│       └── oumeng-biz-crm         //crm系统    [8010] 
├── oumeng-file            // 文件管理模块       [8011]
├── oumeng-job             // 任务调度模块 
      └── xxl-job           // 任务调度      [8012]
      └── xxl-job-admin      // 任务调度管理  [8013] 
├── oumeng-search          // 搜索查询模块       [8014]
├── oumeng-sms             // 消息通知模块       [8015]
├── oumeng-receiver        // 第三方系统对接模块  [8016]
└── oumeng-report             // 报表服务模块       [8017]
├── oumeng-visual          // 图形化管理模块
│       └── oumeng-visual-monitor                     // 监控中心 [8018]
│       └── oumeng-code-generator                     // 代码生成 [8019]
│       └── oumeng-sentinel-dashboard                 // 流量高可用 [8020]
├──pom.xml                // 公共依赖
~~~

## 服务中间件

~~~
服务注册&&配置中心  nacos 端口号：8848
缓存存储           redis 端口号：6379
消息中间件         rabbitmq 端口号：5762
~~~

## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 系统接口：根据业务代码自动生成相关的api接口文档。
14. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
15. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 自动化构建和部署

采用 Jenkins + docker registry + Docker的方式


#### 监控
利用Spring Boot Admin 来监控各个独立Service的运行状态；

#### 负载均衡
将服务保留的rest进行代理和网关控制，除了平常经常使用的node.js、nginx外，Spring Cloud ribbon，可以帮我们进行正常的网关管控和负载均衡。

#### 服务注册与调用
基于Nacos来实现的服务注册与调用，在Spring Cloud中使用Open Feign, 我们可以做到使用HTTP请求远程服务时能与调用本地方法一样的编码体验，开发者完全感知不到这是远程方法，更感知不到这是个HTTP请求。

### 任务调度
xxl-job  xxl-jobAdmin


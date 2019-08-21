# free-meeting-room
SpringBoot項目的種子項目
### 项目使用说明
### 统一包命名规范 代码编写规范（阿里代码规约） idea或者eclipse安装P3C插件  数据库表对应的entity实体不能同意暴露前端  使用包装对象DTO或者VO 

### 设计的后端技术点以及编码建议
* 项目使用lombok 建议idea或者eclipse安装 lombok插件 否则编译报错
* generateCode 已经在test方法中配置 可以定制生成controller service mapper 按需进行修改
* Redis的包已经导入 没有进行配置 如需配置可以在Config下RedisConfig配置
* 集成开发环境中建议安装P3C代码扫描插件 规范代码格式
* 后续设计定时任务的配置 我会在congfig下配置 
* mq在项目中并未配置 按需配置 

后端可能设计的技术框架
- guava
- poi
- fastjson
- gson
- druid
- mybatis

### 注意在使用一些工具类的操作时 不要重复造轮子 guava apache common下可能已经含有相应的方法
- 对字符串的判空或者对对象的判空 
- Preconditions.checkNotNull(T,errMsg);
- Preconditions.checkNotNullOrBlank(string,errmsg);
- 属性copy等等操作 必要重复造轮子

### 项目中业务逻辑层的异常统一向外抛出 全局异常会捕获 参考ServiceRuntimeException


### URL命名规范

URI 表示资源，资源一般对应服务器端领域模型中的实体类。

1. 不用大写；
2. 用中杠`-`不用下杠`_`；
3. 参数列表要encode；
4. URI中的名词表示资源集合，使用复数形式；

#### 标准URL命名

> http://{IP}:{PORT}/{服务缩写}/{资源名称}/**

标准URL主要为平台前端调用接口，基于shiro/pac4j进行安全控制。

举例：

- POST http://{IP}:{PORT}/bd/user/add
- POST http://{IP}:{PORT}/verify/invoice
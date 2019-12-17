###Springboot Demo
####集成mybatis-plus、redis、jasypt、P6spy、dynamic-datasource、lombok、freemarker等技术

####1、准备好一个空的springboot项目，并引入pom依赖：mybatis-plus-boot-starter（在mybatis的基础上做的增强，只增不改）、mybatis-plus-generator（代码生成器）、freemarker依赖（生成器的模板）、lombok（自动生成例如getter、setter、tostring等代码）、p6spy（打印sql语句及其执行时间）以及数据库驱动mysql-connector-java和spring-boot-starter-jdbc；
####2、配置数据源，这里使用mysql，且使用jasypt-spring-boot-starter对敏感配置信息进行加密，步骤为：引入jar：jasypt-spring-boot-starter，使用工具类Jasypt.java生成密文，在x.yml（使用ENC(密文)的形式）或xxxApplication.java或在configuration中配置秘钥，填写敏感信息
####3、在mp util包中准备好代码生成器的相关代码（CodeGenerator.java），设置好项目路径等，执行，获得自动生成好的controller、entity、mapper、service；
####4、配置mybatis-plus分页插件、乐观锁插件和攻击SQL阻断解析器（MybatisPlusConfig.java）,并在resources下放置spy.properties文件，同时写好对应的P6spy.java文件，以配置SQL输出格式
####5、修改自动生成的entity类,例如给主键字段加上@TableId(type = IdType.AUTO)，给自动填充字段加上@TableField(fill = FieldFill.INSERT)等注解
####6、编写字段值自动填充插件MyMetaObjectHandler.java，以便在insert时，为不能为NULL的字段自动赋值
####7、配置动态数据源，实现主从模式，主数据库实现增删改，从数据库用来查询。先引入dynamic-datasource-spring-boot-starter包，然后在.yml文件中配置spring多数据源，在serviceImpl等地方使用@DS（“数据源名”），切换数据源
####8、实现redis缓存功能：引入spring-boot-starter-data-redis，.yml文件中配置spring.cache的缓存方式为redis，配置redis（RedisTemplateConfig.java），实现redis缓存方法（RedisCache.java & RedisCacheManager.java），实现redis订阅服务：在resources/redis下放置listener配置，然后在xxapplication.java中import，最后编写MsgListener.java实现订阅服务
####9、在service中编写业务逻辑，在controller中实现相关功能
####10、添加spring security支持，步骤:1、pom引入spring-boot-starter-security,2、配置WebSecurityConfig类等，3、准备好相应界面和控制器
####11、热部署：1、引入spring-boot-devtools，2、setting中勾选Build project automatically，3、ctrl + shift + alt + /，registry中勾选Compiler autoMake allow when app running即可

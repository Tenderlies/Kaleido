# SpringBoot

## 配置

| 功能                | 配置                                               | 值                                                     |
| ------------------- | -------------------------------------------------- | ------------------------------------------------------ |
| 生产随机实例ID      | spring.application.instanceId                      | ${random.int[1000,9999]}                               |
| DTO属性为空不传递   | spring.jackson.default-property-inclusion          | non_null                                               |
| 默认日志级别        | logging.level.root                                 | warn                                                   |
| 控制台日志格式      | logging.pattern.console                            | "[%p]-[%d{MM-dd HH:mm:ss.SSS}]-[%t]-[%c{32}.%M]: %m%n" |
| 配置文件地址        | logging.config                                     | classpath:logback.xml                                  |
| Mybatis下划线转驼峰 | mybatis.configuration.map-underscore-to-camel-case | true                                                   |
| mp控制台打印SQL语句 | mybatis-plus.configuration.log-impl                | org.apache.ibatis.logging.stdout.StdOutImpl            |

### 日志格式说明

| pattern                      | detail     |
| ---------------------------- | ---------- |
| %p                           | 日志级别   |
| %m                           | 日志内容   |
| %n                           | 换行       |
| %d{yyyy-MMM-dd HH:mm:ss.SSS} | 日期格式   |
| %c(32) / %logger{80}         | 类路径     |
| %M                           | 方法名     |
| %F                           | 文件名     |
| %L                           | 行号       |
| %t                           | 线程名     |
| %cn                          | 上下文名称 |

## 引入

### 参数校验

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### 工具集 -文件、流、加密解密、转码、正则、线程、XML

```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.3.9</version>
</dependency>
```

### 汉字转拼音

```xml
<dependency>
    <groupId>com.belerweb</groupId>
    <artifactId>pinyin4j</artifactId>
    <version>2.5.1</version>
</dependency>
```

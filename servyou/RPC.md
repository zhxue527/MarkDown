[TOC]

# RPC理论

## RPC概念

1. 是什么？
   RPC：Remote Procedure Call，远程过程调用。
   - Remote - 跨服务、进程、物理机
   - Procedure - 一段可执行代码
   - Call - 调用
   - 是分布式系统常见的一种通信方法
2. 做什么？
   提供跨进程交互形式：RESTful、WebService、HTTP、基于DB做数据交换、基于MQ做数据交换，以及RPC。
   - 依赖存储中间件做数据交互：
     - MySQL、RabbitMQ、Kafka、Redis
     - 两个系统是异步的
   - 直接交互：
     - RESTful、WebService、RPC、HTTP
     - 客户端与服务端同步
3. 专业术语

## 现有框架对比

|gRPC|thrift|RMI|dubbo|HadoopRPC
--|--|--|--|--|--
开发语言|多语言|多语言|Java|Java|Java
序列化|protobuf|thrift格式|Java序列化|hession2|R/Writable
注册中心|×|×|JDK自带|Zk等|×
跨语言|√|√|×|×|×
服务定义|Protobuf文件|Thrift文件|Java接口|Java接口|Java接口
服务治理|×|×|×|√|√

## 原理剖析

![RPC整体架构](D:\Me\career\MarkDown\photo\RPC整体架构.png)
![RPC_Call过程](D:\Me\career\MarkDown\photo\RPC_Call过程.png)

## 技术栈介绍

1. 基础知识
   JavaCore、Maven、反射
2. 动态代理（生成client存根实际调用对象）
   Java的动态代理
3. 序列化（Java对象与二进制数据互转）
   fastjson
4. 网络通信（传输序列化后的数据）
   jetty、URLConnection

# RPC实战 - 代码实现

## 一：建工程

### 项目创建

1. Create New Project
2. maven -> jdk1.8
3. group：com.servyou -> Artificats：demoRPC
4. location：…/servyou/demoRPC

### 项目结构搭建

New -> Module…

1. demoRPC-common：通用工具方法模块
2. demoRPC-proto：协议模块
3. demoRPC-codec：序列化模块
4. demoRPC-transport：网络模块
5. demoRPC-server
6. demoRPC-client

### maven依赖配置

#### 公共依赖

所有模块都需要的依赖

声明在<dependencies> </dependencies>中

1. junit:4.12
2. lombok:1.18.8
3. slf4j-api:1.7.26
4. logback-classic:1.2.3

#### 依赖声明

子模块可能用到的依赖

声明在<dependencyManagement> </dependencyManagement>中

1. commons-io:2.5
2. jetty-servlet:9.4.19.v20190610
3. fastjson:1.2.44

### 工程编译版本

```java
<build>
    <plugins>
    	<plugin>
    		<groupId>org.apache.maven.plugins</groupId>
    		<artifactId>maven-compiler-plugin</artifactId>
    		<version>3.3</version>
    		<configuration>
    			<source>1.8</source>
    			<target>1.8</target>
    		</configuration>
    	</plugin>
    </plugins>
</build>
```

### 硬编码转properties

```java
<properties>
    <java.version>1.8</java.version>
    <commons.version>2.5</commons.version>
    <jetty.version>9.4.19.v20190610</jetty.version>
    <fastjson>1.2.44</fastjson>
    <lombok.version>1.18.8</lombok.version>
    <slf4j.version>1.7.26</slf4j.version>
    <logback.version>1.2.3</logback.version>
    <junit.version>4.12</junit.version>
</properties>
```



### lombok插件

1. File -> Settings…：ctrl + alt + S
2. Plugins -> lombok -> Install
3. Build, Execution, Deployment -> Compiler -> Annotation Processors -> √ Enable annotation processing

## 二：proto模块

## 三：common模块

## 四：codec模块

## 五：transport模块 

## 六：Server模块

## 七：Client模块

## 八：gk-rpc使用案例
[TOC]

# RPC理论

## RPC概念

1. 是什么？
   RPC：Remote Procedure Call，远程过程调用。
   - Remote - 跨服务、进程、物理机
   - Procedure - 一段可执行代码
   - Call - 调用
   - 是分布式系统常见的一种通信方法
   - 远程调用：
     - 把进程内部的部分逻辑放到其他机器上去执行，即业务拆解。
     - 让每个服务仅对单个业务负责，使得服务具备独立的可扩展性、可升级性、易维护性
   - 过程调用：
     - 程序内控制和数据的传输
   
2. 做什么？
   
   提供跨进程交互形式：RESTful、WebService、HTTP、基于DB做数据交换、基于MQ做数据交换，以及RPC。
   
   - 依赖存储中间件做数据交互：
     - MySQL、RabbitMQ、Kafka、Redis
     - 两个系统是异步的
   - 直接交互：
     - RESTful、WebService、RPC、HTTP
     - 客户端与服务端同步
   
   > RPC将Client和Server通信细节封装，Client提供调用方法名、参数、返回值信息，Server解析报文，执行对应方法然后将返回值返回
   
3. 为什么要使用RPC？
   用于建立分布式计算（服务）

4. 专业术语

   - Client：服务调用方，作用是通过Client-stub向服务提供方Server发起调用，并且接受从Client-stub返回的调用结果；
   - Client-stub：
     - 服务调用方的本地存根对象，是一个可执行体；
     - 类似于反射获得的invoke；
     - 作用是①将需要远程调用的接口、方法及参数通过约定好的协议进行序列化，②将序列化的数据通过RpcRuntime对象进行传输，③将服务提供方的返回值序列化为Client可以直接使用的对象；
   - RpcRuntime：远程调用运行时的对象，存在于服务双方。作用是建立起双方的连接，以便进行远程通信；
   - Server-stub：
     - 服务提供方的本地存根对象；
     - 作用是①将从RpcRuntime中读取的数据进行反序列化，②调用本地方法，③把返回值进行序列化传输到RpcRuntime进行发送；
   - Server：服务提供方，提供服务的业务逻辑，Server-stub调用的本地方法就是Server中的方法。

5. RPC、分布式本身的问题：

   - 通信延迟
   - 地址空间被隔离
   - 局部故障：发生的概率变高，定位变难
   - 并发问题：多个服务调一个服务

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

### 服务暴露和引用过程

![RPC整体架构](D:\Me\career\MarkDown\photo\RPC整体架构.png)

1. 服务暴露
   服务暴露过程就是将服务绑定到端口（用作Client调用服务的地址）的过程。
   - 暴露到本地：用于业务划分，让服务职责划分变清晰
   - 暴露到远程：使得 **host:port** 作为服务的唯一标识。
     - 直接暴露式：创建接口实现类代理，绑定至端口，等待调用
     - 注册中心式：创建接口实现类代理，绑定至端口，同时注册到注册中心
2. 服务引用
   服务引用过程就是Client和Server服务建立连接的过程。
   大致分为2个步骤：①连接Server；②创建接口代理
   - 直连式：Client直接根据**host:port**连接远程服务。一般用于服务测试；
   - 注册中心式：Client通过注册中心来获取**host:port**，然后再进行服务调用；这种方式还可以让注册中心保存Client的**host:port**用于及时通知Client更新服务地址变更

### 方法调用过程

完成服务暴露和引用后，就可以在Client利用代理对象发起方法调用。

![RPC_Call过程](https://raw.githubusercontent.com/zhxue527/MarkDown/master/photo/RPC_Call%E8%BF%87%E7%A8%8B.png)

1. Client执行Server的一个方法：必须传输 ①方法的全限定名 以及 ②方法参数列表 给Server
2. Client和Server传输的请求包按照约定好的统一序列化协议进行序列化
3. Client将序列化后的数据通过RpcRuntime进行传输，RpcRuntime进行等待\监听
4. Server通过RpcRuntime接收请求包，传递给Server-stub；
5. Server-stub ①用统一的序列化协议进行反序列化，解析请求数据，②根据解析的数据来定位需要调用的具体方法，将方法参数列表传递给Server；
6. Server 执行对应的方法，并返回执行结果给 Server-stub；
7. Server-stub 将结果序列化后传递给RpcRuntime；
8. Server 的 RpcRuntime 将返回结果发送给 Client 的 RpcRuntime；
9. Client 的 RpcRuntime 把接收到的数据传输给 User-stub；
10. User-stub 将返回结果反序列化后传递给最初的调用方，继续后续逻辑处理。

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

```java
/**
 * @PackageName: com.pill
 * @ClassName: Peer
 * @Description: 表示网络传输的端点
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
@AllArgsConstructor
public class Peer {
    private String host;
    private int port;
}
```

```java
/**
 * @PackageName: com.pill
 * @ClassName: Request
 * @Description:
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
public class Request {
    private ServiceDescriptor service;
    private Object[] parameters;
}
```

```java
/**
 * @PackageName: com.pill
 * @ClassName: Response
 * @Description: 表示RPC返回
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
public class Response {
    /***
     * 服务返回编码：0-成功，非0失败
     */
    private int code = 0;
    /***
     * 具体的错误信息
     */
    private String message = "OK";
    /***
     * 返回的数据
     */
    private Object data;
}
```

```java
/**
 * @PackageName: com.pill
 * @ClassName: ServiceDescriptor
 * @Description: 表示服务
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;
}
```



## 三：common模块

反射工具类

```java
public class ReflectionUtils {
    /**
     * 根据class创建对象
     * @param clazz 待创建对象的类
     * @param <T>   对象类型
     * @return  创建好的对象
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取某个class的公有方法
     * @param clazz 待获取方法的类
     * @return 当前类声明的公有方法
     */
    public static Method[] getPublicMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pMethods = new ArrayList<>();
        for (Method m: methods
             ) {
            if (Modifier.isPublic(m.getModifiers())) {
                pMethods.add(m);
            }
        }
        return pMethods.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的指定方法，并返回结果
     * @param obj 调用的对象
     * @param method 调用的方法
     * @param args 方法的参数
     * @return 返回值
     */
    public static Object invoke(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
```



## 四：codec模块

## 五：transport模块 

## 六：Server模块

## 七：Client模块

## 八：gk-rpc使用案例

## 概述
**Service-Agent**模块是为第三方开发者提供的SDK。该模块封装了**Service-Bus**对外提供的开放接口的常用服务。利用该模块，开发者可以方便的进行第三方服务的注册、用户用户组管理、基于OAuth2.0授信、设备对象信息获取以及对象Stub调用等功能。

## 模块列表
### 用户用户组
> 该模块提供对用户用户组的基本操作。包括用户的注册、修改；以及用户组的添加、删除、关联等操作。

### 第三方服务注册
> 该模块提供第三方服务的注册功能。开发者能够提供该模块在Anicloud 平台注册自己的应用（服务）。平台会为该应用颁发唯一的ID以及client_secret。

### 设备对象（DeviceObj）信息获取
> 该模块提供对DeviceObj的信息查询功能。通过用户授信的第三方应用可以利用该接口获取用户的设备状态信息、设备Stub 列表信息等。

### 基于OAuth2.0 的授信
> 该模块提供对OAuth2.0 协议的流程封装，提供该模块可以方便地进行access_token的获取以及token的刷新操作。该模块提供了对**Authorization Code**、**Password**、**Implicit**三种授信模式的支持。

### 对象Stub调用
> 该模块提供了对象Stub调用的接口**AniInvokable**。对Stub的调用提供了同步和异步两种方式。异步的调用需要开发者实现消息结果解析的观察者接口**MessageObserver**，利用该接口来处理异步调用的结果。同时也提供了平台对应用的反向调用接口**ClientInvokable**，该接口需要由应用的开发者实现。

## 设计实现

### 技术选择
* 协议WebSocket、Http、OAuth2.0
* 设计模式：观察者、工厂、单例
* Spring RestTemplate 提供对Http 接口的访问

### 系统配置
anicel_meta.properties 文件是**Servuce-Agent**的核心配置文件。主要包括**ServiceBus**的各类资源地址。当**ServiceBus**的地址发生改变时，可以对该文件进行编辑。

### 核心类
* __AnicelMeta__ 提供对anicel_meta.properties 文件的读取操作，并作为**Service-Agent**的核心配置类存在。Http和Websocket的实现都依赖于该类。
* __DomainObjectValidator__ 提供对Bean Valication的实现。采用的是Hibernate Validation 实现。

### 核心业务类
* __AccountService__
* __AccountGroupService__
* __AniServiceManager__
* __DeviceObjService__
* __AniOAuthService__
* __AccountInvoker__
* __AniInvokable__
* __ClientInvokable__
* __AgentTemplate__

### 外部接口
* __MessageObserver__

### 开发者文档

## 部署要求

## 参考

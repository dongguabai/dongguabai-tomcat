# Dongguabai-Tomcat
【学习】自己动手写一个 Tomcat

## 版本更新日志
### 2024-01-27

- 新增
  - HTTP 基本请求/响应
### 2024-01-28

- 重构
  - 基础组件划分
    - `Server`：负责初始化和启动服务器，管理服务器的生命周期
    - `Config`：负责加载和管理服务器的配置信息
    - `Engine`：负责处理 HTTP 请求，包括路由请求到正确的 Servlet，并管理 Servlet 和 Filter 的生命周期。
    - `Connector`：负责接受和处理连接
  
- 新增
  - `Session` 支持


### 2024-01-29

- 新增

  - `Filter` 支持

  * NIO 支持

* 重构
  * 重构 `Config` 以支持后续整合 Spring

### 2024-01-30

- 新增
  - `Filter` 、`Servlet` 支持顺序
  - 增加两种启动方式：`ServerConfigBuilder`

* 重构
  * 重构 `Config` 以支持后续整合 Spring

# Dongguabai-Tomcat
【学习】自己动手写一个 Tomcat

## 版本更新日志
### 2024-01-27

- 新增：HTTP 基本请求/响应
### 2024-01-28

- 重构：基本组件划分
  - `Server`：负责初始化和启动服务器
  - `Config`：负责加载服务器的配置
  - `Engine`：负责处理 HTTP 请求
  - `Connector`：这个类负责接受和处理连接



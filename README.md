# Mini-Tomcat
目标：开发一个用于学习的高性能 Servlet Web 容器。

## 核心组建
* Tomcat：负责启动服务器和处理客户端连接。
* ThreadPoolManager：负责管理线程池。
* ServletManager：负责管理 Servlet 映射。
* PropertiesLoader：负责加载配置。


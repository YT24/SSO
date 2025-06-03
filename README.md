# SSO 单点登录系统

## 项目简介
本项目是基于Spring Boot实现的单点登录（SSO）系统，支持OAuth2.0授权码模式和OpenID Connect（OIDC）协议，适用于多系统统一认证场景。

## 主要功能
- OAuth2.0授权码模式认证与授权
- OIDC用户信息获取
- Access Token、Refresh Token管理
- Token校验与刷新
- 用户登录、授权、回调处理
- API文档自动生成（Knife4j）

## 技术栈
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.3.1
- MySQL 8.x
- Redis
- JWT (jjwt)
- Knife4j（API文档）
- Lombok
- ZXing（二维码）

## 快速启动
1. **准备环境**
   - JDK 1.8+
   - MySQL 8.x（默认端口3300，数据库名`sso`，用户名/密码均为root，可在`application.yml`中修改）
   - Redis（默认localhost:6379）
2. **配置数据库和Redis**
   - 修改`src/main/resources/application.yml`中的数据库和Redis连接信息（如有需要）
3. **构建与运行**
   ```bash
   mvn clean package
   java -jar target/sso-1.0-SNAPSHOT.jar
   ```
4. **访问API文档**
   - 访问 [http://localhost:8080/doc.html](http://localhost:8080/doc.html) 查看接口文档（Knife4j）

## 主要接口
- `GET /oauth2/authorize`：获取授权码
- `POST /oauth2/token`：获取access_token
- `GET /oauth2/userinfo`：获取用户信息（OIDC）
- `GET /oauth2/check_token`：校验token有效性
- `POST /oauth2/refresh_token`：刷新token
- `POST /oauth2/login`：用户名密码登录并跳转授权
- `GET /oauth2/callback`：SSO回调处理

详细参数和流程请参考 [doc/oauth2_authorization_code_api.md](doc/oauth2_authorization_code_api.md)

## 配置说明
主要配置文件：`src/main/resources/application.yml`
- 服务端口：`server.port`（默认8080）
- 数据库：`spring.datasource.*`
- Redis：`spring.redis.*`
- SSO相关：`sso.*`、`oauth2.*`

## 目录结构
```
├── src/main/java/com/auth/sso
│   ├── controller      # 控制器（接口层）
│   ├── service         # 业务逻辑
│   ├── entity          # 实体类
│   ├── mapper          # MyBatis-Plus数据访问
│   ├── config          # 配置类
│   └── ...
├── src/main/resources
│   ├── application.yml # 配置文件
│   └── ...
├── doc                # 接口与设计文档
│   └── oauth2_authorization_code_api.md
├── pom.xml            # Maven依赖管理
└── README.md
```

## 文档与参考
- [OAuth2.0 RFC6749](https://datatracker.ietf.org/doc/html/rfc6749)
- [OIDC 官方文档](https://openid.net/specs/openid-connect-core-1_0.html)
- [doc/oauth2_authorization_code_api.md](doc/oauth2_authorization_code_api.md)

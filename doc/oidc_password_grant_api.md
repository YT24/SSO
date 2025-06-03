# OIDC Password模式单点登录对接文档

## 1. 概述
本接口文档描述如何通过OIDC协议的password模式实现单点登录（SSO），包括token获取、id_token验签与解析等流程。

---

## 2. 获取Token（Password模式）

### 请求地址
```
POST /oidc/token
```

### 请求参数
| 参数名       | 类型   | 必填 | 说明           |
| ------------ | ------ | ---- | -------------- |
| username     | string | 是   | 用户名         |
| password     | string | 是   | 密码           |
| client_id    | string | 是   | 客户端ID       |
| client_secret| string | 是   | 客户端密钥     |
| scope        | string | 否   | 授权范围，建议填写openid |

### 请求示例
```
POST /oidc/token
Content-Type: application/x-www-form-urlencoded

data: username=youruser&password=yourpass&client_id=xxx&client_secret=xxx&scope=openid
```

### 响应参数
| 字段           | 类型   | 说明                 |
| -------------- | ------ | -------------------- |
| access_token   | string | 访问令牌             |
| token_type     | string | 类型，固定为Bearer   |
| refresh_token  | string | 刷新令牌             |
| expires_in     | int    | access_token有效期（秒）|
| id_token       | string | OIDC身份令牌（JWT）  |

### 响应示例
```
{
  "code": 0,
  "msg": "success",
  "data": {
    "access_token": "xxxxxx",
    "token_type": "Bearer",
    "refresh_token": "xxxxxx",
    "expires_in": 3600,
    "id_token": "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoic3NvLWNsaWVudCIsImlzcyI6Imh0dHA6Ly8xMjcuMC4wLjE6ODA4MC9vZGljL2p3a3MiLCJpYXQiOjE3NDg5NjIxMDMsImV4cCI6MTc0ODk2NTcwMywidXNlcm5hbWUiOiJyb290In0.GK..."
  }
}
```

---

## 3. id_token说明与验签

- id_token为JWT（RS256签名），包含用户身份信息。
- 主要字段：
  - `sub`：用户唯一标识
  - `aud`：客户端ID
  - `iss`：签发者（issuer）
  - `iat`：签发时间（时间戳）
  - `exp`：过期时间（时间戳）
  - `username`：用户名

### 验签流程
1. 通过 `/oidc/jwks` 获取公钥（JWK格式）。
2. 使用 [https://8gwifi.org/jwkconvertfunctions.jsp](https://8gwifi.org/jwkconvertfunctions.jsp) 或相关工具将JWK转换为PEM格式公钥。
3. 在 [jwt.io](https://jwt.io/) 粘贴id_token和PEM公钥，可验证签名。

#### 公钥获取示例
```
GET /oidc/jwks
```
返回：
```
{
  "keys": [
    {
      "kty": "RSA",
      "alg": "RS256",
      "use": "sig",
      "n": "...",
      "e": "AQAB",
      "kid": "sso-key-1"
    }
  ]
}
```

---

## 4. id_token解析接口

### 请求地址
```
GET /oidc/parse_id_token?id_token=xxx
```

### 响应示例
```
{
  "code": 0,
  "msg": "success",
  "data": {
    "sub": "1",
    "aud": "sso-client",
    "iss": "http://127.0.0.1:8080/odic/jwks",
    "exp": "2024-xx-xx xx:xx:xx",
    "iat": "2024-xx-xx xx:xx:xx",
    "username": "root"
  }
}
```

---

## 5. 常见问题
- password模式仅适用于自有App或高度信任场景，不推荐用于第三方Web应用。
- id_token必须用JWT标准库解析，且需用服务端公钥验签。
- 密钥对如未持久化，服务重启后公钥会变化，需重新获取。

---

## 6. 参考
- [OIDC 官方文档](https://openid.net/specs/openid-connect-core-1_0.html)
- [jwt.io](https://jwt.io/)
- [JWK转PEM工具](https://8gwifi.org/jwkconvertfunctions.jsp) 
# OAuth2.0 授权码模式接入接口文档

## 1. 授权码获取接口

### 请求地址
```
GET /oauth2/authorize
```

### 请求参数

| 参数名         | 类型   | 必填 | 说明                       |
| -------------- | ------ | ---- | -------------------------- |
| client_id      | string | 是   | 客户端ID                   |
| redirect_uri   | string | 是   | 授权码回调地址             |
| response_type  | string | 是   | 固定为"code"               |
| scope          | string | 否   | 授权范围（如 openid profile）|
| state          | string | 否   | 防CSRF攻击的随机字符串     |
| user_id        | long   | 是   | 用户ID（实际应由登录态获取）|

### 响应

- **302 重定向**  
  成功后重定向到 `redirect_uri`，并携带授权码和state参数：

```
HTTP/1.1 302 Found
Location: http://localhost:8081/callback?code=xxxxxx&state=xxxxxx
```

---

## 2. 令牌获取接口

### 请求地址
```
POST /oauth2/token
```

### 请求参数（application/x-www-form-urlencoded）

| 参数名         | 类型   | 必填 | 说明           |
| -------------- | ------ | ---- | -------------- |
| code           | string | 是   | 授权码         |
| client_id      | string | 是   | 客户端ID       |
| client_secret  | string | 是   | 客户端密钥     |
| redirect_uri   | string | 是   | 回调地址       |

### 响应

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "access_token": "xxxxxx",
    "token_type": "Bearer",
    "refresh_token": "xxxxxx",
    "expires_in": 3600,
    "id_token": "xxxxxx" // OIDC场景下返回
  }
}
```

---

## 3. 用户信息获取接口（OIDC）

### 请求地址
```
GET /oauth2/userinfo
```

### 请求头

| 名称           | 必填 | 说明                   |
| -------------- | ---- | ---------------------- |
| Authorization  | 是   | Bearer access_token    |

### 响应

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "username": "testuser",
    "nickname": "测试用户"
  }
}
```

---

## 4. 错误响应示例

```json
{
  "code": 401,
  "msg": "token无效或已过期",
  "data": null
}
```

---

## 5. 接入流程说明

1. **用户在第三方应用点击登录，跳转到SSO服务 `/oauth2/authorize`。**
2. **用户同意授权后，SSO服务重定向回`redirect_uri`，并带上`code`。**
3. **第三方应用用`code`请求 `/oauth2/token` 换取`access_token`、`id_token`等。**
4. **第三方应用用`access_token`请求 `/oauth2/userinfo` 获取用户信息。**

---

## 6. 参考

- [OAuth2.0 RFC6749](https://datatracker.ietf.org/doc/html/rfc6749)
- [OIDC 官方文档](https://openid.net/specs/openid-connect-core-1_0.html) 
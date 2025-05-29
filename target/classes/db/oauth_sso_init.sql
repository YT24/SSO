-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='用户表';

-- 客户端表
CREATE TABLE IF NOT EXISTS oauth_client (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    client_id VARCHAR(100) NOT NULL UNIQUE,
    client_secret VARCHAR(100) NOT NULL,
    redirect_uri VARCHAR(500) NOT NULL,
    scope VARCHAR(200),
    protocol VARCHAR(20) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='SSO客户端表';

-- 授权码表
CREATE TABLE IF NOT EXISTS oauth_code (
    code VARCHAR(100) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    client_id VARCHAR(100) NOT NULL,
    redirect_uri VARCHAR(500) NOT NULL,
    scope VARCHAR(200),
    expires_at DATETIME NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='授权码表';

-- Token表
CREATE TABLE IF NOT EXISTS oauth_token (
    access_token VARCHAR(200) PRIMARY KEY,
    refresh_token VARCHAR(200),
    user_id BIGINT NOT NULL,
    client_id VARCHAR(100) NOT NULL,
    scope VARCHAR(200),
    expires_at DATETIME NOT NULL,
    id_token VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='Token表'; 
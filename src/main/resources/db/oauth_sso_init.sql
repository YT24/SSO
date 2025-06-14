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
    app_name VARCHAR(100) NOT NULL COMMENT '应用名称',
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

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
    role_desc VARCHAR(100) COMMENT '角色描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT='角色表';

-- 角色-应用关联表
CREATE TABLE IF NOT EXISTS sys_role_app (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    client_id VARCHAR(100) NOT NULL COMMENT '应用client_id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_role_app (role_id, client_id),
    FOREIGN KEY (role_id) REFERENCES sys_role(id),
    FOREIGN KEY (client_id) REFERENCES oauth_client(client_id)
) COMMENT='角色-应用关联表';

-- 用户-角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_role (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id),
    FOREIGN KEY (role_id) REFERENCES sys_role(id)
) COMMENT='用户-角色关联表'; 
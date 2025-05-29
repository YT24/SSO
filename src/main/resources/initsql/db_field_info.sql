CREATE TABLE db_field_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    table_id BIGINT NOT NULL COMMENT '关联的表ID',
    field_name VARCHAR(100) NOT NULL COMMENT '字段名',
    field_type VARCHAR(50) NOT NULL COMMENT '字段类型',
    field_length INT COMMENT '字段长度',
    field_precision INT COMMENT '字段精度',
    field_scale INT COMMENT '小数位数',
    field_comment VARCHAR(500) COMMENT '字段注释',
    is_nullable TINYINT(1) DEFAULT 1 COMMENT '是否可为空：0-否，1-是',
    is_primary TINYINT(1) DEFAULT 0 COMMENT '是否主键：0-否，1-是',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    FOREIGN KEY (table_id) REFERENCES db_table_info(id),
    UNIQUE KEY uk_table_field (table_id, field_name)
) COMMENT '表字段信息表';
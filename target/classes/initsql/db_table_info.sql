CREATE TABLE db_table_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    table_name VARCHAR(100) NOT NULL COMMENT '表名',
    table_comment VARCHAR(500) COMMENT '表注释',
    database_name VARCHAR(100) NOT NULL COMMENT '数据库名称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    UNIQUE KEY uk_table_db (table_name, database_name)
) COMMENT '数据库表信息表';
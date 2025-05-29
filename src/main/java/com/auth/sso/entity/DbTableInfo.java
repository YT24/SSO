package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("db_table_info")
public class DbTableInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String tableName;
    
    private String tableComment;
    
    private String databaseName;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
} 
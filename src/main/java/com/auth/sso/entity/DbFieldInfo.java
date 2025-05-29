package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("db_field_info")
public class DbFieldInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long tableId;
    
    private String fieldName;
    
    private String fieldType;
    
    private Integer fieldLength;
    
    private Integer fieldPrecision;
    
    private Integer fieldScale;
    
    private String fieldComment;
    
    private Integer isNullable;
    
    private Integer isPrimary;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
} 
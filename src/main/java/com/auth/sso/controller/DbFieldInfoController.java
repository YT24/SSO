package com.auth.sso.controller;

import com.auth.sso.common.Result;
import com.auth.sso.entity.DbFieldInfo;
import com.auth.sso.service.DbFieldInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "数据库字段信息管理")
@RestController
@RequestMapping("/api/db/field")
public class DbFieldInfoController {

    @Autowired
    private DbFieldInfoService dbFieldInfoService;

    @ApiOperation("新增字段信息")
    @PostMapping
    public Result<Boolean> save(@RequestBody DbFieldInfo dbFieldInfo) {
        return Result.success(dbFieldInfoService.save(dbFieldInfo));
    }

    @ApiOperation("修改字段信息")
    @PutMapping
    public Result<Boolean> update(@RequestBody DbFieldInfo dbFieldInfo) {
        return Result.success(dbFieldInfoService.updateById(dbFieldInfo));
    }

    @ApiOperation("删除字段信息")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(dbFieldInfoService.removeById(id));
    }

    @ApiOperation("获取字段信息详情")
    @GetMapping("/{id}")
    public Result<DbFieldInfo> getById(@PathVariable Long id) {
        return Result.success(dbFieldInfoService.getById(id));
    }

    @ApiOperation("分页查询字段信息")
    @GetMapping("/page")
    public Result<Page<DbFieldInfo>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long tableId,
            @RequestParam(required = false) String fieldName) {
        
        LambdaQueryWrapper<DbFieldInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(tableId != null, DbFieldInfo::getTableId, tableId)
               .like(fieldName != null, DbFieldInfo::getFieldName, fieldName)
               .orderByAsc(DbFieldInfo::getFieldName);
        
        return Result.success(dbFieldInfoService.page(new Page<>(current, size), wrapper));
    }

    @ApiOperation("根据表ID查询字段列表")
    @GetMapping("/list/{tableId}")
    public Result<List<DbFieldInfo>> listByTableId(@PathVariable Long tableId) {
        LambdaQueryWrapper<DbFieldInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DbFieldInfo::getTableId, tableId)
               .orderByAsc(DbFieldInfo::getFieldName);
        return Result.success(dbFieldInfoService.list(wrapper));
    }
} 
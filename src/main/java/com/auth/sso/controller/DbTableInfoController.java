package com.auth.sso.controller;

import com.auth.sso.common.Result;
import com.auth.sso.entity.DbTableInfo;
import com.auth.sso.service.DbTableInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "数据库表信息管理")
@RestController
@RequestMapping("/api/db/table")
public class DbTableInfoController {

    @Autowired
    private DbTableInfoService dbTableInfoService;

    @ApiOperation("新增表信息")
    @PostMapping
    public Result<Boolean> save(@RequestBody DbTableInfo dbTableInfo) {
        return Result.success(dbTableInfoService.save(dbTableInfo));
    }

    @ApiOperation("修改表信息")
    @PutMapping
    public Result<Boolean> update(@RequestBody DbTableInfo dbTableInfo) {
        return Result.success(dbTableInfoService.updateById(dbTableInfo));
    }

    @ApiOperation("删除表信息")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(dbTableInfoService.removeById(id));
    }

    @ApiOperation("获取表信息详情")
    @GetMapping("/{id}")
    public Result<DbTableInfo> getById(@PathVariable Long id) {
        return Result.success(dbTableInfoService.getById(id));
    }

    @ApiOperation("分页查询表信息")
    @GetMapping("/page")
    public Result<Page<DbTableInfo>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String tableName,
            @RequestParam(required = false) String databaseName) {
        
        LambdaQueryWrapper<DbTableInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(tableName != null, DbTableInfo::getTableName, tableName)
               .like(databaseName != null, DbTableInfo::getDatabaseName, databaseName)
               .orderByDesc(DbTableInfo::getCreateTime);
        
        return Result.success(dbTableInfoService.page(new Page<>(current, size), wrapper));
    }
} 
package com.chenj.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置类
 * createTime:2020年3月7日20:56:29
 */

@Configuration
@MapperScan({"com.chenj.mall.mbg.mapper", "com.chenj.mall.dao"})
public class MybatisConfig {
}

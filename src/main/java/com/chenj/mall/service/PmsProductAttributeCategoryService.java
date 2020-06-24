package com.chenj.mall.service;

import com.chenj.mall.mbg.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * 商品类型service类
 * Created by chen on 2020/6/24
 */

public interface PmsProductAttributeCategoryService {

    /**
     * 添加商品类型
     */
    int createItem(String name);

    /**
     * 更改商品类型
     */
    int updateItem(Long id, String name);

    /**
     * 根据id查找商品类型
     */
    PmsProductAttributeCategory getItem(Long id);

    /**
     * 根据id删除商品类型
     */
    int deleteItem(Long id);

    /**
     * 分页查找商品类型
     */
    List<PmsProductAttributeCategory> list(int pageNum, int pageSize);
}

package com.chenj.mall.service;

import com.chenj.mall.dto.PmsAttributeParam;
import com.chenj.mall.mbg.model.PmsProductAttribute;

import java.util.List;

/**
 * 商品属性（参数）service类
 * Created by chen on 2020/7/7
 */
public interface PmsAttributeService {

    /**
     * 添加商品属性（参数）
     */
    int createItem(PmsAttributeParam pmsAttributeParam);

    /**
     * 更改商品属性（参数）
     */
    int updateItem(Long id, PmsAttributeParam pmsAttributeParam);

    /**
     * 根据id查找商品属性（参数）
     */
    PmsProductAttribute getItem(Long id);

    /**
     * 根据id删除商品属性（参数）
     */
    int deleteItem(Long id);

    /**
     * 分页查找商品属性（参数）
     */
    List<PmsProductAttribute> list(int pageNum, int pageSize, Long cid, int type);
}

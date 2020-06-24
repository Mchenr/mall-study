package com.chenj.mall.service;

import com.chenj.mall.dto.PmsProductCategoryParam;
import com.chenj.mall.mbg.model.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductCategoryService {

    /**
     * 创建商品分类
     */
    @Transactional
    int create(PmsProductCategoryParam pmsProductCategoryParam);

    /**
     * 删除商品分类
     */
    int delete(Long id);

    /**
     * 根据id查找商品分类
     */
    PmsProductCategory getItem(Long id);

    /**
     * 分页查找商品分类列表
     */
    List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 修改商品分类
     */
    @Transactional
    int update(Long id, PmsProductCategoryParam pmsProductCategoryParam);

    /**
     * 修改显示状态
     */
    int updateShowStatus(Long id, Integer showStatus);

    /**
     * 修改导航显示状态
     */
    int updateNavStatus(Long id, Integer factoryStatus);

}

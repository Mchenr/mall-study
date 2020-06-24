package com.chenj.mall.service.impl;

import com.chenj.mall.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.chenj.mall.mbg.model.PmsProductAttributeCategory;
import com.chenj.mall.mbg.model.PmsProductAttributeCategoryExample;
import com.chenj.mall.service.PmsProductAttributeCategoryService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类型service实现类
 *Created by chen on 2020/6/24
 */

@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public int createItem(String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);
        productAttributeCategory.setAttributeCount(0);
        productAttributeCategory.setParamCount(0);
        return productAttributeCategoryMapper.insertSelective(productAttributeCategory);
    }

    @Override
    public int updateItem(Long id, String name) {
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(id);
        productAttributeCategory.setName(name);
        return productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
    }

    @Override
    public PmsProductAttributeCategory getItem(Long id) {
        return productAttributeCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteItem(Long id) {
        return productAttributeCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsProductAttributeCategory> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeCategoryExample example = new PmsProductAttributeCategoryExample();
        return productAttributeCategoryMapper.selectByExample(example);
    }
}

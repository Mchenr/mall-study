package com.chenj.mall.service.impl;

import com.chenj.mall.dao.PmsProductAttributeDao;
import com.chenj.mall.dto.PmsAttributeParam;
import com.chenj.mall.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.chenj.mall.mbg.mapper.PmsProductAttributeMapper;
import com.chenj.mall.mbg.model.PmsProductAttribute;
import com.chenj.mall.mbg.model.PmsProductAttributeCategory;
import com.chenj.mall.service.PmsAttributeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PmsAttributeServiceImpl implements PmsAttributeService {

    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;

    @Autowired
    private PmsProductAttributeDao productAttributeDao;

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;


    @Override
    public int createItem(PmsAttributeParam pmsAttributeParam) {
        PmsProductAttribute productAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(pmsAttributeParam, productAttribute);
        int count = productAttributeMapper.insertSelective(productAttribute);
        //更新商品分类中的数量
        Long cid = productAttribute.getProductAttributeCategoryId();
        PmsProductAttributeCategory productAttributeCategory =
                productAttributeCategoryMapper.selectByPrimaryKey(cid);
        if (productAttribute.getType() == 0){
            productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount()+1);
        }else if (productAttribute.getType() == 1){
            productAttributeCategory.setParamCount(productAttributeCategory.getParamCount()+1);
        }
        return count;
    }

    @Override
    public int updateItem(Long id, PmsAttributeParam pmsAttributeParam) {
        PmsProductAttribute productAttribute = new PmsProductAttribute();
        productAttribute.setId(id);
        BeanUtils.copyProperties(pmsAttributeParam, productAttribute);
        return productAttributeMapper.updateByPrimaryKeySelective(productAttribute);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return productAttributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteItem(Long id) {
        return productAttributeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsProductAttribute> list(int pageNum, int pageSize, Long cid, int type) {
        PageHelper.startPage(pageNum, pageSize);
        return productAttributeDao.productAttrList(cid, type);
    }
}

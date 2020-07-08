package com.chenj.mall.service.impl;

import com.chenj.mall.dao.PmsProductAttributeDao;
import com.chenj.mall.dto.PmsAttributeParam;
import com.chenj.mall.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.chenj.mall.mbg.mapper.PmsProductAttributeMapper;
import com.chenj.mall.mbg.model.PmsProductAttribute;
import com.chenj.mall.mbg.model.PmsProductAttributeCategory;
import com.chenj.mall.mbg.model.PmsProductAttributeExample;
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
        if (productAttribute.getType() == 0) {
            productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() + 1);
        } else if (productAttribute.getType() == 1) {
            productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() + 1);
        }
        productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
        return count;
    }

    @Override
    public int updateItem(Long id, PmsAttributeParam pmsAttributeParam) {
        PmsProductAttribute productAttributeNew = new PmsProductAttribute();
        productAttributeNew.setId(id);
        BeanUtils.copyProperties(pmsAttributeParam, productAttributeNew);
        PmsProductAttribute productAttributeOld = productAttributeMapper.selectByPrimaryKey(id);
        Integer type = productAttributeNew.getType();
        if (productAttributeOld != null){
            if (!productAttributeOld.getProductAttributeCategoryId().equals(productAttributeNew.getProductAttributeCategoryId())){
                Long cidOld = productAttributeOld.getProductAttributeCategoryId();
                Long cidNew = productAttributeNew.getProductAttributeCategoryId();
                PmsProductAttributeCategory productAttributeCategoryOld = productAttributeCategoryMapper.selectByPrimaryKey(cidOld);
                PmsProductAttributeCategory productAttributeCategoryNew = productAttributeCategoryMapper.selectByPrimaryKey(cidNew);
                if (type == 0) {
                    productAttributeCategoryOld.setAttributeCount(productAttributeCategoryOld.getAttributeCount()-1);
                    productAttributeCategoryNew.setAttributeCount(productAttributeCategoryNew.getAttributeCount()+1);
                } else if (type == 1) {
                    productAttributeCategoryOld.setParamCount(productAttributeCategoryOld.getParamCount()-1);
                    productAttributeCategoryNew.setParamCount(productAttributeCategoryNew.getParamCount()+1);
                }
                productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategoryNew);
                productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategoryOld);
            }
        }
        return productAttributeMapper.updateByPrimaryKeySelective(productAttributeNew);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return productAttributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteItem(List<Long> ids) {
        PmsProductAttribute productAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
        Long cid = productAttribute.getProductAttributeCategoryId();
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(cid);
        if (productAttribute.getType() == 0) {
            if (productAttributeCategory.getAttributeCount() >= ids.size()) {
                productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() - ids.size());
            }else {
                productAttributeCategory.setAttributeCount(0);
            }
        } else if (productAttribute.getType() == 1) {
            if (productAttributeCategory.getParamCount() >= ids.size()) {
                productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() - ids.size());
            }else {
                productAttributeCategory.setParamCount(0);
            }
        }
        productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        return productAttributeMapper.deleteByExample(example);
    }

    @Override
    public List<PmsProductAttribute> list(int pageNum, int pageSize, Long cid, int type) {
        PageHelper.startPage(pageNum, pageSize);
        return productAttributeDao.productAttrList(cid, type);
    }
}

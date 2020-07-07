package com.chenj.mall.service.impl;

import com.chenj.mall.dao.PmsProductAttributeDao;
import com.chenj.mall.dto.PmsAttributeParam;
import com.chenj.mall.mbg.mapper.PmsProductAttributeMapper;
import com.chenj.mall.mbg.model.PmsProductAttribute;
import com.chenj.mall.service.PmsAttributeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsAttributeServiceImpl implements PmsAttributeService {

    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;

    @Autowired
    private PmsProductAttributeDao productAttributeDao;


    @Override
    public int createItem(PmsAttributeParam pmsAttributeParam) {
        return 0;
    }

    @Override
    public int updateItem(Long id, PmsAttributeParam pmsAttributeParam) {
        return 0;
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return null;
    }

    @Override
    public int deleteItem(Long id) {
        return 0;
    }

    @Override
    public List<PmsProductAttribute> list(int pageNum, int pageSize) {
        return null;
    }


    @Override
    public List<PmsProductAttribute> list(int pageNum, int pageSize, Long cid, int type) {
        PageHelper.startPage(pageNum, pageSize);
        return productAttributeDao.productAttrList(cid, type);
    }
}

package com.chenj.mall.service.impl;

import com.chenj.mall.dao.SmsHomeRecommendProductDao;
import com.chenj.mall.dto.SmsHomeRecommendProductParam;
import com.chenj.mall.mbg.model.SmsHomeRecommendProduct;
import com.chenj.mall.service.SmsHomeRecommendProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsHomeRecommendProductServiceImpl implements SmsHomeRecommendProductService {

    @Autowired
    private SmsHomeRecommendProductDao homeRecommendProductDao;

    @Override
    public SmsHomeRecommendProduct selectById(Long id) {
        return homeRecommendProductDao.selectById(id);
    }

    @Override
    public List<SmsHomeRecommendProduct> selectAll() {
        return homeRecommendProductDao.selectAll();
    }

    @Override
    public List<SmsHomeRecommendProduct> selectByProductNameOrRecommendStatus(String productName, Integer recommendStatus) {
        return homeRecommendProductDao.selectByProductNameOrRecommendStatus("%" + productName + "%", recommendStatus);
    }

    @Override
    public int insert(SmsHomeRecommendProductParam smsHomeRecommendProductParam) {
        Long productId = (long) (homeRecommendProductDao.selectAll().size() + 26);
        SmsHomeRecommendProduct smsHomeRecommendProduct = new SmsHomeRecommendProduct();
        BeanUtils.copyProperties(smsHomeRecommendProductParam,smsHomeRecommendProduct);
        smsHomeRecommendProduct.setProductId(productId);
        return homeRecommendProductDao.insert(smsHomeRecommendProduct);
    }

    @Override
    public int updateById(Long id, SmsHomeRecommendProductParam smsHomeRecommendProductParam) {
        SmsHomeRecommendProduct smsHomeRecommendProduct = homeRecommendProductDao.selectById(id);
        BeanUtils.copyProperties(smsHomeRecommendProductParam, smsHomeRecommendProduct);
        return homeRecommendProductDao.updateById(smsHomeRecommendProduct);
    }

    @Override
    public int deleteById(Long id) {
        return homeRecommendProductDao.deleteById(id);
    }
}

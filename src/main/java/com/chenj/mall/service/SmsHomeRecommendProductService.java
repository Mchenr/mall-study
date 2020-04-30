package com.chenj.mall.service;

import com.chenj.mall.dto.SmsHomeRecommendProductParam;
import com.chenj.mall.mbg.model.SmsHomeRecommendProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeRecommendProductService {

    /**
     * 通过id查询首页人气推荐
     * @param id
     * @return
     */
    SmsHomeRecommendProduct selectById(Long id);

    /**
     * 查询所有首页人气推荐
     * @return
     */
    List<SmsHomeRecommendProduct> selectAll();

    /**
     * 根据人气名称和推荐状态组合条件查询
     * @return
     */
    List<SmsHomeRecommendProduct> selectByProductNameOrRecommendStatus(String productName, Integer recommendStatus);

    /**
     * 新增首页人气推荐
     * @param smsHomeRecommendProductParam
     * @return
     */
    @Transactional
    int insert(SmsHomeRecommendProductParam smsHomeRecommendProductParam);

    /**
     * 根据主键更新
     */
    @Transactional
    int updateById(Long id, SmsHomeRecommendProductParam smsHomeRecommendProductParam);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    @Transactional
    int deleteById(Long id);
}

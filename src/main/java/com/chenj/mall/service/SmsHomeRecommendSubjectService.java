package com.chenj.mall.service;

import com.chenj.mall.dto.SmsHomeRecommendSubjectParam;
import com.chenj.mall.mbg.model.SmsHomeRecommendSubject;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeRecommendSubjectService {

    /**
     * 通过id查询首页标题推荐
     * @param id
     * @return
     */
    SmsHomeRecommendSubject selectById(Long id);

    /**
     * 查询所有首页标题推荐
     * @return
     */
    List<SmsHomeRecommendSubject> selectAll();

    /**
     * 分页查询所有首页标题推荐
     * @return
     */
    List<SmsHomeRecommendSubject> list(Integer pageNum, Integer pageSize);

    /**
     * 根据专题名称和推荐状态组合条件查询
     * @return
     */
    List<SmsHomeRecommendSubject> selectBySubjectNameOrRecommendStatus(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize);

    /**
     * 通过专题id或专题名称查询首页标题推荐
     * @return
     */
    SmsHomeRecommendSubject selectBySubjectIdOrSubjectName(Long subjectId, String subjectName);

    /**
     * 新增首页标题推荐
     * @param smsHomeRecommendSubjectParam
     * @return
     */
    @Transactional
    int insert(SmsHomeRecommendSubjectParam smsHomeRecommendSubjectParam);

    /**
     * 批量插入
     * @return
     */
    @Transactional
    int insertList(List<SmsHomeRecommendSubjectParam> smsHomeRecommendSubjectParams);

    /**
     * 根据主键更新
     */
    @Transactional
    int updateById(Long id, SmsHomeRecommendSubjectParam smsHomeRecommendSubjectParam);

    /**
     * 批量更新推荐状态
     * @return
     */
    @Transactional
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    @Transactional
    int deleteById(Long id);

    /**
     * 批量删除
     * @return
     */
    @Transactional
    int deleteByIds(List<Long> ids);
}

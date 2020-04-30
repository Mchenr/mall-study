package com.chenj.mall.dao;

import com.chenj.mall.mbg.model.SmsHomeRecommendSubject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsHomeRecommendSubjectDao {

    /**
     * 通过id查询首页标题推荐
     * @param id
     * @return
     */
    SmsHomeRecommendSubject selectById(Long id);

    /**
     * 通过专题id或专题名称查询首页标题推荐
     * @return
     */
    SmsHomeRecommendSubject selectBySubjectIdOrSubjectName(Long subjectId, String subjectName);

    /**
     * 查询所有首页标题推荐
     * @return
     */
    List<SmsHomeRecommendSubject> selectAll();


    /**
     * 根据专题名称和推荐状态组合条件查询
     * @return
     */
    List<SmsHomeRecommendSubject> selectBySubjectNameOrRecommendStatus(@Param("subjectName") String subjectName,
                                                                       @Param("recommendStatus") Integer recommendStatus);

    /**
     * 新增首页标题推荐
     * @param smsHomeRecommendSubject
     * @return
     */
    int insert(SmsHomeRecommendSubject smsHomeRecommendSubject);

    /**
     * 选择性新增首页标题推荐（输入值为空则使用默认值）
     * @param smsHomeRecommendSubject
     * @return
     */
    int insertSelective(SmsHomeRecommendSubject smsHomeRecommendSubject);

    /**
     * 批量插入
     * @param smsHomeRecommendSubjects
     * @return
     */
    int insertList(List<SmsHomeRecommendSubject> smsHomeRecommendSubjects);

    /**
     * 根据主键更新
     * @return
     */
    int updateById(SmsHomeRecommendSubject smsHomeRecommendSubject);

    /**
     * 批量更新推荐状态
     * @return
     */
    int updateRecommendStatus(@Param("ids") List<Long> ids,
                              @Param("recommendStatus") Integer recommendStatus);

    /**
     * 根据主键选择性更新
     * @return
     */
    int updateByIdSelective(SmsHomeRecommendSubject smsHomeRecommendSubject);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 批量删除
     * @return
     */
    int deleteByIds(List<Long> ids);

}

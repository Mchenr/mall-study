package com.chenj.mall.dao;

import com.chenj.mall.mbg.model.SmsHomeRecommendProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static org.apache.ibatis.type.JdbcType.*;

public interface SmsHomeRecommendProductDao {

    /**
     * 通过id查询首页人气推荐
     * @param id
     * @return
     */
    //通过在注解中定义字段与对象属性的映射，不推荐使用
//    @Select({"select id, product_id productId, product_name productName, recommend_status recommendStatus, sort sort " +
//            "from sms_home_recommend_product " +
//            "where id = #{id}"})
//    SmsHomeRecommendProduct selectById(Long id);
//    @ResultMap("smsHomeRecommendProductMap")

    //通过resultMap方式定义字段与属性的映射，推荐
    @Results(id = "smsHomeRecommendProductMap", value = {
            @Result(property = "id", column = "id", id = true, jdbcType = BIGINT),
            @Result(property = "productId", column = "product_id", jdbcType = BIGINT),
            @Result(property = "productName", column = "product_name", jdbcType = VARCHAR),
            @Result(property = "recommendStatus", column = "recommend_status", jdbcType = INTEGER),
            @Result(property = "sort", column = "sort", jdbcType = INTEGER)
    })
    @Select({"select * " +
            "from sms_home_recommend_product " +
            "where id = #{id}"})
    SmsHomeRecommendProduct selectById(Long id);

    /**
     * 查询所有首页人气推荐
     * @return
     */
    @ResultMap("smsHomeRecommendProductMap")
    @Select({"select * " +
            "from sms_home_recommend_product"})
    List<SmsHomeRecommendProduct> selectAll();

    /**
     * 根据专题名称和推荐状态组合条件查询
     * @return
     */
    @ResultMap("smsHomeRecommendProductMap")
    @Select({"select * " +
            "from sms_home_recommend_product " +
            "where product_name like #{productName} or recommend_status = #{recommendStatus}"})
    List<SmsHomeRecommendProduct> selectByProductNameOrRecommendStatus(@Param("productName") String productName,
                                                                       @Param("recommendStatus") Integer recommendStatus);

    /**
     * 新增首页标题推荐
     * @param smsHomeRecommendProduct
     * @return
     */
    //不需要返回主键，自定义主键，根据业务这里需要返回自增主键，这一段仅学习使用
//    @Insert({"insert into sms_home_recommend_product(id, product_id, product_name, recommend_status, sort) " +
//            "values(#{id, jdbcType = BIGINT}, #{productId, jdbcType = BIGINT}, " +
//            "#{productName, jdbcType = VARCHAR}, #{recommendStatus, jdbcType = INTEGER}, " +
//            "#{sort, jdbcType = INTEGER})"})
    @Insert({"insert into sms_home_recommend_product(id, product_id, product_name, recommend_status, sort) " +
            "values(#{id, jdbcType = BIGINT}, #{productId, jdbcType = BIGINT}, " +
            "#{productName, jdbcType = VARCHAR}, #{recommendStatus, jdbcType = INTEGER}, " +
            "#{sort, jdbcType = INTEGER})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SmsHomeRecommendProduct smsHomeRecommendProduct);

    /**
     * 根据主键更新
     * @return
     */
    @Update({"update sms_home_recommend_product " +
            "set product_id = #{productId, jdbcType = BIGINT}, " +
            "product_name = #{productName, jdbcType = VARCHAR}, " +
            "recommend_status = #{recommendStatus, jdbcType = INTEGER}, " +
            "sort = #{sort, jdbcType = INTEGER} " +
            "where id = #{id}"})
    int updateById(SmsHomeRecommendProduct smsHomeRecommendProduct);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    @Delete({"delete from sms_home_recommend_product where id = #{id}"})
    int deleteById(Long id);
}

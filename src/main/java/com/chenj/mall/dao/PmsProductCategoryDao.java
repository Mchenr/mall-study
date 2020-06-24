package com.chenj.mall.dao;

import org.apache.ibatis.annotations.Param;


public interface PmsProductCategoryDao {

    /**
     * 更新显示状态
     * @return
     */
    int updateShowStatus(@Param("id") Long id,
                         @Param("showStatus") Integer showStatus);

    /**
     * 更新导航显示状态
     * @return
     */
    int updateNavStatus(@Param("id") Long id,
                         @Param("navStatus") Integer navStatus);
}

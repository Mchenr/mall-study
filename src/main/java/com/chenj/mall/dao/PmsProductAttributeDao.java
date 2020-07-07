package com.chenj.mall.dao;

import com.chenj.mall.mbg.model.PmsProductAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductAttributeDao {

    List<PmsProductAttribute> productAttrList(@Param("cid") Long cid,
                                              @Param("type") Integer type);
}

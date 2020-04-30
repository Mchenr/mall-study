package com.chenj.mall.dto;

import com.chenj.mall.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SmsHomeRecommendSubjectParam {

    @ApiModelProperty("专题名称")
    @NotEmpty(message = "名称不能为空")
    private String subjectName;
    @ApiModelProperty("推荐状态")
    @FlagValidator(value = {"0", "1"}, message = "只能为0或1")
    private Integer recommendStatus;
    @ApiModelProperty("排序")
    private Integer sort;
}

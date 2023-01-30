package com.project.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("TranslStatisticsAO实体类")
public class TranslStatisticsAO {

    @ApiModelProperty("词条")
    @NotNull(message = "词条不能为空")
    @Size(max = 100,min = 1,message = "数据长度不合法")
    private String word;

    @ApiModelProperty("释义")
    @NotNull(message = "释义不能为空")
    @Size(max = 100,min = 1,message = "数据长度不合法")
    private String translation;

    @ApiModelProperty("是否正在流行")
    @Nullable
    private boolean popular;

    @ApiModelProperty("是否已经过时")
    @Nullable
    private boolean outdated;

    @ApiModelProperty("是否为中性词")
    @Nullable
    private boolean neutral;

    @ApiModelProperty("是否为褒义词")
    @Nullable
    private boolean commendation;

    @ApiModelProperty("是否为贬义词")
    @Nullable
    private boolean derogatory;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int isPopular() {
        if (popular){
            return 1;
        }else {
            return 0;
        }
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public int isOutdated() {
        if (outdated){
            return 1;
        }else {
            return 0;
        }
    }

    public void setOutdated(boolean outdated) {
        this.outdated = outdated;
    }

    public int isNeutral() {
        if (neutral){
            return 1;
        }else {
            return 0;
        }
    }

    public void setNeutral(boolean neutral) {
        this.neutral = neutral;
    }

    public int isCommendation() {
        if (commendation){
            return 1;
        }else {
            return 0;
        }
    }

    public void setCommendation(boolean commendation) {
        this.commendation = commendation;
    }

    public int isDerogatory() {
        if (derogatory){
            return 1;
        }else {
            return 0;
        }
    }

    public void setDerogatory(boolean derogatory) {
        this.derogatory = derogatory;
    }


}

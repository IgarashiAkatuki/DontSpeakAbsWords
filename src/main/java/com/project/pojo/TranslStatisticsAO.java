package com.project.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.*;

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

    @ApiModelProperty("流行/过时")
    @Nullable
    @DecimalMin(value = "-1",message = "数据不合法")
    @DecimalMax(value = "1",message = "数据不合法")
    private Integer fluency = -1;

    @ApiModelProperty("词性")
    @Nullable
    @DecimalMin(value = "-1",message = "数据不合法")
    @DecimalMax(value = "2",message = "数据不合法")
    private Integer partOfSpeech = -1;


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

    public int getFluency() {
        return fluency == null ? -1 : fluency;
    }

    public void setFluency(int fluency) {
        this.fluency = fluency;
    }

    public int getPartOfSpeech() {
        return partOfSpeech == null ? -1 : partOfSpeech;
    }

    public void setPartOfSpeech(int partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}

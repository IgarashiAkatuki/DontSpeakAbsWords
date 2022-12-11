package com.project.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "constant")
public class Constant {

    // like阈值(word的like数达到多少时会被随机到)
    private int  LikeThresholds;

    // 存在temp中的翻译like数多少时会被转移到PS
    private int TransformThresholds;

    // 问卷数目大小
    private int QuestionnaireLimits;

    // 问卷冷却时间
    private int QuestionnaireCoolDown;


    public int getLikeThresholds() {
        return LikeThresholds;
    }

    public void setLikeThresholds(int likeThresholds) {
        LikeThresholds = likeThresholds;
    }

    public int getTransformThresholds() {
        return TransformThresholds;
    }

    public void setTransformThresholds(int transformThresholds) {
        TransformThresholds = transformThresholds;
    }

    public int getQuestionnaireLimits() {
        return QuestionnaireLimits;
    }

    public void setQuestionnaireLimits(int questionnaireLimits) {
        QuestionnaireLimits = questionnaireLimits;
    }

    public int getQuestionnaireCoolDown() {
        return QuestionnaireCoolDown;
    }

    public void setQuestionnaireCoolDown(int questionnaireCoolDown) {
        QuestionnaireCoolDown = questionnaireCoolDown;
    }
}

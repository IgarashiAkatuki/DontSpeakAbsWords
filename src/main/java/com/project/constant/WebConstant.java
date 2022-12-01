package com.project.constant;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "constant")
@PropertySource("classpath:constant.properties")
public class WebConstant {
    private int  LikeThresholds;
    private int TransformThresholds;
    private int QuestionnaireLimits;
    private int QuestionnaireCoolDown;
    private String LoginURL;
    private String BackstageURL;
    private String ForbiddenPage;

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

    public String getLoginURL() {
        return LoginURL;
    }

    public void setLoginURL(String loginURL) {
        LoginURL = loginURL;
    }

    public String getBackstageURL() {
        return BackstageURL;
    }

    public void setBackstageURL(String backstageURL) {
        BackstageURL = backstageURL;
    }

    public String getForbiddenPage() {
        return ForbiddenPage;
    }

    public void setForbiddenPage(String forbiddenPage) {
        ForbiddenPage = forbiddenPage;
    }
}

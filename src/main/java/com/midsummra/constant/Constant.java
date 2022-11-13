package com.midsummra.constant;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constant {
    private static Properties properties = null;
    static {
        try {
            String path = Constant.class.getClassLoader().getResource("/com/midsummra/constant/webConfig.properties").getPath();
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final int LikeThresholds = Integer.parseInt(properties.getProperty("LikeThresholds"));
    public static final int TransformThresholds = Integer.parseInt(properties.getProperty("TransformThresholds"));
    public static final int QuestionnaireLimits = Integer.parseInt(properties.getProperty("QuestionnaireLimits"));

    public static final int QuestionnaireCoolDown = Integer.parseInt(properties.getProperty("QuestionnaireCoolDown"));
}

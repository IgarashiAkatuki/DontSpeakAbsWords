package com.project.pojo;

import com.mysql.cj.util.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SourceAO implements Serializable{

        @NotNull(message = "id不能为空")
        private int id;
        @NotNull(message = "翻译不能为空")
        private String translation;
        @NotNull(message = "来源不能为空")
        private String source;

        @Nullable
        private String url;

        public SourceAO() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlOrDefault(String defaultUrl){
            if (StringUtils.isNullOrEmpty(this.url)){
                return defaultUrl;
            }else {
                return this.getUrl();
            }
    }

    @Override
    public String toString() {
        return "SourceAO{" +
                "id=" + id +
                ", translation='" + translation + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.project.pojo.Source;
import com.project.service.SourceService;
import com.project.service.TranslationService;
import com.project.service.WordService;
import com.project.uitls.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SourceController {
    @Autowired
    @Qualifier("sourceServiceImpl")
    private SourceService sourceService;

    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;

    @RequestMapping(value = "/addTranslationsSource",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addTranslationsSource(String translation,String source) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map = new HashMap<>();

        if (!StringUtils.isNullOrEmpty(translation) && !StringUtils.isNullOrEmpty(source)){

            int flag = 0;

            if (!ObjectUtils.isEmpty(sourceService.querySource(translation))){

                flag = sourceService.addSourceLikes(translation, source);

            }else {

                Source tempSource = new Source();
                tempSource.setDate(new Date());
                tempSource.setLikes("1");
                tempSource.setTranslation(translation);
                tempSource.setSource(source);

                flag = sourceService.addSource(tempSource);

            }


            if (flag == 1){
                map.put("info","1");
            }else {
                map.put("info","0");
            }

        }else {
            map.put("info","0");
        }

        String json = mapper.writeValueAsString(map);

        return json;
    }

    @RequestMapping(value = "/admin/queryAllSource",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String QueryAllSource() throws Exception{

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String json;

        List<Source> sources = sourceService.queryAllSource();

        if (ObjectUtils.isEmpty(sources)){
            map.put("info","0");
            json = mapper.writeValueAsString(map);
        }else {
            json = mapper.writeValueAsString(sources);
        }

        return json;
    }

    @RequestMapping(value = "/admin/addSource",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addSource(String translation,String source) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        int flag = 0;
        if (StringUtils.isNullOrEmpty(translation) || StringUtils.isNullOrEmpty(source)){
            map.put("info","0");
        }else {
            translation = RegexUtils.removeExtraSpace(translation);
            source = RegexUtils.removeExtraSpace(source);

            flag = translationService.addSource(translation, source);

            if (flag == 1){
                map.put("info","1");
            }else {
                map.put("info","0");
            }
        }

        String json = mapper.writeValueAsString(map);

        return json;
    }
}

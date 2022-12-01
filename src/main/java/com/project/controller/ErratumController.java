package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.constant.WebConstant;
import com.project.pojo.Erratum;
import com.project.pojo.Translation;
import com.project.service.ErratumService;
import com.project.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/api")
public class ErratumController {
    @Autowired
    @Qualifier("erratumServiceImpl")
    private ErratumService erratumService;

    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @Autowired
    @Qualifier("webConstant")
    private WebConstant webConstant;

    @RequestMapping(value = "/addErratum",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addErratum(String word,String translation,String reason) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map = new HashMap<>();
        int flag = 0;

        Translation tempTranslation = translationService.queryTranslationByTranslationInPersistenceFixed(word,translation);

        if (ObjectUtils.isEmpty(tempTranslation)){
            map.put("info","0");
            String json = mapper.writeValueAsString(map);

            return json;

        }else {

            Erratum erratum = new Erratum();
            erratum.setDate(new Date());
            erratum.setTranslation(translation);
            erratum.setWord(word);
            erratum.setReason(reason);

            flag = erratumService.addErratum(erratum);

            if (flag == 1){
                map.put("info","1");
            }else {
                map.put("info","0");
            }

            String json = mapper.writeValueAsString(map);

            return json;
        }
    }

    @RequestMapping(value = "/admin/deleteErratum",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String deleteErratum(int id) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        int flag = 0;

        flag = erratumService.deleteErratum(id);

        if (flag == 1){
            map.put("info","1");
        }else {
            map.put("info","0");
        }

        String json = mapper.writeValueAsString(map);

        return json;
    }

    @RequestMapping("/admin/queryAllErratum")
    @ResponseBody
    public String queryAllErratum() throws Exception{

        Erratum erratum = erratumService.queryAllErratum();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(erratum);

        return json;
    }
}

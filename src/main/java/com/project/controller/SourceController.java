package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.entity.Source;
import com.project.service.SourceService;
import com.project.service.TranslationService;
import com.project.service.WordService;
import com.project.common.utils.RegexUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
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


    @ApiOperation("添加翻译的出处")
    @PostMapping(value = "/addTranslationsSource")
    @ResponseBody
    public Result addTranslationsSource(@ApiParam("翻译") String translation, @ApiParam("出处") String source){

        if (!StringUtils.isNullOrEmpty(translation) && !StringUtils.isNullOrEmpty(source)){

            int flag = 0;

            if (!ObjectUtils.isEmpty(sourceService.querySourceByName(translation,source))){

                flag = sourceService.addSourceLike(translation, source);

            }else {

                Source tempSource = new Source();
                tempSource.setDate(new Date());
                tempSource.setLikes("1");
                tempSource.setTranslation(translation);
                tempSource.setSource(source);

                flag = sourceService.submitSource(tempSource);

            }

            if (flag == 1){
                return Result.suc();
            }else {
                return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
            }

        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.NOT_FOUND.getResultCode(), ResponseStatusCode.NOT_FOUND.getResultMsg()));
        }
    }

    @PostMapping(value = "/admin/queryAllSource",produces = "text/html;charset = utf-8")
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

    @PostMapping(value = "/admin/addSource",produces = "text/html;charset = utf-8")
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

            flag = sourceService.submitSourceToTransl(translation, source);

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

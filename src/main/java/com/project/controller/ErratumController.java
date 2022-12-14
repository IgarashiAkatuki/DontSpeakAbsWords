package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.constant.Constant;
import com.project.entity.Erratum;
import com.project.entity.Translation;
import com.project.service.ErratumService;
import com.project.service.TranslationService;
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
public class ErratumController {
    @Autowired
    @Qualifier("erratumServiceImpl")
    private ErratumService erratumService;

    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @Autowired
    @Qualifier("constant")
    private Constant constant;

    @ApiOperation(value = "添加勘误")
    @PostMapping(value = "/addErratum")
    @ResponseBody
    public Result addErratum( @ApiParam("词条")String word, @ApiParam("词条翻译") String translation, @ApiParam("错误原因") String reason){

        int flag = 0;

        Translation tempTranslation = translationService.queryTranslInPS(word, translation);

        if (ObjectUtils.isEmpty(tempTranslation)) {

            return Result.error(new ErrorInfo(ResponseStatusCode.NOT_FOUND.getResultCode(), ResponseStatusCode.NOT_FOUND.getResultMsg()));
        } else {

            Erratum erratum = new Erratum();
            erratum.setDate(new Date());
            erratum.setTranslation(translation);
            erratum.setWord(word);
            erratum.setReason(reason);

            flag = erratumService.addErratum(erratum);

            if (flag == 1) {
                return Result.suc(new ErrorInfo(ResponseStatusCode.SUCCESS.getResultCode(), ResponseStatusCode.SUCCESS.getResultMsg()));
            } else {
                return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
            }

        }
    }

    @PostMapping(value = "/admin/deleteErratum", produces = "text/html;charset = utf-8")
    @ResponseBody
    public String deleteErratum(int id) throws Exception {

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        int flag = 0;

        flag = erratumService.deleteErratumById(id);

        if (flag == 1) {
            map.put("info", "1");
        } else {
            map.put("info", "0");
        }

        String json = mapper.writeValueAsString(map);

        return json;
    }

    @PostMapping("/admin/queryAllErratum")
    @ResponseBody
    public String queryAllErratum() throws Exception {

        List<Erratum> errata = erratumService.queryAllErratum();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(errata);

        return json;
    }
}

package com.project.controller;

import com.mysql.cj.util.StringUtils;
import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.common.utils.FuzzyQueryUtils;
import com.project.common.utils.PageUtils;
import com.project.constant.Constant;
import com.project.entity.Translation;
import com.project.entity.Word;
import com.project.pojo.FuzzyWordAO;
import com.project.pojo.TranslationAO;
import com.project.pojo.WordAO;
import com.project.service.PagerHelperService;
import com.project.service.TranslationService;
import com.project.service.WordService;
import com.project.common.utils.RegexUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api")
public class TranslationController {

    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;

    @Autowired
    @Qualifier("constant")
    private Constant webConstant;

    @Autowired
    @Qualifier("fuzzyQueryUtils")
    private FuzzyQueryUtils fuzzyQueryUtils;

    @Autowired
    @Qualifier("pageUtils")
    private PageUtils pageUtils;

    @Autowired
    @Qualifier("pagerHelperServiceImpl")
    private PagerHelperService pagerHelperService;

    @Autowired
    private ServletContext servletContext;

    // 从暂存区获得释义
    @ApiOperation("从暂存区获得释义")
    @PostMapping(value = "/getTranslationsFromTemp")
    @ResponseBody
    public Result getTranslationsFromTemp( @Valid WordAO wordAO){

        // JSR303验证失败直接返回错误原因[info]
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//
//            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
//        }

        // 获取word值
        String word = wordAO.getWord();

        // 验证word是否为空
        word = word.trim();
        if (word.isEmpty()){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        word = RegexUtils.replaceSpaceToUnderscore(word);

        if (StringUtils.isNullOrEmpty(word)){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));

        }

        List<Translation> translations = translationService.queryTranslInTempByWord(word);

        if (!ObjectUtils.isEmpty(translations)){

            return Result.suc(translations);
        }else {

            return Result.error(new ErrorInfo(ResponseStatusCode.NOT_FOUND.getResultCode(), ResponseStatusCode.NOT_FOUND.getResultMsg()));
        }
    }

    // 从持久区获得释义
    @ApiOperation("从持久区获得释义")
    @PostMapping(value = "/getTranslationsFromPersistence")
    @ResponseBody
    public Result getTranslationsFromPersistence(@Valid WordAO wordAO){

        // JSR303验证失败直接返回错误原因[info]
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//
//            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
//        }

        // 获取word值
        String word = wordAO.getWord();

        // 验证word是否为空
        word = word.trim();
        if (word.isEmpty()){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        word = RegexUtils.replaceSpaceToUnderscore(word);

        if (StringUtils.isNullOrEmpty(word)){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        // 获取释义
        List<Translation> translations = translationService.queryTranslInPSByWord(word);
        String json = null;

        // 如果存在释义
        if (!ObjectUtils.isEmpty(translations)){

            return Result.suc(translations);
        }else {

            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

//    向暂存区中提交释义
    @ConditionalOnProperty(
            name = "config.enableFuzzyQuery",
            havingValue = "false"
    )
    @ApiOperation("向暂存区中提交释义")
    @GetMapping(value = "/submitTranslationsToTemp")
    @ResponseBody
    public Result submitTranslationToTemp(@Valid TranslationAO translationAO){

        // JSR303验证失败直接返回错误原因[info]
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//
//            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
//        }

        int flag = 0;
//        if (StringUtils.isNullOrEmpty(temp)||StringUtils.isNullOrEmpty(temp.replace(" ",""))){
//            map.put("info","0");
//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(map);
//            return json;
//        }

        String translation = translationAO.getTranslation();
        String word = translationAO.getWord();

        // 如果word和translation去除首尾空格后为空，错误原因[info]
        word = word.trim();
        translation = translation.trim();
        if (word.isEmpty() || translation.isEmpty()){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        // 获得translation和word数据并且去除多余的空格
        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        //如果持久区中有该释义，则直接给次释义的like+1;
        if (!ObjectUtils.isEmpty(translationService.queryTranslInPS(word,translation))){

            flag = translationService.addTranslLikeInPS(word,translation);

            if (flag == 1){
                return Result.suc();
            }else {
                return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
            }
        }

        int wordId = -1;

        // 如果暂存区中没有此翻译
        if (ObjectUtils.isEmpty(translationService.queryTranslInTemp(word,translation))){

            // 如果不存在这个词条，则添加这个词条并且添加这个词条的翻译到暂存区
            if (ObjectUtils.isEmpty(wordService.queryWordByName(word))){

                Word wordBean = new Word();
                wordBean.setLikes(1);
                wordBean.setWord(word);
                wordBean.setDate(new Date());


                flag = wordService.addWord(wordBean);
            }

            wordId = wordService.getWordId(word);

            Translation tempTranslation = new Translation();
            tempTranslation.setTranslation(translation);
            tempTranslation.setWord(word);
            tempTranslation.setLikes(1);
            tempTranslation.setDate(new Date());
            tempTranslation.setWordId(wordId);

            flag = translationService.addTranslToTemp(tempTranslation);

        }else {
            flag = translationService.addLikeToTemp(word,translation);
            //查询此释义现在的like数
            if (translationService.queryTranslLikeInTemp(word,translation) >= webConstant.getTransformThresholds()){
                //如果持久表中已有释义
                if (!ObjectUtils.isEmpty(translationService.queryTranslInPS(word,translation))){

                    flag = translationService.addTranslLikeInPS(word,translation);

                }else {

                    wordId = wordService.getWordId(word);

                    Translation tempTranslation = new Translation();
                    tempTranslation.setWord(word);
                    tempTranslation.setTranslation(translation);
                    tempTranslation.setDate(new Date());
                    tempTranslation.setLikes(0);
                    tempTranslation.setWordId(wordId);
                    flag = translationService.addTranslToPS(tempTranslation);
                }
            }
        }

        if (flag == 1){
            return Result.suc();
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

    //向持久区中释义提交点赞(使用session)
    @ApiOperation("向持久区中释义提交点赞")
    @PostMapping(value = "/addLikesToPersistence")
    @ResponseBody
    public Result addLikesToPersistence(@Valid TranslationAO translationAO, HttpServletRequest request){

        HttpSession session = request.getSession();

//        // JSR303验证失败直接返回错误原因[info]
//        if (result.hasErrors()) {
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//
//            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
//        }
        String word = translationAO.getWord();
        String translation = translationAO.getTranslation();

        // 如果word和translation去除首尾空格后为空，错误原因[info]
        word = word.trim();
        translation = translation.trim();
        if (word.isEmpty() || translation.isEmpty()) {

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        int flag = 0;
        Object attribute = session.getAttribute(translation);

        if (ObjectUtils.isEmpty(attribute) || "0".equals((String) attribute)) {

            flag = translationService.addTranslLikeInPS(word, translation);
            session.setAttribute(translation, "1");

        } else if ("1".equals((String) attribute)) {

            flag = translationService.deleteTranslLikeInPS(word, translation);
            session.setAttribute(translation, "0");

        } else {
            session.setAttribute(translation, "-1");
        }

        if (flag == 1) {
            return Result.suc();
        } else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

// ===================================================================================================
// 模糊查询相关
    @ConditionalOnProperty(
            name = "config.enableFuzzyQuery",
            havingValue = "true"

    )
    @ApiOperation("在持久区进行模糊查询")
    @ResponseBody
    @PostMapping("/fuzzyQuery")
    public Result fuzzyQuery(@Valid WordAO wordAO){
        // 获取word值
        String word = wordAO.getWord();

        // 验证word是否为空
        word = word.trim();
        if (word.isEmpty()){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        word = RegexUtils.replaceSpaceToUnderscore(word);

        if (StringUtils.isNullOrEmpty(word)){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        // 获取释义
        List<Translation> translations = translationService.fuzzyQueryInPS(word);

        // 如果存在释义
        if (!ObjectUtils.isEmpty(translations)){
            return Result.suc(translations);
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

    //向暂存区中提交释义
    @ConditionalOnProperty(
            name = "config.enableFuzzyQuery",
            havingValue = "true"
    )
    @ApiOperation("向暂存区中提交释义")
    @PostMapping(value = "/submitTranslationsToTemp")
    @ResponseBody
    public Result submitTranslationToTempFuzzy(@Valid TranslationAO translationAO){


        int flag = 0;


        String translation = translationAO.getTranslation();
        String word = translationAO.getWord();

        // 如果word和translation去除首尾空格后为空，错误原因[info]
        word = word.trim();
        translation = translation.trim();
        if (word.isEmpty() || translation.isEmpty()){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        // 获得translation和word数据并且去除多余的空格
        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        //如果持久区中有该释义，则直接给次释义的like+1;
        if (!ObjectUtils.isEmpty(translationService.queryTranslInPS(word,translation))){

            flag = translationService.addTranslLikeInPS(word,translation);

            if (flag == 1){
                return Result.suc();
            }else {
                return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
            }
        }

        int wordId = -1;

        // 如果暂存区中没有此翻译
        if (ObjectUtils.isEmpty(translationService.queryTranslInTemp(word,translation))){

            // 如果不存在这个词条，则添加这个词条并且添加这个词条的翻译到暂存区
            if (ObjectUtils.isEmpty(wordService.queryWordByName(word))){

                Word wordBean = new Word();
                wordBean.setLikes(1);
                wordBean.setWord(word);
                wordBean.setDate(new Date());


                flag = wordService.addWord(wordBean);
            }

            wordId = wordService.getWordId(word);

            Translation tempTranslation = new Translation();
            tempTranslation.setTranslation(translation);
            tempTranslation.setWord(word);
            tempTranslation.setLikes(1);
            tempTranslation.setDate(new Date());
            tempTranslation.setWordId(wordId);

            flag = translationService.addTranslToTemp(tempTranslation);

        }else {
            flag = translationService.addLikeToTemp(word,translation);
            //查询此释义现在的like数
            if (translationService.queryTranslLikeInTemp(word,translation) >= webConstant.getTransformThresholds()){
                //如果持久表中已有释义
                if (!ObjectUtils.isEmpty(translationService.queryTranslInPS(word,translation))){

                    flag = translationService.addTranslLikeInPS(word,translation);

                }else {
                    String fuzzyWord = null;
                    wordId = wordService.getWordId(word);
                    try{
                        fuzzyWord = fuzzyQueryUtils.setFuzzyWord(word);
                    }catch (Exception e){
                        System.out.println("模糊查询未启用");
                    }


                    Translation tempTranslation = new Translation();
                    tempTranslation.setWord(word);
                    tempTranslation.setTranslation(translation);
                    tempTranslation.setDate(new Date());
                    tempTranslation.setLikes(0);
                    tempTranslation.setWordId(wordId);

                    if (fuzzyWord != null){
                        tempTranslation.setFuzzyWord(fuzzyWord);
                    }
                    flag = translationService.addTranslToPS(tempTranslation);
                }
            }
        }

        if (flag == 1){
            return Result.suc();
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

// ===================================================================================================
// 测试功能
    @PostMapping("/testGetPages")
    @ResponseBody
    @ApiOperation("测试功能")
    @ConditionalOnProperty(
            name = "enableTestMethod",
            havingValue = "true"
    )
    public Result testMethod1(@Valid FuzzyWordAO fuzzyWordAO){
        // 获取word值
        String word = fuzzyWordAO.getWord();

        // 验证word是否为空
        word = word.trim();
        if (word.isEmpty()){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        word = RegexUtils.replaceSpaceToUnderscore(word);

        if (StringUtils.isNullOrEmpty(word)){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        int totalRows = pagerHelperService.getTotalRows(word);
        pageUtils.setRows(totalRows);
        pageUtils.setCurrPage(1);
        int size = pageUtils.getTotalPages();

        if (totalRows <= webConstant.getPageSize()){
            List<Translation> translations = translationService.fuzzyQueryInPS(word);
            return Result.suc(translations,size+"");
        }else {
            String currPage = fuzzyWordAO.getCurrPageOrDefault();
            int startIndex = (Integer.parseInt(currPage)-1) * pageUtils.getPageSize();
            List<Translation> translations = pagerHelperService.fuzzyQuery(word, startIndex + "", pageUtils.getPageSize() + "");
            return Result.suc(translations,size+"");
        }
    }
}

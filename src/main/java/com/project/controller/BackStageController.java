package com.project.controller;

import com.project.entity.Erratum;
import com.project.entity.Source;
import com.project.pojo.SourceAO;
import com.project.pojo.TempTranslations;
import com.project.service.ErratumService;
import com.project.service.SourceService;
import com.project.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BackStageController {

    @Autowired
    @Qualifier("erratumServiceImpl")
    private ErratumService erratumService;

    @Autowired
    @Qualifier("sourceServiceImpl")
    private SourceService sourceService;

    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @RequestMapping("/info")
    public String getErratumInfo(Model model){

        List<Erratum> errata = erratumService.queryAllErratum();
        List<Source> sources = sourceService.queryAllSource();
        model.addAttribute("erratum",errata);
        model.addAttribute("sources",sources);

        return "admin/backstage";
    }

    @PostMapping("/deleteTranslation")
    public String deleteTranslation(@Validated TempTranslations translations, BindingResult result, Model model){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            model.addAttribute("translationError",fieldErrors.get(0).getDefaultMessage());

            return "forward:/admin/info";
        }else {
            int info = translationService.deleteTranslInPS(translations.getWord(), translations.getTranslation());
            if (info != 1){
                model.addAttribute("translationError","删除失败");
            }else {
                erratumService.deleteErratumByTransl(translations.getTranslation());
                model.addAttribute("translationError","提交成功");
            }

            return "forward:/admin/info";
        }
    }

    @PostMapping("/updateTranslation")
    public String updateTranslation(@Validated TempTranslations translations,BindingResult result,Model model){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            model.addAttribute("translationError",fieldErrors.get(0).getDefaultMessage());

            return "forward:/admin/info";
        }else {
            if (!translations.getNewTranslation().isEmpty()){
                int info = translationService.updateTranslInPS(translations.getWord(), translations.getTranslation(), translations.getNewTranslation());
                if (info != 1){
                    model.addAttribute("translationError","更改失败");
                }else {
                    erratumService.deleteErratumById(translations.getId());
                    model.addAttribute("translationError","提交成功");
                }
                return "forward:/admin/info";
            }else {
                model.addAttribute("translationError","新翻译为空");
                return "forward:/admin/info";
            }
        }
    }

    @PostMapping("/submitSource")
    public String submitSource(@Validated SourceAO tempSource, BindingResult result, Model model){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            model.addAttribute("sourceError",fieldErrors.get(0).getDefaultMessage());

            return "forward:/admin/info";
        }else {
            if (!tempSource.getSource().isEmpty()){
                int info = sourceService.submitSourceToTransl(tempSource.getTranslation(), tempSource.getSource());
                if (info != 1){
                    model.addAttribute("sourceError","提交失败");
                }else {
                    model.addAttribute("sourceError","提交成功");
                }
                return "forward:/admin/info";
            }else {
                model.addAttribute("sourceError","来源为空");
                return "forward:/admin/info";
            }
        }
    }
}

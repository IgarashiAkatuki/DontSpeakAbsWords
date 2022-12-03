package com.project.controller;

import com.project.pojo.Erratum;
import com.project.pojo.Source;
import com.project.service.ErratumService;
import com.project.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BackStageController {

    @Autowired
    @Qualifier("erratumServiceImpl")
    ErratumService erratumService;

    @Autowired
    @Qualifier("sourceServiceImpl")
    SourceService sourceService;

    @RequestMapping("/info")
    public String getErratumInfo(Model model){

            List<Erratum> errata = erratumService.queryAllErratum();
            List<Source> sources = sourceService.queryAllSource();
            model.addAttribute("erratum",errata);
            model.addAttribute("sources",sources);

            return "admin/backstage";
    }
}

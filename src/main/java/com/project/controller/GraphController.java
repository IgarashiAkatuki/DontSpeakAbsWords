package com.project.controller;

import com.project.common.response.Result;
import com.project.pojo.GraphVO;
import com.project.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

//@Controller
@RestController
@RequestMapping("/api")
public class GraphController {

    @Autowired
    private GraphService graphService;


    @PostMapping("/generateGraph")
    public Result generateGraph(@Valid GraphVO graphVO){
        String val = graphVO.getVal();
        List<Collection> collections = graphService.generateGraph(val);

        return Result.suc(collections);
    }
}

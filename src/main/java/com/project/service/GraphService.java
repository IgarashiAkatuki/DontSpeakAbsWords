package com.project.service;

import com.project.common.exceptions.GenerateMapException;
import com.project.common.utils.FormatUtils;
import com.project.common.utils.Neo4jUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("rawtypes")
public class GraphService {

    @Autowired
    private Neo4jUtils neo4jUtils;

    @Autowired
    private FormatUtils formatUtils;

    public List<Collection> generateGraph(String val){
        Collection<Map<String, Object>> graph = null;
        try {
            graph = neo4jUtils.getGraph(val);
        }catch (GenerateMapException e){
            e.printStackTrace();
        }
        if (graph == null){
            return new ArrayList<>();
        }
        return formatUtils.convert(graph);
    }
}

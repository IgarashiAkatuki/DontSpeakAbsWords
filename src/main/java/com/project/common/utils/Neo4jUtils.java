package com.project.common.utils;

import com.mysql.cj.util.StringUtils;
import com.project.common.exceptions.GenerateMapException;
import com.project.entity.jpa.Persistence;
import com.project.entity.neo4j.SourceNode;
import com.project.entity.neo4j.TranslationNode;
import com.project.entity.neo4j.WordNode;
import com.project.repository.jpa.Translation4NeoRepo;
import com.project.repository.neo4j.SourceRepo;
import com.project.repository.neo4j.TranslationRepo;
import com.project.repository.neo4j.WordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Neo4jUtils {

    @Autowired
    private Translation4NeoRepo repository;

    @Autowired
    private TranslationRepo translationRepo;

    @Autowired
    private WordRepo wordRepo;

    @Autowired
    private SourceRepo sourceRepo;

    @Autowired
    private Neo4jClient neo4jClient;


    public void addSourceNodes(){
        HashSet<SourceNode> nodesToSave = new HashSet<>();
        for (Persistence data : repository.findAll()) {
            String source = data.getSource();
            if (StringUtils.isNullOrEmpty(source)){
                continue;
            }
            List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(source.trim().toLowerCase());
            if (sourceNodes != null && sourceNodes.size() > 0){
                continue;
            }
            SourceNode sourceNode = new SourceNode(data.getSource().hashCode(), data.getSource().trim().toLowerCase());
//            System.out.println(sourceNode.getVal());
            nodesToSave.add(sourceNode);
        }

        System.out.println(nodesToSave.size());
        sourceRepo.saveAll(nodesToSave);
    }

    public void addTranslationNodes(){
        HashMap<String,TranslationNode> nodesToSave = new HashMap<>();
        for (Persistence data : repository.findAll()) {
            String source = data.getSource();
            TranslationNode translationNode = null;
            if (nodesToSave.containsKey(data.getTranslation())){
                translationNode = nodesToSave.get(data.getTranslation());
                if (!StringUtils.isNullOrEmpty(source)){
                    List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(data.getSource().trim().toLowerCase());
                    translationNode.addSourceNode(sourceNodes.get(0));
                }else {
                    continue;
                }
            }else {
                translationNode = new TranslationNode();
                translationNode.setId(data.getTranslation().hashCode());
                translationNode.setVal(data.getTranslation().trim().toLowerCase());
                translationNode.setWord(data.getWord());
                if (!StringUtils.isNullOrEmpty(source)){
                    translationNode.setSource(data.getSource());
                    List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(data.getSource().trim().toLowerCase());
                    translationNode.addSourceNode(sourceNodes.get(0));
                }
            }
            nodesToSave.put(translationNode.getVal(),translationNode);
        }
        System.out.println(nodesToSave.size());
        translationRepo.saveAll(nodesToSave.values());
    }

    public void addWordNodes() {
        HashMap<String, WordNode> nodesToSave = new HashMap<>();
        for (Persistence data : repository.findAll()) {

            List<TranslationNode> translationNodes = translationRepo.queryAllByVal(data.getTranslation().trim().toLowerCase());
            TranslationNode translationNode = null;
            if (translationNodes == null || translationNodes.size() < 1) {
                translationNode = new TranslationNode();
                translationNode.setSource(data.getSource());
                translationNode.setVal(data.getTranslation().trim().toLowerCase());
                translationNode.setWord(data.getWord());
                translationNode.setId(data.getTranslation().hashCode());
                translationRepo.save(translationNode);
            } else {
                translationNode = translationNodes.get(0);
            }

            WordNode wordNode = null;
            if (!nodesToSave.containsKey(data.getWord())) {
                wordNode = new WordNode();
                wordNode.setId(data.getWord().hashCode());
                wordNode.setVal(data.getWord().trim().toLowerCase());
                wordNode.addTranslationNodes(translationNode);
            } else {
                wordNode = nodesToSave.get(data.getWord());
            }

            wordNode.addTranslationNodes(translationNode);
            nodesToSave.put(wordNode.getVal(), wordNode);
        }

        System.out.println(nodesToSave.size());
        wordRepo.saveAll(nodesToSave.values());
    }

    public Collection<Map<String,Object>> getGraph(String nodeVal) throws GenerateMapException{
        Collection<Map<String,Object>> list = null;
        try {
            list = neo4jClient.query("match path = (n)-[*1..6]-(r) where n.val = '"+ nodeVal +"' " +
                    "unwind nodes(path) as node with distinct node match (node)-[r]-(related) " +
                    "where NOT (node)<-[r]-(related) " +
                    "return distinct node.val, type(r), related.val").fetch().all();
        }catch (Exception e){
            GenerateMapException generateMapException = new GenerateMapException("获取图结构失败");
            generateMapException.initCause(e);
            throw generateMapException;
        }

        return list;
    }
}

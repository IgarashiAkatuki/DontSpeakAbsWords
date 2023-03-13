package com.project.common.utils;

import com.mysql.cj.util.StringUtils;
import com.project.common.exceptions.GenerateMapException;
import com.project.entity.neo4j.SourceNode;
import com.project.entity.neo4j.TranslationNode;
import com.project.entity.neo4j.WordNode;
import com.project.repository.jpa.Translation4NeoRepo;
import com.project.repository.neo4j.SourceRepo;
import com.project.repository.neo4j.TranslationRepo;
import com.project.repository.neo4j.WordRepo;
import com.project.service.SourceService;
import com.project.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class Neo4jUtils {
    @Autowired
    private SourceService sourceService;

    @Autowired
    private Translation4NeoRepo repository;

    @Autowired
    private WordService wordService;


    @Autowired
    private TranslationRepo translationRepo;

    @Autowired
    private WordRepo wordRepo;

    @Autowired
    private SourceRepo sourceRepo;

    @Autowired
    private Neo4jClient neo4jClient;


    public void addSourceNodes(){
        repository.findAll().forEach(data->{
            String source = data.getSource();
            if (StringUtils.isNullOrEmpty(source)){
                return;
            }
            List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(source);
            if (sourceNodes != null && sourceNodes.size() > 0){
                return;
            }
            SourceNode sourceNode = new SourceNode(data.getSource().hashCode()+(long)(Math.random()*1000), data.getSource());
            sourceRepo.save(sourceNode);
            System.out.println("添加了[sourceNode]-> "+ sourceNode.getVal());
        });
    }

    public void addTranslationNodes(){
        repository.findAll().forEach(data->{
            String source = data.getSource();
            TranslationNode translationNode = null;
            List<TranslationNode> translationNodes = translationRepo.queryAllByVal(data.getTranslation());

            if (translationNodes == null || translationNodes.size() < 1){
                translationNode = new TranslationNode();
                translationNode.setVal(data.getTranslation());
                translationNode.setWord(data.getWord());
                translationNode.setLikes(1);
                translationNode.setId(data.getTranslation().hashCode()+(long)(Math.random()*1000));
                if (!StringUtils.isNullOrEmpty(source)){
                    translationNode.setSource(data.getSource());
//                    SourceNode sourceNode = new SourceNode(data.getSource().hashCode(),data.getSource());
                    List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(data.getSource());
                    translationNode.addSourceNode(sourceNodes.get(0));
                }
            }else {
                translationNode = translationNodes.get(0);
                if (!StringUtils.isNullOrEmpty(source)){
                    List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(data.getSource());
                    translationNode.addSourceNode(sourceNodes.get(0));
                }
            }

            translationRepo.save(translationNode);
            System.out.println("添加了[translationNode]-> "+ data.getTranslation());
        });
    }

    public void addWordNodes(){
        repository.findAll().forEach(data->{

            List<TranslationNode> nodes = translationRepo.queryAllByVal(data.getTranslation());
            TranslationNode translationNode = null;
            if (nodes == null || nodes.size() < 1){
                TranslationNode node = new TranslationNode();
                try{
                    node.setSource(data.getSource());
                    node.setVal(data.getTranslation());
                    node.setWord(data.getWord());
                    node.setLikes(1);
                    node.setId(System.currentTimeMillis());
                    translationNode = node;
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(node);
                }

            }else {
                translationNode = nodes.get(0);
            }


            List<WordNode> wordNodes = wordRepo.queryAllByVal(data.getWord());
            if (wordNodes == null || wordNodes.size() < 1){
                WordNode wordNode = new WordNode();
                wordNode.setId(data.getWord().hashCode()+(long)(Math.random()*100));
                wordNode.setVal(data.getWord());
                wordNode.addTranslationNodes(translationNode);
                wordRepo.save(wordNode);
            }else {
                WordNode wordNode = wordNodes.get(0);
                wordNode.addTranslationNodes(translationNode);
                wordRepo.save(wordNode);
            }
            System.out.println("添加了[wordNode]-> "+ data.getWord());
        });
    }



    public Collection<Map<String,Object>> getGraph(String nodeVal) throws GenerateMapException{
        Collection<Map<String,Object>> list = null;
        try {
            list = neo4jClient.query("match path = (n)-[*]-(r) where n.val = '"+ nodeVal +"' " +
                    "unwind nodes(path) as node with distinct node match (node)-[r]-(related) " +
                    "where NOT (node)<-[r]-(related) "+
                    "return distinct node.val, type(r), related.val").fetch().all();
        }catch (Exception e){
            GenerateMapException generateMapException = new GenerateMapException("获取图结构失败");
            generateMapException.initCause(e);
            throw generateMapException;
        }

        return list;
    }
}

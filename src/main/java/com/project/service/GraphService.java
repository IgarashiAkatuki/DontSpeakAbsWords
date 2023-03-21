package com.project.service;

import com.mysql.cj.util.StringUtils;
import com.project.common.exceptions.GenerateMapException;
import com.project.common.utils.FormatUtils;
import com.project.common.utils.Neo4jUtils;
import com.project.entity.jpa.Persistence;
import com.project.entity.neo4j.SourceNode;
import com.project.entity.neo4j.TranslationNode;
import com.project.entity.neo4j.WordNode;
import com.project.repository.jpa.Translation4NeoRepo;
import com.project.repository.neo4j.SourceRepo;
import com.project.repository.neo4j.TranslationRepo;
import com.project.repository.neo4j.WordRepo;
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

    @Autowired
    private Translation4NeoRepo dataRepo;

    @Autowired
    private SourceRepo sourceRepo;

    @Autowired
    private TranslationRepo translationRepo;

    @Autowired
    private WordRepo wordRepo;

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

    public int updateGraph(int id) throws GenerateMapException{
        long start = System.currentTimeMillis();

        List<Persistence> persistences = dataRepo.queryAllByIdGreaterThan(id);

        for (Persistence persistence : persistences) {
            String source = persistence.getSource();
            if (StringUtils.isNullOrEmpty(source)){
                continue;
            }
            List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(source);
            if (sourceNodes != null && sourceNodes.size() > 0){
                continue;
            }
            SourceNode sourceNode = new SourceNode(persistence.getSource().hashCode()+(long)(Math.random()*1000), persistence.getSource());
            sourceRepo.save(sourceNode);
            System.out.println("添加了[sourceNode]-> "+ sourceNode.getVal());
        }

        for (Persistence persistence : persistences) {
            String source = persistence.getSource();
            TranslationNode translationNode = null;
            List<TranslationNode> translationNodes = translationRepo.queryAllByVal(persistence.getTranslation());

            if (translationNodes == null || translationNodes.size() < 1){
                translationNode = new TranslationNode();
                translationNode.setVal(persistence.getTranslation());
                translationNode.setWord(persistence.getWord());
                translationNode.setLikes(1);
                translationNode.setId(persistence.getTranslation().hashCode()+(long)(Math.random()*1000));
                if (!StringUtils.isNullOrEmpty(source)){
                    translationNode.setSource(persistence.getSource());
//                    SourceNode sourceNode = new SourceNode(data.getSource().hashCode(),data.getSource());
                    List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(persistence.getSource());
                    translationNode.addSourceNode(sourceNodes.get(0));
                }
            }else {
                translationNode = translationNodes.get(0);
                if (!StringUtils.isNullOrEmpty(source)){
                    List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(persistence.getSource());
                    translationNode.addSourceNode(sourceNodes.get(0));
                }
            }

            translationRepo.save(translationNode);
            System.out.println("添加了[translationNode]-> "+ persistence.getTranslation());
        }

        for (Persistence persistence : persistences) {
            List<TranslationNode> nodes = translationRepo.queryAllByVal(persistence.getTranslation());
            TranslationNode translationNode = null;
            if (nodes == null || nodes.size() < 1){
                TranslationNode node = new TranslationNode();
                try{
                    node.setSource(persistence.getSource());
                    node.setVal(persistence.getTranslation());
                    node.setWord(persistence.getWord());
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


            List<WordNode> wordNodes = wordRepo.queryAllByVal(persistence.getWord());
            if (wordNodes == null || wordNodes.size() < 1){
                WordNode wordNode = new WordNode();
                wordNode.setId(persistence.getWord().hashCode()+(long)(Math.random()*100));
                wordNode.setVal(persistence.getWord());
                wordNode.addTranslationNodes(translationNode);
                wordRepo.save(wordNode);
            }else {
                WordNode wordNode = wordNodes.get(0);
                wordNode.addTranslationNodes(translationNode);
                wordRepo.save(wordNode);
            }
            System.out.println("添加了[wordNode]-> "+ persistence.getWord());
        }
        System.out.println("图构建共耗时"+ (System.currentTimeMillis()-start) + "ms");
        return persistences.get(persistences.size()-1).getId();
    }
}

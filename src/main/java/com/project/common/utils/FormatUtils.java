package com.project.common.utils;

import com.project.entity.g6.Edge;
import com.project.entity.g6.Node;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FormatUtils {
    public List<Collection> convert(Collection<Map<String,Object>> graph){
        HashMap<String, Node> nodeMap = new HashMap<>();
        HashSet<Edge> edgeMap = new HashSet<>();
        for (Map<String, Object> map : graph) {
            String source = map.get("node.val").toString();
            String target = map.get("related.val").toString();
            String edge = map.get("type(r)").toString();

            if ("出自".equals(edge)){
                Node translationNode = null;
                Node sourceNode = null;
                if (nodeMap.containsKey("t:" + source)){
                    translationNode = nodeMap.get("t:" + source);
                }else {
                    translationNode = new Node("translation",source);
                    nodeMap.put("t:" + source,translationNode);
                }
                if (nodeMap.containsKey("s:" + target)){
                    sourceNode = nodeMap.get("s:" + target);
                }else {
                    sourceNode = new Node("source",target);
                    nodeMap.put("s:" + target,sourceNode);
                }
                Edge edgeSet = new Edge(translationNode.getId(), sourceNode.getId(), "出自");

                edgeMap.add(edgeSet);

            }else{
                Node wordNode = null;
                Node translationNode = null;
                if (nodeMap.containsKey("w:" + source)){
                    wordNode = nodeMap.get("w:" + source);
                }else {
                    wordNode = new Node("word",source);
                    nodeMap.put("w:" + source,wordNode);
                }
                if (nodeMap.containsKey("t:" + target)){
                    translationNode = nodeMap.get("t:" + target);
                }else {
                    translationNode = new Node("translation",target);
                    nodeMap.put("t:" + target,translationNode);
                }
                Edge edgeSet = new Edge(wordNode.getId(), translationNode.getId(), "释义");

                edgeMap.add(edgeSet);
            }
        }
        ArrayList<Collection> res = new ArrayList<>();
        res.add(nodeMap.values());
        res.add(edgeMap);

        return res;
    }
}

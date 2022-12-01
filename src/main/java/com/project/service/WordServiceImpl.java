package com.project.service;

import com.project.mapper.WordMapper;
import com.project.pojo.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WordServiceImpl implements WordService{

    @Autowired
    @Qualifier("wordMapper")
    private WordMapper wordMapper;


    @Override
    public int addWord(Word word) {
        return wordMapper.addWord(word);
    }

    @Override
    public int deleteWordById(int id) {
        return wordMapper.deleteWordById(id);
    }

    @Override
    public int deleteWordByName(String word) {
        return wordMapper.deleteWordByName(word);
    }

    @Override
    public int updateWord(Word word) {
        return wordMapper.updateWord(word);
    }

    @Override
    public Word queryWordById(int id) {
        return wordMapper.queryWordById(id);
    }

    @Override
    public Word queryWordByName(String word) {
        return wordMapper.queryWordByName(word);
    }

    @Override
    public List<Word> queryAllWord() {
        return wordMapper.queryAllWord();
    }

    @Override
    public int addLikes(String word) {
        return wordMapper.addLikes(word);
    }

    @Override
    public List<Word> getRandomWords(int likes,int limits) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("likes",likes);
        map.put("limits",limits);
        return wordMapper.getRandomWords(map);
    }

    @Override
    public int getWordId(String word) {
        return wordMapper.getWordId(word);
    }
}

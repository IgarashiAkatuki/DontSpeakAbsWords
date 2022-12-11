package com.project.service;

import com.project.entity.Word;
import com.project.mapper.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
    public int addWordLike(String word) {
        return wordMapper.addWordLike(word);
    }

    @Override
    public List<Word> getRandomWords(int like,int limit) {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("like",like);
        map.put("limit",limit);

        return wordMapper.getRandomWords(map);
    }

    @Override
    public int getWordId(String word) {
        return wordMapper.getWordId(word);
    }
}

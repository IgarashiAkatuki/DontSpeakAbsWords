package com.midsummra.service;

import com.midsummra.mapper.WordMapper;
import com.midsummra.pojo.Word;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordServiceImpl implements WordService{
    private WordMapper wordMapper;

    public void setWordMapper(WordMapper wordMapper) {
        this.wordMapper = wordMapper;
    }

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

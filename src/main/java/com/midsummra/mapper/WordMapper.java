package com.midsummra.mapper;

import com.midsummra.pojo.Word;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WordMapper {
    public int addWord(Word word);
    public int deleteWordById(@Param("id") int id);
    public int deleteWordByName(@Param("word") String word);
    public int updateWord(Word word);
    public Word queryWordById(@Param("id") int id);
    public Word queryWordByName(@Param("name") String word);
    public List<Word> queryAllWord();
    public int addLikes(String word);
    public List<Word> getRandomWords(Map map);

    public int getWordId(String word);

}

package com.project.mapper;

import com.project.pojo.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface WordMapper {
    int addWord(Word word);
    int deleteWordById(@Param("id") int id);
    int deleteWordByName(@Param("word") String word);
    int updateWord(Word word);
    Word queryWordById(@Param("id") int id);
    Word queryWordByName(@Param("name") String word);
    List<Word> queryAllWord();
    int addLikes(String word);
    List<Word> getRandomWords(Map map);

    int getWordId(String word);
}

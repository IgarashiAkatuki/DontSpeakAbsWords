import com.midsummra.mapper.WordMapper;
import com.midsummra.pojo.Source;
import com.midsummra.pojo.Translation;
import com.midsummra.pojo.Word;
import com.midsummra.service.SourceService;
import com.midsummra.service.TranslationService;
import com.midsummra.service.WordService;
import com.midsummra.service.WordServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        TranslationService translationService = context.getBean("translationServiceImpl",TranslationService.class);
//        List<Translation> xf = translationService.queryTranslationByName("xf");
//        for (Translation translation : xf) {
//            System.out.println(translation);
////        }
//        WordService wordServiceImpl = context.getBean("wordServiceImpl", WordService.class);
//        List<Word> randomWords = wordServiceImpl.getRandomWords(1, 5);
//        for (Word randomWord : randomWords) {
//            System.out.println(randomWord);
//        }
//        for (Word word : wordServiceImpl.queryAllWord()) {
//            System.out.println(word);
//        }
//        Word word = new Word();
//        Word name = wordServiceImpl.queryWordByName("橄榄");
//        if (ObjectUtils.isEmpty(name)){
//            System.out.println("null");
//        }else {
//            System.out.println("awa");
//        }
//        word.setWord("橄榄");
//        word.setLikes(114);
////        int i = wordServiceImpl.addWord(word);
////        System.out.println(i);
//        wordServiceImpl.deleteWordById(2);
//        试试正则
//        String word = "晓 峰  a   wa      ";
//        String pattern = "\\s+";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(word);
//        String s = m.replaceAll("_");
//
//        System.out.println(s);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        System.out.println(sdf.format(date));


        TranslationService serviceImpl = (TranslationService) context.getBean("translationServiceImpl");
        List<Translation> xf = serviceImpl.queryTranslationFromPersistence("xf");
        for (Translation translation : xf) {
            System.out.println(translation);
        }
    }

    @org.junit.Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SourceService bean = context.getBean("sourceServiceImpl", SourceService.class);
        Source source = new Source();
        source.setSource("test");
        source.setDate(new Date());
        source.setTranslation("晓峰");
        source.setLikes("1");
        System.out.println(bean.addSource(source));
    }
}

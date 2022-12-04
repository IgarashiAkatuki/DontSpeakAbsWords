package com.project;

import com.project.constant.WebConstant;
import com.project.mapper.ErratumMapper;
import com.project.pojo.Erratum;
import com.project.service.ErratumService;
import com.project.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestApplicationTests {

	@Autowired
	@Qualifier("erratumServiceImpl")
	private ErratumService service;

	@Autowired
	@Qualifier("translationServiceImpl")
	private TranslationService translationService;
	@Autowired
	private WebConstant constant;
	@Test
	void contextLoads() {
		System.out.println(constant.getBackstageURL());
	}

	@Test
	void Test1(){
		int i = translationService.deleteTranslationFormPersistence("xf", "TEst");
		System.out.println(i);
	}

}

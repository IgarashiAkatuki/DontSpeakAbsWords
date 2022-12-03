package com.project;

import com.project.constant.WebConstant;
import com.project.mapper.ErratumMapper;
import com.project.pojo.Erratum;
import com.project.service.ErratumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestApplicationTests {

	@Autowired
	@Qualifier("erratumServiceImpl")
	ErratumService service;
	@Autowired
	private WebConstant constant;
	@Test
	void contextLoads() {
		System.out.println(constant.getBackstageURL());
	}

	@Test
	void Test1(){
		for (Erratum erratum : service.queryAllErratum()) {
			System.out.println(erratum);
		}

	}

}

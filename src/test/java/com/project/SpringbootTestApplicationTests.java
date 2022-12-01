package com.project;

import com.project.constant.WebConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestApplicationTests {

	@Autowired
	private WebConstant constant;
	@Test
	void contextLoads() {
		System.out.println(constant.getBackstageURL());
	}

}

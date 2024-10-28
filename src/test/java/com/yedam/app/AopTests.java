package com.yedam.app; //src/test/java 밑에

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.aop.service.AaaService;

@SpringBootTest
public class AopTests {
	@Autowired
	AaaService aaaService;
	
	@Test
	void transcationalTest() {
		aaaService.insert();
	}
}

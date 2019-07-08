package com.windea.demo.cloudcollect.core;

import com.windea.commons.kotlin.annotation.Tested;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTests {
	@Autowired private MockMvc mockMvc;

	//测试转换器
	@Tested
	@Test
	public void test1() throws Exception {
		mockMvc.perform(get("/test/string").param("string", "abc")).andDo(print());

		mockMvc.perform(get("/test/pageable").param("pageable", "")).andDo(print());

		mockMvc.perform(get("/test/pageable").param("pageable", "1,5")).andDo(print());

		mockMvc.perform(get("/test/pageable").param("pageable", "1,5,+name")).andDo(print());

		mockMvc.perform(get("/test/pageable").param("pageable", "1,5,+name,-age")).andDo(print());
	}
}

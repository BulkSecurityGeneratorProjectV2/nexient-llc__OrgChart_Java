package com.systemsinmotion.orgchart.web.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.systemsinmotion.orgchart.web.View;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
@WebAppConfiguration("/src/main/webapp")
public class AdminControllerTests {

	private AdminController controller;
	
	@Before
	public void before() {
		controller = new AdminController();
	}
	
	@Test
	public void doDefault() {
		assertEquals(View.ADMIN_DEFAULT, controller.doDefault());
	}
	
	@Test
	public void doLogin_GET() {
		assertEquals(View.ADMIN_LOGIN, controller.doLogin());
	}
}

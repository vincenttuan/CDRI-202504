package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.exception.CertException;
import com.example.demo.service.CertService;
import com.example.demo.service.UserService;

@SpringBootTest
public class CertServiceTest {
	
	@Autowired
	private CertService certService;
	
	@Test
	public void testUserAdd()  {
		try {
			System.out.println(certService.getCert("john", "1234"));
		} catch (CertException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}

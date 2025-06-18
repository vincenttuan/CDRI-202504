package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth // 設定授權
				.requestMatchers("/login").permitAll() // "/login" 不用授權
				.anyRequest().authenticated() // 其餘都要授權
		)
		.formLogin(form -> form // 自訂表單授權頁
				.loginPage("/login")
				.permitAll()
		);
		
		return http.build();
	}
	
}

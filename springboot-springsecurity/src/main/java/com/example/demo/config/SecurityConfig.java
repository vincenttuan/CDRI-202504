package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth // 設定授權
				.requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() // 不用授權的路徑
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated() // 其餘都要授權
		)
		.formLogin(form -> form // 自訂表單授權頁
				.loginPage("/login")
				.permitAll()
		)
		.logout(logout -> logout
				.logoutSuccessUrl("/login?logout=true")
				// 因為 logout 預設需使用 POST
				// 若真的需要用 GET 進行 logout 則可以透過 logoutRequestMatcher 來修改成為 GET (非官方建議)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.permitAll());
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		// 建立使用者-InMemory版本
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder.encode("1234"))
				.roles("USER")
				.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("5678"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 建立加密元件(已加鹽)
	}
	
	
}

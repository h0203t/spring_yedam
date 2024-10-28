package com.yedam.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	// 비밀번호 암호화 : 1234 => 암호화된 값으로 인지
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/*
	@Bean // 메모리상 인증 정보 등록 => 테스트 전용 방식
	InMemoryUserDetailsManager inMemoryUserDetailsService() {
		UserDetails user = User.builder()
								.username("user1")
								.password(passwordEncoder().encode("1234"))
								.roles("USER") // ROLE_USER
								//.authorities("ROLE_USER")
								.build();
		
		UserDetails admin = User.builder()
								.username("admin1")
								.password(passwordEncoder().encode("1234"))
								.authorities("ROLE_ADMIN")
								//.roles("ADMIN")
								.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}
	*/
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Spring, Lambda DSL
		// 인증 및 인가를 적용할 URL
		http
			.authorizeHttpRequests((authrize)
					-> authrize
						.requestMatchers("/", "/all").permitAll() //전체 접근 허용
						.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") 
						//ROLE_USER라는 권한을 가진 사용자만 접근 허용
						.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") 
						//ROLE_ADMIN라는 권한을 가진 사용자만 접근 허용
						.anyRequest().authenticated() // 권한 상관없이 인증받은 사용자만
					);
		// <form/>를 기반으로 인증 처리시 설정
		http
			.formLogin((formlogin)
					-> formlogin
						.defaultSuccessUrl("/all")
					);
		// logout를 기반으로 처리부분 설정
		http
			.logout(logout -> logout
				.logoutSuccessUrl("/all")
				.invalidateHttpSession(true));

		// csrf 비활성화
		http.csrf(csrf -> csrf.disable());
		
		
		return http.build();
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web 
						.ignoring() // Security 설정을 제외할 url
						.requestMatchers("/images/**", "/js/**", "/css/**", "/imgs/**");
	}
	
}

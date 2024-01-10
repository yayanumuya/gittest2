package com.bs.firstboot.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import com.bs.firstboot.common.MyAuthority;
import com.bs.firstboot.common.filter.CheckFilter;
import com.bs.firstboot.security.DBConnectionProvider;
import com.bs.firstboot.security.MyAccessDeniedHandler;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	
	//SecurityFilterChain클래스를 bean으로 등록
	@Autowired
	private DBConnectionProvider dbprovider;
	
	
	@Bean 
	BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	SecurityFilterChain authenticationPath(HttpSecurity http) 
			throws Exception{
		return http
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(request->{request
					.requestMatchers(antMatcher("/resources/**")).permitAll()
					.requestMatchers(antMatcher("/")).permitAll()
					.requestMatchers(req->CorsUtils.isPreFlightRequest(req)).permitAll()
					//CorsUtils::isPreFlightRequest).permitAll()
					.requestMatchers(antMatcher("/WEB-INF/views/**")).permitAll()
					.requestMatchers("/members").hasAnyAuthority(MyAuthority.ADMIN.name());
				})
				.formLogin(formlogin->{
					formlogin.loginProcessingUrl("/logintest");
//					.failureForwardUrl("/loginfail")
//					.successForwardUrl("/loginsuccess");
				})
				.logout(logout->logout.logoutUrl("/logout"))
				.authenticationProvider(dbprovider)
				//잘못된 권한으로 접속했을때 응답
				.exceptionHandling(
						exceptionHandle ->exceptionHandle.accessDeniedHandler(new MyAccessDeniedHandler()))
				.build();
//				.csrf()
//					.disable()
//				.formLogin()
//					.loginPage("")
//					.successForwardUrl(null)
	}
	
	
}







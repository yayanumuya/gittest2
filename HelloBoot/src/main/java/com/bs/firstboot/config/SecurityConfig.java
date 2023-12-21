package com.bs.firstboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import com.bs.firstboot.common.MyAuthority;
import com.bs.firstboot.security.DBConnectionProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//SecurityFilterChain클래스를 bean으로 등록
	@Autowired
	private DBConnectionProvider dbprovider;
	
	
	@Bean 
	SecurityFilterChain authenticationPath(HttpSecurity http) 
			throws Exception{
		return http
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(request->{
					request.requestMatchers("/").permitAll()
					.requestMatchers(req->CorsUtils.isPreFlightRequest(req)).permitAll()
					//CorsUtils::isPreFlightRequest).permitAll()
					.requestMatchers("/WEB-INF/views/**").permitAll()
					.requestMatchers("/members").hasAnyAuthority(MyAuthority.ADMIN.name())
					.anyRequest().authenticated();
				})
				.formLogin(formlogin->{
					formlogin.loginProcessingUrl("/logintest");
//					.failureForwardUrl("/loginfail")
//					.successForwardUrl("/loginsuccess");
				})
				.logout(logout->logout.logoutUrl("/logout"))
				.authenticationProvider(dbprovider)
				.build();
//				.csrf()
//					.disable()
//				.formLogin()
//					.loginPage("")
//					.successForwardUrl(null)
	}
	
	
}







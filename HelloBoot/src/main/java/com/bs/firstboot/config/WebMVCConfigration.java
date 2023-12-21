package com.bs.firstboot.config;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.bs.firstboot.common.LoggerInterceptor;
import com.bs.firstboot.model.dto.Member;

@Configuration //WebMvcConfigurer 인터페이스
@MapperScan("com.bs.firstboot.common.mapper")
public class WebMVCConfigration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//인터셉터등록할때 사용하는 메소드 
		registry.addInterceptor(loggerInter())
		.addPathPatterns("/**");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// view 연결해주는 로직을 코드로 작성할때 사용
		registry.addViewController("/board/boardlist")
		.setViewName("board/boardlist");
		registry.addViewController("/chatpage").setViewName("chatting");
	}
	
	@Bean 
	LoggerInterceptor loggerInter() {
		return new LoggerInterceptor();
	}
	
	@Bean
	Member member() {
		return new Member();
	}
	
	@Bean 
	@Primary
	HandlerExceptionResolver handlerExceptionResolver2() {
		Properties exceptionProp=new Properties();
		exceptionProp.setProperty(IllegalArgumentException.class.getName(),
				"error/argumentException");
		
		SimpleMappingExceptionResolver resolve=new SimpleMappingExceptionResolver();
		resolve.setExceptionMappings(exceptionProp);
		resolve.setDefaultErrorView("error/error");
		return resolve;
	}

	//외부에서 js로 요청한것에 대한 허용하기
	//cors : 다른서버에서 js로 요청한 내용을 차단함
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:3000");
	
	}

	
}

package com.daoxue.zhidao.ocr.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Lum
 *
 */
@Configuration
public class LoginWebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private LoginWebMvcInterceptor loginWebMvcInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(loginWebMvcInterceptor).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}

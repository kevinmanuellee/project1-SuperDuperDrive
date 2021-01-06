package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@SpringBootApplication
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		multipartResolver.setMaxUploadSize(5242880);
		return multipartResolver;
	}

	@Bean
	public FilterRegistrationBean multipartFilterRegistrationBean() {
		final MultipartFilter multipartFilter = new MultipartFilter();
		final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(multipartFilter);
		filterRegistrationBean.addInitParameter("multipartResolverBeanName", "multipartResolver");
		return filterRegistrationBean;
	}
}

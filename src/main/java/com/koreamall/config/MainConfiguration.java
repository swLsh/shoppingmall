package com.koreamall.config;


import com.koreamall.converter.FileConverter;
import com.koreamall.converter.FilesConverter;
import com.koreamall.converter.ImageFileConverter;
import com.koreamall.interceptor.CategoryInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MainConfiguration implements WebMvcConfigurer {
    @Autowired
    CategoryInterceptor categoryInterceptor;


    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(categoryInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/category/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new FileConverter());
        registry.addConverter(new FilesConverter());
        registry.addConverter(new ImageFileConverter());
    }
}

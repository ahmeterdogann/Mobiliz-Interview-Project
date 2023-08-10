package com.ahmeterdogan.feign.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfiguration {

    @Bean
    public HttpMessageConverters customHttpMessageConverters() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        // Diğer HttpMessageConverter'ları da ekleme işlemini burada yapabilirsiniz.
        
        return new HttpMessageConverters(jsonConverter);
    }
}
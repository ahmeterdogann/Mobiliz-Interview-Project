package com.ahmeterdogan.feign.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


//Api Gateway'i Spring 3 ile çalıştıramadığım için Spring 2.7.14 kullandım bu nedenle Feign'in bu versiyonunda böyle bir bean tanımlamak durumunda kaldım.
@Configuration
public class FeignConfiguration {

    @Bean
    public HttpMessageConverters customHttpMessageConverters() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

        return new HttpMessageConverters(jsonConverter);
    }
}
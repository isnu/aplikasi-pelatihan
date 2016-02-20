package com.ajariaku.pelatihan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class KonfigurasiWeb extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //bisa ditambahkan sebanyak-banyaknya untuk template yang tidak ada controllernya
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/materi/list").setViewName("/materi/list");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThymeleafLayoutInterceptor());
    }

}

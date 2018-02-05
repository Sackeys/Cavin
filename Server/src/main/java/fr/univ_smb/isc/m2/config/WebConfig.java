package fr.univ_smb.isc.m2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("fr.univ_smb.isc.m2")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/style/**").addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/view/**").addResourceLocations("/WEB-INF/html/");
        registry.addResourceHandler("/script/**").addResourceLocations("/WEB-INF/js/");
    }


}
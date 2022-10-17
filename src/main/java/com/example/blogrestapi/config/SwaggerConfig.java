package com.example.blogrestapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfig {
    private Info apiInfo(){
        return new Info()
                .title("Spring Boot Blog REST APIs")
                .description("Spring Boot Blog REST API Documentation")
                .version("0.1")
                .contact(new Contact()
                        .email("muradkhannovruzov@gmail.com")
                        .name("muradxan")
                        .url("www.muradkhannovruzov.com")
                );
    }
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(apiInfo());
    }
}

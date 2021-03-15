package com.erwan.human.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(path())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private Predicate<String> path() {
        return Predicates.not(PathSelectors.regex("/error"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Human Cloning API",
                "API for creating clone to fight in the clones war",
                "1.0.O",
                "",
                new Contact("Erwan Le Tutour", "https://github.com/ErwanLT", "erwanletutour.elt@gmail.com"),
                "MIT License",
                "https://opensource.org/licenses/mit-license.php",
                Collections.emptyList());
    }
}

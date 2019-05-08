package com.marketlogicsoftware.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    /**
     * Product api.
     *
     * @return the docket
     */
    @Bean
    public Docket productApi() {
        return new Docket( DocumentationType.SWAGGER_2 ).select().apis( RequestHandlerSelectors.basePackage( "com.marketlogicsoftware.controller" ) ).paths( regex( "/.*" ) ).build()
                                                        .apiInfo( metaData() );
    }

    /**
     * Meta data.
     *
     * @return the api info
     */
    private ApiInfo metaData() {
        return new ApiInfoBuilder().title( "Market Logic Software" ).description( "Polling backend endpoints" ).version( "v1" ).build();
    }
}

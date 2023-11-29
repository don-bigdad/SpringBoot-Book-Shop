package com.example.springbootbookshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    protected static final String TITLE = "Book Open API";
    protected static final String DESCRIPTION = "This is a book management API";
    protected static final String VERSION = "1.0";

    private Info getInfo() {
        return new Info()
                .title(TITLE)
                .version(VERSION)
                .description(DESCRIPTION);
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-token", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-token"))
                .info(getInfo());
    }
}

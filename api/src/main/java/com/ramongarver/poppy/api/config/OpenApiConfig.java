package com.ramongarver.poppy.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Poppy API")
                .summary("API REST for the ESN management application Poppy")
                .description("")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("Ramón García Verjaga").url("").email(""))
                .license(new License().name("Copyright © 2023 Ramón García Verjaga").url(""))
                .version("0.4.0"))
            .components(new Components()
                .addSecuritySchemes("JWT Bearer Authentication", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }

}

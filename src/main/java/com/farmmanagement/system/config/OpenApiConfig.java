package com.farmmanagement.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Farm Management System API", version = "v1"),
    security = @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME_NAME)
)
@SecurityScheme(
    name = OpenApiConfig.BEARER_SCHEME_NAME,
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {

    static final String BEARER_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI farmManagementOpenAPI() {
        return new OpenAPI()
            .components(new Components().addSecuritySchemes(
                BEARER_SCHEME_NAME,
                new io.swagger.v3.oas.models.security.SecurityScheme()
                    .name(BEARER_SCHEME_NAME)
                    .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            ))
            .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList(BEARER_SCHEME_NAME));
    }
}

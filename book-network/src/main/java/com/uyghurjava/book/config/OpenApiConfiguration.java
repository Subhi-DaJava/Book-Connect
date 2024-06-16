package com.uyghurjava.book.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Uyghur Java",
                        email = "contact@uyghurjava.com",
                        url = "https://www.uyghurjava.com"),
                title = "Book Network Open API Specification",
                version = "1.0",
                description = "Book Network API Documentation",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                ),
                termsOfService = "https://www.uyghurjava.com/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:9000/api/v1",
                        description = "Local Server"
                ),

                @Server(
                        url = "https://api.uyghurjava.com/api/v1",
                        description = "Production Server"
                )
        },
        externalDocs = @ExternalDocumentation(
                description = "Find out more about Uyghur Java",
                url = "https://www.uyghurjava.com"
        ),
        tags = {
                @Tag(name = "books", description = "Book Operations"),
                @Tag(name = "users", description = "User Operations")
        },

        security = {
                @SecurityRequirement(name = "BearerTokenAuthentication")
        }

)

@SecurityScheme(
        name = "BearerTokenAuthentication",
        description = "Jwt Token Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}

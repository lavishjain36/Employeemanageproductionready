package com.employeemangement.empcursodemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management System API")
                        .description("RESTful API for managing employee data with CRUD operations, search, and filtering capabilities.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Employee Management Team")
                                .email("admin@employeemgmt.com")
                                .url("https://github.com/employeemgmt/empcursodemo"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.employeemgmt.com")
                                .description("Production Server")
                ));
    }
}

package com.maxbyte.sam.SecondaryDBFlow.Swagger;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@OpenAPIDefinition(

        info = @Info(
                description = "SAM documentation for Spring Security",
                title = "SAM Application APIs",
                version = "1.0"
        ),
        servers = {

                @Server(
                        description = "Development",                   /////////\\\\\\\\\\\\\\\\\\\\\\\\\\
                        url = "http://192.168.5.47:8000"
                        //url = "http://localhost:8000"
                ),
                @Server(
                        description = "Local",
                        url = "http://localhost:8000"
                ),


                @Server(
                        description = "Production"
                        //url = "https://maxbyte.co/"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

@ComponentScan({"com.maxbyte.sam.SecondaryDBFlow.Helper.SearchCriteria"})
@EnableWebSecurity
public class SwaggerConfiguration  implements WebMvcConfigurer {

        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
                configurer.addPathPrefix("sam", HandlerTypePredicate.forAnnotation(RestController.class));
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/images/*").addResourceLocations("target/classes/static/images");
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST","PUT","DELETE","PATCH");
        }

}
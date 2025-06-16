package com.auto_gyn_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "${info.build.name}",
                version = "${info.build.version}",
                description = "${info.app.description}",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Pedro Queiroz Lima Barreto",
                        email = "pqlb1512@gmail.com")))

@SpringBootApplication
public class AutoGynBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoGynBackendApplication.class, args);
    }

}

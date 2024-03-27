package org.example.echiktsabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.coreapi", "org.example.echiktsabackend"})
public class EchiktsaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchiktsaBackendApplication.class, args);
    }

}

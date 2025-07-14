package com.app.vibely;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VibelyApplication {

    public static void main(String[] args) {

        // Load .env file
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

        // Inject each variable into system properties
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        // Run Spring Boot
        SpringApplication.run(VibelyApplication.class, args);
    }
}

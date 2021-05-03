package org.fastddd.sample.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.fastddd"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
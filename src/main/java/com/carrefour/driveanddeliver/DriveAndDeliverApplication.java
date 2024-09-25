package com.carrefour.driveanddeliver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DriveAndDeliverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriveAndDeliverApplication.class, args);
    }

}

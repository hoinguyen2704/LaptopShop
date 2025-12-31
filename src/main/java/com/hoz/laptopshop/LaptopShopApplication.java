package com.hoz.laptopshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude =
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
//@SpringBootApplication(excludeName = {
//        "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
//})
public class LaptopShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaptopShopApplication.class, args);
    }

}

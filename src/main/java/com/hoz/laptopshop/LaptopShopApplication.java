package com.hoz.laptopshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication()
public class LaptopShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaptopShopApplication.class, args);
    }

}

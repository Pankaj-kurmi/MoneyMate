package org.example.moneymate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MoneyMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyMateApplication.class, args);
    }

}

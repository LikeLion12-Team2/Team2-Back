package org.smlions.comfortplant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComfortplantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComfortplantApplication.class, args);
    }

}

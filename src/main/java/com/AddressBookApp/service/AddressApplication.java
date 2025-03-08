package com.AddressBookApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class AddressApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AddressApplication.class, args);
        String activeProfile = context.getEnvironment().getActiveProfiles().length > 0
                ? context.getEnvironment().getActiveProfiles()[0]
                : "default";
        log.info("Address Book App Started in {} environment", activeProfile);
    }

}
package com.example.restimage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class Config {
    @Bean
    @Scope("prototype")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Map<String, String> countries() throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();

        try (Scanner scanner = new Scanner(new FileReader("geonames/countries.txt"))) {
            while (scanner.hasNextLine()) {
                String[] columns = scanner.nextLine().split("\t");
                map.put(columns[0], columns[1].toLowerCase());
            }
        }

        return map;
    }
}

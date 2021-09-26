package com.example.restimage.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/restcountries.eu/rest/v2")
public class FlagsController {
    @Value("${upload.path}")
    private String path;

    private Map<String, String> countries;
    private RestTemplate restTemplate;

    @Autowired
    public FlagsController(Map<String, String> countries, RestTemplate restTemplate) {
        this.countries = countries;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    public void getAllFlag() {
        for (Map.Entry<String, String> c : countries.entrySet()) {
            getFlag(c.getKey());
        }
    }

    @GetMapping("/{country}")
    public void getFlag(@PathVariable("country") String country) {

        String countryLiterals = countries.get(country);
        String url = "https://img.geonames.org/flags/x/" + countryLiterals + ".gif";

        ByteArrayResource byteArrayResource = null;
        try {
            byteArrayResource = restTemplate.getForObject(url, ByteArrayResource.class);
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream(path + countryLiterals + ".gif")) {
            fos.write(byteArrayResource.getByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.filtering.micro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.filtering.micro.domain.MyData;
import com.filtering.micro.domain.MyData2;
import com.filtering.micro.domain.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
public class FilterController {

    @Value("${microserviceA.url}")
    private String microserviceAUrl;

    @Value("${microserviceB.url}")
    private String microserviceBUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @PostMapping("/api/{microserviceName}")
    public ResponseEntity<String> receiveFilterProperties(
            @PathVariable String microserviceName,
            @RequestParam int userId,
            @RequestBody Payload payload
    ) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Payload> requestEn = new HttpEntity<>(payload, headers);
            ResponseEntity<?> response = null;

            ObjectMapper objectMapper = new ObjectMapper();

            Class<?> responseType = null;
            String filterName = null;

            if (Objects.equals(microserviceName, "microserviceA")) {
                responseType = MyData.class;
                filterName = "myData";
                response = restTemplate.exchange(microserviceAUrl, HttpMethod.POST, requestEn, responseType);
            } else if (Objects.equals(microserviceName, "microserviceB")) {
                responseType = MyData2.class;
                filterName = "myData2";
                response = restTemplate.exchange(microserviceBUrl, HttpMethod.POST, requestEn, responseType);
            }

            if (response != null) {
                Object responseBody = response.getBody();
                if (responseBody != null) {
                    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(env.getProperty(microserviceName + ".user" + userId + ".filter-include-fields").split(","));
                    FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
                    String filteredJson = objectMapper.writer(filters).writeValueAsString(responseBody);
                    return ResponseEntity.ok().body("Filtered JSON of " + microserviceName + " -> " + filteredJson);
                }
            }

            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
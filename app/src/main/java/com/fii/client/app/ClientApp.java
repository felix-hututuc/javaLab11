package com.fii.client.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ClientApp {

    final Logger log = LoggerFactory.getLogger(ClientApp.class);
    final String uri = "http://localhost:8082/friends";
    @GetMapping("/friends")
    public List<Friends> getFriendsList() {
        log.info("Start");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Friends>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>(){});
        List<Friends> result = response.getBody();
        assert result != null;
        result.forEach(p -> log.info(p.toString()));
        log.info("Stop");
        return result;
    }

}

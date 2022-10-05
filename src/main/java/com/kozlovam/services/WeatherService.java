package com.kozlovam.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@Service
public class WeatherService {
    @Value("${WEATHER_KEY}")
    private String weatherKey;

    @Autowired
    RestTemplate restTemplate;

    public WeatherService() {
    }

    public String getJSON(String city){
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+weatherKey+"&units=metric&lang=ru";
        try{
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherResult result = objectMapper.readValue(response, WeatherResult.class);
            return "Город:" + city + "\nТемпература: " + result.main.temp.toString();
        }
        catch (IOException e){
            System.out.println(e);
        }
        return "Город не найден";
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherResult {
    @JsonProperty Main main;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Main {
        @JsonProperty Double temp;
    }
}

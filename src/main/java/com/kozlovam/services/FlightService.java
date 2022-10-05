package com.kozlovam.services;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kozlovam.models.Flight;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

@Service
public class FlightService {
    @Value("${aviasales_token}")
    String token;

    @Autowired
    RestTemplate restTemplate;

    private String aviasales_url = "https://www.aviasales.ru";

    public JSONObject getJSON(Flight flight){
        String url = "https://api.travelpayouts.com/aviasales/v3/prices_for_dates?origin="
                + flight.getOrigin() +"&destination="
                + flight.getDestination() +"&departure_at="
                + flight.getDeparture_at() + "&return_at="
                + flight.getReturn_at() + "&sorting=price&token="
                +token;
        try{
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            TicketsResult result = objectMapper.readValue(response, TicketsResult.class);
            JSONArray res = new JSONArray();
            JSONObject object = new JSONObject();
            for(int i = 0 ; i < result.data.size(); i++){
                JSONObject obj = new JSONObject();
                obj.put("origin", result.data.get(i).origin);
                obj.put("destination", result.data.get(i).destination);
                obj.put("origin_airport", result.data.get(i).origin_airport);
                obj.put("destination_airport", result.data.get(i).destination_airport);
                obj.put("price", result.data.get(i).price);
                obj.put("departure_at", result.data.get(i).departure_at);
                obj.put("return_at", result.data.get(i).return_at);
                obj.put("link", aviasales_url + result.data.get(i).link);
                res.put(obj);
                object.put("list", res);
            }
            return object;
        }
        catch (IOException e){
            System.out.println(e);
        }
        return null;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TicketsResult{
    @JsonProperty
    ArrayList <Data> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Data{
        @JsonProperty String origin;
        @JsonProperty String destination;
        @JsonProperty String origin_airport;
        @JsonProperty String destination_airport;
        @JsonProperty String price;
        @JsonProperty String departure_at;
        @JsonProperty String return_at;
        @JsonProperty String link;
    }
}


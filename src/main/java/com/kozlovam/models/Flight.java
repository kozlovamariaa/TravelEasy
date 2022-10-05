package com.kozlovam.models;


public class Flight {
    private String origin;
    private String destination;
    private String departure_at;
    private String return_at;

    public void Flight(){
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture_at() {
        return departure_at;
    }

    public void setDeparture_at(String departure_at) {
        this.departure_at = departure_at;
    }

    public String getReturn_at() {
        return return_at;
    }

    public void setReturn_at(String return_at) {
        this.return_at = return_at;
    }
}

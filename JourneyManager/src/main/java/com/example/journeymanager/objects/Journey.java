package com.example.journeymanager.objects;

import java.util.ArrayList;

public class Journey {
    private String location;
    private ArrayList<String> travelMates;
    private String Date;

    public Journey(String location, ArrayList<String> travelMates, String date) {
        this.Date = date;
        this.travelMates = travelMates;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public ArrayList<String> getTravelMates() {
        return travelMates;
    }

    public void setTravelMates(ArrayList<String> travelMates) {
        this.travelMates = travelMates;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}

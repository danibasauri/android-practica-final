package com.example.journeymanager.objects;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JourneyConstantList extends Application {
    HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
    public static Integer contId = 7;
    public static Journey selectedJourney;

    public HashMap<Integer, Journey> getJourneys() {
        journeys = new HashMap<Integer, Journey>();

        Journey journeyExample0 = new Journey("Bilbao", new ArrayList<String>(Arrays.asList("Pedro", "Manuel", "María")), "13/01/2014");
        journeys.put(0, journeyExample0);

        Journey journeyExample1 = new Journey("Sevilla", new ArrayList<String>(Arrays.asList("Pedro")), "28/01/2014");
        journeys.put(1, journeyExample1);

        Journey journeyExample2 = new Journey("Barcelona", new ArrayList<String>(Arrays.asList("Maria", "Dani", "Jesus", "Nacho")), "15/02/2014");
        journeys.put(2, journeyExample2);

        Journey journeyExample3 = new Journey("Cuenca", new ArrayList<String>(Arrays.asList("Manuel", "Carlos", "Alberto", "Ernesto", "Natalia")), "03/08/2014");
        journeys.put(3, journeyExample3);

        Journey journeyExample4 = new Journey("Zaragoza", new ArrayList<String>(Arrays.asList("Lucía", "Dani", "Pedro", "María")), "01/01/2015");
        journeys.put(4, journeyExample4);

        Journey journeyExample5 = new Journey("Teruel", new ArrayList<String>(), "30/05/25");
        journeys.put(5, journeyExample5);
        return journeys;
    }

    public Journey getItemById(int id) {
        return journeys.get(id);
    }

    public void saveJourney(Journey journey) {
        selectedJourney = journey;
    }

    public void deleteJourney(int position) {
        journeys.remove(position);
    }
}

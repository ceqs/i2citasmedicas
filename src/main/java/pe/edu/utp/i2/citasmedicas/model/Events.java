package pe.edu.utp.i2.citasmedicas.model;

import java.util.ArrayList;
import java.util.List;

public class Events {
    private List<Event> events;

    public List<Event> getEvents() {
        if(events == null) events = new ArrayList<>();
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

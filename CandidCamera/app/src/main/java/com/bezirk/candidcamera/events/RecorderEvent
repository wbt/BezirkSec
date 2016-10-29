package com.bezirk.candidcamera.events;
import com.bezirk.middleware.messages.Event;

public class RecorderEvent extends Event {
    private String action;
    private int area;


    public RecorderEvent(String action, int area) {
        this.action = action;
        this.area = area;
    }

    public String getAction() {
        return action;
    }
    public String getArea() {
        return area;
    }

    public String toString() {
        return String.format("Recorder Action: %s for area: %s",
               action);
    }
}

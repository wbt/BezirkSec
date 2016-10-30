package com.bezirk.candidcamera.listeners;

import com.bezirk.candidcamera.events.DoorCloseEvent;
import com.bezirk.candidcamera.events.DoorOpenEvent;
import com.bezirk.candidcamera.events.RecorderEvent;
import com.bezirk.candidcamera.events.VicinityEvent;
import com.bezirk.candidcamera.utilities.SendMail;
import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;


public class RecorderController {
    public static void main(String args[]) {
        RecorderController recorderController = new RecorderController();
        System.out.println("Controller listening...");
    }
    public RecorderController(){
        BezirkMiddleware.initialize();
        final Bezirk bezirk = BezirkMiddleware.registerZirk("Recorder Controller Zirk");
        EventSet events = new EventSet(DoorCloseEvent.class,DoorOpenEvent.class,VicinityEvent.class);
        final int recLengthAfterDoorClose = 30000; // milliseconds. //TODO: make configurable

        events.setEventReceiver(new EventSet.EventReceiver() {
            long lastStart =0;
            RecorderEvent recorderEvent;
            @Override
            public void receiveEvent(Event event, ZirkEndPoint sender) {
                //Check if this event is of interest
                if(event instanceof DoorOpenEvent) {
                    final DoorOpenEvent doorOpen = (DoorOpenEvent) event;
                    System.out.println("Door Open event: " + doorOpen.toString());
                    recorderEvent = new RecorderEvent("start",1);
                    bezirk.sendEvent(recorderEvent);
                    lastStart = System.currentTimeMillis();
                }
                else if(event instanceof VicinityEvent) {
                    final VicinityEvent vicinityEvent = (VicinityEvent) event;
                    System.out.println("Vicinity event received. Is phone near beacon? " +
                            vicinityEvent.isInVicinity());
                    if(System.currentTimeMillis()<lastStart+recLengthAfterDoorClose){
                        // todo: allow for caching in case signals are received out of order
                        if(vicinityEvent.isInVicinity()) {
                            recorderEvent = new RecorderEvent("cancel", 1);
                            bezirk.sendEvent(recorderEvent);
                        }
                    }
                } else if (event instanceof DoorCloseEvent) {
                    final DoorCloseEvent doorClose = (DoorCloseEvent) event;
                    System.out.println("Door Close event: " + doorClose.toString());
                    try{
                        Thread.sleep(recLengthAfterDoorClose);
                    }
                    catch (InterruptedException ie){
                        System.err.println(ie);
                    }
                    //TODO: Add event identifier (e.g. timestamp of start)
                    //to group door events together.
                    // Also consider refactoring to have a single DoorEvent with open/close as data in it.
                    recorderEvent = new RecorderEvent("finish",1);
                    bezirk.sendEvent(recorderEvent);
                    sendNotifications(); //email etc.; devices on the network can catch the event just sent.
                }
            }
        });

        bezirk.subscribe(events);
    }

    private void sendNotifications() {
        SendMail.sendMail();
        //TODO: Any other notifications, such as SMS/MMS, ideally configurable through UI.
    }
}

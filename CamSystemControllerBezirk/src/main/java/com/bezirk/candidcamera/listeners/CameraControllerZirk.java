package com.bezirk.candidcamera.listeners;
/**
 * Created by wbt on 10/29/2016.
 */
import com.bezirk.candidcamera.events.RecorderEvent;
import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CameraControllerZirk {

    private String serverAddress = "http://127.0.0.1:8080/"; //TODO: Make configurable/learn from environment
    public static void main (String args[]) {
        CameraControllerZirk cameraControllerZirk = new CameraControllerZirk();
        System.out.println("Listening for recorder events...");
    }
        public CameraControllerZirk() {
            BezirkMiddleware.initialize();
            final Bezirk bezirk = BezirkMiddleware.registerZirk("Camera Controller Zirk");
            EventSet recorderEvents = new EventSet(RecorderEvent.class);
            recorderEvents.setEventReceiver(new EventSet.EventReceiver() {
                @Override
                public void receiveEvent(Event event, ZirkEndPoint sender) {
                    //Check if this event is of interest
                    if (event instanceof RecorderEvent) {
                        final RecorderEvent recEvent = (RecorderEvent) event;
                        System.out.println("Received recorder event: " + recEvent.toString());
                        //do something in response to this event
                        if (recEvent.getAction().equals("start")) {
                            System.out.println("About to start recording.");
                            startRecording(recEvent.getArea());
                        }
                        if (recEvent.getAction().equals("cancel")) {
                            System.out.println("Cancelling recording.");
                            cancelRecording(recEvent.getArea());
                        }
                        if (recEvent.getAction().equals("finish")) {
                            System.out.println("Finishing recording.");
                            finishRecording(recEvent.getArea());
                        }
                    }
                }
            });

            bezirk.subscribe(recorderEvents);
        }


    private void startRecording(int area) {
        //callAction("allon"); //turns all devices on; may be good step in case any were turned off unintentionally
        callActionOnEachCameraInArea("record", area);
    }
    private void cancelRecording(int area) {
        callActionOnEachCameraInArea("recordstop",area);
        //TODO: Clear out partial recording.
    }
    private void finishRecording(int area) {
        callActionOnEachCameraInArea("recordstop",area);
        //callActionOnEachCameraInArea("getcontentlist",area);
        // TODO: Process generated files as desired,
        //  signal controller with information about file names, still image, and/or link to video
        // Could call notification code here instead of in event sender, but then will have multiple
        // notifications if there are multiple devices running this camera-driving code.
    }
    private void callActionOnEachCameraInArea(String action, int area) {
        //TODO: Get a set of all the devices associated with the given area,
        //and for each an object type and id.
        //Object type (param ot) is 1 for microphones and 2 for cameras.
        //Put the current contents of this method in a loop iterating over that list,
        //setting ot and oid from the device values.
        int ot = 2;
        int oid = 1;
        callAction(action+"?ot="+ot+"&oid="+oid);
    }

    private void callAction(String action) {
        System.out.println("Starting recording.");
        try {
            URL myURL = new URL(serverAddress+action);
            System.out.println("Sending HTTP message to "+myURL+"  Response: ");
            URLConnection myURLConnection = myURL.openConnection();
            //myURLConnection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myURLConnection.getInputStream()));
            String inputLine; //TODO: Better parsing and error handling.
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        }
        catch (MalformedURLException e) {
            System.out.println("new URL() failed.");
        }
        catch (IOException e) {
            System.out.println("openConnection() failed.");
        }
    }

}
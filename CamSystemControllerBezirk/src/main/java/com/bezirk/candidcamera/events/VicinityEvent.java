package com.bezirk.candidcamera.events;

import com.bezirk.middleware.messages.Event;

/**
 * Created by wbt on 10/29/2016.
 */

public class VicinityEvent extends Event {
    private final boolean inVicinity;

    public VicinityEvent(boolean inVicinity) {
        this.inVicinity = inVicinity;
    }

    public boolean isInVicinity() {
        return this.inVicinity;
    }
}

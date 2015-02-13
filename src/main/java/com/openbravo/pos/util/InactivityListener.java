package com.openbravo.pos.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InactivityListener implements ActionListener, AWTEventListener {

    public final static long KEY_EVENTS = AWTEvent.KEY_EVENT_MASK;

    public final static long MOUSE_EVENTS =
        AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK;

    public final static long USER_EVENTS = KEY_EVENTS + MOUSE_EVENTS;

    private final Action action;
    private int interval;
    private final long eventMask;
    private final Timer timer = new Timer(0, this);


// Specify the inactivity interval and listen for USER_EVENTS

    public InactivityListener(Action action, int seconds) {
        this.action = action;
        this.eventMask = USER_EVENTS;
        timer.setInitialDelay(seconds);
    }

    public void start() {
        timer.setRepeats(false);
        timer.start();
        Toolkit.getDefaultToolkit().addAWTEventListener(this, eventMask);
    }

    public void stop() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
        timer.stop();
    }

//  Implement ActionListener for the Timer
    @Override
    public void actionPerformed(ActionEvent e) {
        action.actionPerformed(e);
    }

//  Implement AWTEventListener, all events are dispatched via this
    @Override
    public void eventDispatched(AWTEvent e) {
        if (timer.isRunning()) {
            timer.restart();
        }
    }
// Impement a manually triggered restart

    public void restart() {
        timer.restart();
    }

    public void setRunning() {
        if (!timer.isRunning())
            //timer.start();
        {
            timer.restart();
        }
    }

}


package com.jasonspriggs.lp4jmk2;

import java.util.concurrent.CountDownLatch;

import net.thecodersbreakfast.lp4j.api.*;
import net.thecodersbreakfast.lp4j.midi.*;

public class Runner {

    private static CountDownLatch stop = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

    	Launchpad launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
        LaunchpadClient client = launchpad.getClient();

        MyListener myListener = new MyListener(client);
        launchpad.setListener(myListener);

        // Set a red light under the STOP button
        client.reset();
        //client.setButtonLight(Button.STOP, Color.RED, BackBufferOperation.NONE);
        client.scrollText("test", Color.YELLOW, ScrollSpeed.SPEED_MIN, false, BackBufferOperation.CLEAR);
        
        stop.await();
        client.reset();
        launchpad.close();
    }

    public static class MyListener extends LaunchpadListenerAdapter {

        private final LaunchpadClient client;

        public MyListener(LaunchpadClient client) {
            this.client = client;
        }

        @Override
        public void onPadReleased(Pad pad, long timestamp) {
            System.out.println(pad);
        }

        @Override
        public void onButtonReleased(Button button, long timestamp) {
        	System.out.println(button);
        }
    }

}

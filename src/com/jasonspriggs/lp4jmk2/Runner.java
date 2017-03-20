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
        
        for(int x = 0; x <= Color.MAX_INTENSITY; x++) {
        	for(int y = 0; y <= Color.MAX_INTENSITY; y++) {
        		client.setPadLight(Pad.at(x, y), Color.of(x, y), BackBufferOperation.NONE);
        	}
        }
        
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
            System.out.println(pad + "\t" + timestamp);
            client.setPadLight(pad, Color.YELLOW, BackBufferOperation.CLEAR);
            
        }

        @Override
        public void onButtonReleased(Button button, long timestamp) {
        	System.out.println(button);
            client.setButtonLight(button, Color.AMBER, BackBufferOperation.CLEAR);
            if(button == Button.MIXER) {
            	client.reset();
            }
        }
    }

}

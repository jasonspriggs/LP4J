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
        
        for(int y = 7; y >= 0; y--) {
        	for(int x = 0; x <= 7; x++) {
        		client.setPadLight(Pad.at(x, y), Color.BLACK, BackBufferOperation.NONE);
        	}
    		client.setButtonLight(Button.atTop(y), Color.BLACK, BackBufferOperation.NONE);
    		client.setButtonLight(Button.atRight(y), Color.BLACK, BackBufferOperation.NONE);
        }
        
        //client.setButtonLight(Button.atTop(y), Color.BLACK, BackBufferOperation.NONE);
        
        
        
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
            //client.setPadLight(pad, Color.YELLOW, BackBufferOperation.CLEAR);
            
        }

        @Override
        public void onButtonReleased(Button button, long timestamp) {
        	System.out.println(button);
            //client.setButtonLight(button, Color.AMBER, BackBufferOperation.CLEAR);
            if(button == Button.SND_A) {
            	int c = 0;
                for(int y = 7; y >= 0; y--) {
                	for(int x = 0; x <= 7; x++) {
                		client.setPadLight(Pad.at(x, y), Color.of(c), BackBufferOperation.NONE);
                		c++;
                	}
            		client.setButtonLight(Button.SND_A, Color.of(3), BackBufferOperation.NONE);
            		client.setButtonLight(Button.SND_B, Color.BLACK, BackBufferOperation.NONE);
                }
            } else if(button == Button.SND_B) {
            	int c = 64;
                for(int y = 7; y >= 0; y--) {
                	for(int x = 0; x <= 7; x++) {
                		client.setPadLight(Pad.at(x, y), Color.of(c), BackBufferOperation.NONE);
                		c++;
                	}
            		client.setButtonLight(Button.SND_A, Color.BLACK, BackBufferOperation.NONE);
            		client.setButtonLight(Button.SND_B, Color.of(3), BackBufferOperation.NONE);
                }
            }
        }
    }

}

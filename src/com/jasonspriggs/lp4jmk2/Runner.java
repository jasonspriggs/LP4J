package com.jasonspriggs.lp4jmk2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import net.thecodersbreakfast.lp4j.api.*;
import net.thecodersbreakfast.lp4j.midi.*;

public class Runner {

    private static CountDownLatch stop = new CountDownLatch(1);
    private static String ip;
    private LinkedList<Light> lights = new LinkedList<Light>();

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

    	Scanner sc = new Scanner(System.in);
    	System.out.print("Light IP: ");
    	ip = sc.next();
    	HttpURLConnection con1 = (HttpURLConnection) new URL("http://" + ip + "/clear").openConnection();
    	con1.setRequestMethod("GET");
        
        if(con1.getResponseCode() == 200)
	        client.setButtonLight(Button.SESSION, Color.GREEN, BackBufferOperation.NONE);
        
        
        
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
            if(button == Button.SND_A) {
            	int c = 0;
                for(int y = 7; y >= 0; y--) {
                	for(int x = 0; x <= 7; x++) {
                		client.setPadLight(Pad.at(x, y), Color.of(c), BackBufferOperation.NONE);
                		c++;
                	}
                }
            } else if(button == Button.SND_B) {
            	int c = 64;
                for(int y = 7; y >= 0; y--) {
                	for(int x = 0; x <= 7; x++) {
                		client.setPadLight(Pad.at(x, y), Color.of(c), BackBufferOperation.NONE);
                		c++;
                	}
                }
            } else if(button == Button.STOP) {
                for(int y = 7; y >= 0; y--) {
                	for(int x = 0; x <= 7; x++) {
                		client.setPadLight(Pad.at(x, y), Color.BLACK, BackBufferOperation.NONE);
                	}
                }
                
            	HttpURLConnection con2;
				try {
					con2 = (HttpURLConnection) new URL("http://" + ip + "/add_saved_animation/Off").openConnection();
	            	con2.setRequestMethod("POST");
	            	con2.getResponseCode();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
    }

}

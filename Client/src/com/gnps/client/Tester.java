package com.gnps.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.gnps.lib.event.EventBus;

public class Tester {
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket(3234);
		
		DatagramPacket p = new DatagramPacket(null, 0, null, 0);
		ds.send(p);
		ds.close();
		
		try(EventBus bus = new EventBus(null, null)) {
			bus.publish(null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

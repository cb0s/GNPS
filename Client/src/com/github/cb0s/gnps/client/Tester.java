package com.github.cb0s.gnps.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.github.cb0s.gnps.lib.event.EventBus;

public class Tester {
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket(3234);
		
		DatagramPacket p = new DatagramPacket(null, 0, null, 0);
		ds.send(p);
		ds.close();
		
		try(EventBus bus = new EventBus(null, null)) {
			bus.publish(null);
		}
	}
}

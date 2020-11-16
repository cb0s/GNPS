package com.sjgs.ping.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Tester {
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket(3234);
		
		DatagramPacket p = new DatagramPacket(null, 0, null, 0);
		ds.send(p);
		ds.close();
	}
}

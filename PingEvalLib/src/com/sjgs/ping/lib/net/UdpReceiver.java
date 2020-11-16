package com.sjgs.ping.lib.net;

public abstract class UdpReceiver implements Runnable {
	public abstract void onReceive();
	
	@Override
	public void run() {
		while (running) {
			
		}
	}
	
	private volatile boolean running = false;
}

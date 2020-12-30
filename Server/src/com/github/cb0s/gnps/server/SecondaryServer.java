package com.github.cb0s.gnps.server;

import com.github.cb0s.gnps.lib.event.EventBus;

public class SecondaryServer {
	public static void main(String[] args) {
		try(EventBus bus = new EventBus(null, null)) {
			bus.publish(null);
		}
	}
}

package com.github.cb0s.gnps.supervisor;

import com.github.cb0s.gnps.lib.event.EventBus;

public class MainServer {
	public static void main(String[] args) {
		try(EventBus bus = new EventBus(null, null)) {
			bus.publish(null);
		}
	}
}

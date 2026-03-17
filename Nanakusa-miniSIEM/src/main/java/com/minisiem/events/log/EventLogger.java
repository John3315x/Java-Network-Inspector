package com.minisiem.events.log;

import com.minisiem.model.Event;

public class EventLogger {

	public static void onDeviceConnected(Event event) {
		System.out.println("🟢" + event.getTitle() + " [" + event.getDescription() + "] " + event.getDateTime() + " "
				+ event.getCode());
	}

	public static void onDeviceDisconnected(Event event) {
		System.out.println("🔘" + event.getTitle() + " [" + event.getDescription() + "] " + event.getDateTime() + " "
				+ event.getCode());

	}
}

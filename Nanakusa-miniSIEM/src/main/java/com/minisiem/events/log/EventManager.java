package com.minisiem.events.log;

import java.time.LocalDateTime;

import com.minisiem.model.Device;
import com.minisiem.model.Event;
import com.minisiem.repository.EventRepository;

/**
 * 
 */
public class EventManager {
	
	public static void deviceConnected(Event event) {
		new EventRepository().create(event);
		EventLogger.onDeviceConnected(event);
	}
	
	public static void deviceDisconnected(Event event, Device device) {
		event.setDateTime(LocalDateTime.now());
		new EventRepository().create(event);
		EventLogger.onDeviceDisconnected(event);
	}
}

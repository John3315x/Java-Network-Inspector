package com.minisiem.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.minisiem.EventIDs;

public class Event {

	private final int id;
	private final EventIDs type;
	private final String code, title, description;
	private LocalDateTime dateTime;

	/**
	 * Para recibir desde BBDD
	 * 
	 * @param id
	 * @param code
	 * @param title
	 * @param description
	 * @param dateTime
	 */
	public Event(int id, String code, EventIDs type, String title, String description, LocalDateTime dateTime) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.title = title;
		this.description = description;
		this.dateTime = dateTime;
	}

	public Event(EventIDs type, String title, String description) {
		super();
		this.id = -1;
		this.type = type;
		this.code = "E-" + UUID.randomUUID();
		this.title = title;
		this.description = description;
		this.dateTime = LocalDateTime.now();
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public EventIDs getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", code=" + code + ", type=" + type + ", title=" + title + ", description="
				+ description + ", dateTime=" + dateTime + "]";
	}

}

package com.minisiem.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.minisiem.TaskIDs;

public class Task {

	private final int id;
	private final TaskIDs type;
	private final String code, title, description;
	private TaskIDs status;
	private LocalDateTime dateTime;

	/**
	 * Para recibir desde la BBDD
	 * 
	 * @param id
	 * @param title
	 * @param code
	 * @param dateTime
	 */
	public Task(int id, TaskIDs type, String code, String title, String description, TaskIDs status, LocalDateTime dateTime) {
		super();
		this.id = id;
		this.type = type;
		this.code = code;
		this.title = title;
		this.description = description;
		this.status = status;
		this.dateTime = dateTime;
	}

	/**
	 * 
	 * @param type
	 * @param title
	 */
	public Task(TaskIDs type, String title) {

		this.id = -1;// codigo de no indice
		this.type = type;
		this.code = "BT-" + UUID.randomUUID(); // default code
		this.title = title;
		this.description = "";
		this.status = TaskIDs.RUNNING;
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

	public TaskIDs getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public TaskIDs getStatus() {
		return status;
	}

	public void setStatus(TaskIDs status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", code=" + code + ", type=" + type + ", title=" + title + ", description="
				+ description + ", status=" + status + ", dateTime=" + dateTime + "]";
	}

}

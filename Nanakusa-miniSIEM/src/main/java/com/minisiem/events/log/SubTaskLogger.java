package com.minisiem.events.log;

import com.minisiem.model.Task;

public class SubTaskLogger {

	public static void onSubTaskStarted(Task task) {
		System.out.println("🔼Se ha iniciado una tarea en segundo plano: /" + task.getTitle() + "/ "
				+ task.getDateTime() + " " + task.getCode() + " |" + task.getStatus());
	}

	public static void onSubTaskCompleted(Task task) {
		System.out.println("🔽Ha finalizado una tarea en segundo plano: /" + task.getTitle() + "/ "
				+ task.getDateTime() + " " + task.getCode() + " |" + task.getStatus());
	}

}

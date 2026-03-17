package com.minisiem.events.log;

import java.time.LocalDateTime;

import com.minisiem.TaskIDs;
import com.minisiem.model.Task;
import com.minisiem.repository.TaskRepository;

/**
 * Esta clase se encargar de realizar las acciones decesarias con cada tarea
 * creada en el sistema. Entre sus acciones esta guardar en la BBDD y el
 * registro del sistemas, ademas de mostrar al usuario informacion relevante.
 */
public class TaskManager {

	/**
	 * 
	 * @param task
	 */
	public static void subTaskStarted(Task task) {
		new TaskRepository().createAndGetId(task); // GUARDA EN LA BBDD
		SubTaskLogger.onSubTaskStarted(task); // GUARDA EN EL REGISTRO Y MUESTRA SMS
	}
	
	/**
	 * 	
	 * @param task
	 * @return
	 */
	public static int subTaskStartedAndGetId(Task task) {
		int task_id = new TaskRepository().createAndGetId(task); // GUARDA EN LA BBDD
		SubTaskLogger.onSubTaskStarted(task); // GUARDA EN EL REGISTRO Y MUESTRA SMS
		
		return task_id;
	}

	/**
	 * 
	 * @param task
	 */
	public static void subTaskCompleted(Task task) {

		task.setDateTime(LocalDateTime.now());
		task.setStatus(TaskIDs.COMPLETED);
		new TaskRepository().createAndGetId(task); // GUARDA EN LA BBDD
		SubTaskLogger.onSubTaskCompleted(task); // GUARDA EN EL REGISTRO Y MUESTRA SMS
	}
}

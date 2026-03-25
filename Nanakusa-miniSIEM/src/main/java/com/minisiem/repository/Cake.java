package com.minisiem.repository;

import java.util.List;

public interface Cake {

	List<Object> getAll();
	List<Object> getItems(String param1, int param2);
	List<Object> getItems(String param1, String param2);
	List<Object> getItems(int volume, String order);
	
}

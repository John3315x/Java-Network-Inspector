package com.minisiem.repository;

public interface Controller extends Cake{
	
	String id = "";

	void create(Object object);
	void update(Object object, int id);
	void destroy(int id);
	
}

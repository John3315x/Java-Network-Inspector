package com.minisiem.repository;

public interface Controller {

	void create(Object object);
	void update(Object object, int id);
	void destroy(int id);
}

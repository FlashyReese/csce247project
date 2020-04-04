package me.wilsonhu.csce247.finalproject.databases;

public interface Database<T> {
	public void add(T object);

	public boolean remove(T object);

}

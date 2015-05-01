package me.kazik24.mtdm;

import java.util.ArrayList;


public class RepositoryDeserializationMap {
	
	private final ArrayList<Object> list;
	
	public RepositoryDeserializationMap(int capacity){
		list = new ArrayList<>(capacity);
	}
	public RepositoryDeserializationMap(){this(512);}
	
	public void putRepository(Object h){
		if(h == null) return;
		list.add(h);
	}
	public Object find(int key){
		if(key < 0 && key >= list.size()) return null;
		return list.get(key);
	}
	public void clear(){list.clear();}
}

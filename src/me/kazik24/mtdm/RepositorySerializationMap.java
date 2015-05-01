package me.kazik24.mtdm;

import java.util.ArrayList;

public class RepositorySerializationMap {
	
	
	private final ArrayList<Object> list;
	
	public RepositorySerializationMap(int capacity){
		list = new ArrayList<>(capacity);
	}
	public RepositorySerializationMap(){this(512);}
	
	public int findAndContain(Object h,int def){
		ArrayList<Object> list = this.list;
		int len = list.size();
		for(int i = 0;i<len;i++){
			if(h == list.get(i)) return i;
		}
		list.add(h);
		return def;
	}
	
	public void clear(){list.clear();}
}

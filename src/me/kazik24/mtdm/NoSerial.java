package me.kazik24.mtdm;

import java.util.function.Supplier;

/**
 * Wrapper class for objects that should not be serialized when contained by {@link TaggedData} or {@link KeyedData}.
 * 
 * @author Kazik24
 */
public final class NoSerial implements Supplier<Object>{
	private Object obj;
	
	/**
	 * @param o object to wrap
	 */
	public NoSerial(Object o){obj = o;}
	/**
	 * @return wrapped object.
	 */
	public Object get(){return obj;}
	/**
	 * @param o object to wrap
	 */
	public void set(Object o){obj = o;}
}

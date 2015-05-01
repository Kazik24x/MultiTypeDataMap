package me.kazik24.mtdm;

import java.io.InputStream;

/**
 * Interface providing read only access to entry in {@link TaggedData} object.
 * 
 * @author Kazik24
 */
public interface TagEntry{
	
	/**
	 * @return key of this entry
	 */
	public String getTag();
	/**
	 * @return type of this entry
	 */
	public EntryType getType();
	
	/**Returns value contained by this entry, if value is primitive then boxed value will be returned.
	 * @return value of this entry.
	 */
	public Object getValue();
	/**
	 * @return true if value in this entry is array type.
	 */
	public boolean isArray();
	
	/**
	 * Interface providing access to binary entry in {@link TaggedData} object.
	 * 
	 * @author Kazik24
	 */
	public static interface BinaryTagEntry extends TagEntry,BinaryEntry{
		public InputStream getValue();
	}
}

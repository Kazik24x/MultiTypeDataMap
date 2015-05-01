package me.kazik24.mtdm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.locks.Condition;

import sun.misc.Unsafe;
import net.unitcircuits.block.store.KeyPiece.*;
import net.unitcircuits.simulate.SurfaceSettings;
import net.unitcircuits.util.UCUtils;

public abstract class TaggedData{
	
	
	private int size;
	private TagPiece binary;
	
	public boolean containsTag(String tag){
		
	}
	public int size(){return size;}
	public boolean remove(String tag){
		KeyPiece[] bin = binary;
		if(bin == null || bin.length <= 1) return false;
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null) return false;
		if(piece.key == key){
			bin[idx] = piece.next;
			decSize(bin);
			return true;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null) return false;
			if(next.key == key){
				piece.next = next.next;
				decSize(bin);
				return true;
			}
			piece = next;
		}
	}
	public void clear(){
		size = 0;
		binary = null;
	}
	
	public EntryType getType(String tag){
		KeyPiece piece = getCell(tag);
		if(piece == null) return null;
		return piece.type();
	}
	public void putByte(String tag,byte val);
	
	
	public static void writeToStream(TaggedData data,OutputStream out) throws IOException;
	public static TaggedData readFromStream(InputStream out) throws IOException{
		
	}
}
package me.kazik24.mtdm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.kazik24.mtdm.KeyEntry.BinaryKeyEntry;
import me.kazik24.mtdm.KeyPiece.*;

public class KeyedData{
	
	private static final int INIT_SIZE = 5;
	
	private int size;
	private KeyPiece[] binary;
	private int property;
	
	private KeyPiece getCell(int key){
		KeyPiece[] bin = binary;
		if(bin == null || bin.length <= 1) return null;
		KeyPiece piece = bin[Math.abs(key%(bin.length-1))+1];
		while(piece != null){
			if(piece.key == key) return piece;
			piece = piece.next;
		}
		return null;
	}
	private static KeyPiece[] tryRehash(KeyPiece[] bin){return bin;}
	private static int incSize(KeyPiece[] bin){
		KeyPiece p = bin[0];
		if(p == null){
			p = new KeyPiece(1);
			bin[0] = p;
		}
		return p.key++;
	}
	private static boolean checkConst(KeyPiece[] bin){
		if(bin == null) return false;
		KeyPiece p = bin[0];
		if(p == null) return false;
		if(p.next == KeyPiece.CONST_GUARD) return true;
		return false;
	}
	private static int decSize(KeyPiece[] bin){
		KeyPiece p = bin[0];
		if(p == null) return 0;
		return p.key--;
	}
	private static KeyPiece[] newTable(KeyPiece first){
		KeyPiece[] arr = new KeyPiece[INIT_SIZE];
		arr[0] = new KeyPiece(1);
		int idx = Math.abs(first.key%(INIT_SIZE-1))+1;
		arr[idx] = first;
		return arr;
	}
	
	
	public boolean containsKey(int key){
		KeyPiece[] bin = binary;
		if(bin == null || bin.length <= 1) return false;
		int index = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[index];
		while(piece != null){
			if(piece.key == key) return true;
			piece = piece.next;
		}
		return false;
	}
	public int size(){
		KeyPiece[] bin = binary;
		if(bin == null || bin.length <= 1) return 0;
		KeyPiece piece = bin[0];
		if(piece == null) return 0;
		return piece.key;
	}
	public boolean remove(int key){
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
		binary = null;
	}
	
	
	public void setConst(boolean c){
		
	}
	public boolean isConst(){return checkConst(binary);}
	
	
	public EntryType getType(int key){
		KeyPiece piece = getCell(key);
		if(piece == null) return null;
		return piece.type();
	}
	
	public KeyEntry getEntry(int key){return getCell(key);}
	
	public BinaryKeyEntry getBinaryEntry(int key){
		KeyPiece p = getCell(key);
		if(p != null && p.getClass() == BinPiece.class) return (BinPiece)p;
		return null;
	}
	
	
	
	
	
	
	
	//single
	public byte getByte(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != BytePiece.class) return 0;
		return ((BytePiece)piece).data;
	}
	public boolean putByte(int key,byte data){
		if(data == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new BytePiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new BytePiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new BytePiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == BytePiece.class){
				((BytePiece)piece).data = data;
			}else{
				KeyPiece p = new BytePiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new BytePiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == BytePiece.class){
					((BytePiece)piece).data = data;
				}else{
					KeyPiece p = new BytePiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isByte(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != BytePiece.class) return false;
		return true;
	}
	public short getShort(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != ShortPiece.class) return 0;
		return ((ShortPiece)piece).data;
	}
	public boolean putShort(int key,short data){
		if(data == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new ShortPiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new ShortPiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new ShortPiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == ShortPiece.class){
				((ShortPiece)piece).data = data;
			}else{
				KeyPiece p = new ShortPiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new ShortPiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == ShortPiece.class){
					((ShortPiece)piece).data = data;
				}else{
					KeyPiece p = new ShortPiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isShort(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != ShortPiece.class) return false;
		return true;
	}
	public char getChar(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != CharPiece.class) return 0;
		return ((CharPiece)piece).data;
	}
	public boolean putChar(int key,char data){
		if(data == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new CharPiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new CharPiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new CharPiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == CharPiece.class){
				((CharPiece)piece).data = data;
			}else{
				KeyPiece p = new CharPiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new CharPiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == CharPiece.class){
					((CharPiece)piece).data = data;
				}else{
					KeyPiece p = new CharPiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isChar(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != CharPiece.class) return false;
		return true;
	}
	public int getInt(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != IntPiece.class) return 0;
		return ((IntPiece)piece).data;
	}
	public boolean putInt(int key,int data){
		if(data == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new IntPiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new IntPiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new IntPiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == IntPiece.class){
				((IntPiece)piece).data = data;
			}else{
				KeyPiece p = new IntPiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new IntPiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == IntPiece.class){
					((IntPiece)piece).data = data;
				}else{
					KeyPiece p = new IntPiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isInt(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != IntPiece.class) return false;
		return true;
	}
	public float getFloat(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != FloatPiece.class) return 0;
		return ((FloatPiece)piece).data;
	}
	public boolean putFloat(int key,float data){
		if(data == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new FloatPiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new FloatPiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new FloatPiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == FloatPiece.class){
				((FloatPiece)piece).data = data;
			}else{
				KeyPiece p = new FloatPiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new FloatPiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == FloatPiece.class){
					((FloatPiece)piece).data = data;
				}else{
					KeyPiece p = new FloatPiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isFloat(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != FloatPiece.class) return false;
		return true;
	}
	public long getLong(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != LongPiece.class) return 0;
		return ((LongPiece)piece).data;
	}
	public boolean putLong(int key,long data){
		if(data == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new LongPiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new LongPiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new LongPiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == LongPiece.class){
				((LongPiece)piece).data = data;
			}else{
				KeyPiece p = new LongPiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new LongPiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == LongPiece.class){
					((LongPiece)piece).data = data;
				}else{
					KeyPiece p = new LongPiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isLong(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != LongPiece.class) return false;
		return true;
	}
	public double getDouble(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != DoublePiece.class) return 0;
		return ((DoublePiece)piece).data;
	}
	public boolean putDouble(int key,double data){
		if(data == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new DoublePiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new DoublePiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new DoublePiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == DoublePiece.class){
				((DoublePiece)piece).data = data;
			}else{
				KeyPiece p = new DoublePiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new DoublePiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == DoublePiece.class){
					((DoublePiece)piece).data = data;
				}else{
					KeyPiece p = new DoublePiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isDouble(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != DoublePiece.class) return false;
		return true;
	}
	public String getString(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != StringPiece.class) return null;
		return ((StringPiece)piece).data;
	}
	public boolean putString(int key,String data){
		if(data == null){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new StringPiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new StringPiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new StringPiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == StringPiece.class){
				((StringPiece)piece).data = data;
			}else{
				KeyPiece p = new StringPiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new StringPiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == StringPiece.class){
					((StringPiece)piece).data = data;
				}else{
					KeyPiece p = new StringPiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean isString(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != StringPiece.class) return false;
		return true;
	}
	public InputStream getBinary(int key){
		BinaryKeyEntry e = getBinaryEntry(key);
		if(e == null) return null;
		return e.openRead();
	}
	public boolean isBinary(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != BinPiece.class) return false;
		return true;
	}
	
	
	//array
	public byte[] getByteArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != ABytePiece.class) return null;
		return ((ABytePiece)piece).data;
	}
	public byte[] getByteArrayCopy(int key){
		byte[] arr = getByteArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	public boolean putByteArray(int key,byte[] data){
		if(data == null || data.length == 0){
			remove(key);
			return false;
		}
		KeyPiece[] bin = binary;
		KeyPiece[] old = bin;
		if(bin == null){
			binary = newTable(new ABytePiece(key,data));
			return true;
		}
		if(bin.length <= 1){
			binary = newTable(new ABytePiece(key,data));
			return true;
		}
		bin = tryRehash(bin);
		int idx = Math.abs(key%(bin.length-1))+1;
		KeyPiece piece = bin[idx];
		if(piece == null){
			bin[idx] = new ABytePiece(key,data);
			incSize(bin);
			binary = bin;
			return true;
		}
		if(piece.key == key){
			if(piece.getClass() == ABytePiece.class){
				((ABytePiece)piece).data = data;
			}else{
				KeyPiece p = new ABytePiece(key,data);
				p.next = piece.next;
				bin[idx] = p;
			}
			binary = bin;
			return false;
		}
		KeyPiece next;
		for(;;){
			next = piece.next;
			if(next == null){
				piece.next = new ABytePiece(key,data);
				incSize(bin);
				binary = bin;
				return true;
			}
			if(next.key == key){
				if(next.getClass() == ABytePiece.class){
					((ABytePiece)piece).data = data;
				}else{
					KeyPiece p = new ABytePiece(key,data);
					p.next = next.next;
					piece.next = p;
				}
				binary = bin;
				return false;
			}
			piece = next;
		}
	}
	public boolean putByteArrayCopy(int key,byte[] data){
		if(data == null || data.length == 0){
			remove(key);
			return false;
		}
		return putByteArray(key,data.clone());
	}
	public short[] getShortArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != AShortPiece.class) return null;
		return ((AShortPiece)piece).data;
	}
	public short[] getShortArrayCopy(int key){
		short[] arr = getShortArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	public char[] getCharArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != ACharPiece.class) return null;
		return ((ACharPiece)piece).data;
	}
	public char[] getCharArrayCopy(int key){
		char[] arr = getCharArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	public int[] getIntArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != AIntPiece.class) return null;
		return ((AIntPiece)piece).data;
	}
	public int[] getIntArrayCopy(int key){
		int[] arr = getIntArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	public float[] getFloatArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != AFloatPiece.class) return null;
		return ((AFloatPiece)piece).data;
	}
	public float[] getFloatArrayCopy(int key){
		float[] arr = getFloatArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	public long[] getLongArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != ALongPiece.class) return null;
		return ((ALongPiece)piece).data;
	}
	public long[] getLongArrayCopy(int key){
		long[] arr = getLongArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	public double[] getDoubleArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != ADoublePiece.class) return null;
		return ((ADoublePiece)piece).data;
	}
	public double[] getDoubleArrayCopy(int key){
		double[] arr = getDoubleArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	public String[] getStringArray(int key){
		KeyPiece piece = getCell(key);
		if(piece == null || piece.getClass() != AStringPiece.class) return null;
		return ((AStringPiece)piece).data;
	}
	public String[] getStringArrayCopy(int key){
		String[] arr = getStringArray(key);
		if(arr != null) return arr.clone();
		return null;
	}
	
	
	
	
	//iterate
	public KeyIterator iterator(KeyIterator ref){
		if(ref == null) ref = new KeyIterator();
		ref.reset();
		ref.ref = this;
		return ref;
	}
	public KeyIterator iterator(){return iterator(null);}
	
	public boolean put(int key,Object value);
	public Object get(int key){
		KeyPiece piece = getCell(key);
		if(piece == null) return null;
		return piece.get();
	}
	
	public byte[] toByteArray();
	public void fromByteArray(byte[] arr,int offset,int length);
	public void writeTo(OutputStream out) throws IOException;
	public void readFrom(InputStream in) throws IOException;
	
	public static void writeToStream(KeyedData obj,OutputStream out) throws IOException{writeToStream(obj,out,null);}
	public static void writeToStream(KeyedData obj,OutputStream out,RepositorySerializationMap rmap) throws IOException{
		if(obj == null){
			Utils.writeInt(-1,out);
			return;
		}
		KeyPiece[] hash = obj.binary;
		if(hash == null){
			Utils.writeInt(0,out);
			return;
		}
		int size = obj.size();
		Utils.writeInt(size,out);
		if(size == 0) return;
		Utils.writeInt(hash.length,out);//write hashtable size for fast deserialization
		int count = 0;
		for(int i = 0;i<hash.length;i++){
			for(KeyPiece piece = hash[i];piece != null;piece = piece.next){
				Utils.writeInt(piece.key,out);
				//entry serialization*************************
				Class<?> type = piece.getClass();
				if(!piece.isArray()){
					if(type == BytePiece.class){
						out.write(DataConstants.BYTE);
						out.write(((BytePiece)piece).data);
					}else if(type == ShortPiece.class){
						out.write(DataConstants.SHORT);
						Utils.writeShort(((ShortPiece)piece).data,out);
					}else if(type == CharPiece.class){
						out.write(DataConstants.CHAR);
						Utils.writeChar(((CharPiece)piece).data,out);
					}else if(type == IntPiece.class){
						out.write(DataConstants.INT);
						Utils.writeInt(((IntPiece)piece).data,out);
					}else if(type == FloatPiece.class){
						out.write(DataConstants.FLOAT);
						Utils.writeFloat(((FloatPiece)piece).data,out);
					}else if(type == LongPiece.class){
						out.write(DataConstants.LONG);
						Utils.writeLong(((LongPiece)piece).data,out);
					}else if(type == DoublePiece.class){
						out.write(DataConstants.DOUBLE);
						Utils.writeDouble(((DoublePiece)piece).data,out);
					}else if(type == StringPiece.class){
						out.write(DataConstants.STRING);
						Utils.writeString(((StringPiece)piece).data,out);
					}else if(type == KeyedPiece.class){
						if(rmap == null) rmap = new RepositorySerializationMap(32);
						int rref = rmap.findAndContain(obj,-1);
						if(rref != -1){
							out.write(DataConstants.SERIAL_REF);
							Utils.writeInt(rref,out);
						}else{
							out.write(DataConstants.KEY);
							writeToStream(((KeyedPiece)piece).data,out,rmap);
						}
					}
				}
				
				
				
				
				count++;
				if(count >= size) return;
			}
		}
	}
	public static boolean writePrimitiveEntry(KeyPiece piece,OutputStream out) throws IOException{
		Class<?> type = piece.getClass();
		if(type == BytePiece.class){
			out.write(DataConstants.BYTE);
			out.write(((BytePiece)piece).data);
		}else if(type == ShortPiece.class){
			out.write(DataConstants.SHORT);
			Utils.writeShort(((ShortPiece)piece).data,out);
		}else if(type == CharPiece.class){
			out.write(DataConstants.CHAR);
			Utils.writeChar(((CharPiece)piece).data,out);
		}else if(type == IntPiece.class){
			out.write(DataConstants.INT);
			Utils.writeInt(((IntPiece)piece).data,out);
		}else if(type == FloatPiece.class){
			out.write(DataConstants.FLOAT);
			Utils.writeFloat(((FloatPiece)piece).data,out);
		}else if(type == LongPiece.class){
			out.write(DataConstants.LONG);
			Utils.writeLong(((LongPiece)piece).data,out);
		}else if(type == DoublePiece.class){
			out.write(DataConstants.DOUBLE);
			Utils.writeDouble(((DoublePiece)piece).data,out);
		}else return false;
		return true;
	}
	public static KeyedData readFromStream(InputStream in) throws IOException{
		
	}
}

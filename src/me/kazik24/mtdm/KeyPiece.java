package me.kazik24.mtdm;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class KeyPiece implements KeyEntry{
	
	int key;
	KeyPiece next;
	
	KeyPiece(){}
	KeyPiece(int key){this.key = key;}
	
	Object get(){return null;}
	EntryType type(){return null;}
	@Override
	public boolean isArray(){return false;}
	
	
	@Override
	public int getKey(){return key;}
	@Override
	public EntryType getType(){return type();}
	@Override
	public Object getValue(){return get();}
	
	static final KeyPiece CONST_GUARD = new KeyPiece(-1);
	static class BytePiece extends KeyPiece{
		byte data;
		BytePiece(int key,byte data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.BYTE;}
	}
	static class ABytePiece extends KeyPiece{
		byte[] data;
		ABytePiece(int key,byte[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.BYTE_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class ShortPiece extends KeyPiece{
		short data;
		ShortPiece(int key,short data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.SHORT;}
	}
	static class AShortPiece extends KeyPiece{
		short[] data;
		AShortPiece(int key,short[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.SHORT_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class CharPiece extends KeyPiece{
		char data;
		CharPiece(int key,char data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.CHAR;}
	}
	static class ACharPiece extends KeyPiece{
		char[] data;
		ACharPiece(int key,char[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.CHAR_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class IntPiece extends KeyPiece{
		int data;
		IntPiece(int key,int data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.INT;}
	}
	static class AIntPiece extends KeyPiece{
		int[] data;
		AIntPiece(int key,int[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.INT_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class FloatPiece extends KeyPiece{
		float data;
		FloatPiece(int key,float data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.FLOAT;}
	}
	static class AFloatPiece extends KeyPiece{
		float[] data;
		AFloatPiece(int key,float[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.FLOAT_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class LongPiece extends KeyPiece{
		long data;
		LongPiece(int key,long data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.LONG;}
	}
	static class ALongPiece extends KeyPiece{
		long[] data;
		ALongPiece(int key,long[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.LONG_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class DoublePiece extends KeyPiece{
		double data;
		DoublePiece(int key,double data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.DOUBLE;}
	}
	static class ADoublePiece extends KeyPiece{
		double[] data;
		ADoublePiece(int key,double[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.DOUBLE_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class StringPiece extends KeyPiece{
		String data;
		StringPiece(int key,String data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.STRING;}
	}
	static class AStringPiece extends KeyPiece{
		String[] data;
		AStringPiece(int key,String[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.STRING_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class KeyedPiece extends KeyPiece{
		KeyedData data;
		KeyedPiece(int key,KeyedData data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.KEY;}
	}
	static class AKeyedPiece extends KeyPiece{
		KeyedData[] data;
		AKeyedPiece(int key,KeyedData[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.KEY_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class TaggedPiece extends KeyPiece{
		TaggedData data;
		TaggedPiece(int key,TaggedData data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.TAG;}
	}
	static class ATaggedPiece extends KeyPiece{
		TaggedData[] data;
		ATaggedPiece(int key,TaggedData[] data){super(key);this.data = data;}
		@Override
		Object get(){return data;}
		@Override
		EntryType type(){return EntryType.TAG_ARRAY;}
		@Override
		public boolean isArray(){return true;}
	}
	static class BinPiece extends KeyPiece implements BinaryKeyEntry{
		byte[] data;
		int count;
		private Object stream;
		
		private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
		private void ensureCapacity(int minCapacity){
			if(minCapacity - data.length > 0){
				int oldCapacity = data.length;
				int newCapacity = oldCapacity << 1;
				if (newCapacity - minCapacity < 0)
					newCapacity = minCapacity;
				if (newCapacity - MAX_ARRAY_SIZE > 0)
					newCapacity = hugeCapacity(minCapacity);
				data = Arrays.copyOf(data, newCapacity);
			}
		}
		private static int hugeCapacity(int minCapacity) {
	        if (minCapacity < 0) // overflow
	            throw new OutOfMemoryError();
	        return (minCapacity > MAX_ARRAY_SIZE) ?
	            Integer.MAX_VALUE :
	            MAX_ARRAY_SIZE;
	    }
		
		BinPiece(int key,byte[] data){super(key);this.data = data;}
		@Override
		Object get(){return openRead();}
		@Override
		public InputStream getValue(){return openRead();}
		@Override
		EntryType type(){return EntryType.BINARY;}
		@Override
		public synchronized OutputStream openWrite(boolean append){
			Object str = stream;
			if(str != null) return null;
			if(!append) count = 0;
			OutputStream out = new OutputStream(){
				@Override
				public synchronized void write(int b){
			        ensureCapacity(count + 1);
			        data[count] = (byte)b;
			        count += 1;
			    }
				@Override
				public synchronized void write(byte b[], int off, int len){
			        if ((off < 0) || (off > b.length) || (len < 0) ||
			            ((off + len) - b.length > 0)) {
			            throw new IndexOutOfBoundsException();
			        }
			        ensureCapacity(count + len);
			        System.arraycopy(b, off, data, count, len);
			        count += len;
			    }
				@Override
				public void close(){
					synchronized(BinPiece.this){
						BinPiece.this.stream = null;
					}
				}
			};
			stream = out;
			return out;
		}
		@Override
		public boolean isOpened(){return stream != null;}
		@Override
		public boolean isReadOpened(){return stream instanceof InputStream;}
		@Override
		public boolean isWriteOpened(){return stream instanceof OutputStream;}
		@Override
		public synchronized InputStream openRead(){
			Object str = stream;
			if(str != null) return null;
			InputStream in = new InputStream(){
				int pos;
				int mark;
				@Override
				public synchronized int read(){
					return (pos < count) ? (data[pos++] & 0xff) : -1;
				}
				@Override
				public synchronized int read(byte b[], int off, int len) {
			        if (b == null) {
			            throw new NullPointerException();
			        } else if (off < 0 || len < 0 || len > b.length - off) {
			            throw new IndexOutOfBoundsException();
			        }

			        if (pos >= count) {
			            return -1;
			        }

			        int avail = count - pos;
			        if (len > avail) {
			            len = avail;
			        }
			        if (len <= 0) {
			            return 0;
			        }
			        System.arraycopy(data, pos, b, off, len);
			        pos += len;
			        return len;
			    }
				@Override
				public synchronized long skip(long n) {
			        long k = count - pos;
			        if (n < k) {
			            k = n < 0 ? 0 : n;
			        }
			        pos += k;
			        return k;
				}
				@Override
				public synchronized int available(){return count - pos;}
				@Override
				public boolean markSupported(){return true;}
				@Override
				public synchronized void mark(int readAheadLimit){mark = pos;}
				@Override
				public synchronized void reset(){pos = mark;}
				
				@Override
				public void close(){
					synchronized(BinPiece.this){
						BinPiece.this.stream = null;
					}
				}
			};
			stream = in;
			return in;
		}
	}
	
}

package me.kazik24.mtdm;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Utils{
	public static void writeUTF8(String str,OutputStream out) throws IOException{
		if(str == null){
			writeInt(-1,out);
			return;
		}
		if(str.length() == 0) writeInt(0,out);
		writeInt(str.length(),out);
		out.write(str.getBytes(StandardCharsets.UTF_8));
	}
	public static String readUTF8(InputStream in) throws IOException{
		int len = readInt(in);
		if(len < 0) return null;
		if(len == 0) return "";
		byte[] arr = new byte[len];
		in.read(arr);
		return new String(arr,StandardCharsets.UTF_8);
	}
	public static String readString(InputStream in) throws IOException{
		int len = readInt(in);
		if(len == 0) return "";
		if(len < 0) return null;
		char[] cont = new char[len];
		for(int i = 0;i<len;i++){
			cont[i] = readChar(in);
		}
		return new String(cont);
	}
	public static void writeString(String s,OutputStream out) throws IOException{
		if(s == null){
			writeInt(-1,out);
			return;
		}
		int len = s.length();
		writeInt(len,out);
		for(int i = 0;i<len;i++) writeChar(s.charAt(i),out);
	}
	public static void writeInt(int val,OutputStream out) throws IOException{
		out.write((val >>> 24) & 0xff);
		out.write((val >>> 16) & 0xff);
		out.write((val >>>  8) & 0xff);
		out.write(val & 0xff);
	}
	public static void writeChar(char val,OutputStream out) throws IOException{
		out.write((val >>>  8) & 0xff);
		out.write(val & 0xff);
	}
	public static void writeShort(short val,OutputStream out) throws IOException{
		out.write((val >>>  8) & 0xff);
		out.write(val & 0xff);
	}
	public static short readShort(InputStream in) throws IOException{
		int ch1 = in.read();
		int ch2 = in.read();
		if ((ch1 | ch2) < 0) throw new EOFException();
		return (short)((ch1 << 8) | ch2);
	}
	public static int readInt(InputStream in) throws IOException{
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		int ch4 = in.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0) throw new EOFException();
		return ((ch1 << 24) | (ch2 << 16) | (ch3 << 8) | ch4);
	}
	public static void writeFloat(float val,OutputStream out) throws IOException{
		writeInt(Float.floatToIntBits(val),out);
	}
	public static float readFloat(InputStream in) throws IOException{
		return Float.intBitsToFloat(readInt(in));
	}
	public static long readLong(InputStream in) throws IOException{
		int hi = readInt(in);
		int lo = readInt(in);
		return ((long)hi)<<32 | (lo & 0xffffffffl);
	}
	public static double readDouble(InputStream in) throws IOException{
		return Double.longBitsToDouble(readLong(in));
	}
	public static void writeLong(long val,OutputStream out) throws IOException{
		writeInt((int)(val >> 32),out);
		writeInt((int)val,out);
	}
	public static void writeDouble(double val,OutputStream out) throws IOException{
		writeLong(Double.doubleToLongBits(val),out);
	}
	public static char readChar(InputStream in) throws IOException{
		int ch1 = in.read();
		int ch2 = in.read();
		if ((ch1 | ch2) < 0) throw new EOFException();
		return (char)((ch1 << 8) | ch2);
	}
	
}

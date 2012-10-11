package org.sessioncontainer.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

public class CodecHelpler {
	private static final byte[] nullByteArray = {};
	public static byte[] convertObject2Bytes(Object obj) throws IOException {
		if(obj==null) return nullByteArray;
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			throw e;
		}finally{
			try{if(oos!=null) oos.close();}catch(IOException e){};
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Object convertBytes2Object(byte[] bytes) throws IOException,ClassNotFoundException{
		if(bytes==null||bytes.length==0) return null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object obj = ois.readObject();
			return (T)obj;
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		}finally{
			try{if(ois!=null) ois.close();}catch(IOException e){};
		}
	}
	
	
	private static final String supportedCharSet = "UTF-8";
	public static byte[] convertString2Bytes(String str) {
		try {
			return str.getBytes(supportedCharSet);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported?!!!",e);
		}
	}
	public static String convertBytes2Str(byte[] bytes) {
		try {
			return new String(bytes,supportedCharSet);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported?!!!",e);
		}
	}
	
	
	
	public static int convert4BytesToInt(byte b1,byte b2,byte b3,byte b4) throws EOFException{
		int ch1 = b1 & 0xff;
        int ch2 = b2 & 0xff;
        int ch3 = b3 & 0xff;
        int ch4 = b4 & 0xff;
		if ((ch1 | ch2 | ch3 | ch4) < 0) throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}
	public static byte[] convertIntTo4Bytes(int i) throws EOFException{
		return new byte[]{
				(byte)((i>>>24) & 0xff),
				(byte)((i>>>16) & 0xff),
				(byte)((i>>>8) & 0xff),
				(byte)((i>>>0) & 0xff),
		};
	}
	public static byte[] joinByteArray(byte[]... bs){
		int totalLength = 0;
		for(byte[] byteArray : bs){
			totalLength += byteArray.length;
		}
		byte[] newByteArray = new byte[totalLength];
		int newByteArrayIndex=0;
		for(byte[] byteArray : bs){
			System.arraycopy(byteArray, 0, newByteArray, newByteArrayIndex, byteArray.length);
			newByteArrayIndex +=byteArray.length;
		}
		return newByteArray;
	}
	
}

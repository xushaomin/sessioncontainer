package org.sessioncontainer.test;

import org.sessioncontainer.codec.CodecHelpler;

public class TestMain {
	public static void main(String[] args){
		/*int i=0xf8f;
		
		System.out.println(Integer.toBinaryString(i));
		byte i2=(byte)i;
		System.out.println(((byte)i)&0xff);
		System.out.println(Integer.toBinaryString(i2));
		
		System.out.println(Integer.toBinaryString(i2>>2));*/
		
		/*int i=0x800000ff;
		System.out.println(Integer.toBinaryString(i));
		System.out.println(Integer.toBinaryString(i << 24));
		System.out.println(Integer.toBinaryString(i >> 2));
		System.out.println(Integer.toBinaryString(i << 16));*/
		
		/*for(byte b:CodecHelpler.joinByteArray(new byte[]{1,1,1,1},new byte[]{1,1,})){
			System.out.println(b);
		}*/
		System.out.println(java.lang.Integer.toHexString(1080));
		
		
		/*System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());
		System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());
		System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());
		System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());*/
	}
}

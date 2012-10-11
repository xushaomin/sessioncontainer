package org.sessioncontainer.client;

import java.security.MessageDigest;

public class Utils {
	
	public static String getUUIDInUpperCase(){
		return java.util.UUID.randomUUID().toString().toUpperCase();
	}
	public static String StringToMd5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			return byte2hex(md5.digest());
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static String StringTo16Md5(String str) {
		String s=StringToMd5(str);
		if(s!=null && s.length()==32){
			return s.substring(8, 24);
		}else{
			return null;
		}
	}
	
	private static String byte2hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte oneByte : bytes) {
			String stmp = Integer.toHexString(oneByte&0xff);
			if (stmp.length() == 1) {
				sb.append("0").append(stmp);
			} else {
				sb.append(stmp);
			}
		}
		return sb.toString().toUpperCase();
	}
	
	
	public static boolean StringIsEmpty(String str){
		return (str==null||str.length()==0||str.trim().length()==0)?true:false;
	}
	public static boolean byteArrayIsEmpty(byte[] bytes){
		return (bytes==null||bytes.length==0);
	}

}

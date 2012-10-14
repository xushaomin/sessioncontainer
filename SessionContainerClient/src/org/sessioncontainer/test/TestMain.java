package org.sessioncontainer.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.sessioncontainer.client.command.CommandDef;
import org.sessioncontainer.client.service.SessionService;
import org.sessioncontainer.codec.CodecHelpler;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.codec.MessageException;

public class TestMain {
	public static void main(String[] args) throws MessageException, IOException, InterruptedException{
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
		/*System.out.println(java.lang.Integer.toHexString(1080));
		*/
		testSaveService();
		Thread.sleep(1000);
		//testCodecEncode();
		//testCodecDecode();
		/*System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());
		System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());
		System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());
		System.out.println(java.util.UUID.randomUUID().toString().toUpperCase().length());*/
	}
	
	private static void testSaveService(){
		SessionService sessionService = SessionService.getInstance();
		sessionService.setAttribute(java.util.UUID.randomUUID().toString().toUpperCase(), "testval", "test");
		//sessionService.getAttribute(sessionContainerId, attributeKey)
	}
	
	
	private static void testCodecDecode(){
		Message msg = new Message();
		FileInputStream fout = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			fout = new FileInputStream("d://dd");
			int b;
			while((b=fout.read())!=-1){
				buffer.write(b);
			}
			msg.mergeFrom(buffer.toByteArray());
			
			System.out.println(msg.getAttributeKey());
			System.out.println(msg.getCommandFlag());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {fout.close();} catch (IOException e) {}
		}
	}
	
	private static void testCodecEncode() throws MessageException, IOException{
		Message msg = new Message();
		msg.setAttributeKey("1111");
		msg.setCommandFlag(CommandDef.COMMAND_SET_SESSION_ATTRIBUTE);
		msg.setCommandId("2222");
		msg.setSessionContainerId(java.util.UUID.randomUUID().toString().toUpperCase());
		msg.setValue(CodecHelpler.convertObject2Bytes("33333"));
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream("d://dd");
			fout.write(msg.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {fout.close();} catch (IOException e) {}
		}
	}
}

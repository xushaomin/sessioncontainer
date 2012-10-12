package org.sessioncontainer.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class Message {
	private final static Logger logger=Logger.getLogger(MessageBytesFrameDecoder.class);
	
	
	private String commandFlag;
	private String sessionContainerId;
	private String attributeKey;
	private String commandId;
	private byte[] value;
	public Message() {}
	public String getCommandFlag() {
		return commandFlag;
	}
	public void setCommandFlag(String commandFlag) {
		this.commandFlag = commandFlag;
	}
	public String getSessionContainerId() {
		return sessionContainerId;
	}
	public void setSessionContainerId(String sessionContainerId) {
		this.sessionContainerId = sessionContainerId;
	}
	public String getAttributeKey() {
		return attributeKey;
	}
	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
	public byte[] getValue() {
		return value;
	}
	public void setValue(byte[] value) {
		this.value = value;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	//----------------生成字节码若干方法:start-------------------
	public void writeTo(OutputStream out) throws MessageException {
		try {
			out.write(toByteArray());
		} catch (IOException e) {
			throw MessageException.makeMessageException("写入数据时发生错误！",e);
		} catch (MessageException e) {
			throw e;
		}
	}
	private static final int endTag = -1;
	public byte[] toByteArray() throws MessageException{
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(buffer);
			writeFiled(1,CodecHelpler.convertString2Bytes(commandFlag),dataOut);
			writeFiled(2,CodecHelpler.convertString2Bytes(sessionContainerId),dataOut);
			writeFiled(3,CodecHelpler.convertString2Bytes(attributeKey),dataOut);
			writeFiled(4,CodecHelpler.convertString2Bytes(commandId),dataOut);
			writeFiled(5,value,dataOut);
			dataOut.writeInt(endTag);//写入结束标志
			dataOut.flush();
			dataOut.close();
			return buffer.toByteArray();
		} catch (IOException e) {
			throw MessageException.makeMessageException("生成字节码时发生错误！",e);
		}
	}
	private void writeFiled(int tag,byte[] value,DataOutputStream out) throws IOException{
		if(value==null||value.length==0) return;
		out.writeInt(tag);//写入域相关的一个标志位
		out.writeInt(value.length);//写入数据长度
		out.write(value);//写入数据
	}
	//----------------生成字节码若干方法:start-------------------
	//----------------由字节码生成对象若干方法:start-----------------
	public void mergeFrom(byte[] bytes) throws MessageException{
		System.out.println(bytes[0]);
		System.out.println(bytes[1]);
		System.out.println(bytes[2]);
		System.out.println(bytes[4]);
		
		DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			while(true){
				int tag = dataIn.readInt();
				if(tag==endTag) break;//如果结尾到了，则退出
				readFiled(tag,readBytes(dataIn));
			}
		} catch (IOException e) {
			throw MessageException.makeMessageException("设置属性时发生错误！",e);
		} finally{
			try {dataIn.close();} catch (IOException e) {}
		}
	}
	private void readFiled(int tag,byte[] value) throws IOException{
		if(value==null||value.length==0) return;
		logger.debug("tag:"+tag);
		switch(tag){
			case 1:this.commandFlag = CodecHelpler.convertBytes2Str(value);break;
			case 2:this.sessionContainerId = CodecHelpler.convertBytes2Str(value);break;
			case 3:this.attributeKey = CodecHelpler.convertBytes2Str(value);break;
			case 4:this.commandId = CodecHelpler.convertBytes2Str(value);break;
			case 5:this.value = value;break;
			default:;//do Nothing
		}
	}
	private byte[] readBytes(DataInputStream dataIn) throws IOException{
		byte[] bytes = new byte[dataIn.readInt()];
		dataIn.read(bytes);
		return bytes;
	}
	//----------------由字节码生成对象若干方法:start-----------------
	
	
}

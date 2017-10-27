package com.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public SocketClient(){
		try {
			socket=new Socket("192.168.3.2",10086);
			out=new ObjectOutputStream(socket.getOutputStream());
			ReadThread readThread=new ReadThread();
			readThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendMessage(String msg){
		System.out.println("send message:"+msg);
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	class ReadThread extends Thread{
		boolean runFlag=true;
		public void run(){
			try {
				in=new ObjectInputStream(socket.getInputStream());
				while(runFlag){
					if(socket.isClosed())
						return;
					Object obj = in.readObject();
					if(obj instanceof String){
						System.out.println("Client recive:"+obj);
						if("end".equals(obj)){
							socket.close();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		SocketClient socketClient=new SocketClient();
		System.out.println("build socketCilent");
		try {
			Thread.sleep(1000);
			socketClient.sendMessage("Hello first.");
			Thread.sleep(1000);
			socketClient.sendMessage("Hello second.");
			Thread.sleep(1000);
			socketClient.sendMessage("end");
			//Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(!socketClient.socket.isClosed()){
			System.out.println("wait socket close," + socketClient.socket.isClosed());
		}
		
		
		///
		
		
	}
}

package com.myTomcat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpServer {
	public static final String WEB_ROOT=System.getProperty("user.dir")+File.separator+"webroot";
	private static final String SHUTDOWN_COMMAND="/SHUTDOWN";
	private boolean shutdown=false;
	
	public static void main(String[] args){
		HttpServer httpServer=new HttpServer();
		httpServer.await();
	}
	
	public void await(){
		ServerSocket serverSocket=null;
		Integer port=8080;
		try {
			serverSocket=new ServerSocket(port,1,InetAddress.getByName("192.168.3.2"));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!shutdown){
			Socket socket=null;
			InputStream in=null;
			OutputStream out=null;
			try {
				socket=serverSocket.accept();
				in=socket.getInputStream();
				out=socket.getOutputStream();
				Request request=new Request(in);
				request.parse();
				Response response = new Response(out);
				response.setRequest(request);
				response.sendStaticResource();
				System.out.println("send message over");
				socket.close();
				shutdown=request.getUri().equals(SHUTDOWN_COMMAND);
				System.out.println(request.getUri()+" , "+shutdown);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
		
	}
}

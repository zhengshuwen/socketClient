package com.myTomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
	private static final int BUFFER_SIZE=1024;
	Request request;
	OutputStream output;
	public Response(OutputStream output){
		this.output=output;
	}
	
	public void setRequest(Request request){
		this.request=request;
	}
	
	public void sendStaticResource(){
		byte[] bytes=new byte[BUFFER_SIZE];
		FileInputStream fis=null;
		System.out.println(HttpServer.WEB_ROOT+"/"+request.getUri());
		FileOutputStream outfile=null;
		try {
			outfile=new FileOutputStream(new File("C:\\Users\\MINI\\Desktop\\12345.html"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file=new File("C:\\Users\\MINI\\Desktop\\1234.txt");//new File(HttpServer.WEB_ROOT,request.getUri());
		try{
			if(file.exists()){
				System.out.println("file exists");
				fis=new FileInputStream(file);
				int ch = 0;
				while((ch=fis.read(bytes,0,BUFFER_SIZE))!=-1){
					output.write(bytes,0,ch);
					outfile.write(bytes,0,ch);
				}
			}else{
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n"+
										"Content-Type:text/html\r\n"+
										"Content-length:23\r\n"+
										"\r\n"+
										"<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

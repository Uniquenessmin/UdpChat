package com.xxm.udpchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer{
	
	//tcp套接字
	ServerSocket serverSocket;
	
	//创建线程池
	ExecutorService pool;
	
	//存储用户列表
	Map<String, Integer> users = new HashMap<>();
	
	public ChatServer() {
		pool = Executors.newCachedThreadPool();
	}
	
	public void start() {
		try {
			//服务套接字
			serverSocket = new ServerSocket(9000);
			System.out.println("服务器启动...");
			
			while(true) {
				//套接字连接
				Socket socket = serverSocket.accept();
				//创建新的线程任务
				pool.execute(new OnlineServer(socket,users));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	public static void main(String[] args) {
		ChatServer cs = new ChatServer();
		cs.start();		
		System.out.println("123");
	}
}

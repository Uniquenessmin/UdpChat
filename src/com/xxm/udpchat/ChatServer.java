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
	
	//记录客户信息
	Map<String, Integer> users = new HashMap<>();
	
	public ChatServer() {
		pool = Executors.newCachedThreadPool();
	}
	
	public void start() {
		try {
			//创建连接
			serverSocket = new ServerSocket(9000);
			System.out.println("服务器启动...");
			
			while(true) {
				//让线程池中的一个线程去处理用户上线信息
				Socket socket = serverSocket.accept();
				
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
	
//
//	static public void add(Chat chat) {
////		onlineServer.addObserver(chat);
//		System.out.println("增加了");
//	}
}

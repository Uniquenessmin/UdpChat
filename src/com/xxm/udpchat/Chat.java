package com.xxm.udpchat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.google.gson.Gson;

public class Chat {

	Thread sender;
	Thread receiver;
	
	DatagramSocket udpSocket;
	Socket tcpSocket;
	
	public Chat() {
		try {
			
			if(Thread.currentThread().isInterrupted()) {
				System.out.println("abc");
			}
			//TODO C/S 客户端与服务器建立链接
			tcpSocket = new Socket("127.0.0.1", 9000);
			InputStream in = tcpSocket.getInputStream();
			OutputStream out = tcpSocket.getOutputStream();
			//发送昵称
			System.out.println("昵称: ");
			Scanner sc = new Scanner(System.in);
			String nick = sc.nextLine();			
			out.write(nick.getBytes());
			out.flush();
			
			//udp 客户端之间通信 端到端 p2p
			udpSocket = new DatagramSocket();
			int udpPort = udpSocket.getLocalPort();			
			
			//TODO 发送udp端口号
			out.write(String.valueOf(udpPort).getBytes());
			out.flush();
			System.out.println(String.valueOf(udpPort));
																																																																	
			//把udpSocket发送SendTask
			sender = new Thread(new SendTask(udpSocket));
			receiver = new Thread(new ReceiveTask(udpSocket));			
			sender.start();
			receiver.start();
			
			//接收用户列表
			byte[] buf = new byte[1024];
			int size;
			String json;
			while(-1 != (size = in.read(buf) )) {
				
				json = new String(buf, 0, size);
				
				HashMap<String, Integer> users = new Gson().fromJson(json, HashMap.class);
				System.out.println("在线用户" + users);
			}
			
			while(sender.isAlive()||receiver.isAlive()) {
				
				
			}
			udpSocket.close();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Chat chat = new Chat();
		
	}
	
}

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
			
			//TODO C/S 客户端和服务端连接
			tcpSocket = new Socket("127.0.0.1", 9000);
			InputStream in = tcpSocket.getInputStream();
			OutputStream out = tcpSocket.getOutputStream();
			
			System.out.println("昵称: ");
			Scanner sc = new Scanner(System.in);
			String nick = sc.nextLine();			
			//发送昵称
			out.write(nick.getBytes());
			out.flush();
			
			//udp端到端 p2p
			udpSocket = new DatagramSocket();
			int udpPort = udpSocket.getLocalPort();			
			
			//TODO 把udp的端口号发过去
			out.write(String.valueOf(udpPort).getBytes());
			out.flush();
			System.out.println(String.valueOf(udpPort));
																																																																	
			//传送用户de列表给SendTask
			sender = new Thread(new SendTask(udpSocket));
			receiver = new Thread(new ReceiveTask(udpSocket));			
			sender.start();
			receiver.start();
			
			//接收在线用户列表
			byte[] buf = new byte[1024];
			int size;
			String json;
			while(-1 != (size = in.read(buf) )) {
				
				json = new String(buf, 0, size);
				
				HashMap<String, Integer> users = new Gson().fromJson(json, HashMap.class);
				System.out.println("在线用户：" + users);
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
//客户端发信息给服务端
//服务端给客户端发列表信息
//在线用户更新，客户端可以得到更新信息
//发送不同类型的消息，字符串、图片、文件


//数据解析 文件 网络编程

//通讯双方是谁？
//增加 注册
//发送的信息用json包装
//两台机器用外网
//ui
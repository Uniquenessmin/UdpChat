package com.xxm.udpchat;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Observable;

import com.google.gson.Gson;

public class OnlineServer implements Runnable {
	Socket socket;
	Map<String, Integer> users;

	public OnlineServer(Socket socket, Map<String, Integer> users) {
		this.socket = socket;
		this.users = users;
	}

	@Override
	public void run() {
		try(InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream()) {
			
			//读取昵称
			byte[] buf = new byte[64];
			int size = in.read(buf);
			String nick = new String(buf, 0, size);
			int userSize = users.size();
			
			//读取udpPort
			byte[] udp = new byte[64];
			size = in.read(udp);
			String uString = new String(udp, 0, size);
			
			int udpPort = Integer.parseInt(uString);
//			System.out.println("udp=" + udpPort);

			//存储
			users.put(nick, udpPort);
//			System.out.println(users.size());
			
			
			//发送其他用户信息
			//对象的输出流？
			//users---> XML/JSON
			String json;
			
			//更新在线用户列表
			while(true) {
				
//				if(socket.isClosed()) {
//					//下线，移除
//					users.remove(nick);
//				}
				int newSize = users.size();
				if(newSize != userSize) {
					json =new Gson().toJson(users);
					System.out.println("发送： " + json);
					byte[] data = json.getBytes();
					out.write(data, 0, json.length());
					out.flush();
					userSize = newSize;
				}
				Thread.sleep(3000);
			}
		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}

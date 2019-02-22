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
			
			//��ȡ�ǳ�
			byte[] buf = new byte[64];
			int size = in.read(buf);
			String nick = new String(buf, 0, size);
			int userSize = users.size();
			
			//��ȡudpPort
			byte[] udp = new byte[64];
			size = in.read(udp);
			String uString = new String(udp, 0, size);
			
			int udpPort = Integer.parseInt(uString);
//			System.out.println("udp=" + udpPort);

			//�洢
			users.put(nick, udpPort);
//			System.out.println(users.size());
			
			
			//���������û���Ϣ
			//������������
			//users---> XML/JSON
			String json;
			
			//���������û��б�
			while(true) {
				
//				if(socket.isClosed()) {
//					//���ߣ��Ƴ�
//					users.remove(nick);
//				}
				int newSize = users.size();
				if(newSize != userSize) {
					json =new Gson().toJson(users);
					System.out.println("���ͣ� " + json);
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

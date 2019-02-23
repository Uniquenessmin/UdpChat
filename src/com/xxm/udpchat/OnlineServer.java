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
		String nick = null;
		try(InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream()) {
			
			//��ȡ�ǳ�
			byte[] buf = new byte[64];
			int size = in.read(buf);
			nick = new String(buf, 0, size);
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
			String json = new Gson().toJson(users);
			String newJson = null;
			System.out.println("发送：" + json);
			byte[] data = json.getBytes();
			out.write(data, 0, json.length());
			out.flush();
			
			/**
			 * �����û��б�
			 * 
			 */
			while(true) {
				
//				int newSize = users.size();
				newJson =new Gson().toJson(users);
				System.out.println(socket.getPort());
				if(!json.equals(newJson)) {
	
					System.out.println("发送： " + newJson);
					byte[] datas = newJson.getBytes();
					out.write(datas, 0, newJson.length());
					out.flush();
//					userSize = newSize;
					json = newJson;
					
				}

				Thread.sleep(5000);				
//				socket.getInputStream().read();	//**这句会导致套接字堵塞**	
			}		
			
		} catch (Exception e) {
			System.out.println(nick+"������");
			users.remove(nick);
			System.out.println(users.toString());
		}
		
	}
	
}

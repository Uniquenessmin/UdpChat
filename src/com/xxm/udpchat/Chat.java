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
			
			//TODO C/S �ͻ��˺ͷ��������
			tcpSocket = new Socket("127.0.0.1", 9000);
			InputStream in = tcpSocket.getInputStream();
			OutputStream out = tcpSocket.getOutputStream();
			
			System.out.println("�ǳ�: ");
			Scanner sc = new Scanner(System.in);
			String nick = sc.nextLine();			
			//�����ǳ�
			out.write(nick.getBytes());
			out.flush();
			
			//udp�˵��� p2p
			udpSocket = new DatagramSocket();
			int udpPort = udpSocket.getLocalPort();			
			
			//TODO ��udp�Ķ˿ںŷ���ȥ
			out.write(String.valueOf(udpPort).getBytes());
			out.flush();
			System.out.println(String.valueOf(udpPort));
																																																																	
			//�����û�de�б��SendTask
			sender = new Thread(new SendTask(udpSocket));
			receiver = new Thread(new ReceiveTask(udpSocket));			
			sender.start();
			receiver.start();
			
			//���������û��б�
			byte[] buf = new byte[1024];
			int size;
			String json;
			while(-1 != (size = in.read(buf) )) {
				
				json = new String(buf, 0, size);
				
				HashMap<String, Integer> users = new Gson().fromJson(json, HashMap.class);
				System.out.println("�����û���" + users);
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
//�ͻ��˷���Ϣ�������
//����˸��ͻ��˷��б���Ϣ
//�����û����£��ͻ��˿��Եõ�������Ϣ
//���Ͳ�ͬ���͵���Ϣ���ַ�����ͼƬ���ļ�


//���ݽ��� �ļ� ������

//ͨѶ˫����˭��
//���� ע��
//���͵���Ϣ��json��װ
//��̨����������
//ui
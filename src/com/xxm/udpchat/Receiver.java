package com.xxm.udpchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver {

	public static void main(String[] args) {
		
		DatagramSocket socket = null;
		String msg = null;
		try {
			//����UDp�׽���
			socket = new DatagramSocket(7000);
			
			DatagramPacket packet = null;
			
			
			while(true) {
				byte[] buf = new byte[256];
				packet = new DatagramPacket(buf, buf.length);
				//����
				System.out.println("����...");
				socket.receive(packet);
				
				//��ӡ����
				byte[] data= packet.getData();
			    msg = new String(data, 0, packet.getLength());
				System.out.println(data.length+":"+ packet.getLength());
				
//				System.out.println(packet.getAddress());
//				System.out.println(packet.getPort());
				System.out.println(msg);
				
				if(msg.equals("bye")) {
					break;
				}
			}
			
			System.out.println("�Է��Ѿ�����");
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			socket.close();
		}
		
	}
}

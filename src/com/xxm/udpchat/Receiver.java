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
			//创建UDp套接字
			socket = new DatagramSocket(7000);
			
			DatagramPacket packet = null;
			
			
			while(true) {
				byte[] buf = new byte[256];
				packet = new DatagramPacket(buf, buf.length);
				//接收
				System.out.println("接收...");
				socket.receive(packet);
				
				//打印内容
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
			
			System.out.println("对方已经下线");
			
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

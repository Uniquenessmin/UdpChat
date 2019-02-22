package com.xxm.udpchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Sender {

	public static void main(String[] args) {

		String msg = null;
		Scanner scanner = null;

		// UDP �׽���
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(5000);

			
			InetAddress address = InetAddress.getByName("127.0.0.1");
			int port = 7000;
			scanner = new Scanner(System.in);
			while (true) {
				
				msg = scanner.nextLine();
				byte[] data = msg.getBytes();
				
				// �������ݰ�
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

				// ���ͣ�ʹ���׽��ַ������ݰ�
				socket.send(packet);
				System.out.println("send");
				if(msg.equals("bye"))
					break;
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			scanner.close();
			socket.close();
		}
	}
}

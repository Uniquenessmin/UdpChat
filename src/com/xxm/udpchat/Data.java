package com.xxm.udpchat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Data {
	DatagramSocket socket;
	DatagramPacket packet;
	Scanner sc = new Scanner(System.in);
	int port;

	public Data() {

	}

	public Data(DatagramSocket socket, int port) {
		this.port = port;
		this.socket = socket;
	}

	/**
	 * 
	 *** send string
	 * 
	 * @return
	 */
	public String sendMsg() {

		String msg = sc.nextLine();
//		byte[] data = msg.getBytes();
		try {
			// string flag 0001
			String content = String.format("0001%s", msg);
			byte[] data = content.getBytes();

			// create packet and send
			packet = new DatagramPacket(data, data.length, 
					InetAddress.getByName("127.0.0.1"), port);
			socket.send(packet);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 
	 *** send file .txt
	 * 
	 */
	public void sendFile() {
		// D:\33\msg.txt
		String filePath = sc.nextLine();

		try {
			// send 后缀名
			int index = filePath.lastIndexOf(File.separator);
			String name = filePath.substring(index + 1);
			System.out.println(name);
			packet = new DatagramPacket(name.getBytes(), name.length(), 
					InetAddress.getByName("127.0.0.1"), port);
			socket.send(packet);

			// send file content
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));

			byte[] bs = new byte[1024 * 200000];
			int size = in.read(bs);
			
			// file flag 0000
			String content = String.format("0000%s", new String(bs, 0, size));
			System.out.println(content);
			byte[] data = content.getBytes();
			
			DatagramPacket packet2 = new DatagramPacket(data, data.length, 
					InetAddress.getByName("127.0.0.1"), port);

			// 发送
			socket.send(packet2);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("发送成功！！！");
	}

}
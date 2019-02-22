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
	 *** 发送字符串
	 * 
	 * @return
	 */
	public String sendMsg() {

		String msg = sc.nextLine();
//		byte[] data = msg.getBytes();
		try {
			// 增加 字符串标志 “0001”
			String content = String.format("0001%s", msg);
			byte[] data = content.getBytes();
//			System.err.println(content.length());
			// 创建数据包、发送
//			packet = new DatagramPacket(content.getBytes(), content.length(), InetAddress.getByName("127.0.0.1"), port);
			packet = new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), port);

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
	 *** 发文件
	 * 
	 */
	public void sendFile() {
		// D:\33\msg.txt
		String filePath = sc.nextLine();

		try {
			// 发送文件名字
			int index = filePath.lastIndexOf(File.separator);
			String name = filePath.substring(index + 1);
			System.out.println(name);
			packet = new DatagramPacket(name.getBytes(), name.length(), 
					InetAddress.getByName("127.0.0.1"), port);
			socket.send(packet);

			// 读本地文件内容
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));

			byte[] bs = new byte[1024 * 200000];
			int size = in.read(bs);
			
			// 增加 文件标志 “0000”
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
		System.out.println("文件发送成功！");
	}

}
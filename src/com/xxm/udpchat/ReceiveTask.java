package com.xxm.udpchat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.channels.NotYetBoundException;
import java.util.Arrays;


public class ReceiveTask implements Runnable {

	DatagramSocket socket;
	public ReceiveTask(DatagramSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		String msg = "no";
		String flag = "0000";
		byte[] buf = new byte[1024*8];
		BufferedOutputStream out = null;
		
		do {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			//接收 recieve
			try {
				socket.receive(packet);
				byte[] data = packet.getData();
				flag = new String(data, 0, 4);
				
				//recieve string
				if(flag.equals("0001")) {
					msg= new String(data, 4, packet.getLength()-4);
					System.out.println("recieve：" + msg);
//					System.out.println(msg.length());
					
				}
				else {
					// recieve file
					out = receiveFile(out, packet, data);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} while (!msg.toUpperCase().equals("BYE"));
		System.out.println("recieve over！！！");
		
	}

	public BufferedOutputStream receiveFile(
			BufferedOutputStream out, DatagramPacket packet, byte[] data)
			throws FileNotFoundException, IOException {
		String name = new String(data, 0, packet.getLength());
		File path = new File("D:\\22", name);
		if(path.exists()) {
			// the file exists
			System.out.println("接收成功！秒传");
		}
		else {
			
			byte [] buf = new byte[1024*200000];
			//�����ڣ�����
			DatagramPacket packet2 = new DatagramPacket(buf,buf.length);
			socket.receive(packet2);
			
			out = new BufferedOutputStream(new FileOutputStream(path));
			byte[] cont = packet2.getData();
//			System.out.println("cont.length:"+cont.length);
			
			String string = new String(cont,0,packet2.getLength()).substring(4);
//			System.out.println(string+ ": "+string.length());
			byte[] ss = string.getBytes();
			out.write(ss,0,ss.length);
			
//			out.write(string.getBytes(),0,string.length());
			System.out.println(new String(ss));
			
			System.out.println("接收成功！！！");
		}
		out.close();
		return out;
	}

}

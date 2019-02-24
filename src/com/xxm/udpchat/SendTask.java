package com.xxm.udpchat;

import java.net.DatagramSocket;
import java.util.Scanner;


public class SendTask implements Runnable {
	DatagramSocket socket;
	Data data = new Data();
	public SendTask(DatagramSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		int msgDecide = 0;
		Data data;
		String msg = "no";
		do {
			System.out.println("目标端口： ");
			Scanner scanner = new Scanner(System.in);
			int port = Integer.parseInt(scanner.nextLine());
			
			//两种信息类型
			System.out.println("发送字符串输入1，文件输入2：");
			msgDecide = scanner.nextInt();
			data = new Data(socket,port);
			System.out.print("send：");
			//发字符串
			if (msgDecide == 1) {
				msg = data.sendMsg();
			}
			//发文件
			if(msgDecide == 2) {
				data.sendFile();
			}
				
			
		} while (!msg.toUpperCase().equals("BYE"));
		System.out.println("发送结束");
		
	}

}

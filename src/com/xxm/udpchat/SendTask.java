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
			
			//发送内容的2种类型
			System.out.println("发送文字输入1，文件输入2：");
			msgDecide = scanner.nextInt();
			data = new Data(socket,port);
			System.out.print("发送：");
			//发文字
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

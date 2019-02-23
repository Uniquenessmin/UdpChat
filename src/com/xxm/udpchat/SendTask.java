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
			
			//�������ݵ�2������
			System.out.println("发送字符串输入1，文件输入2：");
			msgDecide = scanner.nextInt();
			data = new Data(socket,port);
			System.out.print("");
			//������
			if (msgDecide == 1) {
				msg = data.sendMsg();
			}
			//���ļ�
			if(msgDecide == 2) {
				data.sendFile();
			}
				
			
		} while (!msg.toUpperCase().equals("BYE"));
		System.out.println("发送结束");
		
	}

}

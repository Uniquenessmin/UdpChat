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
			System.out.println("Ŀ��˿ڣ� ");
			Scanner scanner = new Scanner(System.in);
			int port = Integer.parseInt(scanner.nextLine());
			
			//�������ݵ�2������
			System.out.println("������������1���ļ�����2��");
			msgDecide = scanner.nextInt();
			data = new Data(socket,port);
			System.out.print("���ͣ�");
			//������
			if (msgDecide == 1) {
				msg = data.sendMsg();
			}
			//���ļ�
			if(msgDecide == 2) {
				data.sendFile();
			}
				
			
		} while (!msg.toUpperCase().equals("BYE"));
		System.out.println("���ͽ���");
		
	}

}

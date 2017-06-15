package com.runbox.trace;

import java.util.Scanner;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Tracer {

	private Tracer() {
		
	}

	private static Tracer tracer = new Tracer();

	public static Tracer instance() {
		return tracer;		
	}

	private Socket socket = null;
	
	public void trace() throws Exception {
		socket = new Socket("127.0.0.1", 1025);
		while (true) {
            System.out.print("> ");
			String command = new Scanner(System.in).nextLine();
			if (command.equals("quit")) break;
            write(command);
			System.out.println(read());			
        }
		socket.close();
	}

	private void write(String message) throws Exception {
		if (null != message && !message.equals("")) {
			socket.getOutputStream().write(message.getBytes("UTF-8"));			
		}
	}

	private String read() throws Exception {
		byte[] data = new byte[16];
		socket.getInputStream().read(data);
		return new String(data, "UTF-8");
	}
}

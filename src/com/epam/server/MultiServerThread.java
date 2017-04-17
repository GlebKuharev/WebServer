package com.epam.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.epam.service.Request;
import com.epam.service.RequestHandler;

public class MultiServerThread extends Thread {

	private Socket socket = null;

	public MultiServerThread(Socket socket) {
		super("KKMultiServerThread");
		this.socket = socket;
	}

	public void run() {

		try (
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()));
				) {

			Request request = new Request();
			request.readAndParse(in);
			RequestHandler.handleRequest(request, out);
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

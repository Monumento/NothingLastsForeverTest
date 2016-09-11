/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.TCPIP;

import Connection.Helpclasses.BufferObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonas
 */
public class ServerThread implements Runnable {

	public boolean endConnection, stopClient;
	public Socket socket;
	public BufferObject messageOut, messageIn;
	public int id;
	private BufferedReader in;
	private PrintWriter out;

	public ServerThread(Socket socket) {
		endConnection = false;
		stopClient = false;

		this.socket = socket;

		messageOut = new BufferObject();
		messageIn = new BufferObject();
	}

	public void run() {
		in = null;
		out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception e) {
		}
		System.out.println("Verbindung zu " + socket.getRemoteSocketAddress() + " aufgebaut");
		String lineIn = " ";
		String lineOut = " ";

		try {
			lineIn = in.readLine();
			messageIn.updateMessage(lineIn);
		} catch (IOException ex) {
		}
		while (!stopClient) {

			lineOut = messageOut.getUpdateMessage();

			out.println(lineOut);
			try {
				lineIn = in.readLine();

			} catch (IOException ex) {
			}

			messageIn.updateMessage(lineIn);
		}
	}

	private void close() {
		try {
			out.close();
			socket.close();
			in.close();
			stopClient = true;
		} catch (IOException ex) {
			Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
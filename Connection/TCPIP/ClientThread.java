/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.TCPIP;

/**
 *
 * @author Jonas
 */
import Connection.Helpclasses.BufferObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread implements Runnable {

	public boolean endConnection, stopClient;
	public String ip;
	public int port;
	public BufferObject messageOut, messageIn;

	public int id;

	public ClientThread(String ip, int port) {
		endConnection = false;
		stopClient = false;

		this.ip = ip;
		this.port = port;

		messageOut = new BufferObject();
		messageIn = new BufferObject();
	}

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public void run() {
		socket = null;
		in = null;
		out = null;
		try {
			System.out.println(ip + " " + port);
			socket = new Socket(ip, port);
		} catch (UnknownHostException ex) {
		} catch (IOException ex) {
		}
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception e) {
		}
		System.out.println("Verbindung zu " + socket.getRemoteSocketAddress() + " aufgebaut");
		String lineOut = " ";
		String lineIn = " ";
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
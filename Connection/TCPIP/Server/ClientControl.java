/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.TCPIP.Server;

import Connection.TCPIP.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Helps the server to connect to many clients and keeps the ServerConnection
 * manager and main in connection with them
 *
 * @author jonas
 */
public class ClientControl implements Runnable {
	/*
	 * To change this template, choose Tools | Templates and open the template
	 * in the editor.
	 */

	/**
	 *
	 * @author Jonas
	 */
	public ExecutorService pool;
	public ServerThread[] threadPool;
	private int threadCount;
	private int startPort;

	public String[] getUpdateFromClients() {
		System.out.println(threadCount);
		String[] ret = new String[threadCount+1];
		for (int i = 0; i < threadCount; i++) {
			ret[i] = threadPool[i].messageIn.getUpdateMessage();
		}
		return ret;
	}
	// exceptions für voller werdendes backlog

	public boolean updateToClients(String[] message) {
		for (int i = 0; i < threadCount; i++) {
			threadPool[i].messageOut.updateMessage(message[i]);
		}
		return true;
	}

	public ClientControl(int startPort) {
		this.startPort = startPort;
		threadPool = new ServerThread[256];
		threadCount = 0;
	}

	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(startPort);
			pool = Executors.newCachedThreadPool();
			System.out.println("ClientControl läuft");
			while (true) {
				Socket socket = server.accept();
				threadPool[threadCount] = new ServerThread(socket);

				pool.execute(threadPool[threadCount]);
				threadCount++;
			}
		} catch (IOException e) {
			System.err.println(e);
			pool.shutdown();
		}
	}
}

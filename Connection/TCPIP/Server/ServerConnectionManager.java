/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.TCPIP.Server;

import Connection.TCPIP.Server.ClientControl;
import Connection.Helpclasses.BufferObject;
import Connection.TCPIP.ClientThread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * first connection keeps Connection to serverControl second to fifth servers
 * around this server 6-* clients
 *
 * @author jonas
 */
public abstract class ServerConnectionManager {

	/*
	 * To change this template, choose Tools | Templates and open the template
	 * in the editor.
	 */
	/**
	 *
	 * @author jonas
	 *
	 */
	// connections
	public ClientThread serverControl;
	public ClientControl clientControl;
	// threadpool
	public ExecutorService pool;
	public int threadCount;
	public int startPort;
	public String startIp;
	// internal messages

	// Connection Messages
	public ServerConnectionManager(String startIp, int startPort) {
		this.startPort = startPort;
		this.startIp = startIp;
		clientControl = new ClientControl(startPort);
		threadCount = 0;
	}
	// send update to serverControl

	public void updateServerControl(String message) {
		serverControl.messageOut.updateMessage(message);
	}
	// get update from serverControl

	public String getUpdateFromServerControl() {
		return serverControl.messageIn.getUpdateMessage();
	}

	// get update to clients
	public String[] getUpdateClients() {
		return clientControl.getUpdateFromClients();
	}
	// send update to clients

	public void updateToClients(String[] message) {
		clientControl.updateToClients(message);
	}

	public void startServer() {

		pool = Executors.newCachedThreadPool();
		// connect to ServerControl
		// TODO
		// serverControl = new ClientThread("", 1);
		// pool.execute(serverControl);
		//

		pool.execute(clientControl);
		threadCount++;
		System.out.println("ServerConnectionManager läuft...");
	}
}

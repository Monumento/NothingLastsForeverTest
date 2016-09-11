package Connection.TCPIP.ServerControl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Connection.TCPIP.ClientThread;
import Connection.TCPIP.Server.ClientControl;

public class ServerControlConnectionManager {

	public ClientThread systemControl;
	public ClientControl clientControl;
	// threadpool
	public ExecutorService pool;
	public int threadCount;
	public int startPort;
	public String startIp;
	// internal messages

	// Connection Messages
	public ServerControlConnectionManager(String startIp, int startPort) {
		this.startPort = startPort;
		this.startIp = startIp;
		clientControl = new ClientControl(startPort);
		threadCount = 0;
	}
	// send update to serverControl

	public void updateServerControl(String message) {
		systemControl.messageOut.updateMessage(message);
	}
	// get update from serverControl

	public String getUpdateFromServerControl() {
		return systemControl.messageIn.getUpdateMessage();
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
		// connect to SystemControl
		// TODO
		// serverControl = new ClientThread("", 1);
		// pool.execute(serverControl);
		//

		pool.execute(clientControl);
		threadCount++;
		System.out.println("ServerConnectionManager läuft...");
	}
}

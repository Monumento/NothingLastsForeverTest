/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Connection.TCPIP.Server.ServerConnectionManager;
import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class Server {

	public int serverID;

	ArrayList<ServerPart> serverPart;
	// clients and serverControl
	public ServerConnectionManager serverConnectionManager;

	public Server() {
		serverPart = new ArrayList<ServerPart>();
		serverConnectionManager = new ServerConnectionManager("127.0.0.1", 1000) {
		};
	}

	// public void sendAction(Action action)//explosion, Lebewesen suche anderes
	// Lebewesen, etc.
	// public Action getAction
	public void startServer() {
		serverConnectionManager.startServer(); // throws new
												// NoServerControlException
	}

	public boolean sendServerPart(int partNr) {

		return true;
	}

	public boolean sendServerPartAndDelete(int partNr) {

		return true;
	}

	public void updateToServerControl(String message) {
		serverConnectionManager.updateServerControl(message);
	}

	public String getUpdateFromServerControl() {
		return serverConnectionManager.getUpdateFromServerControl();
	}

	public void updateToClients(String[] message) {
		serverConnectionManager.updateToClients(message);
	}

	public String[] getUpdateFromClients() {
		return serverConnectionManager.getUpdateClients();
	}
}

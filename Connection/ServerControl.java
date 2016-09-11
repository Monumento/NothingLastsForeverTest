/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Connection.TCPIP.ServerControl.ServerControlConnectionManager;

/**
 *
 * @author jonas
 */
public class ServerControl {
	// und Client? alle daten an zentrale die wertet aus
	// rmi oder jms (jms test mit variable die erhöht wird und mehreren servern
	// in eclipse

	public int serverControlID;
	
	public ServerControlConnectionManager serverControlConnectionManager;

	public ServerControl(int serverControlID) {
		this.serverControlID = serverControlID;
	}

}

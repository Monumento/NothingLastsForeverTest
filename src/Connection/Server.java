/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import TCPIP.ServerConnectionManager;
import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class Server {

    ArrayList<ServerPart> serverPart;
    ServerConnectionManager controlAndClients;

    public Server() {
        serverPart = new ArrayList<ServerPart>();
        controlAndClients = new ServerConnectionManager("127.0.0.1", 1000) {
        };
    }

    //public void sendAction(Action action)//explosion, Lebewesen suche anderes Lebewesen, etc.
    //public Action getAction
    public void startServer() {
        controlAndClients.startServer(); //throws new NoServerControlException
    }

    public boolean sendServerPart(int partNr) {

        return true;
    }

    public boolean sendServerPartAndDelete(int partNr) {

        return true;
    }
}

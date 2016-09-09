/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

import Connection.BufferObject;
import com.jme3.math.Vector3f;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Keeps Connection to serverControl
 *
 * @author jonas
 */
public abstract class ServerConnectionManager {

    /*
     * To change this template, choose Tools | Templates
     * and open the template in the editor.
     */
    /**
     *
     * @author jonas
     *
     */
    //connections
    public EchoClient serverControl;
    public ClientControl clientControl;
    // threadpool
    public ExecutorService pool;
    public int threadCount;
    public int startPort;
    public String startIp;
    //internal messages
    public static BufferObject
    // Connection Messages
    public static BufferObject[] serverMessagesOut;
    public static BufferObject[] serverMessagesIn;

    public ServerConnectionManager(String startIp, int startPort) {
        this.startPort = startPort;
        this.startIp = startIp;

        threadCount = 0;

        serverMessagesOut = new BufferObject[16];
        serverMessagesIn = new BufferObject[16];
    }
// send update to serverControl

    public void updateServerControl(String message) {
        for (int i = 0; i < threadCount; i++) {
            serverControl.messageOut.message = message;
            serverControl.messageOut.updated = true;
        }
    }
//get update from serverControl

    public String[] getUpdate() {
        String[] ret = null;
        if (threadCount > 0) {
            ret = new String[threadCount];
            for (int i = 0; i < threadCount; i++) {
                ret[i] = serverControl.messageIn.message;
                serverControl.messageIn.updated = false;
            }
        }
        return ret;
    }

    public void startServer() {
        pool = Executors.newCachedThreadPool();
        serverControl = new EchoClient("", 1);
        pool.execute(serverControl);

        clientControl = new ClientControl(startPort, Vector3f.ZERO, Vector3f.ZERO);
        threadCount++;
        pool.execute(clientControl);
        System.out.println("ServerConnectionManager läuft...");
        while (true) {
            clientControl.getUpdateClients();
            sendUpdateClients();
        }

    }
}

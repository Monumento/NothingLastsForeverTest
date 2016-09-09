/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

import Connection.BufferObject;
import static TCPIP.ThreadServerTCP.threadCount;
import static TCPIP.ThreadServerTCP.threadPool;
import com.jme3.math.Vector3f;
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
     * To change this template, choose Tools | Templates
     * and open the template in the editor.
     */

    /**
     *
     * @author Jonas
     */
    public ExecutorService pool;
    public static EchoClient[] threadPool;
    public static int threadCount;
    private int startPort;
    public Vector3f player, otherPlayer;
    public String[] clientMessage;
    public String serverControlMessage;
    private BufferObject messageIn, messageOut;

    public String getUpdateFromServer() {
        String ret = messageIn.message;
        messageIn.updated = false;
        return ret;
    }

    public boolean updateToServer(String message) {
        if (!messageOut.updated) {
            messageOut.message = message;
            messageOut.updated = true;
        }

        return false;
    }

    public ClientControl(int startPort, Vector3f player, Vector3f otherPlayer) {
        this.startPort = startPort;
        this.player = player.clone();
        this.otherPlayer = otherPlayer.clone();
        threadPool = new EchoClient[256];
        threadCount = 0;
    }

    public void updateClients(String message) {
        for (int i = 0; i < threadCount; i++) {
            threadPool[i].messageOut.message = message;
            threadPool[i].messageOut.updated = true;
        }
    }

    public String[] getUpdateClients() {
        String[] ret = null;
        if (threadCount > 0) {
            ret = new String[threadCount];
            for (int i = 0; i < threadCount; i++) {
                ret[i] = threadPool[i].messageIn.message;
                threadPool[i].messageIn.updated = false;
            }
        }
        return ret;
    }

    public void run() {
        ServerSocket server;
        try {
            server = new ServerSocket(startPort);
            pool = Executors.newCachedThreadPool();
            System.out.println("ClientControl läuft");
            while (true) {
                Socket socket = server.accept();
                threadPool[threadCount] = new EchoClient("", 1);

                pool.execute(threadPool[threadCount]);
                threadCount++;
            }
        } catch (IOException e) {
            System.err.println(e);
            pool.shutdown();
        }
    }
}

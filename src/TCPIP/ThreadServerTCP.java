/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

import com.jme3.math.Vector3f;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Jonas
 */
public class ThreadServerTCP {

    public ExecutorService pool;
    public static EchoThread[] threadPool;
    public static int threadCount;
    private int startPort;
    public Vector3f player, otherPlayer;
    public String[] clientMessage;
    public String serverControlMessage;

    public ThreadServerTCP(int startPort, Vector3f player, Vector3f otherPlayer) {
        this.startPort = startPort;
        this.player = player.clone();
        this.otherPlayer = otherPlayer.clone();
        threadPool = new EchoThread[1000];
        threadCount = 0;
    }

    public void update(Vector3f player) {
        for (int i = 0; i < threadCount; i++) {
            threadPool[i].player = player.clone();
        }
    }

    public void sendServerControlMessage(String message) {
        serverControlMessage = message;
    }

    public void sendClientMessage(String message) {
        clientMessage[0] = message;
    }

    public Vector3f[] getUpdate() {
        Vector3f[] ret = null;
        if (threadCount > 0) {
            ret = new Vector3f[threadCount];
            for (int i = 0; i < threadCount; i++) {
                ret[i] = threadPool[i].otherPlayer;
            }

        }

        return ret;
    }

    public void startServer() {
        ServerSocket server;
        try {

            server = new ServerSocket(startPort);
            pool = Executors.newCachedThreadPool();
            System.out.println("Server läuft");
            while (true) {
                Socket socket = server.accept();
                threadPool[threadCount] = new EchoThread(socket, player, otherPlayer);

                pool.execute(threadPool[threadCount]);
                threadCount++;
            }
        } catch (IOException e) {
            System.err.println(e);
            pool.shutdown();
        }
    }
}

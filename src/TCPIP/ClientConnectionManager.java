/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

import Connection.BufferObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Helps client to connect to several and the right servers. first connection
 * ServerControl to get the Server the serverParts is running on, second to
 * fifth visible serverParts | maybe buffer servers for buffer serverparts
 *
 * @author jonas has a threadpool to control the servers
 *
 */
public class ClientConnectionManager {

    public ExecutorService pool;
    public EchoClient[] threadPool;
    public int threadCount;
    public int startPort;
    public String startIp;
    //

    public ClientConnectionManager(String startIp, int startPort) {
        this.startPort = startPort;
        this.startIp = startIp;

        threadPool = new EchoClient[16];
        threadCount = 0;
    }

    public void updateToServer(String[] message) {
        //servercontrol connection
        threadPool[0].messageOut.updateMessage(message[0]);
        for (int i = 1; i < threadCount; i++) {
            //actual server connectio
            threadPool[i].messageOut.updateMessage(message[2]);
        }
    }

    public String[] getUpdateFromServer() {
        String[] ret = null;
        if (threadCount > 0) {
            ret = new String[threadCount];
            for (int i = 0; i < threadCount; i++) {
                ret[i] = threadPool[i].messageIn.getUpdateMessage();
            }
        }
        return ret;
    }

    public void startClient() {
        pool = Executors.newCachedThreadPool();
        System.out.println("ClientConnectionManager läuft");
        while (true) {
            threadPool[threadCount] = new EchoClient("", 1);
            threadCount++;
            pool.execute(threadPool[threadCount]);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

/**
 *
 * @author Jonas
 */
import Connection.BufferObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoClient implements Runnable {

    public boolean endConnection, stopClient;
    public String ip;
    public int port;
    public BufferObject messageOut, messageIn;

    public EchoClient(String ip, int port) {
        endConnection = false;
        stopClient = false;

        this.ip = ip;
        this.port = port;

        messageOut = new BufferObject();
        messageIn = new BufferObject();
    }

    public void update(BufferObject messageIn) {
        this.messageIn = messageIn;
    }

    public BufferObject getUpdate() {
        return messageOut;
    }
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void run() {
        socket = null;
        in = null;
        out = null;
        try {
            System.out.println(ip + " " + port);
            socket = new Socket(ip, port);
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
        }

        String line = " ";
        while (!stopClient) {

            line = messageOut.getUpdateMessage();
            if (endConnection || messageIn.getUpdateMessage().equals("1")) {
                line = "1";
                close();
            }
            out.println(line);
            try {
                messageIn.updateMessage(in.readLine());
            } catch (IOException ex) {
            }
        }
    }

    private void close() {
        try {
            out.close();
            socket.close();
            in.close();
            stopClient = true;
        } catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
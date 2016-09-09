/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

import com.jme3.math.Vector3f;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


class EchoThread implements Runnable {
public Vector3f player, otherPlayer;
private Socket socket;
public EchoThread(Socket socket, Vector3f player,Vector3f otherPlayer) {
this.socket = socket;
        this.player = player.clone();
        this.otherPlayer = otherPlayer.clone();
}


public void run() {
try {
    BufferedReader in = new BufferedReader(new
InputStreamReader(socket.getInputStream()));
PrintWriter out;
    out = new PrintWriter(socket.getOutputStream(), true);
out.println("Verbindung zu " + socket.getRemoteSocketAddress() + " aufgebaut");
System.out.println("Verbindung zu " + socket.getRemoteSocketAddress() + " aufgebaut");
String input ="";
String line ="";
    //send pos and rotation of all units
    //send all movementObj in radius of


while (true) {
    line =  player.getX()+ " " + player.getY() + " " + player.getZ();
                                    out.println(line);
                                    input = in.readLine();
                                    if(input != null){
                                   String split[] = input.split(" ");
                                   Float [] pos = new Float[3];
                                   pos[0] = Float.parseFloat(split[0]);
                                   pos[1] = Float.parseFloat(split[1]);
                                   pos[2] = Float.parseFloat(split[2]);
                                   otherPlayer = new Vector3f(pos[0],pos[1]-2.5f,pos[2]);
                                         }
}  
} catch (Exception e) {
System.err.println(e);
} finally{
    System.out.println("Verbindung zu " + socket.getRemoteSocketAddress() + "abgebaut");

}
}
}

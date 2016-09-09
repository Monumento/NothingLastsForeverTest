/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

import com.jme3.math.Vector3f;
import combatSystem.UnitSpatialandGhostControl;
import java.io.IOException;
import mygame.Main;
import static mygame.Main.clientNorth;
import static mygame.Main.threadServerTCP;


/**
 *
 * @author Jonas
 */
public class ServerAndClientManager {
    
    static String[] ips =  new String[8];
    
    public static void setIPs(String[] ips){
        //test
        ips[0] = "192.168.2.111";
        
        //maybeFinal
        //ServerAndClientManager.ips = ips;
    }
    
    public static TCPSocketServer setUpLocalServer(TCPSocketServer server, UnitSpatialandGhostControl player,UnitSpatialandGhostControl otherPlayer){
               //server local
                     System.out.print("start Server(local)");
                    server = new TCPSocketServer(1000, 50,player,otherPlayer);
                    return server;
    }
        public static EchoClient setUpLocalClient(EchoClient clientNorth ,UnitSpatialandGhostControl playerThings ,UnitSpatialandGhostControl otherPlayer){
              //client local
            System.out.print("start Client(local)");
  
            return clientNorth;
 }

    public static EchoClient[] setUpOnlineClient(EchoClient[] clientEast, UnitSpatialandGhostControl playerThings, UnitSpatialandGhostControl otherPlayer) {
      return clientEast;
    }
    
    public static ThreadServerTCP setUpOnlineServer(ThreadServerTCP threadServerTCP){
        return threadServerTCP;
    }

    public static ThreadServerTCP setUpOnlineClientServer(ThreadServerTCP threadServerTCP,Vector3f player, Vector3f otherPlayer) {
        threadServerTCP = new ThreadServerTCP(1000, player, otherPlayer);

        return threadServerTCP;
    }

    public static TCPSocketServer setUpOnlineServerServer(TCPSocketServer serverServer, UnitSpatialandGhostControl playerThings, UnitSpatialandGhostControl otherPlayer) {
                  System.out.print("start ServerServer(Online)");
                    serverServer = new TCPSocketServer(1000, 50,playerThings,otherPlayer);
                    serverServer.startServer();
        

        return serverServer;
    }

    public static void setUpServerBridges(UnitSpatialandGhostControl playerThings, UnitSpatialandGhostControl otherPlayer) {
                   //Online 
                System.out.print("start serverbridges(North)");
     
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

/**
 *
 * @author Jonas
 */
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import combatSystem.UnitSpatialandGhostControl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPSocketServer{
        public boolean end;
    
	public int port;
	public int backlog;
        public Vector3f player, otherPlayer;
        public Vector3f weaponPos, otherPlayerWeaponPos;
        public Quaternion playerRotation,weaponRotation;
        public Quaternion otherPlayerRotation,otherWeaponRotation;
        
        
	public TCPSocketServer(int port, int backlog, UnitSpatialandGhostControl player,UnitSpatialandGhostControl otherPlayer) {
                end = false;
		this.port = port;
		this.backlog = backlog;
                this.player = player.getPhysicsLocation().clone();
                this.otherPlayer = otherPlayer.getPhysicsLocation().clone();
                this.playerRotation = player.spatial.getLocalRotation().clone();
                this.weaponRotation = player.weapons[0].getRotation().clone();
                this.otherPlayerRotation = otherPlayer.spatial.getLocalRotation().clone();
                this.otherWeaponRotation = otherPlayer.weapons[0].getRotation().clone();
                this.weaponPos = player.weapons[0].node.getLocalTranslation().clone();
                this.otherPlayerWeaponPos = otherPlayer.weapons[0].node.getLocalTranslation().clone();
	}

	public void startServer() {
		
ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port, backlog);
            } catch (IOException ex) {
                Logger.getLogger(TCPSocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
			System.out.println("EchoServer (iterativ) auf " + serverSocket.getLocalSocketAddress() + " gestartet ...");
            try {
                process(serverSocket);
            } catch (IOException ex) {
                Logger.getLogger(TCPSocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
		
	}
        
    public void updateAll(Vector3f player, Quaternion playerRot,Quaternion weaponRot, Vector3f weaponPos){
         this.player = player.clone();
         this.playerRotation = playerRot.clone();
         this.weaponRotation = weaponRot.clone();
         this.weaponPos = weaponPos.clone();
     }
     
     public Vector3f[] getUpdatePosition(){
         Vector3f[] positions = new Vector3f[2];
         positions[0] = otherPlayer;
         positions[1] = otherPlayerWeaponPos;
         return positions;
     }
      public Quaternion[] getPlayerRotation(){
          Quaternion[] rotations = new  Quaternion[4];
          rotations[0] = otherPlayerRotation;
          rotations[1] = otherWeaponRotation;
         return rotations;
     }
	private void process(ServerSocket server) throws IOException {
		while (true) {
                    
                        otherPlayer = (player.add(new Vector3f(2,-0.5f,2)));
                   
                       SocketAddress socketAddress = null;
                       Socket socket = server.accept();
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				socketAddress = socket.getRemoteSocketAddress();
				System.out.println("Verbindung zu " + socketAddress + " aufgebaut");
                              
                                   String input = "";
                                String line;
        
				while (true) {
                                    
                                   line =  player.getX()+ " " + player.getY() + " " + player.getZ() 
                                           + " " + playerRotation.getX() + " " + playerRotation.getY() 
                                           + " " + playerRotation.getZ()+ " " + playerRotation.getW()    
                                           + " " + weaponRotation.getX() + " " + weaponRotation.getY() 
                                           + " " + weaponRotation.getZ()+ " " + weaponRotation.getW()
                                           +" " +weaponPos.x +" "+weaponPos.y +" "+weaponPos.z;
                                   if(end)
                                       line = "1";
                                   out.println(line);
                                    input = in.readLine();
                        if(input.equals("1")){
                        System.exit(1);
                    }
                                    if(line!=null){
                                   String split[] = input.split(" ");
                                   Float [] values = new Float[14];
                                   if(split.length == 14){
                                                                      //pos
                                   values[0] = Float.parseFloat(split[0]);
                                   values[1] = Float.parseFloat(split[1]);
                                   values[2] = Float.parseFloat(split[2]);
                                   //playerRotation
                                   values[3] = Float.parseFloat(split[3]);
                                   values[4] = Float.parseFloat(split[4]);
                                   values[5] = Float.parseFloat(split[5]);
                                   values[6] = Float.parseFloat(split[6]);
                                   //weaponRotation
                                   values[7] = Float.parseFloat(split[7]);
                                   values[8] = Float.parseFloat(split[8]);
                                   values[9] = Float.parseFloat(split[9]);
                                   values[10] = Float.parseFloat(split[10]);
                                   
                                    values[11] = Float.parseFloat(split[11]);
                                   values[12] = Float.parseFloat(split[12]);
                                   values[13] = Float.parseFloat(split[13]);

                                   otherPlayer = new Vector3f(values[0],values[1]-2.5f,values[2]);
                                   otherPlayerRotation = new Quaternion(values[3],values[4],values[5],values[6]);
                                   otherWeaponRotation = new Quaternion(values[7],values[8],values[9],values[10]);  
                                   otherPlayerWeaponPos = new Vector3f(values[11],values[12],values[13]);
                                   }

                                         }
				}  
                                
					
		}
	}

	
}

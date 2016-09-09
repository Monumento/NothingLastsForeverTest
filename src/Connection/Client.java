/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;


import TCPIP.ClientConnectionManager;


/**
 *
 * @author jonas
 */
public class Client {
    
    public ClientConnectionManager clientToServerConnections;
    
    ServerPart[] visibleParts;
    ServerPart[] loadedParts;
  
    
    public Client(String startIp, int visibleParts){
        this.visibleParts = new ServerPart[visibleParts];
        this.visibleParts = new ServerPart[visibleParts*4];
        clientToServerConnections = new ClientConnectionManager(startIp, 1000);
    }
    
    public String[] getUpdate(){
        return  clientToServerConnections.getUpdateFromServer();
    }
    
    public boolean update(String[] message){
        clientToServerConnections.updateToServer(message);
        return true;
    }
    
}

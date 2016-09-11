/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;


import Connection.TCPIP.Client.ClientConnectionManager;


/**
 *
 * @author jonas
 */
public class Client {
    
    public int clientID;
    String startIp;
    public ClientConnectionManager clientConnectionManager;
    
    ServerPart[] visibleParts;
    ServerPart[] loadedParts;
  
    
    public Client(String startIp, int visibleParts){
    	this.startIp = startIp;
        this.visibleParts = new ServerPart[visibleParts];
        this.visibleParts = new ServerPart[visibleParts*4];
        clientConnectionManager = new ClientConnectionManager(startIp, 1000);
    }
    
    public void startClient(){
         clientConnectionManager.startClient();
    }
    
    public String[] getUpdate(){
        return  clientConnectionManager.getUpdateFromServer();
    }
    
    public boolean update(String[] message){
        clientConnectionManager.updateToServer(message);
        return true;
    }
    public boolean  startNewConnection(){
        return clientConnectionManager.startNewConnection(startIp, 1000);
    }

}

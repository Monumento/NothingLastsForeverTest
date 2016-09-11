/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;



import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class SystemControl {
    
    public int systemControlID;
    
    ArrayList<ServerControl> serverControls;

    
    public void evaluateServers(){
        ServerInformation serverInformation = getServerInformation();
    }
    
    public void evaluateServerControls(){
        
    }

    private ServerInformation getServerInformation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

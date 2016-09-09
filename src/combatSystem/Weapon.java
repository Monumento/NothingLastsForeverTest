/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combatSystem;


import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Jonas
 */
public class Weapon {
    
    public Spatial spatial;
    public GhostControl ghost;
    public Node node;
    
    public Quaternion getRotation(){
        return node.getLocalRotation();
    }
      
     public void setPhysicsLocation(Vector3f vector){
         //spatial.setLocalTranslation(vector);
         //ghost.setPhysicsLocation(vector);
         node.setLocalTranslation(vector);
     }
    
    public Weapon(Spatial spatial, GhostControl ghost, Node node){
        this.spatial = spatial;
        this.ghost = ghost;
        this.node = node;
    }
    
    
}

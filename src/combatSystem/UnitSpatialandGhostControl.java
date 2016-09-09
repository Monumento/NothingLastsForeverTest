/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combatSystem;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;



/**
 *
 * @author Jonas
 */
public class UnitSpatialandGhostControl {

  public Spatial spatial;
  public Weapon[] weapons;
  public int howManyWeapons;
  public CharacterControl character;
  public boolean  hitting;
  public GhostControl ghost;
  public boolean changeFlag;
  
  public boolean isPlayer;
 public  Camera camera;
 
 public int hp;

 public boolean gotHit;
 public long lastTimeGotHit;
  public Spatial getSpatail(){
      return spatial;
  }
  
  public UnitSpatialandGhostControl(Spatial spatial, int howManyWeapons, boolean  isPlayer){
      changeFlag = false;
      this.spatial = spatial;
      weapons = new Weapon[howManyWeapons];
        this.howManyWeapons= 0;
        hitting = false;
        this.isPlayer = isPlayer;
      hp = 100;
      gotHit =false;
    }
  
  public void updateRotationPlayer(){

weapons[0].setPhysicsLocation(spatial.getLocalTranslation().add(camera.getDirection().mult(2f)).subtract(new Vector3f(0,(float)(camera.getDirection().mult(4f).y+3),0)));
if(!hitting)
weapons[0].node.setLocalRotation(camera.getRotation());
  }
  
  
  public Vector3f getPhysicsLocation(){
      return spatial.getLocalTranslation();
  }
  
    public void setPhysicsLocation(Vector3f vector){
       changeFlag = true;
        spatial.setLocalTranslation(vector);
        if(ghost!=null)
        ghost.setPhysicsLocation(vector);

      

    }
    public void setCharacter(CharacterControl character){
        this.character =  character;
    }
    public CharacterControl  getCharacter(){
        return character;
    }
    
    public Weapon[] getWeapons(){
        return weapons;
    }
 
    public boolean addWeapon(Spatial spatial, GhostControl ghost, Node node){
        if(howManyWeapons < weapons.length){
      
            weapons[howManyWeapons] = new Weapon(spatial, ghost, node);
                  howManyWeapons++;
            return true;
        } else{
            return false;
        }
    }
}

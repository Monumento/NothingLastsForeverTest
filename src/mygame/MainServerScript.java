/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author jonas
 */







import Connection.Server;
import JAVARMI.WeltInterface;


import SettingsGUI.SettingsGUI;
import TCPIP.EchoClient;
import TCPIP.TCPSocketServer;
import TCPIP.ServerAndClientManager;
import TCPIP.ThreadServerTCP;
import Welt.Welt;
import Welt.biome.Ebene;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;

import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;


import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

import com.jme3.scene.Spatial;
import combatSystem.UnitSpatialandGhostControl;
import helpClasses.HilfsKlassen;

import helpClasses.Speichern;
import helpClasses.Vector2f;
import humanoide.meineRasse.MeineRasseBauer;
import java.awt.Color;


import lebewesen.Lebewesen;
import lebewesen.tiere.Kuh;
import lebewesen.tiere.Wolf;



/**
 * test
 * @author normenhansen
 */
public class MainServerScript extends SimpleApplication implements ActionListener  {
    //engine
    private BulletAppState bulletAppState;
    private GhostControl ghostComputer, ghostFeuerball;
    private CharacterControl player;
    private GhostControl ghostControlPlayer,ghostControlUnit;
    private RigidBodyControl ballControl, computerControl, fireControl,control;
    private Vector3f walkDirection = new Vector3f(0.0f,0.0f,0.0f);
    private boolean two,one, left, right , up , down, jump, run, shoot, firstStart=true,speichern,laden;
    private GhostControl unitControl[],grassControl;
    private Spatial units[];

    int unitCount = 0;
    //mygame
    public static UnitSpatialandGhostControl unit, playerThings, otherPlayer;
    public static WeltInterface welt1;
    int count = 0;
    int saveCount = 0;
    long lastTime;
    //combatSystem
    
        Quaternion q,q2,q3;
        Welt welt; 

            
            
        //Server Client Setup
        //TCP IP
    public static TCPSocketServer serverServer;
   public static EchoClient clientNorth,clientSouth,clientWest,clientEast;
    public static EchoClient[] playerClient;
   public static ThreadServerTCP threadServerTCP;
   
   //JMS

    //vllt über anderes Protokoll (Provider/Subscriber für jeden server eine | jms)
    //DynamicContent dynCont = playerClientServer.getContent
    //EchoClient bufferedContentClient[16]
    //BufferedContent buffContent[i] = bufferedContentClient[i].getContent;
    public static int isServer = -1;
    
    //digging
    public static RigidBodyControl[] groundparts;//(visible)
    //sererEssentials
    public static Server server;
    

    public static void main(String[] args) {
            //Choose Server/CLient
               // SettingsGUI settings = new SettingsGUI();
                //settings.inputSettings();
                    //startMain
        System.out.println("this");
                 Main app = new Main();
                 server = new Server();
                 app.start();
                 //TCP/IP Configuration
                 server = new Server();
                 server.startServer();
                 while(playerThings == null || otherPlayer == null || playerThings.weapons[0] ==null|| otherPlayer.weapons[0] ==null);
                
                  System.out.print("Setup Finished");
    }


    @Override
    public void simpleInitApp() {
        Ebene e = new Ebene(welt, new Vector2f(100, 100));  
        HilfsKlassen.setWelt(welt);
                //camera
         bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        cam.setLocation(new Vector3f(0.0f,0.5f,0.0f));
           //World
                      
        initPlayer();
        initOtherPlayer();
        initKeys();

        initTerrain();
        initLight();
        initSky();



    }


    
    @Override
    public void simpleUpdate(float tpf) {
        playerThings.updateRotationPlayer();

        if(isServer == 1){
            if(otherPlayer != null){
               // System.out.println(otherPlayer.getPhysicsLocation().getY());
         serverServer.updateAll(playerThings.getPhysicsLocation()
                 ,playerThings.spatial.getLocalRotation()
                 ,playerThings.weapons[0].getRotation()
                 ,playerThings.weapons[0].node.getLocalTranslation());
         otherPlayer.setPhysicsLocation(serverServer.getUpdatePosition()[0]);
         otherPlayer.weapons[0].node.setLocalTranslation(serverServer.getUpdatePosition()[1]);
         if(serverServer.getPlayerRotation()[0] !=null && serverServer.getPlayerRotation()[1] != null){
                      otherPlayer.spatial.setLocalRotation(serverServer.getPlayerRotation()[0]);
         otherPlayer.weapons[0].node.setLocalRotation(serverServer.getPlayerRotation()[1]);
         }

            }
        } else if(isServer==2 || isServer ==4){
            if(otherPlayer != null){
              //  System.out.println(otherPlayer.getPhysicsLocation().getY());
          clientNorth.updateAll(playerThings.getPhysicsLocation()
                  ,playerThings.spatial.getLocalRotation(),playerThings.weapons[0].getRotation()
                  ,playerThings.weapons[0].node.getLocalTranslation());
         otherPlayer.setPhysicsLocation(clientNorth.getUpdatePosition()[0]);
         otherPlayer.weapons[0].node.setLocalTranslation(clientNorth.getUpdatePosition()[1]);
                  if(clientNorth.getPlayerRotation()[0] !=null && clientNorth.getPlayerRotation()[1] != null){
                      otherPlayer.spatial.setLocalRotation(clientNorth.getPlayerRotation()[0]);                
         otherPlayer.weapons[0].node.setLocalRotation(clientNorth.getPlayerRotation()[1]);
         }
            }
       
        }else if(isServer == 3){

            threadServerTCP.update(playerThings.getPhysicsLocation());
            Vector3f[] players = threadServerTCP.getUpdate();
            if(players != null){

             otherPlayer.setPhysicsLocation(players[0]);
            }
        }
        
        playerThings.setPhysicsLocation(player.getPhysicsLocation().subtract(new Vector3f(0,-1f,0)));
        //Test
        if(playerThings.hitting){
            playerThings.getWeapons()[0].node.getLocalRotation().slerp(q3, 0.5f);
             float comparePThingsX = (float)(((int)(playerThings.getWeapons()[0].node.getLocalRotation().getX()*1000))/1000.0);
            float compareQuantX = (float)(((int)(q3.getX()*1000))/1000.0);
             float comparePThingsY = (float)(((int)(playerThings.getWeapons()[0].node.getLocalRotation().getY()*1000))/1000.0);
            float compareQuantY = (float)(((int)(q3.getY()*1000))/1000.0);
            
            float comparePThingsZ = (float)(((int)(playerThings.getWeapons()[0].node.getLocalRotation().getZ()*1000))/1000.0);
            float compareQuantZ = (float)(((int)(q3.getZ()*1000))/1000.0);
         float comparePThingsW = (float)(((int)(playerThings.getWeapons()[0].node.getLocalRotation().getW()*1000))/1000.0);
            float compareQuantW = (float)(((int)(q3.getW()*1000))/1000.0);
            
            if((comparePThingsZ == compareQuantZ && comparePThingsX == compareQuantX && comparePThingsY == compareQuantY && comparePThingsW ==compareQuantW )
                    || (comparePThingsZ == -compareQuantZ && comparePThingsX == -compareQuantX && comparePThingsY == -compareQuantY && comparePThingsW ==-compareQuantW )){
                playerThings.hitting = false;
                playerThings.getWeapons()[0].node.setLocalRotation(q);
            }
        } else{
            q3 = new Quaternion();
            if(cam.getDirection().getX()>0){
             q3 = q3.fromAngles(90,-110-(cam.getDirection().z+1f)*1.5f,50);
                }else{
             q3 = q3.fromAngles(90,110+(cam.getDirection().z+1f)*1.5f,50);
            }
        }

updateUnits();
//TODO  
     if(otherPlayer.ghost.getOverlappingCount()>0){

         if(!otherPlayer.gotHit){
             otherPlayer.lastTimeGotHit = System.currentTimeMillis();
          System.out.println("hitOtherPlayer");
         otherPlayer.hp = otherPlayer.hp-1;
         otherPlayer.gotHit = true; 
         } else if(System.currentTimeMillis()-otherPlayer.lastTimeGotHit>800){
          otherPlayer.gotHit = false; 
         }
    
         if(otherPlayer.hp<0){
             if(isServer==1){
                 serverServer.end=true;
             }else{
                 clientNorth.end=true;
             }
         }
           
     }

//Player moveMent
        Vector3f camDir = cam.getDirection().multLocal(0.2f);
        Vector3f camLeft = cam.getLeft().multLocal(0.1f);
        
        walkDirection.set(0.0f,0.0f,0.0f);
        
        
       
        if ( left){
            if(run){
            walkDirection.addLocal(camLeft).mult(0.4f);
            } else{
                walkDirection.addLocal(camLeft).mult(0.2f);
            }
        }else if (right){
            if(run){
                walkDirection.addLocal(camLeft.negate().mult(0.4f));
            } else {
            walkDirection.addLocal(camLeft.negate().mult(0.2f));
            }
        }else if (up){
            if(run){
                 walkDirection.addLocal(camDir.mult(0.4f));
            }
             walkDirection.addLocal(camDir.mult(0.2f));
        }else if (down){
            if(run){
                 walkDirection.addLocal(camDir.negate().mult(0.4f));
            }
            walkDirection.addLocal(camDir.negate().mult(0.2f));
        } 

        walkDirection.setY(0.0f);
        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation().subtract(0,0.5f,0));
        //mystuff

        
    }

    private void updateUnits() {
  
             Lebewesen l = welt.getEinheiten()[0][0]; 
 

            // pfalnzen.wachsen(), tiere,

            lastTime = System.currentTimeMillis();

            count++;

            l = welt.getEinheiten()[0][0];
            // array oder aehnliches
            Kuh k = null;
            Wolf w = null;
            MeineRasseBauer mRB = null;
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    if (l != null) {
                        // nicht tot := != RED
                        if (l.getColor() != Color.RED) {
                            if (l.getElementNumber() == 100) {
                                k = (Kuh) l;
                                k.macheEtwas();
                            } else if (l.getElementNumber() == 101) {
                                w = (Wolf) l;
                                w.macheEtwas();
                            }    else if (l.getElementNumber() == 151) {
                                mRB = (MeineRasseBauer) l;
                                mRB.macheEtwas();
                            }
                          
                        }

                    }
                    l = welt.getEinheiten()[i][j];
                }
            }
                     for(int i = 0; i< 100;i++){
                for(int j = 0; j< 100;j++){   
                    if(welt.getEinheiten()[i][j] != null){  
                 units[unitCount].setLocalTranslation(
                         new Vector3f((float)welt.getEinheiten()[i][j].getPosition().getPosX()-100,0
                         ,(float)welt.getEinheiten()[i][j].getPosition().getPosY()-100));
                   unitCount++;
             }    
        } 
      }
         unitCount = 0;  
        
           
            
            if (count == 50) {

                saveCount++;
                count = 0;
                l = welt.getEinheiten()[0][0];
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j < 100; j++) {
                        if (l != null)
                            if (l.getColor() != Color.RED)
                                if (l.getElementNumber() == 100
                                        || l.getElementNumber() == 101) {
                                    if (l.isSchwanger())
                                        l.erhoeheSchwangerschaft();
                                    l.erhoeheBeduerfnisse();
                                }
                        l = welt.getEinheiten()[i][j];
                    }
                }
                if (saveCount == 20) {
                    System.out.println("AutoSafe");
                    Speichern speichern = new Speichern();
                    speichern.saveMap(welt.getWeltElemente());
                    speichern.saveUnits(welt.getEinheiten());
                    speichern.savePlants(welt.getPflanzen());
                    saveCount = 0;
                }
            }
   
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initTerrain() {
        int part= 0;
        for(int k  = 0;k<4;k++)
        for(int i = 0; i< 30;i++){
            for(int j = 0; j< 30;j++){
              
                 Spatial terrain = assetManager.loadModel("Models/lowResDirt/lowResDirt.j3o");
                   if(k==3 && i <10 && j<10){
                  if(welt.getWeltElemente()[i][j].getElementNumber() == 5){
                   terrain = assetManager.loadModel("Models/lowResGrass2/lowResGrass2.j3o");
                  } else if(welt.getWeltElemente()[i][j].getElementNumber() == 0){
                    terrain = assetManager.loadModel("Models/lowResWater/lowResWater.j3o");
                }   
                }
        RigidBodyControl landscapeControl = new RigidBodyControl(0.0f);
        terrain.setLocalScale(2f);
        terrain.setLocalTranslation(new Vector3f(i*4f-60,-16+k*4 ,j*4f-60));
        rootNode.attachChild(terrain);
        terrain.addControl(landscapeControl);
        bulletAppState.getPhysicsSpace().add(landscapeControl);
        groundparts[part] = landscapeControl;
        part++;
        } 
        }
        
    }

    private void initPlayer() {

        //unitSpatial kennt rootNode und bulletappstate damit das hier nicht mehr in der main steht
        CapsuleCollisionShape playerShape = new CapsuleCollisionShape(1f,1.5f);

        player = new CharacterControl(playerShape,0.5f);
        ghostControlPlayer = new GhostControl(playerShape);
        Spatial playerSpatial = assetManager.loadModel("/Models/CrystalGuy/CrystalGuy.j3o");
        ghostControlPlayer.setPhysicsLocation(new Vector3f(5,5,5));

  
   
        Spatial weaponSpatial = assetManager.loadModel("/Models/weapon002/weapon002.j3o");
        CapsuleCollisionShape collSHape = new CapsuleCollisionShape(0.2f, 1.5f);
        GhostControl weaponGhost = new GhostControl(collSHape);
weaponSpatial.addControl(weaponGhost);
        rootNode.attachChild(weaponSpatial);
        rootNode.attachChild(playerSpatial);



        player.setFallSpeed(5);
        player.setGravity(800);
        player.setJumpSpeed(75);
        player.setPhysicsLocation(new Vector3f(5,1,5.0f));
        weaponGhost.setPhysicsLocation(new Vector3f(100,100,100));
        bulletAppState.getPhysicsSpace().add(weaponGhost);
        bulletAppState.getPhysicsSpace().add(ghostControlPlayer);
        bulletAppState.getPhysicsSpace().add(player);
  
        playerThings = new UnitSpatialandGhostControl(playerSpatial, 1, true);
        Node node = new Node("myNode");
        node.attachChild(weaponSpatial);
        playerThings.addWeapon(weaponSpatial, weaponGhost,node);
        playerThings.ghost =ghostControlPlayer;
     rootNode.attachChild(node);
        //wenn multiplayer TODO
       playerThings.setPhysicsLocation(player.getPhysicsLocation());
      playerThings.camera = cam;
     

    }

    private void initOtherPlayer(){
          //unitSpatial kennt rootNode und bulletappstate damit das hier nicht mehr in der main steht
        CapsuleCollisionShape playerShape = new CapsuleCollisionShape(1f, 1.5f);

        GhostControl ghostControlOtherPlayer = new GhostControl(playerShape);
      ghostControlOtherPlayer.setPhysicsLocation(new Vector3f(5.0f,5.0f,10.0f));
        Spatial playerSpatial = assetManager.loadModel("/Models/CrystalGuy/CrystalGuy.j3o");
        ghostControlOtherPlayer.setPhysicsLocation(new Vector3f(1,1,1));

  
   
        Spatial weaponSpatial = assetManager.loadModel("/Models/weapon002/weapon002.j3o");
        CapsuleCollisionShape collSHape = new CapsuleCollisionShape(0.2f,1.5f);
        GhostControl weaponGhost = new GhostControl(collSHape);
        weaponSpatial.addControl(weaponGhost);
        rootNode.attachChild(weaponSpatial);
        rootNode.attachChild(playerSpatial);


        weaponGhost.setPhysicsLocation(new Vector3f(1,1,1));
        //bulletAppState.getPhysicsSpace().add(weaponGhost);
        bulletAppState.getPhysicsSpace().add(ghostControlOtherPlayer);

  
        otherPlayer = new UnitSpatialandGhostControl(playerSpatial, 1, false);
        Node node = new Node("myNode");
        node.attachChild(weaponSpatial);
        otherPlayer.addWeapon(weaponSpatial, ghostControlOtherPlayer,node);
        otherPlayer.ghost = ghostControlOtherPlayer;
     rootNode.attachChild(node);
     otherPlayer.setPhysicsLocation(new Vector3f(0f,-1f,0f));

    }
    
    private void initLight(){
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White);
        rootNode.addLight(ambientLight);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1,-1,-1));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }
    
    private void initSky(){
        Spatial sky = assetManager.loadModel("Scenes/Sky.j3o");
        rootNode.attachChild(sky);
    }
         public void initKeys(){
             
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Run", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("One", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Two", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("Speichern", new KeyTrigger(KeyInput.KEY_0));
        inputManager.addMapping("Laden", new KeyTrigger(KeyInput.KEY_9));

        inputManager.addListener( this, "Left");
        inputManager.addListener(this, "Right");
       inputManager.addListener(this, "Up");
       inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
        inputManager.addListener(this, "Run");
        inputManager.addListener(this, "Shoot");
       inputManager.addListener(this, "One");
        inputManager.addListener(this, "Two");
        inputManager.addListener(this, "Speichern");
         inputManager.addListener(this, "Laden");
        

    }
           public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Left")){
            left = isPressed;
        }else if(name.equals("Right")){
            right = isPressed;
        }
         else  if(name.equals("Up")){
            up = isPressed;
        }else   if(name.equals("Down")){
            down = isPressed;
        } else   if(name.equals("Jump")){
            player.jump();
        } else if (name.equals("Run")){
             run = isPressed;
        } else if(name.equals("One") && !isPressed){
            //optimize TODO >0
            player.setPhysicsLocation(player.getPhysicsLocation().add(cam.getDirection().mult(50)));
        }
         else if(name.equals("Two") && !isPressed){
           two = isPressed;
           walkDirection.set(cam.getDirection().mult(20));
           player.setWalkDirection(walkDirection);
           cam.setLocation(player.getPhysicsLocation());
        } 
        if(name.equals("Speichern")){

            System.out.println(1);
        }
          if(name.equals("Laden")){

            System.out.println(2);
        }
        if(name.equals("Shoot") && !isPressed){
           shootFireball();
        }
        
        
    }

    private void initUnits() {
        Lebewesen lebewesen = null;
unitControl = new GhostControl[100];
    units = new Spatial[1000];
    for(int i = 0; i< 100;i++){
        for(int j = 0; j< 100;j++){
             
             if(welt.getEinheiten()[i][j] != null){
                 Spatial model = assetManager.loadModel("/Models/maybeACOw/maybeACOw.j3o");
                 if(welt.getEinheiten()[i][j].getElementNumber()==101){
                     model = assetManager.loadModel("/Models/wolf/wolf.j3o");
                 } else if(welt.getEinheiten()[i][j].getElementNumber()==151){
                     model = assetManager.loadModel("/Models/CrystalGuy/CrystalGuy.j3o");
                 }

                    lebewesen=welt.getEinheiten()[i][j];
                    unitControl[unitCount] = new GhostControl(new BoxCollisionShape(new Vector3f(1,1,1)));
                    model.addControl( unitControl[unitCount]);
                    units[unitCount] = model;
                    units[unitCount].setLocalTranslation(new Vector3f((float)lebewesen.getPosition().getPosX()/20,2,(float)lebewesen.getPosition().getPosX()/20));
                    rootNode.attachChild(model); 
                    bulletAppState.getPhysicsSpace().add(unitControl[unitCount]);
                    lebewesen = null;
                    unitCount++;
            }
        }
       
    }
    System.out.print(unitCount);
    unitCount = 0;

    

  
 //   bulletAppState.getPhysicsSpace().add(cowControl);
    }

    private void initWater() {
            for(int i = 0; i< 100;i++){
                   for(int j = 0; j< 100;j++){
                        if(welt.getWeltElemente()[i][j]!= null && welt.getWeltElemente()[i][j].getColor() == Color.BLUE){
                            unitCount++;
                            Spatial water = assetManager.loadModel("/Models/Pool/Pool.j3o");
                           // grassControl = new RigidBodyControl(0.5f); GhostControl
                            water.addControl(grassControl);
                            grassControl.setPhysicsLocation(new Vector3f(i*5-75,0,j*5-75));
                            rootNode.attachChild(water);
                            if(unitCount == 20){
                                i=100;
                                j=100;
                            }
           
                        }
                }
            }
     
             
            
       unitCount = 0;     
    }
    public void shootFireball(){
        if(!playerThings.hitting){
            playerThings.hitting = true;
        }
        
    }

    private void initUnitsTest() {

        Spatial unitModel = assetManager.loadModel("/Models/Cow/Cow.j3o");
        CapsuleCollisionShape unitShape = new CapsuleCollisionShape(2f,2f);
  
       

        ghostControlUnit = new GhostControl(unitShape);
        ghostControlUnit.setSpatial(unitModel);
        unit = new UnitSpatialandGhostControl(unitModel ,1, false);
        unit.setPhysicsLocation(new Vector3f(0.5f,0.5f,0.5f));
                unitModel.addControl(ghostControlUnit);

       unit.ghost = ghostControlUnit;
        rootNode.attachChild(unitModel);
        bulletAppState.getPhysicsSpace().add(ghostControlUnit);

    }
    
 

}


package mygame;

import Connection.Client;
import JAVARMI.WeltInterface;


import SettingsGUI.SettingsGUI;
import TCPIP.EchoClient;
import TCPIP.TCPSocketServer;
import TCPIP.ServerAndClientManager;
import TCPIP.ThreadServerTCP;
import Welt.Welt;
import Welt.biome.Ebene;
import beduerfnisse.tiere.GrundBeduerfnisse;
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
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

import com.jme3.scene.Spatial;
import combatSystem.UnitSpatialandGhostControl;
import helpClasses.HilfsKlassen;

import helpClasses.Speichern;
import helpClasses.Vector2f;
import humanoide.meineRasse.MeineRasse;
import humanoide.meineRasse.MeineRasseBauer;
import java.awt.Color;


import lebewesen.Lebewesen;
import lebewesen.tiere.Kuh;
import lebewesen.tiere.Wolf;
import static mygame.Main.clientNorth;
import static mygame.Main.groundparts;
import static mygame.Main.isServer;
import static mygame.Main.otherPlayer;
import static mygame.Main.playerClient;
import static mygame.Main.playerThings;
import static mygame.Main.serverServer;
import static mygame.Main.threadServerTCP;
import static mygame.Main.unit;

/**
 * test
 *
 * @author normenhansen
 */
public class MainClientScript extends SimpleApplication implements ActionListener {
    //game connection

    //engine
    private BulletAppState bulletAppState;
    private GhostControl ghostComputer, ghostFeuerball;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f(0.0f, 0.0f, 0.0f);
    private boolean two, one, left, right, up, down, jump, run, shoot, firstStart = true, speichern, laden;
    private GhostControl unitControl[], grassControl;
    private Spatial units[];
    int unitCount = 0;
    //mygame
    public static UnitSpatialandGhostControl unit, playerThings, otherPlayer;
    public static WeltInterface welt1;
    int count = 0;
    int saveCount = 0;
    long lastTime;
    //combatSystem
    Quaternion q, q2, q3;
    Welt welt;
    public static Client client;
    //Server Client Setup
    //TCP IP
    //JMS
    //vllt über anderes Protokoll (Provider/Subscriber für jeden server eine | jms)
    //DynamicContent dynCont = playerClientServer.getContent
    //EchoClient bufferedContentClient[16]
    //BufferedContent buffContent[i] = bufferedContentClient[i].getContent;
    //digging
    public static RigidBodyControl[] groundparts;//(visible)

    public static void main(String[] args) {
        //

        //Choose Server/CLient
        // SettingsGUI settings = new SettingsGUI();
        //settings.inputSettings();
        //startMain
        Main app = new Main();
        app.start();
        //TCP/IP Configuration


        while (playerThings == null || playerThings.weapons[0] == null);



        System.out.print("Setup Finished");
    }

    @Override
    public void simpleInitApp() {
        //digging
        groundparts = new RigidBodyControl[1000000];
        //camera
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        cam.setLocation(new Vector3f(0.0f, 0.5f, 0.0f));
        //World
        initClient();
        initPlayer();
        initKeys();

        initTerrain();
        initLight();
        initSky();

        HilfsKlassen.setWelt(welt);
        //combatSystem
        q = new Quaternion();
        q2 = new Quaternion();
        //von 155 bis -155 x und z
        // max w wert anpassen oder anderen wert finden
        q2.fromAngles(155, 0, 0);

    }

    @Override
    public void simpleUpdate(float tpf) {
        playerMovement();
        String[] temp = client.getUpdate();
        client.update(temp);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initTerrain() {
        int part = 0;
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {

                    Spatial terrain = assetManager.loadModel("Models/lowResDirt/lowResDirt.j3o");
                    if (k == 3 && i < 10 && j < 10) {
                        if (welt.getWeltElemente()[i][j].getElementNumber() == 5) {
                            terrain = assetManager.loadModel("Models/lowResGrass2/lowResGrass2.j3o");
                        } else if (welt.getWeltElemente()[i][j].getElementNumber() == 0) {
                            terrain = assetManager.loadModel("Models/lowResWater/lowResWater.j3o");
                        }
                    }
                    RigidBodyControl landscapeControl = new RigidBodyControl(0.0f);
                    terrain.setLocalScale(2f);
                    terrain.setLocalTranslation(new Vector3f(i * 4f - 60, -16 + k * 4, j * 4f - 60));
                    rootNode.attachChild(terrain);
                    terrain.addControl(landscapeControl);
                    bulletAppState.getPhysicsSpace().add(landscapeControl);
                    groundparts[part] = landscapeControl;
                    part++;
                }
            }
        }

    }

    private void initPlayer() {

        //unitSpatial kennt rootNode und bulletappstate damit das hier nicht mehr in der main steht
        CapsuleCollisionShape playerShape = new CapsuleCollisionShape(1f, 1.5f);

        player = new CharacterControl(playerShape, 0.5f);
        Spatial playerSpatial = assetManager.loadModel("/Models/CrystalGuy/CrystalGuy.j3o");




        Spatial weaponSpatial = assetManager.loadModel("/Models/weapon002/weapon002.j3o");
        CapsuleCollisionShape collSHape = new CapsuleCollisionShape(0.2f, 1.5f);
        GhostControl weaponGhost = new GhostControl(collSHape);
        weaponSpatial.addControl(weaponGhost);
        rootNode.attachChild(weaponSpatial);
        rootNode.attachChild(playerSpatial);



        player.setFallSpeed(5);
        player.setGravity(800);
        player.setJumpSpeed(75);
        player.setPhysicsLocation(new Vector3f(5, 1, 5.0f));
        weaponGhost.setPhysicsLocation(new Vector3f(100, 100, 100));
        bulletAppState.getPhysicsSpace().add(weaponGhost);

        bulletAppState.getPhysicsSpace().add(player);

        playerThings = new UnitSpatialandGhostControl(playerSpatial, 1, true);
        Node node = new Node("myNode");
        node.attachChild(weaponSpatial);
        playerThings.addWeapon(weaponSpatial, weaponGhost, node);

        rootNode.attachChild(node);
        //wenn multiplayer TODO
        playerThings.setPhysicsLocation(player.getPhysicsLocation());
        playerThings.camera = cam;
    }

    private void initLight() {
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White);
        rootNode.addLight(ambientLight);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -1, -1));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }

    private void initSky() {
        Spatial sky = assetManager.loadModel("Scenes/Sky.j3o");
        rootNode.attachChild(sky);
    }

    public void initKeys() {

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

        inputManager.addListener(this, "Left");
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
        if (name.equals("Left")) {
            left = isPressed;
        } else if (name.equals("Right")) {
            right = isPressed;
        } else if (name.equals("Up")) {
            up = isPressed;
        } else if (name.equals("Down")) {
            down = isPressed;
        } else if (name.equals("Jump")) {
            player.jump();
        } else if (name.equals("Run")) {
            run = isPressed;
        } else if (name.equals("One") && !isPressed) {
            //optimize TODO >0
            player.setPhysicsLocation(player.getPhysicsLocation().add(cam.getDirection().mult(50)));
        } else if (name.equals("Two") && !isPressed) {
            two = isPressed;
            walkDirection.set(cam.getDirection().mult(20));
            player.setWalkDirection(walkDirection);
            cam.setLocation(player.getPhysicsLocation());
        }
        if (name.equals("Speichern")) {

            System.out.println(1);
        }
        if (name.equals("Laden")) {

            System.out.println(2);
        }
        if (name.equals("Shoot") && !isPressed) {
            shootFireball();
        }


    }

    private void initUnits() {
        Lebewesen lebewesen = null;
        unitControl = new GhostControl[100];
        units = new Spatial[1000];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {

                if (welt.getEinheiten()[i][j] != null) {
                    Spatial model = assetManager.loadModel("/Models/maybeACOw/maybeACOw.j3o");
                    if (welt.getEinheiten()[i][j].getElementNumber() == 101) {
                        model = assetManager.loadModel("/Models/wolf/wolf.j3o");
                    } else if (welt.getEinheiten()[i][j].getElementNumber() == 151) {
                        model = assetManager.loadModel("/Models/CrystalGuy/CrystalGuy.j3o");
                    }

                    lebewesen = welt.getEinheiten()[i][j];
                    unitControl[unitCount] = new GhostControl(new BoxCollisionShape(new Vector3f(1, 1, 1)));
                    model.addControl(unitControl[unitCount]);
                    units[unitCount] = model;
                    units[unitCount].setLocalTranslation(new Vector3f((float) lebewesen.getPosition().getPosX() / 20, 2, (float) lebewesen.getPosition().getPosX() / 20));
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
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (welt.getWeltElemente()[i][j] != null && welt.getWeltElemente()[i][j].getColor() == Color.BLUE) {
                    unitCount++;
                    Spatial water = assetManager.loadModel("/Models/Pool/Pool.j3o");
                    // grassControl = new RigidBodyControl(0.5f); GhostControl
                    water.addControl(grassControl);
                    grassControl.setPhysicsLocation(new Vector3f(i * 5 - 75, 0, j * 5 - 75));
                    rootNode.attachChild(water);
                    if (unitCount == 20) {
                        i = 100;
                        j = 100;
                    }

                }
            }
        }



        unitCount = 0;
    }

    public void shootFireball() {
        if (!playerThings.hitting) {
            playerThings.hitting = true;
        }

    }

    private void initClient() {
        client = new Client("127.0.0.1", 4);
        client.clientToServerConnections.startClient();
    }

    private void playerMovement() {
        playerThings.updateRotationPlayer();
        playerThings.setPhysicsLocation(player.getPhysicsLocation().subtract(new Vector3f(0, -1f, 0)));
        //Test
        if (playerThings.hitting) {
            playerThings.getWeapons()[0].node.getLocalRotation().slerp(q3, 0.5f);
            float comparePThingsX = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getX() * 1000)) / 1000.0);
            float compareQuantX = (float) (((int) (q3.getX() * 1000)) / 1000.0);
            float comparePThingsY = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getY() * 1000)) / 1000.0);
            float compareQuantY = (float) (((int) (q3.getY() * 1000)) / 1000.0);

            float comparePThingsZ = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getZ() * 1000)) / 1000.0);
            float compareQuantZ = (float) (((int) (q3.getZ() * 1000)) / 1000.0);
            float comparePThingsW = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getW() * 1000)) / 1000.0);
            float compareQuantW = (float) (((int) (q3.getW() * 1000)) / 1000.0);

            if ((comparePThingsZ == compareQuantZ && comparePThingsX == compareQuantX && comparePThingsY == compareQuantY && comparePThingsW == compareQuantW)
                    || (comparePThingsZ == -compareQuantZ && comparePThingsX == -compareQuantX && comparePThingsY == -compareQuantY && comparePThingsW == -compareQuantW)) {
                playerThings.hitting = false;
                playerThings.getWeapons()[0].node.setLocalRotation(q);
            }
        } else {
            q3 = new Quaternion();
            if (cam.getDirection().getX() > 0) {
                q3 = q3.fromAngles(90, -110 - (cam.getDirection().z + 1f) * 1.5f, 50);
            } else {
                q3 = q3.fromAngles(90, 110 + (cam.getDirection().z + 1f) * 1.5f, 50);
            }
        }
//TODO  


//Player moveMent
        Vector3f camDir = cam.getDirection().multLocal(0.2f);
        Vector3f camLeft = cam.getLeft().multLocal(0.1f);

        walkDirection.set(0.0f, 0.0f, 0.0f);



        if (left) {
            if (run) {
                walkDirection.addLocal(camLeft).mult(0.4f);
            } else {
                walkDirection.addLocal(camLeft).mult(0.2f);
            }
        } else if (right) {
            if (run) {
                walkDirection.addLocal(camLeft.negate().mult(0.4f));
            } else {
                walkDirection.addLocal(camLeft.negate().mult(0.2f));
            }
        } else if (up) {
            if (run) {
                walkDirection.addLocal(camDir.mult(0.4f));
            }
            walkDirection.addLocal(camDir.mult(0.2f));
        } else if (down) {
            if (run) {
                walkDirection.addLocal(camDir.negate().mult(0.4f));
            }
            walkDirection.addLocal(camDir.negate().mult(0.2f));
        }

        walkDirection.setY(0.0f);
        player.setWalkDirection(walkDirection);
    }
}

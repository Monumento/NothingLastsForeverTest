package Main;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

import lebewesen.tiere.Kuh;
import helpClasses.HilfsKlassen;
import helpClasses.Laden;
import helpClasses.Speichern;
import helpClasses.Vector2f;
import humanoide.meineRasse.MeineRasse;
import resourcen.Dreck;
import resourcen.Wasser;
import Welt.Welt;
import Welt.biome.Ebene;

public class Main extends Applet {

    static Speichern speichern = new Speichern();
    static Laden laden = new Laden();
    static boolean somethingChanged = false;
    static Welt welt;
    static int x = 0;
    static int rectLengthGround = 50;
    static int rectLengthUnit = 10;
    static int rectLengthTree = 25;
    static boolean onStart = true;
    static GameCycle gameCycle;
    static boolean end = false;

    static int startElementX = 0;
    static int startElementY = 0;

    public static void main(String args[]) {

    }

    // auslagern

    private static void fillWorld() {
       // welt = new Welt();
        Ebene e = new Ebene(welt, new Vector2f(10, 10));
        spawnHumanoide();
        HilfsKlassen.setWelt(welt);
    }

    private static void spawnHumanoide() {

    }

    public void start() {
        System.out.println("Start");
        laden.setWelt(welt);

        KeyListener kl = new KeyListener() {
            public void keyTyped(KeyEvent arg0) {
                if (arg0.getKeyChar() == 'p' || arg0.getKeyChar() == 'P') {
                    System.out.println("P=speichern");
                    speichern.saveMap(welt.getWeltElemente());
                    speichern.saveUnits(welt.getEinheiten());
                    speichern.savePlants(welt.getPflanzen());
                }
                if (arg0.getKeyChar() == 'l' || arg0.getKeyChar() == 'L') {
                    System.out.println("L=laden");
                    laden.loadGround();
                    laden.loadPlants();
                    laden.loadUnits();
                    welt = laden.getWelt();
                }
            }

            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyChar() == 'w' || arg0.getKeyChar() == 'W') {
                    welt.getEinheiten()[0][0].move(new Vector2f(0, -1));
                } else if (arg0.getKeyChar() == 's' || arg0.getKeyChar() == 'S') {
                    welt.getEinheiten()[0][0].move(new Vector2f(0, 1));
                } else if (arg0.getKeyChar() == 'a' || arg0.getKeyChar() == 'A') {
                    welt.getEinheiten()[0][0].move(new Vector2f(-1, 0));
                } else if (arg0.getKeyChar() == 'd' || arg0.getKeyChar() == 'D') {
                    welt.getEinheiten()[0][0].move(new Vector2f(1, 0));
                } else if (arg0.getKeyChar() == '+') {
                    if (welt.getMsPerUpdate() < 100)
                        welt.setMsPerUpdate(welt.getMsPerUpdate() + 1);
                    System.out.println(welt.getMsPerUpdate());
                } else if (arg0.getKeyChar() == '-') {
                    if (welt.getMsPerUpdate() > 0)
                        welt.setMsPerUpdate(welt.getMsPerUpdate() / 10);
                    System.out.println(welt.getMsPerUpdate());
                } else if (arg0.getKeyCode() == 38) {
                    if (startElementY > 0)
                        startElementY--;
                } else if (arg0.getKeyCode() == 37) {
                    if (startElementX > 0)
                        startElementX--;
                } else if (arg0.getKeyCode() == 40) {
                    if (startElementY < 70)
                        startElementY++;
                } else if (arg0.getKeyCode() == 39) {
                    if (startElementX < 70)
                        startElementX++;
                }

            }
        };
        MouseListener ml = new MouseListener() {

            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            public void mouseEntered(MouseEvent arg0) {

            }

            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        };
        this.addKeyListener(kl);
        this.addMouseListener(ml);
        GameCycle gC = new GameCycle(welt, this);
        if (!onStart) {
            gC.start();
            laden.setGc(gC);
        }

        if (onStart) {
            fillWorld();
            onStart = false;
            start();
        }
    }

    public void paint(Graphics g) {
        // vllt wieder double array um sachen zu speichern da blickfeld =
        // [i+pos][j+pos]
        int dimensionx = 1500, dimensiony = 970;
        setSize(new Dimension(dimensionx, dimensiony));

        // paint ground
        for (int i = startElementX; i < startElementX + 30; i++) {
            for (int j = startElementY; j < startElementY + 30; j++) {
                g.setColor(welt.getWeltElemente()[i][j].getColor());
                g.fillRect((i - startElementX) * 50, (j - startElementY) * 50,
                        rectLengthGround, rectLengthGround);
            }
        }
        // paint units
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (welt.getEinheiten()[i][j] != null) {
                    if (welt.getEinheiten()[i][j].getPosition().getPosX() > startElementX * 50
                            && welt.getEinheiten()[i][j].getPosition()
                                    .getPosX() < startElementX * 50 + dimensionx) {
                        if (welt.getEinheiten()[i][j].getPosition().getPosY() > startElementY * 50
                                && welt.getEinheiten()[i][j].getPosition()
                                        .getPosY() < startElementY * 50 + dimensiony) {
                            g.setColor(welt.getEinheiten()[i][j].getColor());
                            g.fillRect(
                                    (int) (welt.getEinheiten()[i][j]
                                            .getPosition().getPosX() - startElementX * 50),
                                    (int) (welt.getEinheiten()[i][j]
                                            .getPosition().getPosY() - startElementY * 50),
                                    rectLengthUnit, rectLengthUnit);
                            g.setColor(Color.WHITE);
                            g.fillRect(
                                    (int) (welt.getEinheiten()[i][j]
                                            .getPosition().getPosX() - startElementX * 50),
                                    (int) (welt.getEinheiten()[i][j]
                                            .getPosition().getPosY() - startElementY * 50),
                                    rectLengthUnit / 5, rectLengthUnit / 5);
                            g.fillRect(
                                    (int) (5 + welt.getEinheiten()[i][j]
                                            .getPosition().getPosX() - startElementX * 50),
                                    (int) (welt.getEinheiten()[i][j]
                                            .getPosition().getPosY() - startElementY * 50),
                                    rectLengthUnit / 5, rectLengthUnit / 5);
                            g.fillRect(
                                    (int) (2 + welt.getEinheiten()[i][j]
                                            .getPosition().getPosX() - startElementX * 50),
                                    (int) (5 + welt.getEinheiten()[i][j]
                                            .getPosition().getPosY() - startElementY * 50),
                                    rectLengthUnit / 2, rectLengthUnit / 5);
                        }
                    }

                }
            }
        }
        // paint Leichen
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (welt.getLeichen()[i][j] != null) {
                    if (welt.getLeichen()[i][j].getPosition().getPosX() > startElementX * 50
                            && welt.getLeichen()[i][j].getPosition().getPosX() < startElementX * 50 + dimensionx) {
                        if (welt.getLeichen()[i][j].getPosition().getPosY() > startElementY * 50
                                && welt.getLeichen()[i][j].getPosition()
                                        .getPosY() < startElementY * 50 + dimensiony) {
                            g.setColor(welt.getLeichen()[i][j].getColor());
                            g.fillRect(
                                    (int) (welt.getLeichen()[i][j]
                                            .getPosition().getPosX() - startElementX * 50),
                                    (int) (welt.getLeichen()[i][j]
                                            .getPosition().getPosY() - startElementY * 50),
                                    rectLengthUnit, rectLengthUnit);
                        }
                    }

                }
            }
        }
        // paint plants
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {

                if (welt.getPflanzen()[i][j] != null) {

                    if (welt.getPflanzen()[i][j].getPosition().getPosX() > startElementX * 50
                            && welt.getPflanzen()[i][j].getPosition().getPosX() < startElementX * 50 + 900) {
                        if (welt.getPflanzen()[i][j].getPosition().getPosY() > startElementY * 50
                                && welt.getPflanzen()[i][j].getPosition()
                                        .getPosY() < startElementY * 50 + 900) {
                            g.setColor(welt.getPflanzen()[i][j].getColor());
                            g.fillRect(
                                    (int) (welt.getPflanzen()[i][j]
                                            .getPosition().getPosX() - startElementX * 50),
                                    (int) (welt.getPflanzen()[i][j]
                                            .getPosition().getPosY() - startElementY * 50),
                                    rectLengthTree, rectLengthTree);

                        }

                    }

                }

            }

        }
    }
}

package Main;

import java.awt.Color;

import helpClasses.Speichern;
import lebewesen.Lebewesen;
import lebewesen.tiere.Kuh;
import lebewesen.tiere.Wolf;
import Welt.Welt;

public class GameCycle extends Thread {

    Welt welt;
    Main main;

    public GameCycle(Welt welt, Main main) {
        this.welt = welt;
        this.main = main;
    }

    public Welt getWelt() {
        return welt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWelt(Welt welt) {
        this.welt = welt;
    }

    public static double getMsPerUpdate() {
        return MS_PER_UPDATE;
    }

    private static double MS_PER_UPDATE = 0.1;

    static double count = 0;
    static double saveCount = 0;
    static double differenceInTime;
    static double lastTime;

    public void run() {
        while (true) {
            lastTime = System.currentTimeMillis();

            count++;

            Lebewesen l = welt.getEinheiten()[0][0];
            // array oder aehnliches
            Kuh k = null;
            Wolf w = null;
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
                            }
                          
                        }

                    }
                    l = welt.getEinheiten()[i][j];
                }
            }
            // pfalnzen.wachsen(), tiere,
            if (count % 10 == 0)
                main.repaint();
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

            try {

                if (System.currentTimeMillis() - lastTime <= welt
                        .getMsPerUpdate())
                    sleep((long) (System.currentTimeMillis() - lastTime));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}

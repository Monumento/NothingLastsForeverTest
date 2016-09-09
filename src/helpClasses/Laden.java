package helpClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Main.GameCycle;
import Welt.Welt;
import Welt.WeltElement;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lebewesen.Lebewesen;
import lebewesen.tiere.Kuh;
import resourcen.Dreck;
import resourcen.Luft;
import resourcen.Wasser;
import resourcen.pflanzen.Gras;
import resourcen.pflanzen.Pflanze;

public class Laden {
    private Welt welt;
    private GameCycle gc;

    public GameCycle getGc() {
        return gc;
    }

    public void setGc(GameCycle gc) {
        this.gc = gc;
    }

    public Welt getWelt() {
        return welt;
    }

    public void setWelt(Welt welt) {
        this.welt = welt;
    }

    public boolean loadGround() {
      
            welt = new Welt();
     
   
        gc.setWelt(welt);
        String line = "";
        BufferedReader in;

        try {

            in = new BufferedReader(new FileReader("savedMap.txt"));
            try {
                line = in.readLine();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {

                    if (line != null) {
                        String temp[] = line.split(",");

                        int switchi = Integer.parseInt(temp[0]);
                        System.out.println(switchi);
                        switch (switchi) {
                        case 0:

                            welt.getWeltElemente()[i][j] = new Wasser(
                                    Double.parseDouble(temp[3]), new Vector2f(
                                            i * 50, j * 50));
                            break;
                        case 1:
                            welt.getWeltElemente()[i][j] = new Dreck(
                                    Double.parseDouble(temp[3]), new Vector2f(
                                            i * 50, j * 50));
                            break;
                        case 2:
                            welt.getWeltElemente()[i][j] = new Luft(
                                    new Vector2f(i * 50, j * 50));
                            break;
                        case 5:
                            welt.getWeltElemente()[i][j] = new Gras(
                                    Double.parseDouble(temp[3]), new Vector2f(
                                            i * 50, j * 50));
                            break;

                        default:
                            break;
                        }
                        try {
                            line = in.readLine();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }

            }
            System.out.println(line);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public boolean loadPlants() {

        return true;
    }

    public boolean loadUnits() {
        String line = "";
        BufferedReader in = null;
        String[] split = null;
        try {
            in = new BufferedReader(new FileReader("savedUnits.txt"));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            line = in.readLine();
            split = line.split(",");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        for (int i = 0; i < 100 && !line.equalsIgnoreCase("empty"); i++) {
            for (int j = 0; j < 100 && !line.equalsIgnoreCase("empty"); j++) {

                if (line != null) {
                    switch (Integer.parseInt(split[10])) {
                    case 100:

                        welt.getEinheiten()[i][j] = Kuh.ladeKuh(welt, Integer
                                .parseInt(split[0]),
                                Integer.parseInt(split[1]), Integer
                                        .parseInt(split[2]), Integer
                                        .parseInt(split[3]), Integer
                                        .parseInt(split[4]), Integer
                                        .parseInt(split[5]), Integer
                                        .parseInt(split[6]), Integer
                                        .parseInt(split[7]), Integer
                                        .parseInt(split[8]), Integer
                                        .parseInt(split[9]), Integer
                                        .parseInt(split[10]), Integer
                                        .parseInt(split[11]), Integer
                                        .parseInt(split[12]), Integer
                                        .parseInt(split[13]), Integer
                                        .parseInt(split[14]), Integer
                                        .parseInt(split[15]), Integer
                                        .parseInt(split[16]), Integer
                                        .parseInt(split[17]), Integer
                                        .parseInt(split[18]), Integer
                                        .parseInt(split[19]), Integer
                                        .parseInt(split[20]),
                                new Vector2f(Integer.parseInt(split[21]),
                                        Integer.parseInt(split[22])), Boolean
                                        .parseBoolean(split[23]));

                        break;

                    default:
                        break;
                    }
                    try {
                        line = in.readLine();
                        split = line.split(",");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }
}

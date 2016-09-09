package helpClasses;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import resourcen.pflanzen.Pflanze;
import lebewesen.Lebewesen;
import Welt.WeltElement;

public class Speichern {

    private static String colorToString(Color color) {
        String s = "G";
        if (color == Color.GREEN) {
            s = "G";
        } else if (color == Color.cyan) {
            s = "C";
        } else if (color == Color.BLUE) {
            s = "B";
        } else if (color == Color.DARK_GRAY) {
            s = "DG";
        } else if (color == Color.WHITE) {
            s = "W";
        } else if (color == Color.BLACK) {
            s = "BL";
        }
        return s;
    }

    public boolean saveMap(WeltElement[][] weltElemente) {
        System.out.println("game savedMap");
        String output[] = new String[10000];
        int line = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                output[line] = "empty";
                if (weltElemente[i][j] != null) {
                    output[line] = "";

                    output[line] = weltElemente[i][j].getElementNumber() + ","
                            + weltElemente[i][j].getPosition().myToString()
                            + "," + weltElemente[i][j].getSize() + ","
                            + weltElemente[i][j].getWasserAnteil();
                }
                line++;
            }

        }
        PrintWriter writer;
        try {

            writer = new PrintWriter("savedMap.txt", "UTF-8");
            for (int i = 0; i < 10000; i++) {
                writer.println(output[i]);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;

    }

    public boolean saveUnits(Lebewesen[][] einheiten) {
        System.out.println("game savedUnits");

        String output[] = new String[10000];
        int line = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                output[line] = "empty";
                if (einheiten[i][j] != null) {

                    output[line] = "" + einheiten[i][j].getAlter() + ","
                            + einheiten[i][j].getAnzahlKinder() + ","
                            + einheiten[i][j].getDrohendeGefahr() + ","
                            + einheiten[i][j].getDurst() + ","
                            + einheiten[i][j].getElementNumber() + ","
                            + einheiten[i][j].getEssGeschwindigkeit() + ","
                            + einheiten[i][j].getGeschlecht() + ","
                            + einheiten[i][j].getHarndrang() + ","
                            + einheiten[i][j].getHealth() + ","
                            + einheiten[i][j].getHunger() + ","
                            + einheiten[i][j].getMaxAlter() + ","
                            + einheiten[i][j].getMaxKinder() + ","
                            + einheiten[i][j].getMaxZeitSchwanger() + ","
                            + einheiten[i][j].getMuedigkeit() + ","
                            + einheiten[i][j].getPaarungsDrang() + ","
                            + einheiten[i][j].getSchwangerZeit() + ","
                            + einheiten[i][j].getSichtWeite() + ","
                            + einheiten[i][j].getSize() + ","
                            + einheiten[i][j].getSpeed() + ","
                            + einheiten[i][j].getTrinkGeschwindigkeit() + ","
                            + einheiten[i][j].getWasserAnteil() + ","
                            + einheiten[i][j].getPosition().myToString() + ","
                            + einheiten[i][j].isSchwanger();

                    line++;
                }
            }
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter("savedUnits.txt", "UTF-8");
            for (int i = 0; i < 10000; i++) {
                writer.println(output[i]);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;

    }
    public boolean saveLeichen(){
        return false;
    }

    public boolean savePlants(Pflanze[][] pflanzen) {
        System.out.println("game savedPlants");

        String output[] = new String[10000];
        int line = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                output[line] = "empty";
                if (pflanzen[i][j] != null) {
                    output[line] = pflanzen[i][j].getAlter() + " , "
                            + pflanzen[i][j].getElementNumber() + " , "
                            + pflanzen[i][j].getMaxAlter() + " , "
                            + pflanzen[i][j].getNaehrstoffe() + " , "
                            + pflanzen[i][j].getSize() + " , "
                            + pflanzen[i][j].getWasser() + " , "
                            + pflanzen[i][j].getWasserAnteil() + " , "
                            + pflanzen[i][j].getPosition().myToString() + " , ";
                    line++;
                }
            }
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter("savedPlants.txt", "UTF-8");
            for (int i = 0; i < 10000; i++) {
                writer.println(output[i]);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;

    }
}

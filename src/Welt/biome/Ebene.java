package Welt.biome;

import lebewesen.Lebewesen;
import lebewesen.tiere.Kuh;
import lebewesen.tiere.Wolf;
import helpClasses.Vector2f;
import resourcen.Wasser;
import resourcen.pflanzen.Baum;
import resourcen.pflanzen.Gras;
import resourcen.pflanzen.Pflanze;
import Welt.Welt;
import Welt.WeltElement;
import humanoide.meineRasse.MeineRasseBauer;

public class Ebene {
    Welt welt;

    public Welt getWelt() {
        return welt;
    }

    public void setWelt(Welt welt) {
        this.welt = welt;
    }

    public Ebene(Welt welt, Vector2f size) {
        setWelt(welt);
        // gorund
        for (int i = 0; i < size.getPosX(); i++) {
            for (int j = 0; j < size.getPosY(); j++) {
                welt.getWeltElemente()[i][j] = spawnSmthn(new Vector2f(i * 50,
                        j * 50));
            }
        }
        // plants (trees atm)
        Pflanze temp = spawnPlants(new Vector2f(50, 50));
        int numberInARow = 0;
        for (int i = 1; i < 0.95*size.getPosX(); i++) {
            for (int j = 1; j < 0.95*size.getPosY(); j++) {
                if (temp != null && numberInARow < 5) {
                    numberInARow++;

                    temp.setPosition(new Vector2f(i * 50 - numberInARow
                            * (int) (Math.random() * 5), j * 50 - numberInARow
                            * (int) (Math.random() * 5)));
                    welt.getPflanzen()[i][j] = temp;
                } else if (numberInARow >= 5 || temp == null) {
                    temp = spawnPlants(new Vector2f(0, 0));
                    numberInARow = 0;
                }
            }
        }
        // Animals
        for (int i = 1; i < size.getPosX()/15; i++) {
            for (int j = 1; j < size.getPosY()/15; j++) {
                    welt.getEinheiten()[i][j] = spawnUnit(new Vector2f(i*25 +50,
                            j*25+50));
                
            }
        }

    }

    private Pflanze spawnPlants(Vector2f position) {
        Pflanze spawn = null;
        int i = (int) (Math.random() * 100);
        if (i < 10) {
            spawn = new Baum(100, position);
        }
        return spawn;
    }

    private Lebewesen spawnUnit(Vector2f position) {
        Lebewesen spawn = null;
        int i = (int) (Math.random()*100);
        if (i < 50) {
            spawn = new Kuh(getWelt(), 0, 100, position);
        } else if (i > 50 && i < 70) {
            spawn = new Wolf(getWelt(), 0, 100, position);
        } else if(i>70 &&i<90){
            spawn = new MeineRasseBauer(getWelt(), 0, 100, position);
        }
        return spawn;
    }

    private WeltElement spawnSmthn(Vector2f position) {
        // Boden
        WeltElement something = new WeltElement();
        int i = (int) (Math.random() * 100);
        if (i < 95) {
            something = new Gras(1000, position);
        } else {
            something = new Wasser(1000, position);
        }

        return something;
    }

}

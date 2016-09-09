package Welt;


import combatSystem.UnitSpatialandGhostControl;
import resourcen.pflanzen.Pflanze;
import lebewesen.Lebewesen;


public class Welt{
    
    WeltElement[][] weltElemente;
    Lebewesen[][] einheiten;
    Lebewesen[][] leichen;
    Pflanze[][] pflanzen;
    
    int zeit;
    double msPerUpdate = 0.1;
    int countLimit;

    UnitSpatialandGhostControl[] unitControls;
       public Welt() {
        zeit = 0;
        pflanzen = new Pflanze[100][100];
        einheiten = new Lebewesen[100][100];
        weltElemente = new WeltElement[100][100];
        leichen = new Lebewesen[100][100];
        unitControls = new UnitSpatialandGhostControl[500];
    }
    

    public double getMsPerUpdate() {
        return msPerUpdate;
    }

    public Lebewesen[][] getLeichen() {
        return leichen;
    }

    public void setLeichen(Lebewesen[][] leichen) {
        this.leichen = leichen;
    }

    public int getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(int countLimit) {
        this.countLimit = countLimit;
    }

    public void setMsPerUpdate(double d) {
        this.msPerUpdate = d;
    }

    
    


    public WeltElement[][] getWeltElemente() {
        return weltElemente;
    }

    public void setWeltElemente(WeltElement[][] weltElemente) {
        this.weltElemente = weltElemente;
    }

    public Lebewesen[][] getEinheiten() {
        return einheiten;
    }

    public void setEinheiten(Lebewesen[][] einheiten) {
        this.einheiten = einheiten;
    }

    public Pflanze[][] getPflanzen() {
        return pflanzen;
    }

    public void setPflanzen(Pflanze[][] pflanzen) {
        this.pflanzen = pflanzen;
    }

    public int getZeit() {
        return zeit;
    }

    public void setZeit(int zeit) {
        this.zeit = zeit;
    }

    public void spawn(Lebewesen lebewesen) {

        int x = 0;
        int y = 0;
        Lebewesen temp = getEinheiten()[x][y];
        for (int i = 0; i < 100 && temp != null; i++) {
            for (int j = 0; j < 100 && temp != null; j++) {
                x = i;
                y = j;
                temp = getEinheiten()[x][y];
            }
        }

        getEinheiten()[x][y] = lebewesen;

    }

    public boolean addLeiche(Lebewesen lebewesen) {
        // TODO pointer Vector2f fuer arrays
        int x = 0;
        int y = 0;
        Lebewesen temp = getLeichen()[x][y];
        for (int i = 0; i < 100 && temp != null; i++) {
            for (int j = 0; j < 100 && temp != null; j++) {
                x=i;
                y=j;
                temp = getLeichen()[x][y];
            }
          
        }
        getLeichen()[x][y] = lebewesen;
        return true;

    }
}

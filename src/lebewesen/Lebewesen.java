package lebewesen;

import faehigkeiten.bewegung.CanMoveOnGround;
import helpClasses.HilfsKlassen;
import helpClasses.Vector2f;
import item.Item;

import java.awt.Color;
import java.util.ArrayList;

import lebewesen.tiere.Kuh;
import beduerfnisse.tiere.GrundBeduerfnisse;
import beduerfnisse.tiere.ZuLetztGetan;
import Welt.Welt;
import Welt.WeltElement;
import combatSystem.UnitSpatialandGhostControl;
import java.util.HashMap;
import java.util.Map;

public class Lebewesen extends WeltElement implements CanMoveOnGround {
    // TODO prioritaeten fuer beduerfnisse als priority array zur evolution

    private Welt welt;
    // Base Things
    private double health;
    private double alter, maxAlter;
    private Color color;
    private Vector2f position;
    private double speed;
    // items
    private ArrayList<Item> ausgeruestet;
    private ArrayList<Item> taschen;
    private ArrayList<Item> hand;
    private ZuLetztGetan zuLetztGetan = ZuLetztGetan.nichts;
    // Verhalten
    private double zufall1;
    private double drohendeGefahr, paarungsDrang, harndrang, hunger, durst,
            muedigkeit;
    private double trinkGeschwindigkeit, essGeschwindigkeit;
    private double sichtWeite;
    private double geschlecht, schwangerZeit;
    private boolean schwanger = false;
    private double maxKinder, anzahlKinder, maxZeitSchwanger;
    //CombatSystem and other
    public UnitSpatialandGhostControl usgc;
    //new Verhalten
    HashMap<String, Double> attributesDouble;
    HashMap<String, Integer> attributesInteger;

    public double getMaxZeitSchwanger() {
        return maxZeitSchwanger;
    }

    public void setMaxZeitSchwanger(double maxZeitSchwanger) {
        this.maxZeitSchwanger = maxZeitSchwanger;
    }

    public void erhoeheSchwangerschaft() {
        if (isSchwanger()) {
            setSchwangerZeit(getSchwangerZeit() + 1);
            if (getSchwangerZeit() > getMaxZeitSchwanger()) {
                setAnzahlKinder(getAnzahlKinder() + 1);
                setSchwanger(false);

                getWelt().spawn(
                        new Kuh(getWelt(), 0, 100, getPosition().addieren(
                        new Vector2f(5, 5))));

            }
        }

    }

    public Lebewesen(Welt welt, double alter, double health) {
        attributesDouble = new HashMap<String, Double>();
        attributesInteger = new HashMap<String, Integer>();

        attributesDouble.put("alter", alter);
        attributesDouble.put("health", health);
        attributesDouble.put("zufall1", (double) (Math.random() * 100));
        attributesDouble.put("anzahlKinder", 0.0);

        this.welt = welt;
    }

    public double getZufall1() {
        return zufall1;
    }

    public void setZufall1(int zufall1) {
        this.zufall1 = zufall1;
    }

    public boolean erhoeheBeduerfnisse() {
        if (getDurst() < 1000 && getHunger() < 1000
                && getAlter() < getMaxAlter() && getHealth() > 0) {
            setDurst(getDurst() + 5);
            setHunger(getHunger() + 2.5);
            setPaarungsDrang(getPaarungsDrang() + 5);
            setHarndrang(getHarndrang() + 5);
            setMuedigkeit(getMuedigkeit() + 2);
            setAlter(getAlter() + 1);
            return true;
        } else {
            setColor(Color.RED);
            return false;
        }

    }

    public boolean altern() {
        if (getAlter() < maxAlter) {
            alter++;
            return true;
        } else {
            return false;
        }

    }

    public Welt getWelt() {
        return welt;
    }

    public void setWelt(Welt welt) {
        this.welt = welt;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getAlter() {
        return alter;
    }

    public void setAlter(double alter) {
        this.alter = alter;
    }

    public double getMaxAlter() {
        return maxAlter;
    }

    public void setMaxAlter(double maxAlter) {
        this.maxAlter = maxAlter;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public ArrayList<Item> getAusgeruestet() {
        return ausgeruestet;
    }

    public void setAusgeruestet(ArrayList<Item> ausgeruestet) {
        this.ausgeruestet = ausgeruestet;
    }

    public ArrayList<Item> getTaschen() {
        return taschen;
    }

    public void setTaschen(ArrayList<Item> taschen) {
        this.taschen = taschen;
    }

    public ArrayList<Item> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Item> hand) {
        this.hand = hand;
    }

    public ZuLetztGetan getZuLetztGetan() {
        return zuLetztGetan;
    }

    public void setZuLetztGetan(ZuLetztGetan zuLetztGetan) {
        this.zuLetztGetan = zuLetztGetan;
    }

    public double getDrohendeGefahr() {
        return drohendeGefahr;
    }

    public void setDrohendeGefahr(double drohendeGefahr) {
        this.drohendeGefahr = drohendeGefahr;
    }

    public double getPaarungsDrang() {
        return paarungsDrang;
    }

    public void setPaarungsDrang(double paarungsDrang) {
        this.paarungsDrang = paarungsDrang;
    }

    public double getHarndrang() {
        return harndrang;
    }

    public void setHarndrang(double harndrang) {
        this.harndrang = harndrang;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    public double getDurst() {
        return durst;
    }

    public void setDurst(double durst) {
        this.durst = durst;
    }

    public double getMuedigkeit() {
        return muedigkeit;
    }

    public void setMuedigkeit(double muedigkeit) {
        this.muedigkeit = muedigkeit;
    }

    public double getTrinkGeschwindigkeit() {
        return trinkGeschwindigkeit;
    }

    public void setTrinkGeschwindigkeit(double trinkGeschwindigkeit) {
        this.trinkGeschwindigkeit = trinkGeschwindigkeit;
    }

    public double getEssGeschwindigkeit() {
        return essGeschwindigkeit;
    }

    public void setEssGeschwindigkeit(double essGeschwindigkeit) {
        this.essGeschwindigkeit = essGeschwindigkeit;
    }

    public double getSichtWeite() {
        return sichtWeite;
    }

    public void setSichtWeite(double sichtWeite) {
        this.sichtWeite = sichtWeite;
    }

    public double getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(double geschlecht) {
        this.geschlecht = geschlecht;
    }

    public double getSchwangerZeit() {
        return schwangerZeit;
    }

    public void setSchwangerZeit(double schwangerZeit) {
        this.schwangerZeit = schwangerZeit;
    }

    public boolean isSchwanger() {
        return schwanger;
    }

    public void setSchwanger(boolean schwanger) {
        this.schwanger = schwanger;
    }

    public double getMaxKinder() {
        return maxKinder;
    }

    public void setMaxKinder(double maxKinder) {
        this.maxKinder = maxKinder;
    }

    public double getAnzahlKinder() {
        return anzahlKinder;
    }

    public void setAnzahlKinder(double anzahlKinder) {
        this.anzahlKinder = anzahlKinder;
    }

    public void setZufall1(double zufall1) {
        this.zufall1 = zufall1;
    }

    // && HilfsKlassen.betrag(
    // getPosition().addieren(richtung.multiplizieren(i)), 1)
    // - HilfsKlassen.betrag(ziel.getPosition(), 1) > 1
    public void moveTo(Vector2f richtung, WeltElement ziel) {

        WeltElement temp = null;
        int hindernisBei = -1;
        for (int i = 0; i <= getSichtWeite() * 3; i++) {
            if ((int) ((getPosition().addieren(richtung.multiplizieren(i))
                    .getPosX()) / 50) < 100
                    && (int) ((getPosition().addieren(
                    richtung.multiplizieren(i)).getPosX()) / 50) > 0
                    && (int) ((getPosition().addieren(
                    richtung.multiplizieren(i)).getPosY()) / 50) < 100
                    && (int) ((getPosition().addieren(
                    richtung.multiplizieren(i)).getPosY()) / 50) > 0) {
                temp = getWelt().getWeltElemente()[(int) ((getPosition()
                        .addieren(richtung.multiplizieren(i)).getPosX()) / 50)][(int) ((getPosition()
                        .addieren(richtung.multiplizieren(i)).getPosY()) / 50)];
            }
            if (!checkDestinationMovement(getPosition().addieren(
                    richtung.multiplizieren(i)))
                    && temp != ziel) {

                hindernisBei = i;
            }
        }
        if (hindernisBei == -1) {
            move(richtung);
        } else {
            if (HilfsKlassen.myRandom() > 0) {
                move(new Vector2f(richtung.getPosY() * -1, richtung.getPosX())
                        .multiplizieren(5));
            } else {
                move(new Vector2f(richtung.getPosY(), richtung.getPosX() * -1)
                        .multiplizieren(5));
            }
        }
    }

    private WeltElement getUnitOnPosition(Vector2f position) {
        Lebewesen ret = null;
        for (int i = 0; i < 100 && ret == null; i++) {
            for (int j = 0; j < 100 && ret == null; j++) {
            }
        }
        return null;
    }

    public void move(Vector2f speed) {
        Vector2f tempPos = getPosition();
        if (checkDestinationMovement(tempPos.addieren(new Vector2f(speed
                .getPosX(), speed.getPosY())))) {
            setPosition(getPosition().addieren(
                    new Vector2f(speed.getPosX(), speed.getPosY())));
        } else if (checkDestinationMovement(tempPos.subtrahieren(new Vector2f(
                speed.getPosX(), speed.getPosY())))) {
            setPosition(getPosition().subtrahieren(
                    new Vector2f(speed.getPosX(), speed.getPosY())));
        } else if (checkDestinationMovement(tempPos.addieren(new Vector2f(speed
                .getPosX(), -speed.getPosY())))) {
            setPosition(getPosition().addieren(
                    new Vector2f(speed.getPosX(), -speed.getPosY())));
        } else if (checkDestinationMovement(tempPos.addieren(new Vector2f(
                -speed.getPosX(), speed.getPosY())))) {
            setPosition(getPosition().addieren(
                    new Vector2f(-speed.getPosX(), speed.getPosY())));
        }

    }

    public boolean move() {
        Vector2f tempPos = getPosition();
        if (checkDestinationMovement(tempPos
                .addieren(new Vector2f(speed, speed)))) {
            setPosition(getPosition().addieren(new Vector2f(speed, speed)));
        } else if (checkDestinationMovement(tempPos.subtrahieren(new Vector2f(
                speed, speed)))) {
            setPosition(getPosition().subtrahieren(new Vector2f(speed, speed)));
        } else if (checkDestinationMovement(tempPos.addieren(new Vector2f(
                speed, -speed)))) {
            setPosition(getPosition().addieren(new Vector2f(speed, -speed)));
        } else if (checkDestinationMovement(tempPos.addieren(new Vector2f(
                -speed, speed)))) {
            setPosition(getPosition().addieren(new Vector2f(-speed, speed)));
        }

        return true;
    }

    private boolean checkDestinationMovement(Vector2f destination) {
        boolean ret = true;
        if (destination.getPosX() < 5 || destination.getPosY() < 5
                || destination.getPosX() > 4900 || destination.getPosY() > 4900) {
            ret = false;
        } else if (welt.getWeltElemente()[(int) (destination.getPosX() / 50)][(int) (destination
                .getPosY() / 50)].getElementNumber() == 2) {
            ret = false;
        } else if (welt.getWeltElemente()[(int) (destination.getPosX() / 50)][(int) (destination
                .getPosY() / 50)].getElementNumber() == 0) {
            ret = false;
        }

        return ret;
    }
}

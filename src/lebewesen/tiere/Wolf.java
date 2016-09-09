package lebewesen.tiere;

import helpClasses.HilfsKlassen;
import helpClasses.Vector2f;

import java.awt.Color;

import resourcen.Luft;
import beduerfnisse.tiere.GrundBeduerfnisse;
import beduerfnisse.tiere.ZuLetztGetan;
import Welt.Welt;
import Welt.WeltElement;
import lebewesen.Lebewesen;

public class Wolf extends Lebewesen implements GrundBeduerfnisse {

    public Wolf(Welt welt, int alter, int health, Vector2f position) {
        super(welt, alter, health);
        setZufall1((int) (Math.random() * 100));
        if (getZufall1() < 50) {
            setGeschlecht(0);
        } else if (getZufall1() < 100) {
            setGeschlecht(1);
        }

        setAlter(0);
        setHealth(100);
        setDrohendeGefahr(0);
        setHunger(800);
        setHarndrang(250);
        setPaarungsDrang(700);
        setDurst(250);
        setColor(Color.GRAY);
        setPosition(position);
        setSpeed(0.075);
        setTrinkGeschwindigkeit(5);
        setEssGeschwindigkeit(5);
        setElementNumber(101);
        setSichtWeite(20);
        setMaxZeitSchwanger(100);
        setMaxAlter(300);
        setMuedigkeit(250);
        setSize(500);
        setMaxKinder(2);
    }

    public boolean macheEtwas() {
        if (getDrohendeGefahr() > 500) {
            sucheVersteck();
        } else if (getMuedigkeit() > 800
                || (getZuLetztGetan() == ZuLetztGetan.geschlafen && getMuedigkeit() > 300)) {

            // System.out.println("durst");
            schlafe();

        } else if (getDurst() > 500
                || (getZuLetztGetan() == ZuLetztGetan.getrunken && getDurst() > 0)) {

            // System.out.println("durst");
            sucheWasser();
        } else if (getHunger() > 500
                || (getZuLetztGetan() == ZuLetztGetan.gegessen && getHunger() > 0)) {
            // System.out.println("hunger");
            sucheNahrung();

        } else if (getHarndrang() > 500
                || (getZuLetztGetan() == ZuLetztGetan.gepinkelt && getHarndrang() > 0)) {
            // System.out.println("pinkeln");
            if (sucheToilette()) {
                pinkeln(getWelt().getWeltElemente()[(int) (getPosition()
                        .getPosX() / 50)][(int) (getPosition().getPosY() / 50)]);
            }
        } else if ((getZuLetztGetan() == ZuLetztGetan.geschlafen && getMuedigkeit() > 0)) {
            schlafe();
        } else if (getPaarungsDrang() > 900
                || (getZuLetztGetan() == ZuLetztGetan.gefickt && getPaarungsDrang() > 0)) {

            suchePartner();
        } else {
            // spiele
            move(HilfsKlassen.vectorToMoveSpeed(getSpeed(), new Vector2f(
                    HilfsKlassen.myRandom(), HilfsKlassen.myRandom())));
        }
        return true;
    }

    public boolean sucheNahrung() {
        // toete tier
        Kuh k = null;
        Kuh k2 = null;

        k = (Kuh) HilfsKlassen.isInRadiusLeiche(getPosition(), getSichtWeite(),
                100);
        k2 = (Kuh) HilfsKlassen.isInRadiusLeiche(getPosition(), 0, 100);

        if (k != null) {
            if (k != k2)
                moveTo(HilfsKlassen.vectorToMoveSpeed(getSpeed(),
                        HilfsKlassen.directionOf(getPosition(), k)), k);
            else {
                k.setHealth(k.getHealth() - 5);
                if (k.getHealth() <= 0) {
                    essen(k);
                }
            }
            return true;
        } else {
            move(HilfsKlassen.vectorToMoveSpeed(getSpeed(), new Vector2f(
                    HilfsKlassen.myRandom(), HilfsKlassen.myRandom())));
        }

        return false;
    }

    public boolean suchePartner() {
        Wolf w = null;
        Wolf w2 = null;
        if (getGeschlecht() == 0) {
            w = (Wolf) HilfsKlassen.isInRadiusUnit(this, getPosition(),
                    getSichtWeite(), 1, false, 101);
            w2 = (Wolf) HilfsKlassen.isInRadiusUnit(this, getPosition(), 0, 1,
                    false, 101);
        } else {
            w = (Wolf) HilfsKlassen.isInRadiusUnit(this, getPosition(),
                    getSichtWeite(), 0, false, 101);
            w2 = (Wolf) HilfsKlassen.isInRadiusUnit(this, getPosition(), 0, 0,
                    false, 101);
        }
        if (w != null) {
            if (w != w2)
                moveTo(HilfsKlassen.vectorToMoveSpeed(getSpeed(),
                        HilfsKlassen.directionOf(getPosition(), w)), w);
            else {
                geschlVerkehr(w2);
            }
            return true;
        } else {
            move(HilfsKlassen.vectorToMoveSpeed(getSpeed(), new Vector2f(
                    HilfsKlassen.myRandom(), HilfsKlassen.myRandom())));
        }

        return false;
    }

    public boolean sucheVersteck() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean sucheToilette() {

        return true;
    }

    public boolean sucheWasser() {
        WeltElement wasser = HilfsKlassen.isInRadius(this.getPosition(), 0,
                getSichtWeite());
        WeltElement wasser2 = HilfsKlassen.isInRadius(this.getPosition(), 0, 1);
        if (wasser != null) {
            if (wasser2 != wasser)
                moveTo(HilfsKlassen.vectorToMoveSpeed(getSpeed(),
                        HilfsKlassen.directionOf(getPosition(), wasser)),
                        wasser);

            if (wasser2 != null) {
                trinken(wasser2);
            }
        } else {
            move();
        }

        return true;
    }

    public boolean trinken(WeltElement trinkeVon) {
        if (trinkeVon.getSize() == 0) {

            getWelt().getWeltElemente()[(int) (trinkeVon.getPosition().getPosX() / 50)][(int) (trinkeVon
                    .getPosition().getPosY() / 50)] = new Luft(
                    trinkeVon.getPosition());

        }

        if (getDurst() < 100) {
            setZuLetztGetan(ZuLetztGetan.nichts);
        } else {
            setZuLetztGetan(ZuLetztGetan.getrunken);
        }
        if (trinkeVon.getSize() >= getTrinkGeschwindigkeit()) {
            setDurst(getDurst() - getTrinkGeschwindigkeit());
            trinkeVon.setSize(trinkeVon.getSize() - getTrinkGeschwindigkeit());
            setHarndrang(getHarndrang() + 1);

        }

        return true;
    }

    public boolean essen(WeltElement esseVon) {
        if (esseVon.getSize() >= 5) {
            esseVon.setSize(esseVon.getSize() - 5);
            setHunger(getHunger() - 5);
        } else {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    if (getWelt().getEinheiten()[i][j] != null
                            && getWelt().getEinheiten()[i][j].getSize() <= 5)
                        getWelt().getEinheiten()[i][j] = null;
                }
            }
        }
        return true;
    }

    public boolean pinkeln(WeltElement pinkleAuf) {
        setZuLetztGetan(ZuLetztGetan.gepinkelt);
        if (getHarndrang() < 100) {
            setZuLetztGetan(ZuLetztGetan.nichts);
        }
        setHarndrang(getHarndrang() - 10);
        pinkleAuf.setWasserAnteil(pinkleAuf.getWasserAnteil() + 1);
        return true;
    }

    public boolean geschlVerkehr(Lebewesen verkehrMit) {
        setZuLetztGetan(ZuLetztGetan.gefickt);
        setZuLetztGetan(ZuLetztGetan.getrunken);
        if (getPaarungsDrang() < 100) {
            setZuLetztGetan(ZuLetztGetan.nichts);
        }
        if (getGeschlecht() == 1 && !isSchwanger()
                && getAnzahlKinder() < getMaxKinder()) {
            System.out.println("schwangerWolf");
            setSchwangerZeit(0);
            setSchwanger(true);
        }
        setPaarungsDrang(getPaarungsDrang() - 5);
        return false;
    }

    public boolean schlafe() {
        // TODO Auto-generated method stub
        return false;
    }

}

package humanoide.meineRasse;

import java.awt.Color;

import resourcen.Dreck;
import resourcen.Luft;
import helpClasses.HilfsKlassen;
import helpClasses.Vector2f;
import beduerfnisse.tiere.GrundBeduerfnisse;
import beduerfnisse.tiere.ZuLetztGetan;
import Welt.Welt;
import Welt.WeltElement;
import lebewesen.Lebewesen;
import lebewesen.tiere.Wolf;

public class MeineRasseBauer extends Lebewesen implements GrundBeduerfnisse {

    public MeineRasseBauer(Welt welt, int alter, int health, Vector2f position) {
        super(welt, alter, health);
        setZufall1((int) (Math.random() * 100));
        if (getZufall1() < 50) {
            setGeschlecht(0);
        } else if (getZufall1() < 100) {
            setGeschlecht(1);
        }
        setMaxKinder(4);
        setAlter(0);
        setHealth(100);
        setDrohendeGefahr(0);
        setHunger(600);
        setHarndrang(250);
        setPaarungsDrang(899);
        setDurst(600);
        setColor(Color.PINK);
        setPosition(position);
        setSpeed(0.5);
        setTrinkGeschwindigkeit(5);
        setEssGeschwindigkeit(5);
        setElementNumber(151);
        setSichtWeite(15);
        setSize(500);
        setMaxZeitSchwanger(150);
        setMaxAlter(500);
        setMuedigkeit(300);

    }

    public static MeineRasseBauer ladeKuh(Welt welt, int alter, int anzahlKinder,
            int drohendeGefahr, int durst, int elementnumber,
            int essGeschwindigkeit, int geschlecht, int harndrang, int health,
            int hunger, int maxAlter, int maxKinder, int maxZeitSchwanger,
            int muedigkeit, int paarungsDrang, int schwangerZeit,
            int sichtWeite, int size, int speed, int trinkGeschwindigkeit,
            int wasserAnteil, Vector2f position, boolean schwanger) {
        MeineRasseBauer ret = new MeineRasseBauer(welt, alter, health, position);
        ret.setAlter(alter);
        ret.setAnzahlKinder(anzahlKinder);
        ret.setMaxAlter(maxAlter);
        ret.setMaxKinder(maxKinder);
        ret.setDrohendeGefahr(drohendeGefahr);
        ret.setDurst(durst);
        ret.setEssGeschwindigkeit(essGeschwindigkeit);
        ret.setGeschlecht(geschlecht);
        ret.setHarndrang(harndrang);
        ret.setHunger(hunger);
        ret.setMaxZeitSchwanger(maxZeitSchwanger);
        ret.setMuedigkeit(muedigkeit);
        ret.setPaarungsDrang(paarungsDrang);
        ret.setSchwanger(schwanger);
        ret.setSchwangerZeit(schwangerZeit);
        ret.setSichtWeite(sichtWeite);
        ret.setSize(size);
        ret.setTrinkGeschwindigkeit(trinkGeschwindigkeit);
        ret.setWasserAnteil(wasserAnteil);

        return ret;
    }

    public boolean partnerSuche() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean sucheToilette() {

        return true;
    }

    public boolean macheEtwas() {
        // System.out.print("mache");
        // update method
        Wolf w = (Wolf) HilfsKlassen.isInRadiusLeiche(getPosition(), 4, 101);
        if (w != null) {
            setDrohendeGefahr(getDrohendeGefahr() + 100);
        } else {
            setDrohendeGefahr(getDrohendeGefahr() - 100);
        }

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
                pinkeln(getWelt().getWeltElemente()[(int) (getPosition().getPosX() / 50)][(int) (getPosition()
                        .getPosY() / 50)]);
            }
        } else if ((getZuLetztGetan() == ZuLetztGetan.geschlafen && getMuedigkeit() > 0)) {
            schlafe();
        } else if (getPaarungsDrang() > 900
                || (getZuLetztGetan() == ZuLetztGetan.gefickt && getPaarungsDrang() > 0)) {
            // System.out.println("ficken");
            suchePartner();
        } else {
            // spiele
            move(HilfsKlassen.vectorToMoveSpeed(getSpeed(), new Vector2f(
                    HilfsKlassen.myRandom(), HilfsKlassen.myRandom())));
        }
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

    public boolean sucheNahrung() {
        WeltElement nahrung = HilfsKlassen.isInRadius(this.getPosition(), 5,
                getSichtWeite());
        WeltElement nahrung2 = HilfsKlassen
                .isInRadius(this.getPosition(), 5, 1);
        if (nahrung != null) {
            if (nahrung != nahrung2)
                moveTo(HilfsKlassen.vectorToMoveSpeed(getSpeed(),
                        HilfsKlassen.directionOf(getPosition(), nahrung)),
                        nahrung);
        }

        if (nahrung2 != null) {
            essen(nahrung2);
        } else {
            move();
        }
        return true;
    }

    public boolean suchePartner() {
        MeineRasse k = null;
        MeineRasse k2 = null;
        if (getGeschlecht() == 0) {
            k = (MeineRasse) HilfsKlassen.isInRadiusUnit(this, getPosition(),
                    getSichtWeite(), 1, false, 151);
            k2 = (MeineRasse) HilfsKlassen.isInRadiusUnit(this, getPosition(), 0, 1,
                    false, 151);
        } else {
            k = (MeineRasse) HilfsKlassen.isInRadiusUnit(this, getPosition(),
                    getSichtWeite(), 0, false, 151);
            k2 = (MeineRasse) HilfsKlassen.isInRadiusUnit(this, getPosition(), 0, 0,
                    false, 151);
        }
        if (k != null) {
            if (k != k2)
                moveTo(HilfsKlassen.vectorToMoveSpeed(getSpeed(),
                        HilfsKlassen.directionOf(getPosition(), k)), k);
            else {
                geschlVerkehr(k2);
            }
            return true;
        } else {
            move(HilfsKlassen.vectorToMoveSpeed(getSpeed(), new Vector2f(
                    HilfsKlassen.myRandom(), HilfsKlassen.myRandom())));
        }

        return false;
    }

    public boolean sucheVersteck() {
        Wolf w = (Wolf) HilfsKlassen.isInRadiusLeiche(getPosition(),
                getSichtWeite(), 101);
        move(HilfsKlassen.vectorToMoveSpeed(getSpeed(), new Vector2f(0, 0)
                .subtrahieren(HilfsKlassen.directionOf(getPosition(), w))));
        return false;
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
        if (esseVon.getSize() == 0) {

            getWelt().getWeltElemente()[(int) (esseVon.getPosition().getPosX() / 50)][(int) (esseVon
                    .getPosition().getPosY() / 50)] = new Dreck(100,
                    esseVon.getPosition());

        }

        if (esseVon.getSize() >= getEssGeschwindigkeit()) {
            setZuLetztGetan(ZuLetztGetan.gegessen);
            if (getHunger() < 100) {
                setZuLetztGetan(ZuLetztGetan.nichts);
            }

            setHunger(getHunger() - getEssGeschwindigkeit());
            esseVon.setSize(esseVon.getSize() - getEssGeschwindigkeit());
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
            // System.out.println("schwangerKuh");
            setSchwangerZeit(0);
            setSchwanger(true);
        }
        setPaarungsDrang(getPaarungsDrang() - 5);
        return false;
    }

    public boolean schlafe() {
        if (getMuedigkeit() > 0) {
            setMuedigkeit(getMuedigkeit() - 1);
            return true;
        }
        return false;
    }

}

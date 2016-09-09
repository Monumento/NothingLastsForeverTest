package helpClasses;

import java.awt.Color;
import java.io.ObjectInputStream.GetField;

import javax.swing.text.StyleContext.SmallAttributeSet;

import lebewesen.Lebewesen;
import Welt.Welt;
import Welt.WeltElement;

public class HilfsKlassen {
    static Welt welt;

    public static void setWelt(Welt welt) {
        HilfsKlassen.welt = welt;
    }

    public static int charToInt(Character c) {
        int ret = 0;
        if (c == '1') {
            ret = 1;
        } else if (c == '2') {
            ret = 2;
        } else if (c == '3') {
            ret = 3;
        } else if (c == '4') {
            ret = 4;
        } else if (c == '5') {
            ret = 5;
        } else if (c == '6') {
            ret = 6;
        } else if (c == '7') {
            ret = 7;
        } else if (c == '8') {
            ret = 8;
        } else if (c == '9') {
            ret = 9;
        }
        return ret;
    }

    public static int stringToInt(String toInt) {
        int ret = 0;
        for (int i = 0; i < toInt.length(); i++) {
            if (toInt.length() != 1)
                ret += charToInt(toInt.charAt(i)) * (toInt.length() - i) * 10;
            else
                ret = charToInt(toInt.charAt(0));
        }
        return ret;
    }

    public static WeltElement isInRadius(Vector2f position,
            int numberOfElement, double d) {

        WeltElement ret = null;

        int differenzRadiusAnKanteX = 0;
        int differenzRadiusAnKanteY = 0;
        int posX = (int) position.getPosX() / 50;
        int posY = (int) position.getPosY() / 50;

        if (welt.getWeltElemente()[posX][posY].getElementNumber() == numberOfElement) {
            ret = welt.getWeltElemente()[posX][posY];
        }

        for (int i = 1; i <= d && ret == null; i++) {
            posX = (int) position.getPosX() / 50;
            posY = (int) position.getPosY() / 50;
            // 1 set position -X-Y oben links
            for (int k = 0; k < i && ret == null; k++) {
                posX--;
                differenzRadiusAnKanteX = i - k;
            }
            for (int k = 0; k < i && ret == null; k++) {
                posY--;
                differenzRadiusAnKanteY = i - k;
            }
            // 1 look for
            for (int j = 0; j < 1 + (i * 2) - differenzRadiusAnKanteX
                    && ret == null; j++) {
                if (posX < 100
                        && posY < 100
                        && posX > 0
                        && posY > 0
                        && welt.getWeltElemente()[posX][posY]
                                .getElementNumber() == numberOfElement) {
                    ret = welt.getWeltElemente()[posX][posY];
                }
                posX++;
            }
            // 2look for
            for (int j = 0; j < 1 + (i * 2) - differenzRadiusAnKanteY
                    && ret == null; j++) {
                if (posX < 100
                        && posY < 100
                        && posX > 0
                        && posY > 0
                        && welt.getWeltElemente()[posX][posY]
                                .getElementNumber() == numberOfElement) {
                    ret = welt.getWeltElemente()[posX][posY];
                }
                posY++;
            }

            // 3 look for
            for (int j = 0; j < 1 + (i * 2) - differenzRadiusAnKanteX
                    && ret == null; j++) {
                if (posX < 100
                        && posY < 100
                        && posX > 0
                        && posY > 0
                        && welt.getWeltElemente()[posX][posY]
                                .getElementNumber() == numberOfElement) {
                    ret = welt.getWeltElemente()[posX][posY];
                }
                posX--;
            }
            // 4 look for
            for (int j = 0; j < 1 + (i * 2) - differenzRadiusAnKanteY
                    && ret == null; j++) {
                if (posX < 100
                        && posY < 100
                        && posX > 0
                        && posY > 0
                        && welt.getWeltElemente()[posX][posY]
                                .getElementNumber() == numberOfElement) {
                    ret = welt.getWeltElemente()[posX][posY];
                }
                posY--;
            }
        }

        return ret;
    }

    public static Vector2f vectorToMoveSpeed(double d, Vector2f direction) {
        double temp = Math.sqrt(d * d + d * d);
        double skalar = 100;

        while (Math.abs(temp - betrag(direction, skalar)) > 1) {
            skalar = (skalar / 1.0001);
        }

        return betragToVector2f(direction, skalar);
    }

    public static double betrag(Vector2f direction, double skalar) {
        double ret = Math.sqrt((direction.getPosX() * skalar)
                * (direction.getPosX() * skalar)
                + (direction.getPosY() * skalar)
                * (direction.getPosY() * skalar));
        return ret;
    }

    public static Vector2f betragToVector2f(Vector2f direction, double skalar) {
        return new Vector2f((int) (direction.getPosX() * skalar),
                (int) (direction.getPosY() * skalar));
    }

    public static Vector2f directionOf(Vector2f position, WeltElement inRadius) {
        if (inRadius == null || position == null) {
            return null;
        } else
            return new Vector2f(inRadius.getPosition().getPosX()
                    - position.getPosX(), inRadius.getPosition().getPosY()
                    - position.getPosY());
    }

    public static Vector2f directionOfPosPos(Vector2f position,
            Vector2f positionSache) {
        if (positionSache == null || position == null) {
            return null;
        } else
            return new Vector2f(positionSache.getPosX() - position.getPosX(),
                    positionSache.getPosY() - position.getPosY());
    }

    public static int myRandom() {
        int ret = 0;
        if (Math.random() * 100 > 50) {
            ret = (int) (Math.random() * 1000);
        } else {
            ret = (int) (Math.random() * 1000);
            ret = 0 - ret;
        }
        return ret;
    }

    public static Lebewesen isInRadiusLeiche(Vector2f position, double d,
            int numberOfElement) {
        Lebewesen temp = welt.getEinheiten()[0][0];
        Lebewesen ret = null;
        double kleinsteDistanz = 10000;
        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                if (welt.getEinheiten()[j][k] != null) {
                    temp = welt.getEinheiten()[j][k];
                    if ((betrag(
                            HilfsKlassen.directionOfPosPos(position,
                                    temp.getPosition()), 1) < kleinsteDistanz)
                            && temp.getElementNumber() == numberOfElement

                            && temp.getPaarungsDrang() > 500) {
                        kleinsteDistanz = betrag(
                                HilfsKlassen.directionOfPosPos(position,
                                        temp.getPosition()), 1);
                        ret = temp;
                    }

                }
            }

        }
        if (kleinsteDistanz < d * 150 + 50) {
            return ret;
        } else {
            return null;
        }

    }

    public static Lebewesen isInRadiusUnit(Lebewesen esSelbst,
            Vector2f position, double d, int geschlecht, boolean ignoreSex,
            int numberOfElement) {
        Lebewesen temp = welt.getEinheiten()[0][0];
        Lebewesen ret = null;
        double kleinsteDistanz = 100000;
        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                if (welt.getEinheiten()[j][k] != null) {
                    if (!ignoreSex) {
                        temp = welt.getEinheiten()[j][k];
                        if ((betrag(
                                HilfsKlassen.directionOfPosPos(position,
                                        temp.getPosition()), 1) < kleinsteDistanz)
                                && temp.getElementNumber() == numberOfElement
                                && temp.getGeschlecht() == geschlecht
                                && temp.getPaarungsDrang() > 500
                                && (temp.getColor() != Color.RED)
                                && temp != esSelbst) {
                            kleinsteDistanz = betrag(
                                    HilfsKlassen.directionOfPosPos(position,
                                            temp.getPosition()), 1);
                            ret = temp;
                        }
                    } else {
                        temp = welt.getEinheiten()[j][k];
                        if (betrag(
                                HilfsKlassen.directionOfPosPos(position,
                                        temp.getPosition()), 1) < kleinsteDistanz
                                && temp.getElementNumber() == numberOfElement
                                && temp != esSelbst
                                && (temp.getColor() != Color.RED)) {
                            kleinsteDistanz = betrag(
                                    HilfsKlassen.directionOfPosPos(position,
                                            temp.getPosition()), 1);
                            ret = temp;
                        }
                    }

                }
            }

        }
        if (kleinsteDistanz < d * 150 + 50) {
            return ret;
        } else {
            return null;
        }

    }
}

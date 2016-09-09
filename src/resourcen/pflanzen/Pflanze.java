package resourcen.pflanzen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.Welt;
import Welt.WeltElement;

public class Pflanze extends WeltElement {
    Welt welt;
    public double size;
    public Color color;
    Vector2f position;
    int alter, wasser, naehrstoffe;
    private int maxAlter;
    private int maxSize;

    public boolean altern() {
        if (getAlter() < maxAlter) {
            setAlter(getAlter() + 1);
            return true;
        }
        return false;
    }

    public boolean wachse() {
        if (getSize() < maxSize) {
            setSize(getSize() + 1);
            erhoeheBeduerfnisse();
            return true;
        } else {
            return false;
        }
    }

    public boolean erhoeheBeduerfnisse() {
        if (getWasser() < 1000 && getNaehrstoffe() < 1000) {
            setWasser(getWasser() + 1);
            setNaehrstoffe(getNaehrstoffe() + 1);
            return true;
        } else {
            return false;
        }

    }

    public int getWasser() {
        return wasser;
    }

    public void setWasser(int wasser) {
        this.wasser = wasser;
    }

    public int getNaehrstoffe() {
        return naehrstoffe;
    }

    public void setNaehrstoffe(int naehrstoffe) {
        this.naehrstoffe = naehrstoffe;
    }

    public int getMaxAlter() {
        return maxAlter;
    }

    public void setMaxAlter(int maxAlter) {
        this.maxAlter = maxAlter;
    }

    public Welt getWelt() {
        return welt;
    }

    public void setWelt(Welt welt) {
        this.welt = welt;
    }

    public void setSize(double size) {
        this.size = size;
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

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }
}

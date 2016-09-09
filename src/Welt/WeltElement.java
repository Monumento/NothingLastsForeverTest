package Welt;

import helpClasses.Vector2f;

import java.awt.Color;

public class WeltElement  {

    boolean hasUpdated;
    Welt welt;
    public double size;
    public Color color;
    private int ElementNumber;
    private int wasserAnteil = 0;

    public int getWasserAnteil() {
        return wasserAnteil;
    }

    public void setWasserAnteil(int wasserAnteil) {
        this.wasserAnteil = wasserAnteil;
    }

    public Welt getWelt() {
        return welt;
    }

    public void setWelt(Welt welt) {
        this.welt = welt;
    }

    public int getElementNumber() {
        return ElementNumber;
    }

    public void setElementNumber(int elementNumber) {
        ElementNumber = elementNumber;
    }

    Vector2f position;

    public double getSize() {
        return size;
    }

    public void setSize(double d) {
        this.size = d;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}

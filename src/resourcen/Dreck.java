package resourcen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;

public class Dreck extends WeltElement {

    public Dreck(double d, Vector2f position) {
        setElementNumber(1);
        setColor(Color.DARK_GRAY);
        setSize(d);
        setPosition(position);
    }
}

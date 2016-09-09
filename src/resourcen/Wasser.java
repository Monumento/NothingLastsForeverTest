package resourcen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;

public class Wasser extends WeltElement {

    public Wasser(double d, Vector2f position) {
        setElementNumber(0);
        setColor(Color.BLUE);
        setSize(d);
        setPosition(position);
    }
}

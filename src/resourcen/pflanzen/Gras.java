package resourcen.pflanzen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;

public class Gras extends WeltElement {

    public Gras(double d, Vector2f position) {
        setElementNumber(5);
        setColor(Color.GREEN);
        setSize(d);
        setPosition(position);
    }
}

package resourcen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;

public class Luft extends WeltElement {

    public Luft(Vector2f position) {
        setColor(Color.WHITE);
        setElementNumber(2);
        setPosition(position);
    }

}

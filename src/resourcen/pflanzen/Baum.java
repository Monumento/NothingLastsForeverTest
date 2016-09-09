package resourcen.pflanzen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;

public class Baum extends Pflanze {
    public Baum(double size, Vector2f position) {
        color = Color.BLACK;
        super.size = size;
        setPosition(position);
    }

}

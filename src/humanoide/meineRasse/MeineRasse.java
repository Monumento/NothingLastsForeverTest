package humanoide.meineRasse;

import java.awt.Color;

import Welt.Welt;
import helpClasses.Vector2f;
import humanoide.Humanoide;

public class MeineRasse extends Humanoide {

    public MeineRasse(Welt welt, int health, int alter, Vector2f position) {
        super(welt, health, alter);
        setColor(Color.white);
        setPosition(position);
    }

}

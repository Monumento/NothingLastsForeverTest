package helpClasses;

public class Vector2f  {
    double posX, posY;

    public String myToString() {
        String ret = posX + "," + posY;
        return ret;
    }

    public static Vector2f fromVectorToField(Vector2f vector) {
        return null;
    }

    public Vector2f multiplizieren(double skalar) {
        return new Vector2f(getPosX() * skalar, getPosY() * skalar);
    }

    public Vector2f subtrahieren(Vector2f sub) {
        return new Vector2f(getPosX() - sub.getPosX(), getPosY()
                - sub.getPosY());
    }

    public Vector2f addieren(Vector2f sub) {
        return new Vector2f(getPosX() + sub.getPosX(), getPosY()
                + sub.getPosY());
    }

    public Vector2f(double x, double y) {
        posX = x;
        posY = y;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

}

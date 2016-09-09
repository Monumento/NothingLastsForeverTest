package beduerfnisse.tiere;

import lebewesen.Lebewesen;
import Welt.WeltElement;

public interface GrundBeduerfnisse {

    public boolean macheEtwas();

    public boolean sucheNahrung();

    public boolean suchePartner();

    public boolean sucheVersteck();

    public boolean sucheToilette();

    public boolean sucheWasser();

    public boolean trinken(WeltElement trinkeAus);

    public boolean essen(WeltElement esseVon);

    public boolean pinkeln(WeltElement pinkleAuf);

    public boolean geschlVerkehr(Lebewesen verkehrMit);

    public boolean schlafe();

}

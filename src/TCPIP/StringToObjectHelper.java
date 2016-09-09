/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPIP;

import lebewesen.Lebewesen;
import lebewesen.tiere.Kuh;

/**
 *
 * @author jonas
 */
public class StringToObjectHelper {

    public static Lebewesen getObject(String objectAsString) {
        String[] parametesFromSplit = objectAsString.split("/");
        int i = Integer.parseInt(parametesFromSplit[1]);
        
        switch (i) {
            case 1:
                //Kuh.ladeKuh(String) aus String
                //Kuh k = Kuh.ladeKuh();
                break;
            default:

                break;
        }

        return null;
    }
}

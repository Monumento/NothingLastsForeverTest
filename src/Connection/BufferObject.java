/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class BufferObject {

    public ArrayList<String> backlog;
    public boolean updated;
    private String message;

    public BufferObject() {
        backlog = new ArrayList<String>();
        message = "-1";
        updated = false;
    }

    public boolean updateMessage(String message) {
        if (!updated) {
            updated = true;
            this.message = message;
            return true;
        } else {
            backlog.add(message);
            return false;
        }
    }

    public String getUpdateMessage() {
        String ret = "-1";
        if (updated) {
            //zugriffszeit bedenken erst wert holen dann wieder frei geben
            ret = message;
            updated = false;
        } else if (backlog.size() > 0) {
            ret = backlog.remove(0);
        }
        return ret;
    }
}

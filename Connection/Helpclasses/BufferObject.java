/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.Helpclasses;

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
		message = "default";
		updated = false;
	}
	// TODO check backlogsize thorw backlogException

	public boolean updateMessage(String message) {
		if (!updated) {
			updated = true;
			this.message = message;
			return true;
		} else {
			if (backlog.size() < 100)
				backlog.add(message);
			return false;
		}
	}

	public String getUpdateMessage() {
		String ret = "default";
		if (updated) {
			updated = false;
			ret = message;
		} else if (backlog.size() > 0) {
			ret = backlog.remove(0);
		}
		return ret;
	}
}

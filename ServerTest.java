import Connection.Server;

public class ServerTest {

	public static Server server;

	public static void main(String args[]) {
		long lastTime = 0;

		server = new Server();
		server.startServer();
		String s[] = { "hey you", "", "", "" };
		String temp = "s";
		while (true) {
			lastTime = System.currentTimeMillis();
			s = server.getUpdateFromClients();
			if (temp != null) {
				if (!temp.equalsIgnoreCase("default"))
					System.out.println("s0 " + s[0]);
				s[0] += "b";
				if (s.length == 5) {
					System.out.println("s1 " + s[1]);
					System.out.println("s2 " + s[2]);
					System.out.println("s3 " + s[3]);
					s[1] += "b";
					s[2] += "b";
					s[3] += "b";
				}
				server.updateToClients(s);
			}
			while (System.currentTimeMillis() - lastTime < 1000)
				;
		}
	}
}

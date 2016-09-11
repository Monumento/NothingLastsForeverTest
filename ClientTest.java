import Connection.Client;

public class ClientTest {
	public static Client client;

	public static void main(String args[]) {
		long lastTime = 0;

		client = new Client("127.0.0.1", 1000);
		client.startClient();
		client.startNewConnection();
		int i = 0;

		String test = "hey" + i;
		String in = "-1";
		String[] s = { test };
		client.update(s);
		while (true) {
			lastTime = System.currentTimeMillis();
			test = "hey" + i;
			s[0] = test;
			in = client.getUpdate()[0];
			if (in != null) {
				if (!in.equalsIgnoreCase("default")) {
					i++;
					System.out.println(in);
				}

				test = "hey" + i;
				s[0] = test;
				client.update(s);
			}
		}
	}
}

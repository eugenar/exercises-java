import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Driver {
	static int limit = 10; // TODO make it configurable

	public static void main(String[] args) {
		int offset = 0;
		int count = 0;
		String line = null;
		while (true) {
			try {
				BufferedReader rd = getReader(limit, offset);
				while ((line = rd.readLine()) != null) {
					System.out.println(line.replace("\"", ""));
					count++;
				}
				rd.close();
				if (count < limit)
					break;
				else {
					offset += limit;
					count = 0;
					System.out.println("Press Enter key to see more results...");
					System.in.read();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Press Enter key to exit...");
		try {
			System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			System.exit(0);
		}
	}

	private static BufferedReader getReader(int limit, int offset)
			throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL(FoodTruckFinder.query(limit, offset));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		return rd;
	}
}

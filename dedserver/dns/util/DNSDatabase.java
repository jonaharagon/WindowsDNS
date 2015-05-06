package dedserver.dns.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * This is basically the class that parses the dns.config file
 * @author DevilMental
 *
 */
public class DNSDatabase {
	private static Properties prop;

	public static void init() {
		prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream("dns.config");
			prop.load(input);
		} catch (Exception ex) {
			// I don't know how you guys handle exceptions so I'll probably
			// leave it like that
			ex.printStackTrace();
		}
	}

	public static String get(String addr) throws UnknownHostException {
		return prop.containsKey(addr) ? (String) prop.getProperty(addr) : InetAddress.getByName(addr).toString();
	}
}

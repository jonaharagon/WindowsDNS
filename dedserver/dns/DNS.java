package dedserver.dns;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import dedserver.dns.util.DNSDatabase;
import dedserver.dns.util.RequestHandler;

/**
 * The DNS server is started with the DNS.start() method It runs a thread (which
 * is basically itself) to get all the requests If you want to stop the DNS
 * server, DNS.stop()
 *
 * @author DevilMental
 */
public class DNS implements Runnable {
	// //
	// THESE FIELDS ARE ONLY FOR TESTING
	// //
	public static boolean debugMode = true;
	private static int dnsPort = 53;

	// FOR TEST PURPOSES
	public static void main(String[] args) {
		DNS.start();
	}

	// //
	// THESE ARE THE REAL FIELDS
	// //
	/** The maximum size of the UDP packets */
	public static int bufferMax = 1024;

	/**
	 * When to stop the DNS server To close, DNS.stop()
	 */
	private static boolean stop = false;

	/**
	 * Usage: Starts the DNS Server
	 */
	public static void start() {
		DNSDatabase.init();
		if (debugMode) {
			System.out.println("> Loading database");
			System.out.println("> DNS server is starting...");
		}
		new Thread(new DNS()).start();
		if (debugMode)
			System.out.println("> DNS server has started");
	}

	/**
	 * Usage: Stops the DNS Server
	 */
	public static void stop() {
		stop = true;
	}

	/**
	 * Usage: Method that is run with the Thread created at DNS.start
	 */
	@Override
	public void run() {
		try {
			while (true) {
				DatagramSocket serverSocket = new DatagramSocket(dnsPort);
				byte[] recvBuffer = new byte[bufferMax];
				while (!stop) {
					DatagramPacket receivePacket = new DatagramPacket(
							recvBuffer, recvBuffer.length);
					serverSocket.receive(receivePacket);
					byte[] send = RequestHandler.handle(receivePacket);
					DatagramPacket a = new DatagramPacket(send, bufferMax,
							receivePacket.getAddress(), receivePacket.getPort());
					serverSocket.send(a);
				}
				serverSocket.close();
			}
		} catch (Exception ex) {
			// I don't know how you guys handle exceptions so I'll probably
			// leave it like that
			ex.printStackTrace();
		}
	}
}

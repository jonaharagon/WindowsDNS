package dedserver.dns;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import dedserver.dns.util.DNSDatabase;
import dedserver.dns.util.RequestHandler;

/**
 * The DNS server is started with the DNS.start() method It runs a thread to get
 * all the requests If you want to stop the DNS server, DNS.stop()
 *
 * @author DevilMental
 */
public class DNS {
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
		// NOTE : This is a lambda which is only used in Java 8
		new Thread(() -> DNS.run()).start();
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
	public static void run() {
		while (!stop) {
			try {
				DatagramSocket socket = new DatagramSocket(dnsPort);
				byte[] recvBuffer = new byte[bufferMax];
				DatagramPacket receivePacket = new DatagramPacket(recvBuffer,
						recvBuffer.length);
				socket.receive(receivePacket);
				byte[] send = RequestHandler.handle(receivePacket);
				DatagramPacket a = new DatagramPacket(send, bufferMax,
						receivePacket.getAddress(), receivePacket.getPort());
				socket.send(a);
				socket.close();
			} catch (SocketException ex) {
				// When binding the server multiple times (at the same time)
				// It can happen so it's okay
				// To not print the exception
			} catch (UnknownHostException ex) {
				// It means when a DNS request with an unknown domain name
			} catch (Exception ex) {
				// I don't know how you guys handle exceptions so I'll probably
				// leave it like that
			}
		}
	}
}
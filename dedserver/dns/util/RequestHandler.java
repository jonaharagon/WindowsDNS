package dedserver.dns.util;

import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.Arrays;

import dedserver.dns.DNS;
import dedserver.dns.packet.PacketDNS;

/**
 * RequestHandler is the class that handles all the DNS requests
 * 
 * @author DevilMental
 */
public class RequestHandler {
	/**
	 * @return The packet to send
	 * @param receivePacket
	 *            = DatagramPacket received in the DNS server
	 * @param recvBuffer
	 * @throws UnknownHostException 
	 */
	public static byte[] handle(DatagramPacket receivePacket) throws UnknownHostException {
		if (DNS.debugMode)
			debugHandle(receivePacket);
		int[] data = Util.fromByteArrayToUnsigned(receivePacket.getData());
		System.out.println(Arrays.toString(data));
		PacketDNS pdns = new PacketDNS(data);
		pdns.analyse();
		String domain = pdns.getDomain();
		if (DNS.debugMode)
			System.out.println("Domain : " + domain);
		int[] toSend = pdns
				.getAnswer(DNSDatabase.get(domain), receivePacket.getLength());
		return Util.fromUnsignedByteArrayToSigned(toSend);
	}

	/**
	 * Usage: Debugging
	 * @param receivePacket
	 */
	private static void debugHandle(DatagramPacket receivePacket) {
		String ipAddress = receivePacket.getSocketAddress().toString();
		int length = receivePacket.getLength();
		System.out.println("> Received DNS request (" + length + ") from "
				+ ipAddress);
	}

}

package dedserver.dns.util;

import java.net.DatagramPacket;
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
	 */
	public static byte[] handle(DatagramPacket receivePacket) {
		if (DNS.debugMode)
			debugHandle(receivePacket);
		int[] data = Util.fromByteArrayToUnsigned(receivePacket.getData());
		System.out.println(Arrays.toString(data));
		PacketDNS pdns = new PacketDNS(data);
		pdns.analyse();
		int[] toSend = pdns
				.getAnswer("", receivePacket.getLength());
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

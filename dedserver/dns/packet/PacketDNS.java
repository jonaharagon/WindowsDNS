package dedserver.dns.packet;

import java.util.Arrays;

import dedserver.dns.DNS;
import dedserver.dns.util.Util;

/**
 * PacketDNS is the class that analyses the DNS requests and that also give the
 * right packet to answer with.
 *
 * @author DevilMental
 */
public class PacketDNS {

	private int[] data;
	private String domain;

	/**
	 * Constructor method
	 *
	 * @param data
	 */
	public PacketDNS(int[] data) {
		this.data = data;
		domain = "";
	}

	/**
	 * Analyse the packet
	 */
	public void analyse() {
		if (DNS.debugMode)
			System.out.println("> Analysing the packet");
		int a = data[2] >> 3 & 15; // Opcode bits
		int b;
		if (a == 0) {
			b = 12;
			a = data[b];
			while (a != 0) {
				domain = Util.joinArray(data, b + 1, a + b + 1) + ".";
				b += a + 1;
				a = data[b];
			}
		} else {
			// If it gets here, it means the query isn't "standard"
		}
	}

	/**
	 * Gets the Answer to send back
	 * 
	 * @param ip
	 * @param a
	 *            : the real length of the UDP packet
	 * @return an unsigned byte array that contains the answer
	 */
	public int[] getAnswer(String ip, int a) {
		PacketBuilder pb = new PacketBuilder();
		if (domain != null) {
			pb.addArraySlice(data, 0, 2);
			pb.addString("\u0081\u0080");
			pb.addArraySlice(data, 4, 6);
			pb.addArraySlice(data, 4, 6);
			pb.addString("\u0000\u0000\u0000\u0000");
			pb.addArraySlice(data, 12, a);
			pb.addString("\u00c0\u000c");
			pb.addString("\u0000\u0001\u0000\u0001\u0000\u0000\u0000\u003c\u0000\u0004");
			pb.addString(Util.getIPAddress(ip));
			if (DNS.debugMode)
				System.out.println("Sending out " + Arrays.toString(pb.getData()));
			return pb.getData();
		}
		return null;
	}
}

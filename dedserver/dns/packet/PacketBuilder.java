package dedserver.dns.packet;

import dedserver.dns.DNS;
import dedserver.dns.util.Util;

/**
 * This class can be used to make DNS packets
 * 
 * @author DevilMental
 */
public class PacketBuilder {
	private int[] data;
	private int offset;

	/**
	 * Constructor method
	 */
	public PacketBuilder() {
		data = new int[DNS.bufferMax];
		offset = -1;
	}

	/**
	 * Add an unsigned byte array (int[]) to the packet
	 * @param toAdd : array to be added
	 */
	public void addArray(int[] toAdd) {
		for (int element : toAdd) {
			offset++;
			data[offset] = element;
		}
	}

	/**
	 * Add a sliced array to the packet
	 * @param toAdd : array to be sliced
	 * @param i : first offset
	 * @param j : second offset
	 */
	public void addArraySlice(int[] toAdd, int i, int j) {
		for (int a = i; a < j; a++) {
			offset++;
			data[offset] = toAdd[a];
		}
	}

	/**
	 * Add a String to the packet
	 * @param s : the String to be added
	 */
	public void addString(String s) {
		int[] tmp = Util.fromByteArrayToUnsigned(Util.getBytes(s));
		addArray(tmp);
	}

	/**
	 * Returns the whole packet
	 * @return the whole packet
	 */
	public int[] getData() {
		return data;
	}
}

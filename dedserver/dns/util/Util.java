package dedserver.dns.util;

/**
 * Util is the class I use to convert all sorts of things It is useful ;)
 *
 * @author DevilMental
 */
public class Util {

	/**
	 * Converts a signed byte array to an unsigned byte array
	 *
	 * @param data
	 *            : The signed byte array
	 * @return Converted unsigned byte array
	 */
	public static int[] fromByteArrayToUnsigned(byte[] data) {
		int[] toReturn = new int[data.length];
		for (int a = 0; a < toReturn.length; a++)
			toReturn[a] = data[a] & 0xff;
		return toReturn;
	}

	/**
	 * Joins an array from i to j and returns the String representation of it
	 *
	 * @param data
	 *            : The unsigned byte array (represented by int[])
	 * @param i
	 *            : 1st offset
	 * @param j
	 *            : 2nd offset
	 * @return (String) data[i:j]
	 */
	public static String joinArray(int[] data, int i, int j) {
		String toReturn = "";
		for (int a = i; a <= j; a++)
			toReturn += (char) data[a];
		return toReturn;
	}

	/**
	 * Appends an array (data) to the packet array (packet)
	 *
	 * @param packet
	 * @param data
	 * @param i
	 * @param j
	 * @param offset
	 * @return the offset
	 */
	public static int appendIntArray(int[] packet, int[] data, int i, int j,
			int offset) {
		for (int a = i; a <= j; a++) {
			packet[offset + a] = data[a + i];
			offset++;
		}
		System.out.println(offset + i - j);
		return offset;
	}

	/**
	 * Same as appendIntArray(int[], int[], i, j, offset) except it takes a
	 * String instead of an array and its extremities offset
	 *
	 * @param packet
	 * @param string
	 * @param offset
	 * @return the offset
	 */
	public static int appendIntArray(int[] packet, String string, int offset) {
		int[] tmp = fromByteArrayToUnsigned(string.getBytes());
		return appendIntArray(packet, tmp, 0, tmp.length - 1, offset);
	}

	/**
	 * Represents the IPAddress in 4 bytes
	 * 
	 * @param ip
	 * @return the 4char String where the ip is stored
	 */
	public static String getIPAddress(String ip) {
		String toReturn = "";
		String[] tmp_ip = ip.split("\\.");
		char c;
		for (int a = 0; a <= 3; a++) {
			c = (char) Integer.valueOf(tmp_ip[a]).intValue();
			toReturn += c;
		}
		return toReturn;
	}

	/**
	 * Converts an unsigned byte array (int[]) to a signed byte array (byte[])
	 *
	 * @param data
	 *            : The unsigned byte array
	 * @return Converted signed byte array
	 */
	public static byte[] fromUnsignedByteArrayToSigned(int[] data) {
		byte[] toReturn = new byte[data.length];
		for (int a = 0; a < toReturn.length; a++)
			toReturn[a] = (byte) data[a];
		return toReturn;
	}

	/**
	 * Converts an String to a byte array
	 * 
	 * @param s
	 * @return the byte array
	 */
	public static byte[] getBytes(String s) {
		byte[] tmp = new byte[s.length()];
		int offset = -1;
		for (char a : s.toCharArray()) {
			offset++;
			tmp[offset] = (byte) a;
		}
		return tmp;
	}

}

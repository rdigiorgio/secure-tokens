package com.github.sarxos.securetoken;

import org.apache.commons.lang3.SystemUtils;

import com.github.sarxos.securetoken.impl.Hardware4Mac;
import com.github.sarxos.securetoken.impl.Hardware4Nix;
import com.github.sarxos.securetoken.impl.Hardware4Win;

import java.net.InetAddress;
import java.net.NetworkInterface;


public class Hardware {

	/**
	 * Return computer serial number.
	 * 
	 * @return Computer's SN
	 */
	public static final String getSerialNumber() {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                return Hardware4Win.getSerialNumber();
            }
            if (SystemUtils.IS_OS_LINUX) {
                return Hardware4Nix.getSerialNumber();
            }
            if (SystemUtils.IS_OS_MAC_OSX) {
                return Hardware4Mac.getSerialNumber();
            }
        } catch (Exception e) { }

        return "578E65421189DEA9";
	}

    /**
     * Return computer current used MAC Address
     *
     * @return Computer's MAC Address
     */
    public static final String getMacAddress() {
        byte[] def = new byte[]{24, 4, 124, 10, 91};
        byte[] mac;

        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (ni != null) {
                mac = ni.getHardwareAddress();
                if (mac == null) {
                    mac = def;
                }
            } else {
                mac = def;
            }
        } catch (Exception ex) {
            mac = def;
        }

        return bytesToHex(mac);
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}

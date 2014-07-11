package com.github.sarxos.securetoken.impl;

import com.github.sarxos.securetoken.util.HashUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;


public class Hardware4Nix {

	private static String sn = null;

	public static final String getSerialNumber() {

		if (sn != null) {
			return sn;
		}

		Runtime runtime = Runtime.getRuntime();
		Process process = null;

		try {
            // hal-get-property --udi '/org/freedesktop/Hal/devices/computer' --key system.hardware.serial
			process = runtime.exec(new String[] { "dmidecode", "-t", "system" });
            //process = runtime.exec(new String[] { "hal-get-property", "--udi", "'/org/freedesktop/Hal/devices/computer'",
                                        //"--key", "system.hardware.serial"});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

        try(InputStream is = process.getInputStream();) {
            sn = HashUtils.hashInMD5(IOUtils.toString(is, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}
}

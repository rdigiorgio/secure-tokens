package com.github.sarxos.securetoken.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class Hardware4Nix {

	private static String sn = null;

	public static final String getSerialNumber() {

		if (sn != null) {
			return sn;
		}

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
            // hal-get-property --udi '/org/freedesktop/Hal/devices/computer' --key system.hardware.serial
			//process = runtime.exec(new String[] { "dmidecode", "-t", "system" });
            process = runtime.exec(new String[] { "hal-get-property", "--udi", "'/org/freedesktop/Hal/devices/computer'",
                                        "--key", "system.hardware.serial"});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
                sn = line;
                break;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}
}

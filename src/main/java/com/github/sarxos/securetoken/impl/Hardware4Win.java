package com.github.sarxos.securetoken.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


public class Hardware4Win {

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
			process = runtime.exec(new String[] { "wmic", "bios", "get", "biosversion" });
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

		Scanner sc = new Scanner(is);
		try {
            sn = convertStreamToString(is);
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

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}

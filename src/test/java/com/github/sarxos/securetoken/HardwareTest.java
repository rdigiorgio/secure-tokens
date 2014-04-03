package com.github.sarxos.securetoken;

import junit.framework.Assert;

import org.junit.Test;


public class HardwareTest {

	@Test
	public void test_getSerialNumber() {
		String sn = Hardware.getSerialNumber();
        System.out.println("System SN: " + sn);
        Assert.assertNotNull(sn);

	}

}

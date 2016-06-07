package com.automic;

import org.junit.Test;

public class AECredentialsTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAEHostnameOrIpEmpty() {
		new AECredentials("", 0, 0, "", "", "", 'E');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAECPPort() {
		new AECredentials("test", 0, 0, "", "", "", 'E');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAEClientToConnect() {
		new AECredentials("test", 123, -1, "", "", "", 'E');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAEClientToConnectGranterThan() {
		new AECredentials("test", 123, 10000, "", "", "", 'E');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAEUserLogin() {
		new AECredentials("test", 123, 123, "", "", "", 'E');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAEDepartment() {
		new AECredentials("test", 123, 123, "ccc", "", "", 'E');
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAEUserPassword() {
		new AECredentials("test", 123, 123, "ccc", "asdasd", "", 'E');
	}

	@Test(expected = IllegalArgumentException.class)
	public void AEMessageLanguage() {
		new AECredentials("test", 123, 123, "ccc", "asdasd", "", 'e');
	}
}

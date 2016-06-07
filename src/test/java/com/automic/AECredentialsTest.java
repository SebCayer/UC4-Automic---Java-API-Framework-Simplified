package com.automic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AECredentialsTest {

	/**
	 * Logger.
	 */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Test
	public void aeHostnameOrIpEmptyTest() {
		try {
			new AECredentials("", 0, 0, "", "", "", 'E');
			fail("This test should throw IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AEHostnameOrIp") != -1);
		}

	}

	@Test
	public void aeCPPortTest() {
		try {
			new AECredentials("test", 0, 0, "", "", "", 'E');
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AECPPort") != -1);
		}
	}

	@Test
	public void aeClientToConnectTest() {
		try {
			new AECredentials("test", 123, -1, "", "", "", 'E');
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AEClientToConnect") != -1);
		}
	}

	@Test
	public void aeClientToConnectGranterThanTest() {
		try {
			new AECredentials("test", 123, 10000, "", "", "", 'E');
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AEClientToConnect") != -1);
		}
	}

	@Test
	public void aeUserLoginTest() {
		try {
			new AECredentials("test", 123, 123, "", "", "", 'E');
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AEUserLogin") != -1);
		}
	}

	@Test
	public void aeDepartmentTest() {
		try {
			new AECredentials("test", 123, 123, "ccc", "", "", 'E');
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AEDepartment") != -1);
		}
	}

	@Test
	public void aeUserPasswordTest() {
		try {
			new AECredentials("test", 123, 123, "ccc", "asdasd", "", 'E');
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AEUserPassword") != -1);
		}
	}

	@Test
	public void aeMessageLanguageTest() {
		try {
			new AECredentials("test", 123, 123, "ccc", "asdasd", "asdasd", 'e');
		}
		catch (IllegalArgumentException iae) {
			LOGGER.info(iae.getMessage(), iae);
			assertTrue(iae.getMessage().indexOf("AEMessageLanguage") != -1);
		}
	}
}

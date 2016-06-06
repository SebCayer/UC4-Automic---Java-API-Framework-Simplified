package com.automic;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automic.exception.AutomicAEApiException;
import com.uc4.communication.Connection;
import com.uc4.communication.requests.CreateSession;

public class ConnectionManager {

	/**
	 * Logger.
	 */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private Connection conn = null;

	public ArrayList<Connection> ConnectionList = new ArrayList<Connection>();

	public ConnectionManager() {

	}

	public Connection connectToClient(AECredentials credentials) throws IOException, AutomicAEApiException {

		LOGGER.debug("Authenticating to Client " + credentials.getAEClientToConnect() + " with user " + credentials.getAEUserLogin());
		conn = Connection.open(credentials.getAEHostnameOrIp(), credentials.getAECPPort());

		CreateSession sess = conn.login(credentials.getAEClientToConnect(), credentials.getAEUserLogin(),
				credentials.getAEDepartment(), credentials.getAEUserPassword(), credentials.getAEMessageLanguage());

		if (sess.getMessageBox() != null) {
			throw new AutomicAEApiException(AutomicAEApiException.ERROR_CONNECTION_MESSAGE_BOX, "-- Error: " + sess.getMessageBox());
		}
		// Check Server Version:
		String serverVersion = conn.getSessionInfo().getServerVersion();
		if (!SupportedAEVersions.SupportedVersions.contains(serverVersion)) {
			throw new AutomicAEApiException(AutomicAEApiException.ERROR_CODE_VERSION_NOT_SUPPORTED,
					"Error! Version of the Automation Engine does not seem supported." + " -- current version is: "
							+ serverVersion + " -- versions supported: " + SupportedAEVersions.SupportedVersions.toString());
		}

		ConnectionList.add(conn);
		return conn;

	}

	public Connection switchToClient(AECredentials credentials) throws IOException {
		conn = Connection.open(credentials.getAEHostnameOrIp(), credentials.getAECPPort());
		CreateSession sess = conn.login(credentials.getAEClientToConnect(), credentials.getAEUserLogin(),
				credentials.getAEDepartment(), credentials.getAEUserPassword(), credentials.getAEMessageLanguage());
		ConnectionList.add(conn);
		return conn;
	}

	public Connection switchToExistingClient(String ClientNumber) {
		for (Connection conn : ConnectionList) {
			if (conn.getSessionInfo().getClient().equalsIgnoreCase(ClientNumber)) {
				return conn;
			}
		}
		return null;
	}
}

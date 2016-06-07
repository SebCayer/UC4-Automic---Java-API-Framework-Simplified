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

	public ArrayList<Connection> ConnectionList = new ArrayList<Connection>();

	public ConnectionManager() {

	}

	public Connection connectToClient(AECredentials credentials) throws IOException, AutomicAEApiException {

		LOGGER.debug("Authenticating to Client " + credentials.getAEClientToConnect() + " with user " + credentials.getAEUserLogin());
		Connection conn = openConnection(credentials);

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

	protected Connection openConnection(AECredentials credentials) throws IOException {
		return Connection.open(credentials.getAEHostnameOrIp(), credentials.getAECPPort());
	}

	public Connection switchToClient(AECredentials credentials) throws IOException {
		Connection conn = openConnection(credentials);
		conn.login(credentials.getAEClientToConnect(), credentials.getAEUserLogin(),
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

	public void closeAllConnection() {
		for (Connection conn : ConnectionList) {
			try {
				conn.close();
			}
			catch (IOException e) {
				// Silent exception on close
				LOGGER.warn(e.getMessage(), e);
			}
		}
		ConnectionList.clear();
	}
}

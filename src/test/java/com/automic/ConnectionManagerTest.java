package com.automic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.UnresolvedAddressException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automic.exception.AutomicAEApiException;
import com.uc4.api.MessageBox;
import com.uc4.communication.Connection;
import com.uc4.communication.ConnectionAttributes;
import com.uc4.communication.requests.CreateSession;

public class ConnectionManagerTest {

	/**
	 * Logger.
	 */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Test(expected = UnresolvedAddressException.class)
	public void connectToClientUnresolvedAddressExceptionTest() throws IOException {
		AECredentials credentials = new AECredentials("127asdasdasdsa.0.0.1", 1500, 2217, "UC", "UC", "UC", 'E');
		LOGGER.info("connectToClientUnresolvedAddressExceptionTest with credentials : " + credentials.toString());
		ConnectionManager mgrTarget = new ConnectionManager();
		mgrTarget.openConnection(credentials);
	}

	@Test(expected = ConnectException.class)
	public void connectToClientConnectExceptionTest() throws IOException {
		AECredentials credentials = new AECredentials("127.0.0.1", 1500, 2217, "UC", "UC", "UC", 'E');
		LOGGER.info("connectToClientConnectExceptionTest with credentials : " + credentials.toString());
		ConnectionManager mgrTarget = new ConnectionManager();
		mgrTarget.openConnection(credentials);
	}

	@Test
	public void connectToClientMockedWithMessageBoxTest() throws IOException {
		AECredentials credentials = new AECredentials("127.0.0.1", 1500, 2217, "UC", "UC", "UC", 'E');
		LOGGER.info("connectToClientMockedWithMessageBoxTest with credentials : " + credentials.toString());
		ConnectionManager mgrTarget = new ConnectionManager();
		mgrTarget = spy(mgrTarget);
		Connection conn = mock(Connection.class);
		doReturn(conn).when(mgrTarget).openConnection(credentials);

		MessageBox message = mock(MessageBox.class);
		CreateSession session = new CreateSession(message);

		String messageErreur = "Une erreur a ete retourner par le serveur!";
		when(message.toString()).thenReturn(messageErreur);
		try {
			Connection connection = mgrTarget.openConnection(credentials);
			assertNotNull(connection);
			when(connection.login(credentials.getAEClientToConnect(), credentials.getAEUserLogin(),
					credentials.getAEDepartment(), credentials.getAEUserPassword(), credentials.getAEMessageLanguage()))
							.thenReturn(session);
			LOGGER.info("Call mgrTarget.connectToClient(credentials)");
			mgrTarget.connectToClient(credentials);
			fail("This test should throw AutomicAEApiException");
		}
		catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
			fail("This test should throw AutomicAEApiException but throw an IOException");
		}
		catch (AutomicAEApiException e) {
			LOGGER.info(e.getMessage(), e);
			assertTrue(e.getCode().equals(AutomicAEApiException.ERROR_CONNECTION_MESSAGE_BOX));
		}
		finally {
			mgrTarget.closeAllConnection();
		}
	}

	@Test
	public void connectToClientWrongVersionNotSupportedTest() throws IOException {
		AECredentials credentials = new AECredentials("127.0.0.1", 1500, 2217, "UC", "UC", "UC", 'E');
		LOGGER.info("connectToClientWrongVersionNotSupportedTest with credentials : " + credentials.toString());
		ConnectionManager mgrTarget = new ConnectionManager();
		mgrTarget = spy(mgrTarget);
		Connection conn = mock(Connection.class);
		doReturn(conn).when(mgrTarget).openConnection(credentials);

		CreateSession session = mock(CreateSession.class);
		ConnectionAttributes ca = mock(ConnectionAttributes.class);
		when(ca.getServerVersion()).thenReturn("8.0.0");
		when(conn.getSessionInfo()).thenReturn(ca);
		try {
			Connection connection = mgrTarget.openConnection(credentials);
			assertNotNull(connection);
			when(connection.login(credentials.getAEClientToConnect(), credentials.getAEUserLogin(),
					credentials.getAEDepartment(), credentials.getAEUserPassword(), credentials.getAEMessageLanguage()))
							.thenReturn(session);
			LOGGER.info("Call mgrTarget.connectToClient(credentials)");
			mgrTarget.connectToClient(credentials);
			fail("This test should throw AutomicAEApiException");
		}
		catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
			fail("This test should throw AutomicAEApiException but throw an IOException");
		}
		catch (AutomicAEApiException e) {
			LOGGER.info(e.getMessage(), e);
			assertTrue(e.getCode().equals(AutomicAEApiException.ERROR_CODE_VERSION_NOT_SUPPORTED));
		}
		finally {
			mgrTarget.closeAllConnection();
		}
	}

	@Test
	public void switchToClientTest() throws IOException {
		// Setup for client 2000
		AECredentials credentials = new AECredentials("127.0.0.1", 1500, 2000, "UC", "UC", "UC", 'E');
		ConnectionManager mgrTarget = new ConnectionManager();
		mgrTarget = spy(mgrTarget);
		Connection conn = mock(Connection.class);
		CreateSession session = mock(CreateSession.class);
		ConnectionAttributes ca = mock(ConnectionAttributes.class);
		when(ca.getServerVersion()).thenReturn("11.0");
		when(conn.getSessionInfo()).thenReturn(ca);

		doReturn(conn).when(mgrTarget).openConnection(credentials);

		// Setup for client 2500
		AECredentials credentialsClient2 = new AECredentials("127.0.0.1", 1500, 2500, "UC", "UC", "UC", 'E');
		Connection connClient2 = mock(Connection.class);
		ConnectionAttributes caConnClient2 = mock(ConnectionAttributes.class);
		when(caConnClient2.getClient()).thenReturn(Integer.valueOf(credentialsClient2.getAEClientToConnect()).toString());
		when(connClient2.getSessionInfo()).thenReturn(caConnClient2);
		doReturn(connClient2).when(mgrTarget).openConnection(credentialsClient2);

		try {

			// Run tests
			Connection connection = mgrTarget.openConnection(credentials);
			assertNotNull(connection);
			when(connection.login(credentials.getAEClientToConnect(), credentials.getAEUserLogin(),
					credentials.getAEDepartment(), credentials.getAEUserPassword(), credentials.getAEMessageLanguage()))
							.thenReturn(session);

			LOGGER.info("connectToClient with credentials : " + credentials.toString());
			mgrTarget.connectToClient(credentials);

			LOGGER.info("switchToClient with credentials : " + credentialsClient2.toString());
			Connection connectionExpected = mgrTarget.switchToClient(credentialsClient2);
			assertEquals(connectionExpected.getSessionInfo().getClient(),
					Integer.valueOf(credentialsClient2.getAEClientToConnect()).toString());
		}
		catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
			fail("This test should not throw Exception!");
		}
		catch (AutomicAEApiException e) {
			LOGGER.warn(e.getMessage(), e);
			fail("This test should not throw Exception!");
		}
		finally {
			mgrTarget.closeAllConnection();
		}
	}
}

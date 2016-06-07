package com.automic;

import com.automic.utils.Assert;

public class AECredentials {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + AECPPort;
		result = prime * result + AEClientToConnect;
		result = prime * result + ((AEDepartment == null) ? 0 : AEDepartment.hashCode());
		result = prime * result + ((AEHostnameOrIp == null) ? 0 : AEHostnameOrIp.hashCode());
		result = prime * result + AEMessageLanguage;
		result = prime * result + ((AEUserLogin == null) ? 0 : AEUserLogin.hashCode());
		result = prime * result + ((AEUserPassword == null) ? 0 : AEUserPassword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AECredentials other = (AECredentials) obj;
		if (AECPPort != other.AECPPort) {
			return false;
		}
		if (AEClientToConnect != other.AEClientToConnect) {
			return false;
		}
		if (AEDepartment == null) {
			if (other.AEDepartment != null) {
				return false;
			}
		}
		else if (!AEDepartment.equals(other.AEDepartment)) {
			return false;
		}
		if (AEHostnameOrIp == null) {
			if (other.AEHostnameOrIp != null) {
				return false;
			}
		}
		else if (!AEHostnameOrIp.equals(other.AEHostnameOrIp)) {
			return false;
		}
		if (AEMessageLanguage != other.AEMessageLanguage) {
			return false;
		}
		if (AEUserLogin == null) {
			if (other.AEUserLogin != null) {
				return false;
			}
		}
		else if (!AEUserLogin.equals(other.AEUserLogin)) {
			return false;
		}
		if (AEUserPassword == null) {
			if (other.AEUserPassword != null) {
				return false;
			}
		}
		else if (!AEUserPassword.equals(other.AEUserPassword)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AECredentials [AEHostnameOrIp=" + AEHostnameOrIp + ", AECPPort=" + AECPPort + ", AEClientToConnect=" + AEClientToConnect
				+ ", AEDepartment=" + AEDepartment + ", AEUserLogin=" + AEUserLogin + ", AEUserPassword=" + AEUserPassword
				+ ", AEMessageLanguage=" + AEMessageLanguage + "]";
	}

	/**
	 * !!! Change the credentials below to your own system !!!
	 **/

	private String AEHostnameOrIp; // Automation Engine IP Adr
	private int AECPPort; // "Primary" Communication Process port
	private int AEClientToConnect; // AE Client Number (0 - 9999)
	private String AEDepartment; // User Department
	private String AEUserLogin; // AE User Login
	private String AEUserPassword; // AE User Password
	private char AEMessageLanguage; // Language: 'E' or 'D', or 'F'

	public AECredentials(String AEHostnameOrIp, int AECPPort, int AEClientToConnect, String AEUserLogin, String AEDepartment,
			String AEUserPassword, char AEMessageLanguage) {
		Assert.hasLength(AEHostnameOrIp, "AEHostnameOrIp cannot be null or empty!");
		Assert.isTrue(AECPPort > 0, "AECPPort has to be bigger than 0!");
		Assert.isTrue(AEClientToConnect >= 0 && AEClientToConnect <= 9999,
				"AEClientToConnect has to be egal or bigger than 0 and lower than and egal to 9999!");
		Assert.hasLength(AEUserLogin, "AEUserLogin cannot be null or empty!");
		Assert.hasLength(AEDepartment, "AEDepartment cannot be null or empty!");
		Assert.hasLength(AEUserPassword, "AEUserPassword cannot be null or empty!");
		Assert.isTrue((AEMessageLanguage == 'E' || AEMessageLanguage == 'D' || AEMessageLanguage == 'F'),
				"AEMessageLanguage : 'E' or 'D', or 'F'!");

		this.AEHostnameOrIp = AEHostnameOrIp;
		this.AECPPort = AECPPort;
		this.AEClientToConnect = AEClientToConnect;
		this.AEDepartment = AEDepartment;
		this.AEUserLogin = AEUserLogin;
		this.AEUserPassword = AEUserPassword;
		this.AEMessageLanguage = AEMessageLanguage;

	}

	public void setAEHostnameOrIp(String aEHostnameOrIp) {
		AEHostnameOrIp = aEHostnameOrIp;
	}

	public void setAECPPort(int aECPPort) {
		AECPPort = aECPPort;
	}

	public void setAEClientToConnect(int aEClientToConnect) {
		AEClientToConnect = aEClientToConnect;
	}

	public void setAEDepartment(String aEDepartment) {
		AEDepartment = aEDepartment;
	}

	public void setAEUserLogin(String aEUserLogin) {
		AEUserLogin = aEUserLogin;
	}

	public void setAEUserPassword(String aEUserPassword) {
		AEUserPassword = aEUserPassword;
	}

	public void setAEMessageLanguage(char aEMessageLanguage) {
		AEMessageLanguage = aEMessageLanguage;
	}

	public String getAEHostnameOrIp() {
		return AEHostnameOrIp;
	}

	public int getAECPPort() {
		return AECPPort;
	}

	public int getAEClientToConnect() {
		return AEClientToConnect;
	}

	public String getAEDepartment() {
		return AEDepartment;
	}

	public String getAEUserLogin() {
		return AEUserLogin;
	}

	public String getAEUserPassword() {
		return AEUserPassword;
	}

	public char getAEMessageLanguage() {
		return AEMessageLanguage;
	}
}

package de.klimsa.rico.maven.test;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class Connector {

	private final static Logger LOGGER = Logger.getLogger(Connector.class.getName());

	private Session session;

	private Store store;

	public Store login(final String provider,final String username, final char[] password) throws MessagingException {

		LOGGER.info("Attempt to log in with username: " + username + " on server: " + ServerCache.getInstance().getImapServer(provider));
		return this.login(this.createProtocolProperties(), provider, username, password);
	}

	public void logout() {
		// TODO Auto-generated method stub

	}

	private Store login(final Properties properties,final String provider, final String username, final char[] password) throws MessagingException {
		this.session = Session.getInstance(properties, null);
		this.store = this.session.getStore();
		this.store.connect(ServerCache.getInstance().getImapServer(provider),username, new String(password));
		return this.store;
	}

	private Properties createProtocolProperties() {
		final Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		return properties;
	}

	public boolean isLoggedIn() {
		return this.store.isConnected();
	}
}

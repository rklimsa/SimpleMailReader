/**
 *
 */
package de.klimsa.rico.maven.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rico Klimsa
 *
 */
public class ServerCache {

	private final Map<String, String> mailRecieverCache_IMAP = new HashMap<>();

	private final Map<String, String> mailSenderCache_SMTP = new HashMap<>();

	private static ServerCache instance;

	private ServerCache() {
		this.mailRecieverCache_IMAP.put("AOL", "imap.de.aol.com");
		this.mailRecieverCache_IMAP.put("GMX", "imap.gmx.net");
	}

	public static ServerCache getInstance() {
		if(instance == null) {
			instance = new ServerCache();
		}
		return instance;
	}

	public String getImapServer(final String provider) {
		return this.mailRecieverCache_IMAP.getOrDefault(provider, "");
	}

	public String getSmtpServer(final String provider) {
		return this.mailSenderCache_SMTP.getOrDefault(provider, "");
	}

}

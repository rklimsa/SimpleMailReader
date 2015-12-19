/**
 *
 */
package de.klimsa.rico.maven.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author Rico Klimsa
 *
 */
public class ApplicationDelegator implements Callback {

	private final static Logger LOGGER = Logger.getLogger(ApplicationDelegator.class.getName());

	private final Connector connector = new Connector();
	private final MailHandler mailHandler = new MailHandler();

	private ILoginWindow loginWindow;

	private IMailWindow mailWindow;

	public void setLoginWindow(final ILoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}

	public void setMailWindow(final IMailWindow mailWindow) {
		this.mailWindow = mailWindow;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.klimsa.rico.maven.test.Callback#login(java.lang.String,
	 * java.lang.String, char[])
	 */
	@Override
	public void login(final String provider, final String username, final char[] password) {
		try {
			this.mailHandler.setStore(this.connector.login(provider, username, password));
			if(this.connector.isLoggedIn()) {
				this.mailWindow = new MailWindow(this, provider);
				this.mailWindow.init(new Properties());
				this.mailWindow.upateFolders(this.folderArrayToStringList(this.mailHandler.getAllFolders()));
				this.mailWindow.updateMails(this.messageListToStringList(this.mailHandler.getMailsFromFolder("INBOX")));

			}
		} catch (final MessagingException | IOException e) {
			LOGGER.severe("Error occured: " + e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.klimsa.rico.maven.test.Callback#logout()
	 */
	@Override
	public void logout() {
		this.connector.logout();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.klimsa.rico.maven.test.Callback#getAllMails()
	 */
	@Override
	public void getAllMails() {
		try {
			this.mailWindow.updateMails(this.messageListToStringList(this.mailHandler.getAllMails()));
		} catch (final MessagingException | IOException e) {
			LOGGER.severe("Error occured: " + e.toString());
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.klimsa.rico.maven.test.Callback#searchMails(java.lang.String)
	 */
	@Override
	public void searchMails(final String searchString) {
		try {
			this.mailWindow.updateMails(this.messageListToStringList(this.mailHandler.searchMails(searchString)));
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.klimsa.rico.maven.test.Callback#sendMail(java.lang.String,
	 * java.lang.String, javax.activation.DataSource)
	 */
	@Override
	public void sendMail(final String to, final String content, final DataSource attachment) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.klimsa.rico.maven.test.Callback#openMail(int)
	 */
	@Override
	public void openMail(final int index, final String folderName) {
		try {
			LOGGER.info("open mail");
			this.mailWindow.displayMailContent(this.getContent(this.mailHandler.getMailsFromFolder(folderName).get(index)));
		} catch (final MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void getAllFolders() {
		try {
			this.mailWindow.upateFolders(this.folderArrayToStringList(this.mailHandler.getAllFolders()));
		} catch (final MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private List<List<String>> messageListToStringList(final List<Message> messageList)
			throws MessagingException, IOException {
		final List<List<String>> mails = new ArrayList<>();

		for (final Message message : messageList) {
			final List<String> list = new ArrayList<>();
			list.add(message.getSentDate().toString());
			// TODO Refactor
			list.add(message.getFrom()[0].toString());
			list.add(message.getSubject());
			list.add(this.getContent(message));

			mails.add(list);
		}
		return mails;
	}

	private List<String> folderArrayToStringList(final Folder[] folders) {
		final List<String> folderNames = new ArrayList<>();
		if(folders != null) {
			for(final Folder folder : folders) {
				folderNames.add(folder.getName());
			}

		}
		return folderNames;
	}

	@Override
	public void getMailsFromFolder(final String folderName) {
		try {
			this.mailWindow.updateMails(this.messageListToStringList(this.mailHandler.getMailsFromFolder(folderName)));
		} catch (final MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getContent(final Message message) throws MessagingException, IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		message.writeTo(byteArrayOutputStream);
		return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());	}

}

/**
 *
 */
package de.klimsa.rico.maven.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

/**
 * @author Rico Klimsa
 *
 */
public class MailHandler {

	private Store store;

	private Folder[] folders;

	public Store getStore() {
		return this.store;
	}

	public void setStore(final Store store) {
		this.store = store;
	}

	public List<Message> getAllMails() throws MessagingException {
		final List<Message> messageList = new ArrayList<>();

		for(final Folder folder : this.folders) {
			messageList.addAll(Arrays.asList(folder.getMessages()));
		}

		return messageList;
	}

	public List<Message>  searchMails(final String searchString) throws MessagingException {
		final List<Message> messageList = new ArrayList<>();

		this.searchMessages(messageList, new FromStringTerm(searchString));
		this.searchMessages(messageList, new SubjectTerm(searchString));

		return messageList;
	}

	public void sendMail(final String to, final String content, final DataSource attachment) {
		// TODO Auto-generated method stub

	}

	public void openMail(final int index) {
		// TODO Auto-generated method stub

	}

	public List<Message> getMailsFromFolder(final String folderName) throws MessagingException {
		final List<Message> mails = new ArrayList<>();
		for(final Folder folder : this.getAllFolders()) {
			if(folder.getName().equalsIgnoreCase(folderName)) {
				if(!folder.isOpen()) {
					folder.open(Folder.READ_ONLY);
				}
				mails.addAll(Arrays.asList(folder.getMessages()));
				//				folder.close(true);
			}
		}
		return mails;
	}

	public Folder[] getAllFolders() throws MessagingException {
		if(this.folders == null) {
			this.folders = this.store.getDefaultFolder().list();
		}
		return this.folders;
	}

	private void searchMessages(final List<Message> messageList, final SearchTerm searchTerm) throws MessagingException {
		for(final Folder folder : this.getAllFolders()) {
			messageList.addAll(Arrays.asList(folder.search(searchTerm)));
		}
	}
}

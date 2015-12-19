/**
 *
 */
package de.klimsa.rico.maven.test;

import javax.activation.DataSource;

/**
 * @author Rico Klimsa
 *
 */
public interface Callback extends Loginable {

	void getAllMails();

	void searchMails(String searchString);

	void sendMail(String to, String content, DataSource attachment);

	void openMail(int index, String folderName);

	void getAllFolders();

	void getMailsFromFolder(String folderName);
}

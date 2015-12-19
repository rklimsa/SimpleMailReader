package de.klimsa.rico.maven.test;

import java.util.List;
import java.util.Properties;

public interface IMailWindow {

	void init(Properties properties);

	void updateMails(List< List <String>> mails);

	void setActualProvider(String actualProvider);

	void upateFolders(final List<String> folders);

	void displayMailContent(final String content);

}

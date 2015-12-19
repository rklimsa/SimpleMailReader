package de.klimsa.rico.maven.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

	private final ApplicationDelegator callback = new ApplicationDelegator();
	private ILoginWindow loginWindow;

	public static void main(final String[] args) {

		final Properties properties = new Properties();

		final Main main = new Main();
		main.loginWindow = new LoginWindow(main.callback,"AOL","GMX");
		main.loginWindow.init(properties);
		main.callback.setLoginWindow(main.loginWindow);

	}


}

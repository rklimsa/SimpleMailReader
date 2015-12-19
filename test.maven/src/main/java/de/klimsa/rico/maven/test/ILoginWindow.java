package de.klimsa.rico.maven.test;

import java.util.Properties;

public interface ILoginWindow {

	void init(Properties properties);

	void setProviders(String... providers);

}

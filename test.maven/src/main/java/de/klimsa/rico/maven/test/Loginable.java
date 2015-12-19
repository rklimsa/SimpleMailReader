package de.klimsa.rico.maven.test;

public interface Loginable {

	void login(String provider, String username, char[] password);

	void logout();


}

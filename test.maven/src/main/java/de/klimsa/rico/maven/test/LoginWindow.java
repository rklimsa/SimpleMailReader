package de.klimsa.rico.maven.test;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow implements ILoginWindow {

	private final JFrame mainFrame = new JFrame("SimpleMailReader - Login");
	private String[] providers = null;

	private final JPanel providerPanel = new JPanel(true);
	private final JPanel credentialPanel = new JPanel(true);
	private final JPanel buttonPanel = new JPanel(true);

	private final JComboBox<String> providerBox;
	private final JLabel userLabel = new JLabel();
	private final JLabel passLabel = new JLabel();
	private final JTextField userField = new JTextField(50);
	private final JPasswordField passField = new JPasswordField(50);

	private final JButton submit = new JButton();

	private final JPanel contentPane = new JPanel(true);

	private final Callback callback;

	public LoginWindow(final Callback callback,final String... providers) {
		this.callback = callback;
		this.setProviders(providers);
		this.providerBox = new JComboBox<>(this.providers);
	}

	@Override
	public void setProviders(final String... providers) {
		this.providers = providers;
	}

	@Override
	public void init(final Properties properties) {
		this.userLabel.setText(properties.getProperty("userLabel", "Benutzername oder EMail-Adresse:"));
		this.passLabel.setText(properties.getProperty("passLabel", "Passwort:"));
		this.submit.setText(properties.getProperty("submit","Einloggen"));

		this.providerPanel.add(this.providerBox);

		this.credentialPanel.setLayout(new GridLayout(2, 2));
		this.credentialPanel.add(this.userLabel);
		this.credentialPanel.add(this.userField);
		this.credentialPanel.add(this.passLabel);
		this.credentialPanel.add(this.passField);

		this.submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent ae) {
				LoginWindow.this.callback.login((String)LoginWindow.this.providerBox.getSelectedItem(),LoginWindow.this.userField.getText(), LoginWindow.this.passField.getPassword());
			}
		});

		this.buttonPanel.add(this.submit);

		this.contentPane.setLayout(new GridBagLayout());

		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		this.contentPane.add(this.providerPanel,gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;

		this.contentPane.add(this.credentialPanel,gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;

		this.contentPane.add(this.buttonPanel,gridBagConstraints);

		this.mainFrame.setPreferredSize(new Dimension(800,600));
		this.mainFrame.setResizable(false);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setContentPane(this.contentPane);
		this.mainFrame.pack();
		this.mainFrame.setVisible(true);

	}
}

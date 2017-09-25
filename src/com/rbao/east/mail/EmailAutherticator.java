package com.rbao.east.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAutherticator extends Authenticator {
	private String username = "eastcang@126.com";

	private String password = "25672567";

	public EmailAutherticator() {
		super();
	}

	public EmailAutherticator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
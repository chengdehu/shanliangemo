package com.etc.app;

import com.etc.entity.Entertainment;
import com.etc.entity.User;

import android.app.Application;

public class MyApp extends Application {
	private User user;

	private Entertainment entertainment;
	
	public Entertainment getEntertainment() {
		return entertainment;
	}

	public void setEntertainment(Entertainment entertainment) {
		this.entertainment = entertainment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}

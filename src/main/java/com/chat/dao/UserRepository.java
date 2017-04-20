package com.chat.dao;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/**
 * This repo keeps the active online users in the chat
 *
 * @author gervasio.amy
 */
@Component
public class UserRepository {

	private List<String> activeUsers = new CopyOnWriteArrayList<String>();

	public boolean add(String username) {
		return activeUsers.add(username);
	}

	public boolean isUserPresent(String username) {
		return activeUsers.contains(username);
	}

	public boolean removeUser(String username) {
		return activeUsers.remove(username);
	}

	public List<String> getActiveUsers() {
		return activeUsers;
	}

}

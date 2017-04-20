package com.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.chat.dao.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	private static final String LOGIN_DESTINATION = "/topic/chat.login";
	private static final String LOGOUT_DESTINATION = "/topic/chat.logout";

	/**
	 * It adds a username to the repo and send a message to <code>/topic/chat.login</code>
	 * 
	 * @param username the username to be added
	 */
	public void addUser(String username) {
		if (userRepo.add(username)) {
			messagingTemplate.convertAndSend(LOGIN_DESTINATION, username);
		}
	}

	public boolean isUserPresent(String username) {
		return userRepo.isUserPresent(username);
	}

	public void removeUser(String username) {
		if (userRepo.removeUser(username)) {
			messagingTemplate.convertAndSend(LOGOUT_DESTINATION, username);
		}
	}

	public List<String> getActiveUsers() {
		return userRepo.getActiveUsers();
	}
}

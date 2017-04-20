package com.chat.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class CustomUsernameHandshakeHandler extends DefaultHandshakeHandler {
	
	private int count = 0;

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		//TODO how to get the username?? the one used at connect time
		String username = "user-" + count;
		count++;
		//return new UsernamePasswordAuthenticationToken(username, null);
		return new Principal() {
			
			@Override
			public String getName() {
				return username;
			}
		};
	}
}

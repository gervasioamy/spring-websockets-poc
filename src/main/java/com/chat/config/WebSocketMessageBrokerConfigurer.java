package com.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * WebSockets configuration.
 * The application destination prefix is set to <code>/app</code>. This means WebSocket mappings are prefixed with 
 * <code>/app</code> to obtain the real value.
 * 
 * The endpoint <code>/chat</code> is registered for starting the WebSocket protocol handshake.
 * 
 * @author gervasio.amy
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfigurer extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		/* 
		 * why "app" prefix? >> It’s simply meant to differentiate messages to be routed to message-handling methods to do 
		 * application work vs messages to be routed to the broker to broadcast to subscribed clients. 
		 */
		config.setApplicationDestinationPrefixes("/app");
		/*
		 * "/topic" is used as a prefix for destinations with pub-sub semantics and 
		 * "/queue" for destinations with point-to-point semantics.
		 * Note: simple broker works only for testing and small applications, in 
		 * case of need more power use an external message broker (Rabbit / ActiveMQ)
		 */
	    config.enableSimpleBroker("/topic", "/queue");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry.addEndpoint("/chat")
	    		//.setHandshakeHandler(new CustomUsernameHandshakeHandler())
	    		.setAllowedOrigins("*")
	    		.withSockJS();
	}

}

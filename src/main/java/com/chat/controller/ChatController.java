package com.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.domain.Message;
import com.chat.domain.OutputMessage;
import com.chat.service.UserService;

/**
 * Controller to process the chat messages
 * 
 * @author gervasio.amy
 */
@Controller
public class ChatController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * The Message Controller method accepts a POJO into which a message is de-serialized from JSON. 
	 * 
	 * @param topic The @DestinationVariable annotation is used to capture the template variable topic from the destination. 
	 * @param message the received {@link Message}
	 * @return To send responses back to the client, it uses a @SendTo annotation specifying the client-side queue to which the response will be sent. 
	 * The value returned from the method is serialized to JSON and sent to the specified destination. In our case, we define the response message as follows.
	 * @throws Exception
	 */
	@MessageMapping("/chat/{topic}")
	@SendTo("/topic/{topic}/messages")
	public OutputMessage send(@DestinationVariable("topic") String topic, Message message) throws Exception {
	    return new OutputMessage(message.getFrom(), message.getText(), topic);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> getActiveUsers() {
		return userService.getActiveUsers();
	}


}

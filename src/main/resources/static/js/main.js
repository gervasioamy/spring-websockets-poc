/**
 * The application uses the SocksJS library for dealing with the WebSocket connection, and the stomp-websocket library for the STOMP support. 
 */

/* the STOMP client */
var stompClient;

var username;

//TODO use handlebars or any templating tool
var activeUserLiElement = 
	"<li class='left clearfix'>" +
	"  <span class='chat-img pull-left'><img src='{{img}}' class='img-circle'></span>" +
	"  <div class='chat-body clearfix'>" +
	"    <div class='header_sec'>" +
	"      <strong class='primary-font'>{{name}}</strong> " +
	//"      <strong class="pull-right"> 09:45AM</strong>" +
	"    </div>" +
	"  </div>" +
	"</li>";

$(function() {
	'use strict';
	
	doConnect();
	
	loadUsers();


	$("form").on('submit', function(e) {
		e.preventDefault();
	});

	/*
	$('#from').on('blur change keyup', function(ev) {
		$('#connect').prop('disabled', $(this).val().length == 0);
	});
	$('#connect,#disconnect,#text').prop('disabled', true);
	*/

	//$('#connect').click(function() {
	
    function doConnect() {
		/* The connection code below creates a WebSocket channel and uses it to create the STOMP client. 
		 * Note that the host is not specified due to we are running in the same server */
		stompClient = Stomp.over(new SockJS('/chat'));
		var headers = { }
		stompClient.connect(headers, function(frame) {
			//setConnected(true);
			
		    username = frame.headers['user-name'];
			
			/* Once the WebSocket is connected, a callback is registered for push messages from the server. */
			stompClient.subscribe('/topic/sports/messages', function(message) {
				/* The message is unmarshalled from JSON and displayed to the user. */
				showSportsMessage(JSON.parse(message.body));
				
			});
			stompClient.subscribe('/topic/travel/messages', function(message) {
				/* The message is unmarshalled from JSON and displayed to the user. */
				showTravelMessage(JSON.parse(message.body));
				
			});
			
			// user login
			stompClient.subscribe("/topic/chat.login", function(message) {
				addActiveUser(message.body);
			});
			
			// user loguot
			stompClient.subscribe("/topic/chat.logout", function(message) {
				removeActiveUser(message.body);
			});
			
			/* now for private chats. */
			stompClient.subscribe('/user/queue/private', function(message) {
				/* The message is unmarshalled from JSON and displayed to the user. 
				 * message to this queue should be sent like this: stompClient.send("/user/{name}/queue/private", {}, {payload})  */
				//showMessage(JSON.parse(message.body));
				alert("Private message received!: " + message.body);
			});
			
		});
	};
	
	/**
	 * Sends a GET call to /users and then adds a <li> element for each user to 
	 * @returns
	 */
	function loadUsers() {
		$.get("/users").done(function(users) {
			for (let user of users) {
				addActiveUser(user);
			}
		});
	}	

	$('#send').click(function() {
		var topic = $('#topic').val();
		var payload = JSON.stringify({
			from : username,
			text : $('#text').val(),
		});
		var destination = "/app/chat/" + topic;
		var headers = {};
		stompClient.send(destination, headers, payload);
		$('#text').val(""); //clear the chat input
	});
	
	
	$('#disconnect').click(function() {
		if (stompClient != null) {
			stompClient.disconnect();
			setConnected(false);
		}
		stompClient = null;
	});

	
	/**
	 * Formats a timestamp (to be shown in the chat)
	 * 
	 * @param timestamp the timestamp given
	 * @returns the time in format: hh:mm:ss
	 */
	function formatDate(timestamp) {
		var d = new Date(timestamp);
		//var dateStr = d.getDate()  + "-" + (d.getMonth()+1) + "-" + d.getFullYear() + " " +
		//		d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
		var dateStr = d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
		return dateStr;
	}

	function showSportsMessage(mesg) {
		$('#sportsMessages').append(
				'<tr> <td>' + mesg.from + '</td> <td><small>' + formatDate(mesg.time) + '</small></td> <td>' + mesg.message + '</td> </tr>');
	}
	
	function showTravelMessage(mesg) {
		$('#travelMessages').append(
				'<tr> <td>' + mesg.from + '</td> <td><small>' + formatDate(mesg.time) + '</small></td> <td>' + mesg.message + '</td> </tr>');
	}
	
	/**
	 * Adds a li element to activeUsers list and sets the username as the element id.
	 * 
	 * @param username the username recently joined the chat
	 */
	function addActiveUser(username) {
		//$('#activeUsers').append("<li id='" + username + "' class='list-group-item'>" + username + '</li>');
		let replaced = activeUserLiElement.replace("{{name}}", username);
		let randomImageUrl = generateRandomFaceImage();
		replaced = replaced.replace("{{img}}", randomImageUrl);
		$('#activeUsers').append(replaced);
	}
	
	function removeActiveUser(username) {
		$('#' + username).remove();
	}
	
	//TODO move this to backend
	function generateRandomFaceImage() {
		let imgNum = randomIntFromInterval(0,99);
		let isMale = randomIntFromInterval(0,1);
		let gender = isMale?'men':'women';
		return "https://randomuser.me/api/portraits/med/" + gender + "/" + imgNum + ".jpg";
		// https://randomuser.me/api/portraits/med/women/83.jpg
	}
	
	function randomIntFromInterval(min,max) {
	    return Math.floor(Math.random()*(max-min+1)+min);
	}
	
});